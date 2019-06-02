<template>
  <div>
    <el-button size="mini" type="primary" icon="el-icon-edit" @click="to"/>
    <eForm ref="form" :sup_this="sup_this" :is-add="false"/>
  </div>
</template>
<script>
import eForm from './form'
export default {
  components: { eForm },
  props: {
    data: {
      type: Object,
      required: true
    },
    sup_this: {
      type: Object,
      required: true
    }
  },
  methods: {
    to() {
      const _this = this.$refs.form
      _this.form = {
        id: this.data.id,
        imgAlipay: this.data.imgAlipay,
        imgWechat: this.data.imgWechat,
        imgQq: this.data.imgQq,
        imgUnionpay: this.data.imgUnionpay,
        uid: this.data.uid,
        notifyUrl: this.data.notifyUrl,
        privateKey: this.data.privateKey,
        publicKey: this.data.publicKey,
        sysServiceProviderId: this.data.sysServiceProviderId
      }
      _this.dialog = true
    }
  }
}
</script>

<style scoped>
  div{
    display: inline-block;
    margin-right: 3px;
  }
</style>
