<template>
  <el-dialog :visible.sync="dialog" :title="isAdd ? '新增任务' : '编辑任务'" append-to-body width="600px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
      <el-form-item label="任务名称" prop="jobName">
        <el-input v-model="form.jobName" style="width: 460px;"/>
      </el-form-item>
      <el-form-item label="Bean名称" prop="beanName">
        <el-input v-model="form.beanName" style="width: 460px;"/>
      </el-form-item>
      <el-form-item label="执行方法" prop="methodName">
        <el-input v-model="form.methodName" style="width: 460px;"/>
      </el-form-item>
      <el-form-item label="参数内容">
        <el-input v-model="form.params" style="width: 460px;"/>
      </el-form-item>
      <el-form-item label="Cron表达式" prop="cronExpression">
        <el-input v-model="form.cronExpression" style="width: 460px;"/>
      </el-form-item>
      <el-form-item label="任务状态">
        <el-radio v-model="form.isPause" label="false">启用</el-radio>
        <el-radio v-model="form.isPause" label="true" >暂停</el-radio>
      </el-form-item>
      <el-form-item label="任务描述">
        <el-input v-model="form.remark" style="width: 460px;" rows="2" type="textarea"/>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { add, edit } from '@/api/timing'
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
      form: { jobName: '', beanName: '', methodName: '', params: '', cronExpression: '', isPause: 'false', remark: '' }, permissionIds: [],
      rules: {
        jobName: [
          { required: true, message: '请输入任务名称', trigger: 'blur' }
        ],
        beanName: [
          { required: true, message: '请输入Bean名称', trigger: 'blur' }
        ],
        methodName: [
          { required: true, message: '请输入方法名称', trigger: 'blur' }
        ],
        cronExpression: [
          { required: true, message: '请输入Cron表达式', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    cancel() {
      this.resetForm()
    },
    doSubmit() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true
          if (this.isAdd) {
            this.doAdd()
          } else this.doEdit()
        } else {
          return false
        }
      })
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
      this.form = { jobName: '', beanName: '', methodName: '', params: '', cronExpression: '', isPause: 'false', remark: '' }
    }
  }
}
</script>

<style scoped>

</style>
