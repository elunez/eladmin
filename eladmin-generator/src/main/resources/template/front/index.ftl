<#--noinspection ALL-->
<template>
  <div class="app-container">
    <!--Toolbar-->
    <div class="head-container">
    <#if hasQuery>
      <div v-if="crud.props.searchToggle">
        <!-- Search -->
        <#if queryColumns??>
          <#list queryColumns as column>
            <#if column.queryType != 'BetWeen'>
        <label class="el-form-item-label"><#if column.remark != ''>${column.remark}<#else>${column.changeColumnName}</#if></label>
        <el-input v-model="query.${column.changeColumnName}" clearable placeholder="<#if column.remark != ''>${column.remark}<#else>${column.changeColumnName}</#if>" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
            </#if>
          </#list>
        </#if>
  <#if betweens??>
    <#list betweens as column>
      <#if column.queryType = 'BetWeen'>
        <date-range-picker
          v-model="query.${column.changeColumnName}"
          start-placeholder="${column.changeColumnName}Start"
          end-placeholder="${column.changeColumnName}Start"
          class="date-item"
        />
      </#if>
    </#list>
  </#if>
        <rrOperation :crud="crud" />
      </div>
    </#if>
      <!--To add more buttons to the toolbar, you can use the slot method, slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--Form Component-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" <#if isNotNullColumns??>:rules="rules"</#if> size="small" label-width="80px">
    <#if columns??>
      <#list columns as column>
        <#if column.formShow>
          <el-form-item label="<#if column.remark != ''>${column.remark}<#else>${column.changeColumnName}</#if>"<#if column.istNotNull> prop="${column.changeColumnName}"</#if>>
            <#if column.formType = 'Input'>
            <el-input v-model="form.${column.changeColumnName}" style="width: 370px;" />
            <#elseif column.formType = 'Textarea'>
            <el-input v-model="form.${column.changeColumnName}" :rows="3" type="textarea" style="width: 370px;" />
            <#elseif column.formType = 'Radio'>
              <#if (column.dictName)?? && (column.dictName)!="">
            <el-radio v-model="form.${column.changeColumnName}" v-for="item in dict.${column.dictName}" :key="item.id" :label="item.value">{{ item.label }}</el-radio>
              <#else>
                Dictionary not set, please set Radio manually
              </#if>
            <#elseif column.formType = 'Select'>
              <#if (column.dictName)?? && (column.dictName)!="">
            <el-select v-model="form.${column.changeColumnName}" filterable placeholder="Please select">
              <el-option
                v-for="item in dict.${column.dictName}"
                :key="item.id"
                :label="item.label"
                :value="item.value" />
            </el-select>
              <#else>
                Dictionary not set, please set Select manually
              </#if>
            <#else>
            <el-date-picker v-model="form.${column.changeColumnName}" type="datetime" style="width: 370px;" />
            </#if>
          </el-form-item>
        </#if>
      </#list>
    </#if>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">Cancel</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">Confirm</el-button>
        </div>
      </el-dialog>
      <!--Table Rendering-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <#if columns??>
            <#list columns as column>
            <#if column.columnShow>
          <#if (column.dictName)?? && (column.dictName)!="">
        <el-table-column prop="${column.changeColumnName}" label="<#if column.remark != ''>${column.remark}<#else>${column.changeColumnName}</#if>">
          <template slot-scope="scope">
            {{ dict.label.${column.dictName}[scope.row.${column.changeColumnName}] }}
          </template>
        </el-table-column>
                <#else>
        <el-table-column prop="${column.changeColumnName}" label="<#if column.remark != ''>${column.remark}<#else>${column.changeColumnName}</#if>" />
                </#if>
            </#if>
            </#list>
        </#if>
        <el-table-column v-if="checkPer(['admin','${changeClassName}:edit','${changeClassName}:del'])" label="Operations" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--Pagination Component-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crud${className} from '@/api/${changeClassName}'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { <#if columns??><#list columns as column>${column.changeColumnName}: null<#if column_has_next>, </#if></#list></#if> }
export default {
  name: '${className}',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  <#if hasDict>
  dicts: [<#if hasDict??><#list dicts as dict>'${dict}'<#if dict_has_next>, </#if></#list></#if>],
  </#if>
  cruds() {
    return CRUD({ title: '${apiAlias}', url: 'api/${changeClassName}', idField: '${pkChangeColName}', sort: '${pkChangeColName},desc', crudMethod: { ...crud${className} }})
  },
  data() {
    return {
      permission: {
        add: ['admin', '${changeClassName}:add'],
        edit: ['admin', '${changeClassName}:edit'],
        del: ['admin', '${changeClassName}:del']
      },
      rules: {
        <#if isNotNullColumns??>
        <#list isNotNullColumns as column>
        <#if column.istNotNull>
        ${column.changeColumnName}: [
          { required: true, message: '<#if column.remark != ''>${column.remark}</#if> cannot be empty', trigger: 'blur' }
        ]<#if column_has_next>,</#if>
        </#if>
        </#list>
        </#if>
      }<#if hasQuery>,
      queryTypeOptions: [
        <#if queryColumns??>
        <#list queryColumns as column>
        <#if column.queryType != 'BetWeen'>
        { key: '${column.changeColumnName}', display_name: '<#if column.remark != ''>${column.remark}<#else>${column.changeColumnName}</#if>' }<#if column_has_next>,</#if>
        </#if>
        </#list>
        </#if>
      ]
      </#if>
    }
  },
  methods: {
    // Hook: executed before getting table data, false means no data will be fetched
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
