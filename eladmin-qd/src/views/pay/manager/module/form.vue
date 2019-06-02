<template>
  <el-dialog :append-to-body="true" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="500px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
      <el-form-item label="支付宝" >
        <el-input v-model="form.imgAlipay" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="微信" >
        <el-input v-model="form.imgWechat" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="QQ" >
        <el-input v-model="form.imgQq" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="银联" >
        <el-input v-model="form.imgUnionpay" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="用户ID" >
        <el-input v-model="form.uid" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="异步回调" >
        <el-input v-model="form.notifyUrl" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="私钥" >
        <el-input v-model="form.privateKey" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="公钥" >
        <el-input v-model="form.publicKey" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="商户号" >
        <el-input v-model="form.sysServiceProviderId" style="width: 370px;"/>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { add, edit } from '@/api/payConfig'
export default {
  props: {
    isAdd: {
      type: Boolean,
      required: true
    },
    sup_this: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      loading: false, dialog: false,
      form: {
        id: '',
        imgAlipay: '',
        imgWechat: '',
        imgQq: '',
        imgUnionpay: '',
        uid: '',
        notifyUrl: '',
        privateKey: '',
        publicKey: '',
        sysServiceProviderId: ''
      },
      rules: {
      }
    }
  },
  methods: {
    cancel() {
      this.resetForm()
    },
    doSubmit() {
      this.loading = true
      if (this.isAdd) {
        this.doAdd()
      } else this.doEdit()
    },
    doAdd() {
      add(this.form).then(res => {
        this.resetForm()
        this.$notify({
          title: '添加成功',
          type: 'success',
          duration: 2500
        })
        this.loading = false
        this.$parent.$parent.init()
      }).catch(err => {
        this.loading = false
        console.log(err.response.data.message)
      })
    },
    doEdit() {
      edit(this.form).then(res => {
        this.resetForm()
        this.$notify({
          title: '修改成功',
          type: 'success',
          duration: 2500
        })
        this.loading = false
        this.sup_this.init()
      }).catch(err => {
        this.loading = false
        console.log(err.response.data.message)
      })
    },
    resetForm() {
      this.dialog = false
      this.$refs['form'].resetFields()
      this.form = {
        id: '',
        imgAlipay: '',
        imgWechat: '',
        imgQq: '',
        imgUnionpay: '',
        uid: '',
        notifyUrl: '',
        privateKey: '',
        publicKey: '',
        sysServiceProviderId: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
