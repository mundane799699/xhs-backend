#!/usr/bin/env bash

# 默认部署 mail/target/mail-0.0.1-SNAPSHOT.jar：
#   ./deploy-mail.sh
# 可通过 XHS_MAIL_LOCAL_JAR、XHS_MAIL_DEPLOY_TARGET、XHS_MAIL_REMOTE_DIR、
# XHS_MAIL_HEALTH_URL 和 XHS_MAIL_STARTUP_TIMEOUT 覆盖默认配置。

set -Eeuo pipefail

SCRIPT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"

LOCAL_JAR="${XHS_MAIL_LOCAL_JAR:-${SCRIPT_DIR}/mail/target/mail-0.0.1-SNAPSHOT.jar}"
DEPLOY_TARGET="${XHS_MAIL_DEPLOY_TARGET:-root@118.25.22.240}"
REMOTE_DIR="${XHS_MAIL_REMOTE_DIR:-/home/springboot}"
JAR_NAME="mail-0.0.1-SNAPSHOT.jar"
HEALTH_URL="${XHS_MAIL_HEALTH_URL:-http://127.0.0.1:8088/mail/user/userInfo}"
STARTUP_TIMEOUT="${XHS_MAIL_STARTUP_TIMEOUT:-60}"
HEALTH_URL_PATTERN='^http://127\.0\.0\.1:[0-9]+/[A-Za-z0-9._/?&=-]+$'

log() {
  printf '[deploy-mail] %s\n' "$*"
}

fail() {
  printf '[deploy-mail] ERROR: %s\n' "$*" >&2
  exit 1
}

command -v ssh >/dev/null 2>&1 || fail "未找到 ssh"
command -v scp >/dev/null 2>&1 || fail "未找到 scp"
[[ -s "$LOCAL_JAR" ]] || fail "JAR 不存在或为空：${LOCAL_JAR}"
[[ "$STARTUP_TIMEOUT" =~ ^[1-9][0-9]*$ ]] || fail "XHS_MAIL_STARTUP_TIMEOUT 必须是正整数"
[[ "$DEPLOY_TARGET" =~ ^[A-Za-z0-9._-]+@[A-Za-z0-9._:-]+$ ]] || fail "部署目标不合法：${DEPLOY_TARGET}"
[[ "$REMOTE_DIR" =~ ^/[A-Za-z0-9._/-]+$ ]] || fail "远程目录包含不支持的字符：${REMOTE_DIR}"
[[ "$HEALTH_URL" =~ $HEALTH_URL_PATTERN ]] || fail "健康检查地址不合法：${HEALTH_URL}"

if command -v jar >/dev/null 2>&1; then
  jar tf "$LOCAL_JAR" >/dev/null || fail "JAR 文件校验失败：${LOCAL_JAR}"
fi

upload_suffix="$(date +%Y%m%d%H%M%S).$$"
remote_upload="${REMOTE_DIR}/.${JAR_NAME}.upload.${upload_suffix}"
printf -v remote_dir_quoted '%q' "$REMOTE_DIR"
printf -v jar_name_quoted '%q' "$JAR_NAME"
printf -v remote_upload_quoted '%q' "$remote_upload"
printf -v health_url_quoted '%q' "$HEALTH_URL"
printf -v startup_timeout_quoted '%q' "$STARTUP_TIMEOUT"

log "本地 JAR：${LOCAL_JAR}"
log "部署目标：${DEPLOY_TARGET}:${REMOTE_DIR}/${JAR_NAME}"

ssh "$DEPLOY_TARGET" "mkdir -p -- ${remote_dir_quoted}"
log "上传 JAR"
scp "$LOCAL_JAR" "${DEPLOY_TARGET}:${remote_upload}"

log "停止旧进程并启动新版本"
ssh "$DEPLOY_TARGET" "bash -s -- ${remote_dir_quoted} ${jar_name_quoted} ${remote_upload_quoted} ${health_url_quoted} ${startup_timeout_quoted}" <<'REMOTE_SCRIPT'
set -Eeuo pipefail

REMOTE_DIR="$1"
JAR_NAME="$2"
UPLOAD_PATH="$3"
HEALTH_URL="$4"
STARTUP_TIMEOUT="$5"

cd "$REMOTE_DIR"

remote_log() {
  printf '[deploy-mail][remote] %s\n' "$*"
}

find_mail_pids() {
  local proc proc_cwd
  local -a args

  for proc in /proc/[0-9]*; do
    [[ -r "${proc}/cmdline" ]] || continue
    proc_cwd="$(readlink -f "${proc}/cwd" 2>/dev/null || true)"
    [[ "$proc_cwd" == "$REMOTE_DIR" ]] || continue

    args=()
    mapfile -d '' -t args < "${proc}/cmdline" || true
    [[ "${#args[@]}" -ge 3 ]] || continue
    [[ "${args[0]##*/}" == "java" ]] || continue
    [[ "${args[1]}" == "-jar" ]] || continue
    [[ "${args[2]##*/}" == "$JAR_NAME" ]] || continue

    printf '%s\n' "${proc##*/}"
  done
}

stop_pids() {
  local -a pids=("$@")
  local -a alive
  local pid attempt

  [[ "${#pids[@]}" -gt 0 ]] || return 0
  remote_log "停止进程：${pids[*]}"
  kill "${pids[@]}" 2>/dev/null || true

  for ((attempt = 1; attempt <= 30; attempt++)); do
    alive=()
    for pid in "${pids[@]}"; do
      if kill -0 "$pid" 2>/dev/null; then
        alive+=("$pid")
      fi
    done
    [[ "${#alive[@]}" -eq 0 ]] && return 0
    sleep 1
  done

  remote_log "进程未在 30 秒内退出，强制停止：${alive[*]}"
  kill -KILL "${alive[@]}" 2>/dev/null || true
}

start_app() {
  nohup java -jar "$JAR_NAME" > mail.log 2>&1 < /dev/null &
  APP_PID=$!
  printf '%s\n' "$APP_PID" > mail.pid
  remote_log "已启动进程 ${APP_PID}"
}

wait_until_healthy() {
  local pid="$1"
  local timeout="$2"
  local deadline=$((SECONDS + timeout))

  while ((SECONDS < deadline)); do
    kill -0 "$pid" 2>/dev/null || return 1
    if curl --silent --show-error --fail --max-time 2 "$HEALTH_URL" >/dev/null 2>&1; then
      return 0
    fi
    sleep 1
  done
  return 1
}

[[ -s "$UPLOAD_PATH" ]] || {
  remote_log "上传文件不存在或为空：${UPLOAD_PATH}"
  exit 1
}
command -v java >/dev/null 2>&1 || {
  remote_log "服务器未找到 java"
  exit 1
}
command -v curl >/dev/null 2>&1 || {
  remote_log "服务器未找到 curl"
  exit 1
}

timestamp="$(date +%Y%m%d%H%M%S)"
backup_path="${JAR_NAME}.bak.${timestamp}"
had_previous=false

if [[ -f "$JAR_NAME" ]]; then
  cp -p -- "$JAR_NAME" "$backup_path"
  had_previous=true
  remote_log "已备份旧 JAR：${backup_path}"
fi

mapfile -t old_pids < <(find_mail_pids)
stop_pids "${old_pids[@]}"

if [[ -f mail.log ]]; then
  mv -- mail.log "mail.log.${timestamp}"
  remote_log "已归档旧日志：mail.log.${timestamp}"
fi

mv -f -- "$UPLOAD_PATH" "$JAR_NAME"
start_app

if wait_until_healthy "$APP_PID" "$STARTUP_TIMEOUT"; then
  remote_log "健康检查通过：${HEALTH_URL}"
  exit 0
fi

remote_log "新版本未在 ${STARTUP_TIMEOUT} 秒内通过健康检查"
tail -n 80 mail.log >&2 || true
stop_pids "$APP_PID"

if [[ "$had_previous" == true ]]; then
  mv -- mail.log "mail.log.failed.${timestamp}" 2>/dev/null || true
  cp -p -- "$backup_path" "$JAR_NAME"
  remote_log "已恢复旧 JAR，正在重新启动"
  start_app
  if wait_until_healthy "$APP_PID" "$STARTUP_TIMEOUT"; then
    remote_log "旧版本恢复成功"
  else
    remote_log "旧版本恢复后仍未通过健康检查，请查看 ${REMOTE_DIR}/mail.log"
  fi
else
  remote_log "服务器没有可回滚的旧 JAR"
fi

exit 1
REMOTE_SCRIPT

log "部署完成"
