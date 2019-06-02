<template>
  <div class="head-container">
    <el-input v-model="query.name" clearable placeholder="请输入表名" style="width: 200px;" class="filter-item" @keyup.enter.native="toQuery"/>
    <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>
    <!-- 新增 -->
    <div style="display: inline-block;margin: 0px 2px;">
      <el-button
        class="filter-item"
        size="mini"
        type="warning"
        icon="el-icon-setting"
        @click="to">生成器配置</el-button>
      <eForm ref="form"/>
    </div>
  </div>
</template>

<script>
import { get } from '@/api/genConfig'
import eForm from './form'
// 查询条件
export default {
  components: { eForm },
  props: {
    query: {
      type: Object,
      required: true
    }
  },
  methods: {
    toQuery() {
      this.$parent.page = 0
      this.$parent.init()
    },
    to() {
      const _this = this.$refs.form
      get().then(data => {
        _this.form = data
        _this.form.cover = _this.form.cover.toString()
      })
      _this.dialog = true
    }
  }
}
</script>
