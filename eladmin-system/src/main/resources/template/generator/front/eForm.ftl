<template>
  <el-dialog :append-to-body="true" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="500px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
<#if columns??>
  <#list columns as column>
  <#if column.changeColumnName != '${pkChangeColName}'>
      <el-form-item label="<#if column.columnComment != ''>${column.columnComment}<#else>${column.changeColumnName}</#if>" <#if column.columnKey = 'UNI'>prop="${column.changeColumnName}"</#if>>
        <el-input v-model="form.${column.changeColumnName}" style="width: 370px;"/>
      </el-form-item>
  </#if>
  </#list>
</#if>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { add, edit } from '@/api/${changeClassName}'
export default {
  props: {
    isAdd: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      loading: false, dialog: false,
      form: {
<#if columns??>
    <#list columns as column>
        ${column.changeColumnName}: ''<#if column_has_next>,</#if>
    </#list>
</#if>
      },
      rules: {
<#list columns as column>
<#if column.columnKey = 'UNI'>
        ${column.changeColumnName}: [
          { required: true, message: 'please enter', trigger: 'blur' }
        ]<#if (column_has_next)>,</#if>
</#if>
</#list>
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
        this.$parent.init()
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
        this.$parent.init()
      }).catch(err => {
        this.loading = false
        console.log(err.response.data.message)
      })
    },
    resetForm() {
      this.dialog = false
      this.$refs['form'].resetFields()
      this.form = {
<#if columns??>
    <#list columns as column>
        ${column.changeColumnName}: ''<#if column_has_next>,</#if>
    </#list>
</#if>
      }
    }
  }
}
</script>

<style scoped>

</style>
