<template>
  <div class="login">
    <el-form ref="regForm" :model="regForm" :rules="loginRules" label-position="left" label-width="0px" class="login-form">
      <h3 class="title">Xpay 个人入驻</h3>
      <el-form-item prop="email">
        <el-input v-model="regForm.email" :maxlength="30" type="text" auto-complete="off" placeholder="邮箱">
          <svg-icon slot="prefix" icon-class="email" class="el-input__icon" style="height: 39px;width: 13px;margin-left: 2px;" />
          <el-button slot="append" style="width: 115px" @click.native.prevent="cutDown">{{ btnSendCode }}</el-button>
        </el-input>
      </el-form-item>
      <el-form-item prop="email_code">
        <el-input v-model="regForm.email_code" :maxlength="4" type="number" auto-complete="off" placeholder="邮箱验证码">
          <svg-icon slot="prefix" icon-class="dev" class="el-input__icon" style="height: 39px;width: 13px;margin-left: 2px;" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="regForm.password" :maxlength="20" type="password" auto-complete="off" placeholder="密码">
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon" style="height: 39px;width: 13px;margin-left: 2px;" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password_repeat">
        <el-input v-model="regForm.password_repeat" :maxlength="20" type="password" auto-complete="off" placeholder="重复密码">
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon" style="height: 39px;width: 13px;margin-left: 2px;" />
        </el-input>
      </el-form-item>
      <el-form-item style="width:100%;">
        <el-button :loading="loading" size="medium" type="primary" style="width:100%;" @click.native.prevent="handleLogin">
          <span v-if="!loading">注 册</span>
          <span v-else>注 册 中...</span>
        </el-button>
      </el-form-item>
      <p class="login-tip"><a href="/login">登陆</a></p>
    </el-form>
  </div>
</template>

<script>
var time = ''
export default {
  name: 'Reg',
  data() {
    const confirmPass = (rule, value, callback) => {
      if (this.regForm.password !== value) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }
    return {
      regForm: {
        password: '',
        email_code: '',
        email: ''
      },
      loginRules: {
        password: [{ required: true, trigger: 'blur', message: '密码不能为空' }, { type: 'string', min: 6, message: '密码不允许小于6位' }],
        email_code: [{ required: true, trigger: 'blur', message: '邮箱验证码不能为空' }],
        password_repeat: [{ required: true, validator: confirmPass }],
        email: [{ required: true, trigger: 'blur', message: '邮箱不能为空' }, { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }]
      },
      loading: false,
      redirect: undefined,
      btnSendCode: '获取验证码',
      canClick: true,
      seconds: 10
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
  },
  methods: {
    cutDown() {
      console.log('cutdown')
      if (!this.canClick) return
      if (!this.validEmail(this.regForm.email)) {
        this.$message.error('请输入正确邮箱')
        return false
      }
      this.canClick = false
      this.btnSendCode = this.seconds + '秒后重发'
      time = setInterval(() => {
        this.seconds--
        if (this.seconds === 0) {
          this.btnSendCode = '获取验证码'
          this.canClick = true
          this.seconds = 10
          clearInterval(time)
        } else {
          this.btnSendCode = this.seconds + '秒后重发'
        }
      }, 1000)
    },
    handleLogin() {
      this.$refs.regForm.validate(valid => {
        const user = this.regForm
        if (valid) {
          // if (!this.validEmail(user.email)) {
          //   this.$message.info('邮箱格式不正确')
          //   return false
          // }

          this.loading = true
          console.log(user)
          // this.$store.dispatch('Reg', user).then(() => {
          //   this.loading = false
          //   this.$router.push({ path: this.redirect || '/' })
          // }).catch(() => {
          //   this.loading = false
          // })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    validEmail: function(email) {
      const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      return re.test(email)
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
  .login {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    background-image:url(	https://aurora-1255840532.cos.ap-chengdu.myqcloud.com/1547428971990.jpg);
    background-size: cover;
  }
  .title {
    margin: 0px auto 30px auto;
    text-align: center;
    color: #707070;
  }

  .login-form {
    border-radius: 6px;
    background: #ffffff;
    width: 365px;
    padding: 25px 25px 5px 25px;
    .el-input {
      height: 38px;
      input {
        height: 38px;
      }
    }
  }
  .login-tip {
    font-size: 13px;
    text-align: right;
    color: #198fff;
  }
</style>
