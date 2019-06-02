<template>
  <div class="head-container">
    <!-- 搜索 -->
    <el-input v-model="query.value" clearable placeholder="输入关键词搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="toQuery"/>
    <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>
    <!-- 新增 -->
    <div v-permission="['ADMIN','REDIS_ALL','REDIS_CREATE']" style="display: inline-block;margin: 0px 2px;">
      <el-button
        class="filter-item"
        size="mini"
        type="primary"
        icon="el-icon-plus"
        @click="$refs.form.dialog = true">新增</el-button>
      <eForm ref="form" :is-add="true"/>
    </div>
    <div style="display: inline-block;">
      <!-- 清空缓存 -->
      <el-button v-permission="['ADMIN','REDIS_ALL','REDIS_DELETE']" :loading="deleteAllLoading" type="warning" size="mini" class="filter-item" icon="el-icon-delete" @click="deleteAll">清空缓存</el-button>
    </div>
  </div>
</template>

<script>
import eForm from './form'
import { delAll } from '@/api/redis'
// 查询条件
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
      deleteAllLoading: false
    }
  },
  methods: {
    toQuery() {
      this.$parent.page = 0
      this.$parent.init()
    },
    deleteAll() {
      this.deleteAllLoading = true
      delAll().then(res => {
        this.$parent.page = 0
        this.$parent.init()
        this.deleteAllLoading = false
      })
    }
  }
}
</script>
