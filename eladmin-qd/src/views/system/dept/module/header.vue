<template>
  <div class="head-container">
    <!-- 搜索 -->
    <el-input v-model="query.value" clearable placeholder="输入部门名称搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="toQuery"/>
    <el-select v-model="query.enabled" clearable placeholder="状态" class="filter-item" style="width: 90px" @change="toQuery">
      <el-option v-for="item in enabledTypeOptions" :key="item.key" :label="item.display_name" :value="item.key"/>
    </el-select>
    <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>
    <!-- 新增 -->
    <div v-permission="['ADMIN','DEPT_ALL','DEPT_CREATE']" style="display: inline-block;margin: 0px 2px;">
      <el-button
        class="filter-item"
        size="mini"
        type="primary"
        icon="el-icon-plus"
        @click="add">新增</el-button>
      <eForm ref="form" :is-add="true" :dicts="dicts"/>
    </div>
    <div style="display: inline-block;">
      <el-button
        class="filter-item"
        size="mini"
        type="warning"
        icon="el-icon-more"
        @click="expand">{{ $parent.expand ? '折叠' : '展开' }}</el-button>
      <eForm ref="form" :is-add="true" :dicts="dicts"/>
    </div>
  </div>
</template>

<script>
import eForm from './form'
export default {
  components: { eForm },
  props: {
    query: {
      type: Object,
      required: true
    },
    dicts: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      enabledTypeOptions: [
        { key: 'true', display_name: '正常' },
        { key: 'false', display_name: '禁用' }
      ]
    }
  },
  methods: {
    toQuery() {
      this.$parent.page = 0
      this.$parent.init()
    },
    add() {
      this.$refs.form.getDepts()
      this.$refs.form.dialog = true
    },
    expand() {
      this.$parent.expand = !this.$parent.expand
      this.$parent.init()
    }
  }
}
</script>
