<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" style="margin-top: 6px;" size="small" label-width="100px">
      <el-form-item label="邮件标题" prop="subject">
        <el-input v-model="form.subject" style="width: 40%"/>
      </el-form-item>
      <el-form-item
        v-for="(domain, index) in tos"
        :label="'收件邮箱' + (index === 0 ? '': index)"
        :key="domain.key">
        <el-input v-model="domain.value" style="width: 31%"/>
        <el-button icon="el-icon-plus" @click="addDomain" />
        <el-button style="margin-left:0px;" icon="el-icon-minus" @click.prevent="removeDomain(domain)"/>
      </el-form-item>
      <div ref="editor" class="editor"/>
      <el-button :loading="loading" style="margin-left:1.6%;" size="medium" type="primary" @click="doSubmit">发送邮件</el-button>
    </el-form>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getToken } from '@/utils/auth'
import { send } from '@/api/email'
import { validatEmail } from '@/utils/validate'
import E from 'wangeditor'
export default {
  name: 'Index',
  data() {
    return {
      headers: {
        'Authorization': 'Bearer ' + getToken()
      },
      loading: false, form: { subject: '', tos: [], content: '' },
      tos: [{
        value: ''
      }],
      rules: {
        subject: [
          { required: true, message: '标题不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    ...mapGetters([
      // sm.ms图床
      'imagesUploadApi',
      // 七牛云 按需选择
      'qiNiuUploadApi'
    ])
  },
  mounted() {
    var editor = new E(this.$refs.editor)
    editor.customConfig.uploadImgShowBase64 = true // 使用 base64 保存图片
    // 不可修改
    editor.customConfig.uploadImgHeaders = this.headers
    // 自定义文件名，不可修改，修改后会上传失败
    editor.customConfig.uploadFileName = 'file'
    // 上传到哪儿，按需选择
    editor.customConfig.uploadImgServer = this.imagesUploadApi // 上传图片到服务器
    editor.customConfig.onchange = (html) => {
      this.form.content = html
    }
    editor.create()
  },
  methods: {
    removeDomain(item) {
      var index = this.tos.indexOf(item)
      if (index !== -1 && this.tos.length !== 1) {
        this.tos.splice(index, 1)
      } else {
        this.$message({
          message: '请至少保留一位联系人',
          type: 'warning'
        })
      }
    },
    addDomain() {
      this.tos.push({
        value: '',
        key: Date.now()
      })
    },
    doSubmit() {
      const _this = this
      this.$refs['form'].validate((valid) => {
        this.form.tos = []
        if (valid) {
          let sub = false
          this.tos.forEach(function(data, index) {
            if (data.value === '') {
              _this.$message({
                message: '收件邮箱不能为空',
                type: 'warning'
              })
              sub = true
            } else if (validatEmail(data.value)) {
              _this.form.tos.push(data.value)
            } else {
              _this.$message({
                message: '收件邮箱格式错误',
                type: 'warning'
              })
              sub = true
            }
          })
          if (sub) { return false }
          this.loading = true
          send(this.form).then(res => {
            this.$notify({
              title: '发送成功',
              type: 'success',
              duration: 2500
            })
            this.loading = false
          }).catch(err => {
            this.loading = false
            console.log(err.response.data.message)
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style scoped>
  .editor{
    text-align:left;
    margin: 20px;
  }
</style>
