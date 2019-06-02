<template>
  <div class="head-container">
    <!-- 搜索 -->
    <el-input v-model="query.value" clearable placeholder="输入字典标签查询" style="width: 200px;" class="filter-item" @keyup.enter.native="toQuery"/>
    <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>
    <div style="display: inline-block;margin: 0px 2px;">
      <eForm ref="form" :is-add="true" :dict-id="dictId"/>
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
    },
    dictId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {}
  },
  methods: {
    checkPermission,
    toQuery() {
      this.$parent.page = 0
      this.$parent.init()
    }
  }
}
</script>
