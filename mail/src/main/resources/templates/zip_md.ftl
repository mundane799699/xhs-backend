# ${title!}

### 笔记信息
笔记链接：
<https://www.xiaohongshu.com/explore/${noteId!}>

作者：
${authorName!}
主页链接：<https://www.xiaohongshu.com/user/profile/${authorId}>

### 笔记描述
${desc!}

${tags!}

### 图片链接
<#list images as image>
![](images/${image})
</#list>

<#if videoUrl??>
### 视频链接
${videoUrl}
</#if>