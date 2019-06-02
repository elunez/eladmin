<template>
  <div>
    <div ref="editor" style="text-align:left;margin: 5px">
      <h3 style="text-align: center;">欢迎使用 wangEditor 富文本编辑器!</h3>
      <ul>
        <li>富文本中图片上传使用的是sm.ms图床，支持上传到七牛云：<a style="color: #42b983" target="_blank" href="https://sm.ms/">sm.ms</a></li>
        <li>更多帮助请查看官方文档：<a style="color: #42b983" target="_blank" href="https://www.kancloud.cn/wangfupeng/wangeditor3/332599">wangEditor</a></li>
      </ul>
    </div>
    <div style="margin: 12px 5px;font-size: 16px;font-weight: bold;color: #696969">HTML渲染如下：</div>
    <div class="editor-content" v-html="editorContent"/>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import E from 'wangeditor'
import { getToken } from '@/utils/auth'
export default {
  data() {
    return {
      headers: {
        'Authorization': 'Bearer ' + getToken()
      },
      editorContent:
        `<h3 style="text-align: center;">欢迎使用 wangEditor 富文本编辑器!</h3>
        <ul>
          <li>富文本中图片上传使用的是sm.ms图床，支持上传到七牛云：<a style="color: #42b983" target="_blank" href="https://sm.ms/">sm.ms</a></li>
          <li>更多帮助请查看官方文档：<a style="color: #42b983" target="_blank" href="https://www.kancloud.cn/wangfupeng/wangeditor3/332599">wangEditor</a></li>
        </ul>`
    }
  },
  computed: {
    ...mapGetters([
      'imagesUploadApi'
    ])
  },
  mounted() {
    var editor = new E(this.$refs.editor)
    editor.customConfig.uploadImgShowBase64 = true // 使用 base64 保存图片
    // 不可修改
    editor.customConfig.uploadImgHeaders = this.headers
    // 自定义文件名，不可修改，修改后会上传失败
    editor.customConfig.uploadFileName = 'file'
    editor.customConfig.uploadImgServer = this.imagesUploadApi // 上传图片到服务器
    editor.customConfig.onchange = (html) => {
      this.editorContent = html
    }
    editor.create()
  }
}
</script>

<style scoped>
  .editor-content{
    padding-left: 5px;
  }
</style>
