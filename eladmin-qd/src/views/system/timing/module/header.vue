<template>
  <div class="head-container">
    <!-- 搜索 -->
    <el-input v-model="query.value" clearable placeholder="输入任务名称搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="toQuery"/>
    <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>
    <!-- 新增 -->
    <div v-permission="['ADMIN','JOB_ALL','JOB_CREATE']" style="display: inline-block;margin: 0px 2px;">
      <el-button
        class="filter-item"
        size="mini"
        type="primary"
        icon="el-icon-plus"
        @click="$refs.form.dialog = true">新增</el-button>
      <eForm ref="form" :is-add="true"/>
    </div>
    <!-- 任务日志 -->
    <div v-permission="['ADMIN','JOB_ALL','JOB_SELECT']" style="display: inline-block;">
      <el-button
        class="filter-item"
        size="mini"
        type="warning"
        icon="el-icon-tickets"
        @click="doLog">执行日志</el-button>
      <Log ref="log"/>
    </div>
  </div>
</template>

<script>
import eForm from './form'
import Log from './log'
// 查询条件
export default {
  components: { eForm, Log },
  props: {
    query: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      downloadLoading: false
    }
  },
  methods: {
    toQuery() {
      this.$parent.page = 0
      this.$parent.init()
    },
    doLog() {
      this.$refs.log.dialog = true
      this.$refs.log.doInit()
    }
  }
}
</script>
