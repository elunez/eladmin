<template>
  <el-dialog :visible.sync="dialog" append-to-body width="500px" @close="doSubmit">
    <el-upload
      :before-remove="handleBeforeRemove"
      :on-success="handleSuccess"
      :on-error="handleError"
      :file-list="fileList"
      :headers="headers"
      :action="qiNiuUploadApi"
      class="upload-demo"
      multiple>
      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" style="display: block;" class="el-upload__tip">请勿上传违法文件，且文件不超过15M</div>
    </el-upload>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { mapGetters } from 'vuex'
import { del } from '@/api/qiniu'
import { getToken } from '@/utils/auth'
export default {
  data() {
    return {
      headers: {
        'Authorization': 'Bearer ' + getToken()
      },
      dialog: false,
      dialogImageUrl: '',
      dialogVisible: false,
      fileList: [],
      files: []
    }
  },
  computed: {
    ...mapGetters([
      'qiNiuUploadApi'
    ])
  },
  methods: {
    handleSuccess(response, file, fileList) {
      const uid = file.uid
      const id = response.id
      this.files.push({ uid, id })
    },
    handleBeforeRemove(file, fileList) {
      for (let i = 0; i < this.files.length; i++) {
        if (this.files[i].uid === file.uid) {
          del(this.files[i].id).then(res => {})
          return true
        }
      }
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    // 刷新列表数据
    doSubmit() {
      this.fileList = []
      this.dialogVisible = false
      this.dialogImageUrl = ''
      this.dialog = false
      this.$parent.$parent.init()
    },
    // 监听上传失败
    handleError(e, file, fileList) {
      const msg = JSON.parse(e.message)
      this.$notify({
        title: msg.message,
        type: 'error',
        duration: 2500
      })
    }
  }
}
</script>

<style scoped>

</style>
