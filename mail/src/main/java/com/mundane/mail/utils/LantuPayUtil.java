package com.mundane.mail.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LantuPayUtil {
    public static final String mchId = "1676302952";
    public static final String notifyUrl = "https://mundane.ink/mail/wxpay/notify";
//    public static final String notifyUrl = "https://www.weixin.qq.com/wxpay/pay.php";
    public static final String key = "7df816af051095f6780fa3eeade1838d";

    public static String createQrCodeUrl(String out_trade_no, String total_fee, String body) {
        Long stime = System.currentTimeMillis() / 1000;
        //当前时间戳
        String timestamp = String.valueOf(stime);
        Map<String, Object> map = new HashMap<>();
        map.put("mch_id", mchId);
        map.put("out_trade_no", out_trade_no);
        map.put("total_fee", total_fee);
        map.put("body", body);
        map.put("notify_url", notifyUrl);
        map.put("timestamp", timestamp);
        String sign = PaySignUtil.createSign(map, key);
        map.put("sign", sign);
        //微信扫码支付接口地址
        String url = "https://api.ltzf.cn/api/wxpay/native";
        String result = HttpRequest.post(url).form(map).execute().body();
        if (StrUtil.isBlank(result)) {
            String msg = "API接口返回为空，请联系客服";
            log.info(msg);
            throw new RuntimeException(msg);
        }
        JSONObject jsonObject = new JSONObject(result);
        Integer code = jsonObject.getInt("code");
        if (code != 0) {
            String msgstr = jsonObject.getStr("msg");
            log.info("code = {}， msg = {}", code, msgstr);
            throw new RuntimeException(msgstr);
        }
        String qrcodeUrl = jsonObject.getJSONObject("data").getStr("QRcode_url");
        return qrcodeUrl;
    }

    public static JSONObject getPayOrder(String out_trade_no) {
        Long stime = System.currentTimeMillis() / 1000;
        //当前时间戳
        String timestamp = String.valueOf(stime);
        Map<String, Object> map = new HashMap<>();
        map.put("mch_id", mchId);
        map.put("out_trade_no", out_trade_no);
        map.put("timestamp", timestamp);
        String sign = PaySignUtil.createSign(map, key);
        map.put("sign", sign);
        //微信扫码支付接口地址
        String url = "https://api.ltzf.cn/api/wxpay/get_pay_order";
        String result = HttpRequest.post(url).form(map).execute().body();
        if (StrUtil.isBlank(result)) {
            String msg = "API接口返回为空，请联系客服";
            log.info(msg);
            throw new RuntimeException(msg);
        }
        JSONObject jsonObject = new JSONObject(result);
        Integer code = jsonObject.getInt("code");
        if (code != 0) {
            String msgstr = jsonObject.getStr("msg");
            log.info("code = {}， msg = {}", code, msgstr);
            throw new RuntimeException(msgstr);
        }
        JSONObject data = jsonObject.getJSONObject("data");
        return data;
    }

    public static void main(String[] args) {
        //商户订单号，只能是数字、大小写字母_-且在同一个商户号下唯一。
        String out_trade_no = "LTZF20240513124603836";
        JSONObject payOrder = getPayOrder(out_trade_no);
        System.out.println(payOrder);
    }
}

