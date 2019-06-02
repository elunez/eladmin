<template>
  <el-dialog :visible.sync="dialog" append-to-body width="600px" @close="doSubmit">
    <el-upload
      :on-preview="handlePictureCardPreview"
      :before-upload="handleBeforeUpload"
      :before-remove="handleBeforeRemove"
      :on-success="handleSuccess"
      :on-error="handleError"
      :headers="headers"
      :file-list="fileList"
      :action="imagesUploadApi"
      list-type="picture-card">
      <i class="el-icon-plus"/>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img :src="dialogImageUrl" width="100%" alt="">
    </el-dialog>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { mapGetters } from 'vuex'
import { del } from '@/api/picture'
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
      pictures: []
    }
  },
  computed: {
    ...mapGetters([
      'imagesUploadApi'
    ])
  },
  methods: {
    handleSuccess(response, file, fileList) {
      console.log('response:', response)
      const uid = file.uid
      const id = response.id
      this.pictures.push({ uid, id })
    },
    handleBeforeUpload(file) {
      console.log(file.type)
      var isImage = false
      if (file.type === 'image/jpeg' || file.type === 'image/gif' || file.type === 'image/png') {
        isImage = true
      }
      const isLt1M = file.size / 1024 / 1024 < 1
      // return true
      if (!isImage) {
        this.$notify({
          title: '上传文件只能是图片格式!',
          type: 'error',
          duration: 2500
        })
      }
      if (!isLt1M) {
        this.$notify({
          title: '上传文件大小不能超过 1MB!',
          type: 'error',
          duration: 2500
        })
      }
      return isImage && isLt1M
    },
    handleBeforeRemove(file, fileList) {
      for (let i = 0; i < this.pictures.length; i++) {
        if (this.pictures[i].uid === file.uid) {
          del(this.pictures[i].id).then(res => {})
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
    handleError(err, file, fileList) {
      const msg = JSON.parse(err.message)
      this.$notify({
        title: msg.message,
        type: 'error',
        duration: 5000
      })
    }
  }
}
</script>

<style scoped>

</style>
