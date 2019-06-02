<template>
  <div style="display: inline-block;">
    <el-dialog :visible.sync="dialog" :title="title" append-to-body width="475px" @close="cancel">
      <el-form ref="form" :model="form" :rules="rules" size="small" label-width="88px">
        <el-form-item label="登陆邮箱" prop="email">
          <el-input v-model="form.email" :maxlength="30" auto-complete="off" style="width: 200px;"/>
          <el-button :loading="codeLoading" :disabled="isDisabled" size="small" @click="sendCode">{{ buttonName }}</el-button>
        </el-form-item>
        <el-form-item label="验证码" prop="code">
          <el-input v-model="form.code" :maxlength="6" auto-complete="off" style="width: 320px;"/>
        </el-form-item>
        <el-form-item label="登陆密码" prop="pass">
          <el-input v-model="form.pass" :maxlength="20" auto-complete="off" type="password" style="width: 320px;"/>
        </el-form-item>
        <el-form-item label="重复密码" prop="password_repeat">
          <el-input v-model="form.password_repeat" :maxlength="20" auto-complete="off" type="password" style="width: 320px;"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="cancel">取消</el-button>
        <el-button :loading="loading" type="primary" @click="doSubmit">注册</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { validatEmail } from '@/utils/validate'
import { reg, sendCode } from '@/api/login'
export default {
  data() {
    const confirmPass = (rule, value, callback) => {
      if (this.form.pass !== value) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }
    const validMail = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('新邮箱不能为空'))
      } else if (validatEmail(value)) {
        callback()
      } else {
        callback(new Error('邮箱格式错误'))
      }
    }
    return {
      loading: false, dialog: false, title: '个人入驻', form: { pass: '', email: '', code: '' },
      user: { email: '', password: '' }, codeLoading: false,
      codeData: { type: 'email', value: '' },
      buttonName: '获取验证码', isDisabled: false, time: 60,
      rules: {
        pass: [{ required: true, trigger: 'blur', message: '密码不能为空' }, { type: 'string', min: 6, message: '密码不允许小于6位' }],
        password_repeat: [{ required: true, validator: confirmPass, trigger: 'blur' }],
        email: [
          { required: true, validator: validMail, trigger: 'blur' }
        ],
        code: [
          { required: true, message: '验证码不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    cancel() {
      this.resetForm()
    },
    sendCode() {
      if (this.form.email && this.form.email !== this.email) {
        this.codeLoading = true
        this.buttonName = '验证码发送中'
        this.codeData.value = this.form.email
        const _this = this
        sendCode(this.codeData).then(res => {
          this.$message({
            showClose: true,
            message: '发送成功，验证码有效期5分钟',
            type: 'success'
          })
          this.codeLoading = false
          this.isDisabled = true
          this.buttonName = this.time-- + '秒后重新发送'
          this.timer = window.setInterval(function() {
            _this.buttonName = _this.time + '秒后重新发送'
            --_this.time
            if (_this.time < 0) {
              _this.buttonName = '重新发送'
              _this.time = 60
              _this.isDisabled = false
              window.clearInterval(_this.timer)
            }
          }, 1000)
        }).catch(err => {
          this.resetForm()
          this.codeLoading = false
          console.log(err.response.data.message)
        })
      }
    },
    doSubmit() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true
          this.user = { email: this.form.email, password: this.form.pass }
          reg(this.form.code, this.user).then(res => {
            this.loading = false
            this.resetForm()
            this.$message({
              showClose: true,
              message: '注册成功,请使用邮箱登陆！',
              type: 'success'
            })
          }).catch(err => {
            this.loading = false
            console.log(err.response.data.message)
          })
        } else {
          return false
        }
      })
    },
    resetForm() {
      this.dialog = false
      this.$refs['form'].resetFields()
      window.clearInterval(this.timer)
      this.time = 60
      this.buttonName = '获取验证码'
      this.isDisabled = false
      this.form = { pass: '', email: '', code: '' }
    }
  }
}
</script>

<style scoped>

</style>
