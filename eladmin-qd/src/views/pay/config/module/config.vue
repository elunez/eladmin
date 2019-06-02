<template>
  <el-form ref="form" :model="form" :rules="rules" style="margin-top: 6px;" size="small" label-width="100px">
    <el-form-item label="我的密钥" >
      <el-input v-model="form.privateKey" disabled style="width: 370px;">
        <el-button slot="append" @click="handleClipboard($event)">复制</el-button>
      </el-input>
    </el-form-item>
    <el-form-item label="异步回调Url" prop="notifyUrl">
      <el-input v-model="form.notifyUrl" style="width: 370px;"/>
    </el-form-item>
    <el-form-item label="">
      <el-button :loading="loading" size="medium" type="primary" @click="doSubmit">保存配置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { getPapConfig, edit } from '@/api/payConfig'
import clipboard from '@/utils/clipboard'
export default {
  name: 'Config',
  data() {
    return {
      loading: false,
      form: { },
      rules: {
        notifyUrl: [{ required: true, message: '请输入异步回调Url', trigger: 'blur' },
          { type: 'url', message: '请输入正确的URL地址', trigger: 'blur' }]
      }
    }
  },
  created() {
    // this.init()
    this.receiveMessage()
  },
  methods: {
    receiveMessage() {
      const that = this
      this.$bus.$on('messageKey', function(value) {
        that.form = value
      })
    },
    handleClipboard(event) {
      const text = this.form.privateKey
      clipboard(text, event)
    },
    init() {
      getPapConfig().then(res => {
        if (res !== undefined && res) {
          this.form = res
        }
      }).catch(err => {
        console.log(err.response.data.message)
      })
    },
    doSubmit() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true
          edit(this.form).then(res => {
            this.$notify({
              title: '修改成功',
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

</style>
