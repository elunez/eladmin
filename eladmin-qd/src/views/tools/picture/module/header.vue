<template>
  <div class="head-container">
    <!--搜索-->
    <el-input v-model="query.value" clearable placeholder="输入关键字搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="toQuery"/>
    <el-select v-model="query.type" clearable placeholder="类型" class="filter-item" style="width: 130px">
      <el-option v-for="item in queryTypeOptions" :key="item.key" :label="item.display_name" :value="item.key"/>
    </el-select>
    <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>
    <!-- 上传 -->
    <div style="display: inline-block;margin: 0px 2px;">
      <el-button
        v-permission="['ADMIN','PICTURE_ALL','PICTURE_UPLOAD']"
        class="filter-item"
        size="mini"
        type="primary"
        icon="el-icon-upload"
        @click="$refs.form.dialog = true">上传图片</el-button>
      <eForm ref="form"/>
    </div>
    <div v-permission="['ADMIN','PICTURE_ALL','PICTURE_DELETE']" style="display: inline-block;">
      <el-button
        :loading="delLoading"
        :disabled="$parent.data.length === 0 || $parent.$refs.table.selection.length === 0"
        class="filter-item"
        size="mini"
        type="danger"
        icon="el-icon-delete"
        @click="open">删除</el-button>
    </div>
  </div>
</template>

<script>
import eForm from './form'
import { delAll } from '@/api/picture'
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
      downloadLoading: false,
      delLoading: false,
      queryTypeOptions: [
        { key: 'filename', display_name: '文件名' },
        { key: 'username', display_name: '用户名' }
      ]
    }
  },
  methods: {
    toQuery() {
      this.$parent.page = 0
      this.$parent.init()
    },
    doDelete() {
      this.delLoading = true
      const data = this.$parent.$refs.table.selection
      const ids = []
      for (let i = 0; i < data.length; i++) {
        ids.push(data[i].id)
      }
      delAll(ids).then(res => {
        this.delLoading = false
        this.$parent.init()
        this.$notify({
          title: '删除成功',
          type: 'success',
          duration: 2500
        })
      }).catch(err => {
        this.delLoading = false
        console.log(err.response.data.message)
      })
    },
    open() {
      this.$confirm('你确定删除选中的数据吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.doDelete()
      })
    }
  }
}
</script>
