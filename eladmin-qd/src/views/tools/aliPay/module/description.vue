<template>
  <div>
    <blockquote class="my-blockquote">注意</blockquote>
    <pre class="my-code">
测试所用参数都是沙箱环境，仅供测试所用，申请地址：<a style="color: #00a0e9" href="https://openhome.alipay.com/platform/appDaily.htm?tab=info" target="_blank">支付宝开发平台</a>
如需付款测试，请使用
账号：uuxesw9745@sandbox.com
密码：111111
支付密码：111111
文明测试谢谢</pre>
    <blockquote class="my-blockquote"> 支付设置</blockquote>
    <pre class="my-code">
支付提供两个测试地址
PC端与手机端，并且使用如下代码识别
if (/(Android)/i.test(navigator.userAgent)){     // 判断是否为Android手机
    url = "/aliPay/toPayAsWeb"
}else if(/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)){  // 判断是否为苹果手机
    url = "/aliPay/toPayAsWeb"
} else {
    url = "/aliPay/toPayAsPC"
}</pre>
    <blockquote class="my-blockquote">更多帮助</blockquote>
    <pre class="my-code">更多帮助请查看系统源码</pre>
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
