<template>
  <div class="head-container">
<#if hasQuery>
    <!-- 搜索 -->
    <el-input v-model="query.value" clearable placeholder="输入搜索内容" style="width: 200px;" class="filter-item" @keyup.enter.native="toQuery"/>
    <el-select v-model="query.type" clearable placeholder="类型" class="filter-item" style="width: 130px">
      <el-option v-for="item in queryTypeOptions" :key="item.key" :label="item.display_name" :value="item.key"/>
    </el-select>
    <el-button class="filter-item" size="mini" type="primary" icon="el-icon-search" @click="toQuery">搜索</el-button>
</#if>
    <!-- 新增 -->
    <div style="display: inline-block;margin: 0px 2px;">
      <el-button
        v-if="checkPermission(['ADMIN'])"
        class="filter-item"
        size="mini"
        type="primary"
        icon="el-icon-plus"
        @click="$refs.form.dialog = true">新增</el-button>
      <eForm ref="form" :is-add="true"/>
    </div>
  </div>
</template>

<script>
import checkPermission from '@/utils/permission' // 权限判断函数
import eForm from './form'
export default {
  components: { eForm },
  props: {
    query: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
     <#if hasQuery>
      queryTypeOptions: [
    <#if queryColumns??>
      <#list queryColumns as column>
        { key: '${column.changeColumnName}', display_name: '<#if column.columnComment != ''>${column.columnComment}<#else>${column.changeColumnName}</#if>' }<#if column_has_next>,</#if>
      </#list>
    </#if>
      ]
      </#if>
    }
  },
  methods: {
    checkPermission<#if hasQuery>,
    toQuery() {
      this.$parent.page = 0
      this.$parent.init()
    }</#if>
  }
}
</script>
