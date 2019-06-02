<template>
  <div>
    <blockquote class="my-blockquote">注意</blockquote>
    <pre class="my-code">
邮件服务器必须支持并打开SMTP协议，详细请查看相关帮助说明
配置文件的样例中提供的是我测试邮件功能注册的sina.com邮箱
帐号密码公开，供测试使用，存入数据库的密码会加密处理，请文明测试</pre>
    <blockquote class="my-blockquote"> 邮件服务器配置</blockquote>
    <pre class="my-code">
 # 邮件服务器的SMTP地址，可选，默认为smtp
 # 邮件服务器的SMTP端口，可选，默认465或者25
 # 发件人（必须正确，否则发送失败）
 # 用户名，默认为发件人邮箱前缀
 # 密码（注意，某些邮箱需要为SMTP服务单独设置密码，如QQ和163等等）
 # 是否开启ssl，默认开启</pre>
    <blockquote class="my-blockquote">发送邮箱</blockquote>
    <pre class="my-code">
 MailAccount account = new MailAccount();
 account.setHost("smtp.sina.com");
 account.setPort("465");
 account.setAuth(true);
 account.setFrom("xpay@xpay.xin");
 account.setUser("xpay");
 account.setPass("pass");
 # 倒数第二个参数：是否为http格式
 MailUtil.send(account, CollUtil.newArrayList("xpay@tom.com"), "测试", "邮件来自xpay测试", true，file...);</pre>
    <blockquote class="my-blockquote">更多帮助</blockquote>
    <pre class="my-code">更多帮助请查看文档：<a style="color:#009688" href="http://hutool.mydoc.io/#text_319499" target="_black">hutool工具包</a></pre>
  </div>
</template>

<script>
import '@/styles/description.scss'
export default {
  name: 'Description'
}
</script>

<style scoped>
</style>
