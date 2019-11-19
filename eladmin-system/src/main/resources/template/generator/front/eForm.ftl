<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="500px">
    <el-form ref="form" :model="form" <#if isNotNullColumns??>:rules="rules"</#if> size="small" label-width="80px">
<#if columns??>
  <#list columns as column>
  <#if column.formShow>
      <el-form-item label="<#if column.remark != ''>${column.remark}<#else>${column.changeColumnName}</#if>" <#if column.istNotNull>prop="${column.changeColumnName}"</#if>>
        <#if column.formType = 'Input'>
        <el-input v-model="form.${column.changeColumnName}" style="width: 370px;"/>
        <#elseif column.formType = 'Textarea'>
        <el-input :rows="3" v-model="form.${column.changeColumnName}" type="textarea" style="width: 370px;"/>
        <#elseif column.formType = 'Radio'>
        <#if column.dictName??>
        <el-radio v-for="item in dicts.${column.dictName}" :key="item.id" v-model="form.${column.changeColumnName}" :label="item.value">{{ item.label }}</el-radio>
        <#else>
        未设置字典，请手动设置 Radio
        </#if>
        <#elseif column.formType = 'Select'>
          <#if column.dictName??>
        <el-select v-model="form.${column.changeColumnName}" filterable placeholder="请选择">
          <el-option
            v-for="item in dicts.${column.dictName}"
            :key="item.id"
            :label="item.label"
            :value="item.value"/>
        </el-select>
          <#else>
          未设置字典，请手动设置 Select
          </#if>
        <#else>
        <el-date-picker v-model="form.${column.changeColumnName}" type="datetime" style="width: 370px;"/>
        </#if>
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
    }<#if hasDict>,
    dicts: {
      type: Object,
      required: true
    }
    </#if>
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
<#if isNotNullColumns??>
<#list isNotNullColumns as column>
<#if column.istNotNull>
        ${column.changeColumnName}: [
          { required: true, message: 'please enter', trigger: 'blur' }
        ]<#if column_has_next>,</#if>
</#if>
</#list>
</#if>
      }
    }
  },
  methods: {
    cancel() {
      this.resetForm()
    },
    doSubmit() {
      <#if isNotNullColumns??>
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
      <#else>
        this.loading = true
        if (this.isAdd) {
          this.doAdd()
        } else this.doEdit()
      </#if>
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
