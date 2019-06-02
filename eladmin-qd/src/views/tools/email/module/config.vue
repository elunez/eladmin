<template>
  <el-form ref="form" :model="form" :rules="rules" style="margin-top: 6px;" size="small" label-width="100px">
    <el-form-item label="发件人邮箱" prop="fromUser">
      <el-input v-model="form.fromUser" style="width: 40%"/>
      <span style="color: #C0C0C0;margin-left: 10px;">Sender mailbox</span>
    </el-form-item>
    <el-form-item label="发件用户名" prop="user">
      <el-input v-model="form.user" style="width: 40%;"/>
      <span style="color: #C0C0C0;margin-left: 10px;">Sender usernamex</span>
    </el-form-item>
    <el-form-item label="邮箱密码" prop="pass">
      <el-input v-model="form.pass" type="password" style="width: 40%;"/>
      <span style="color: #C0C0C0;margin-left: 10px;">email Password</span>
    </el-form-item>
    <el-form-item label="SMTP地址" prop="host">
      <el-input v-model="form.host" style="width: 40%;"/>
      <span style="color: #C0C0C0;margin-left: 10px;">SMTP address</span>
    </el-form-item>
    <el-form-item label="SMTP端口" prop="port">
      <el-input v-model="form.port" style="width: 40%;"/>
      <span style="color: #C0C0C0;margin-left: 10px;">SMTP port</span>
    </el-form-item>
    <el-form-item label="">
      <el-button :loading="loading" size="medium" type="primary" @click="doSubmit">保存配置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { get, update } from '@/api/email'
export default {
  name: 'Config',
  data() {
    return {
      loading: false, form: { id: 1, fromUser: '', user: '', pass: '', host: '', port: '', sslEnable: '' },
      rules: {
        fromUser: [
          { required: true, message: '请输入发件人邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
        ],
        user: [
          { required: true, message: '请输入发件用户名', trigger: 'blur' }
        ],
        pass: [
          { required: true, message: '密码不能为空', trigger: 'blur' }
        ],
        host: [
          { required: true, message: 'SMTP地址不能为空', trigger: 'blur' }
        ],
        port: [
          { required: true, message: 'SMTP端口不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      get().then(res => {
        this.form = res
      })
    },
    doSubmit() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true
          update(this.form).then(res => {
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
