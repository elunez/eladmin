<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" style="margin-top: 6px;" size="small" label-width="90px">
      <el-form-item label="商品名称" prop="subject">
        <el-input v-model="form.subject" style="width: 35%"/>
      </el-form-item>
      <el-form-item label="商品价格" prop="totalAmount">
        <el-input v-model="form.totalAmount" style="width: 35%"/>
        <span style="color: #C0C0C0;margin-left: 10px;">测试允许区间(0,5000]</span>
      </el-form-item>
      <el-form-item label="商品描述" prop="body">
        <el-input v-model="form.body" style="width: 35%" rows="8" type="textarea"/>
      </el-form-item>
      <el-form-item label="">
        <el-button :loading="loading" size="medium" type="primary" @click="doSubmit">去支付</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { toAliPay } from '@/api/alipay'
export default {
  data() {
    return {
      url: '',
      // 新窗口的引用
      newWin: null,
      loading: false, form: { subject: '', totalAmount: '', body: '' },
      rules: {
        subject: [
          { required: true, message: '商品名称不能为空', trigger: 'blur' }
        ],
        totalAmount: [
          { required: true, message: '商品价格不能为空', trigger: 'blur' }
        ],
        body: [
          { required: true, message: '商品描述不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    url(newVal, oldVal) {
      if (newVal && this.newWin) {
        this.newWin.sessionStorage.clear()
        this.newWin.location.href = newVal
        // 重定向后把url和newWin重置
        this.url = ''
        this.newWin = null
      }
    }
  },
  methods: {
    doSubmit() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true
          // 先打开一个空的新窗口，再请求
          this.newWin = window.open()
          let url = ''
          if (/(Android)/i.test(navigator.userAgent)) { // 判断是否为Android手机
            url = 'aliPay/toPayAsWeb'
          } else if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) { // 判断是否为苹果手机
            url = 'aliPay/toPayAsWeb'
          } else {
            url = 'aliPay/toPayAsPC'
          }
          toAliPay(url, this.form).then(res => {
            this.loading = false
            this.url = res
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
