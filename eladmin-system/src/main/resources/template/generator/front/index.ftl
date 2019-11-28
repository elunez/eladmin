<#--noinspection ALL-->
<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
    <#if hasQuery>
      <!-- 搜索 -->
      <el-input v-model="query.value" clearable placeholder="输入搜索内容" style="width: 200px;" class="filter-item" @keyup.enter.native="toQuery" />
      <el-select v-model="query.type" clearable placeholder="类型" class="filter-item" style="width: 130px">
        <el-option v-for="item in queryTypeOptions" :key="item.key" :label="item.display_name" :value="item.key" />
      </el-select>
<#if betweens??>
  <#list betweens as column>
    <#if column.queryType = 'BetWeen'>
      <el-date-picker
        v-model="query.${column.changeColumnName}"
        :default-time="['00:00:00','23:59:59']"
        type="daterange"
        range-separator=":"
        size="small"
        class="date-item"
        value-format="yyyy-MM-dd HH:mm:ss"
        start-placeholder="${column.changeColumnName}Start"
        end-placeholder="${column.changeColumnName}End"
      />
    </#if>
  </#list>
</#if>
      <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>
    </#if>
      <!-- 新增 -->
      <el-button v-permission="['admin','${changeClassName}:add']" class="filter-item" size="mini" type="primary" icon="el-icon-plus" @click="showAddFormDialog">新增</el-button>
      <!-- 导出 -->
      <el-button :loading="downloadLoading" size="mini" class="filter-item" type="warning" icon="el-icon-download" @click="downloadMethod">导出</el-button>
      <!-- 多选删除 -->
      <el-button v-permission="['admin','${changeClassName}:del']" :loading="delAllLoading" :disabled="data.length === 0 || $refs.table.selection.length === 0" class="filter-item" size="mini" type="danger" icon="el-icon-delete" @click="beforeDelAllMethod">删除</el-button>
      <!--表单组件-->
      <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="getFormTitle()" width="500px">
        <el-form ref="form" :model="form" <#if isNotNullColumns??>:rules="rules"</#if> size="small" label-width="80px">
    <#if columns??>
      <#list columns as column>
        <#if column.formShow>
          <el-form-item label="<#if column.remark != ''>${column.remark}<#else>${column.changeColumnName}</#if>" <#if column.istNotNull>prop="${column.changeColumnName}"</#if>>
            <#if column.formType = 'Input'>
            <el-input v-model="form.${column.changeColumnName}" style="width: 370px;" />
            <#elseif column.formType = 'Textarea'>
            <el-input :rows="3" v-model="form.${column.changeColumnName}" type="textarea" style="width: 370px;" />
            <#elseif column.formType = 'Radio'>
              <#if column.dictName??>
            <el-radio v-for="item in dict.${column.dictName}" :key="item.id" v-model="form.${column.changeColumnName}" :label="item.value">{{ item.label }}</el-radio>
              <#else>
                未设置字典，请手动设置 Radio
              </#if>
            <#elseif column.formType = 'Select'>
              <#if column.dictName??>
            <el-select v-model="form.${column.changeColumnName}" filterable placeholder="请选择">
              <el-option
                v-for="item in dict.${column.dictName}"
                :key="item.id"
                :label="item.label"
                :value="item.value" />
            </el-select>
              <#else>
            未设置字典，请手动设置 Select
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
          <el-button type="text" @click="cancel">取消</el-button>
          <el-button :loading="loading" type="primary" @click="submitMethod">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="loading" :data="data" size="small" style="width: 100%;">
        <el-table-column type="selection" width="55" />
        <#if columns??>
            <#list columns as column>
            <#if column.columnShow>
          <#if column.dictName??>
        <el-table-column prop="${column.changeColumnName}" label="<#if column.remark != ''>${column.remark}<#else>${column.changeColumnName}</#if>">
          <template slot-scope="scope">
            {{ dict.label.${column.dictName}[scope.row.${column.changeColumnName}] }}
          </template>
        </el-table-column>
          <#elseif column.columnType != 'Timestamp'>
        <el-table-column prop="${column.changeColumnName}" label="<#if column.remark != ''>${column.remark}<#else>${column.changeColumnName}</#if>" />
                <#else>
        <el-table-column prop="${column.changeColumnName}" label="<#if column.remark != ''>${column.remark}<#else>${column.changeColumnName}</#if>">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.${column.changeColumnName}) }}</span>
          </template>
        </el-table-column>
                </#if>
            </#if>
            </#list>
        </#if>
        <el-table-column v-if="checkPermission(['admin','${changeClassName}:edit','${changeClassName}:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <el-button v-permission="['admin','${changeClassName}:edit']" size="mini" type="primary" icon="el-icon-edit" @click="showEditFormDialog(scope.row)" />
            <el-popover
              :ref="scope.row.${pkChangeColName}"
              v-permission="['admin','${changeClassName}:del']"
              placement="top"
              width="180"
            >
              <p>确定删除本条数据吗？</p>
              <div style="text-align: right; margin: 0">
                <el-button size="mini" type="text" @click="$refs[scope.row.${pkChangeColName}].doClose()">取消</el-button>
                <el-button :loading="delLoading" type="primary" size="mini" @click="delMethod(scope.row.${pkChangeColName})">确定</el-button>
              </div>
              <el-button slot="reference" type="danger" icon="el-icon-delete" size="mini" />
            </el-popover>
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <el-pagination
        :total="total"
        :current-page="page + 1"
        style="margin-top: 8px;"
        layout="total, prev, pager, next, sizes"
        @size-change="sizeChange"
        @current-change="pageChange"
      />
    </div>
  </div>
</template>

<script>
import crud from '@/mixins/crud'
import crud${className} from '@/api/${changeClassName}'
export default {
  mixins: [crud],
  <#if hasDict>
  dicts: [<#if hasDict??><#list dicts as dict>'${dict}'<#if dict_has_next>, </#if></#list></#if>],
  </#if>
  data() {
    return {
      title: '${apiAlias}',
      crudMethod: { ...crud${className} },
      form: { <#if columns??><#list columns as column>${column.changeColumnName}: null<#if column_has_next>, </#if></#list></#if> },
      rules: {
        <#if isNotNullColumns??>
        <#list isNotNullColumns as column>
        <#if column.istNotNull>
        ${column.changeColumnName}: [
          { required: true, message: '<#if column.remark != ''>${column.remark}</#if>不能为空', trigger: 'blur' }
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
  created() {
    this.$nextTick(() => {
      this.init()
    })
  },
  methods: {
    // 获取数据前设置好接口地址
    beforeInit() {
      this.url = 'api/${changeClassName}'
      const sort = '${pkChangeColName},desc'
      this.params = { page: this.page, size: this.size, sort: sort }
      <#if hasQuery>
      const query = this.query
      const type = query.type
      const value = query.value
      if (type && value) { this.params[type] = value }
      </#if>
      return true
    }
  }
}
</script>

<style scoped>

</style>
