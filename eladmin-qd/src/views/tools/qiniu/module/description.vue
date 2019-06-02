<template>
  <div>
    <blockquote class="my-blockquote">注意</blockquote>
    <pre class="my-code">
1、配置时外链域名需带上协议，也就是必须http/https开头
2、如果七牛云中存在数据，使用同步按钮即可将数据同步到数据库
3、本次集成了七牛云的常用操作，如：上传，下载，删除，同步，支持私有空间上传下载
4、项目中配置存入数据库，如需测试，请使用临时空间进行测试，测试完成及时修改配置，反正数据泄露</pre>
    <blockquote class="my-blockquote"> 开始使用</blockquote>
    <pre class="my-code">
#引入依赖
&lt;dependency&gt;
  &lt;groupId&gt;com.qiniu&lt;/groupId&gt;
  &lt;artifactId&gt;qiniu-java-sdk&lt;/artifactId&gt;
  &lt;version&gt;[7.2.0, 7.2.99]&lt;/version&gt;
&lt;dependency&gt;
#简单的上传文件
//构造一个带指定Zone对象的配置类
Configuration cfg = new Configuration(Zone.zone0());
//...其他参数参考类注释
UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
String accessKey = "your access key";
String secretKey = "your secret key";
String bucket = "your bucket name";
//默认不指定key的情况下，以文件内容的hash值作为文件名
String key = null;
try {
    byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
    Auth auth = Auth.create(accessKey, secretKey);
    String upToken = auth.uploadToken(bucket);
    try {
        Response response = uploadManager.put(uploadBytes, key, upToken);
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        System.out.println(putRet.key);
        System.out.println(putRet.hash);
    } catch (QiniuException ex) {
        Response r = ex.response;
        System.err.println(r.toString());
        try {
            System.err.println(r.bodyString());
        } catch (QiniuException ex2) {
            //ignore
        }
    }
} catch (UnsupportedEncodingException ex) {
    //ignore
}</pre>
    <blockquote class="my-blockquote">更多帮助</blockquote>
    <pre class="my-code">更多帮助请查看系统源码，或者七牛云java开发文档
七牛云官网：<a style="color: #00a2d4" href="https://sso.qiniu.com/" target="_blank">https://sso.qiniu.com/</a>
七牛云java开发文档：<a style="color: #00a2d4" href="https://developer.qiniu.com/kodo/sdk/1239/java#3" target="_blank">https://developer.qiniu.com/kodo/sdk/1239/java#3</a></pre>
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
