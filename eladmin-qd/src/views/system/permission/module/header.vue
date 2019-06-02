<template>
  <div class="head-container">
    <!-- 搜索 -->
    <el-input v-model="query.value" clearable placeholder="输入名称搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="toQuery"/>
    <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>
    <!-- 新增 -->
    <div v-permission="['ADMIN','PERMISSION_ALL','PERMISSION_CREATE']" style="display: inline-block;margin: 0px 2px 0px">
      <el-button
        class="filter-item"
        size="mini"
        type="primary"
        icon="el-icon-plus"
        @click="add">新增</el-button>
      <eForm ref="form" :is-add="true"/>
    </div>
    <div style="display: inline-block;">
      <el-button
        class="filter-item"
        size="mini"
        type="warning"
        icon="el-icon-more"
        @click="expand">{{ $parent.expand ? '折叠' : '展开' }}</el-button>
      <eForm ref="form" :is-add="true"/>
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
    add() {
      this.$refs.form.getPermissions()
      this.$refs.form.dialog = true
    },
    expand() {
      this.$parent.expand = !this.$parent.expand
      this.$parent.init()
    }
  }
}
</script>
