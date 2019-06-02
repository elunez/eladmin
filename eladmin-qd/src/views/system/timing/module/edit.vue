<template>
  <div>
    <el-button size="mini" type="text" @click="to">编辑</el-button>
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
      _this.form = { id: this.data.id, jobName: this.data.jobName, beanName: this.data.beanName, methodName: this.data.methodName, params: this.data.params, cronExpression: this.data.cronExpression, isPause: this.data.isPause.toString(), remark: this.data.remark }
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
