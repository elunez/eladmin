<template>
  <div class="app-container">
    <eHeader :query="query"/>
    <!--表格渲染-->
    <el-table v-loading="loading" :data="data" size="small" style="width: 100%;">
      <el-table-column prop="username" label="用户名"/>
      <el-table-column prop="requestIp" label="IP"/>
      <el-table-column prop="description" label="描述"/>
      <el-table-column :show-overflow-tooltip="true" prop="method" label="方法名称"/>
      <el-table-column :show-overflow-tooltip="true" prop="params" label="参数"/>
      <el-table-column prop="createTime" label="创建日期">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="异常详情" width="100px">
        <template slot-scope="scope">
          <el-button size="mini" type="text" @click="info(scope.row.id)">查看详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="dialog" title="异常详情" append-to-body top="0" width="85%">
      <span>
        {{ errorInfo }}
      </span>
    </el-dialog>
    <!--分页组件-->
    <el-pagination
      :total="total"
      style="margin-top: 8px;"
      layout="total, prev, pager, next, sizes"
      @size-change="sizeChange"
      @current-change="pageChange"/>
  </div>
</template>

<script>
import initData from '@/mixins/initData'
import { parseTime } from '@/utils/index'
import { getErrDetail } from '@/api/log'
import eHeader from './module/header'
export default {
  components: { eHeader },
  mixins: [initData],
  data() {
    return {
      errorInfo: '', dialog: false
    }
  },
  created() {
    this.$nextTick(() => {
      this.init()
    })
  },
  methods: {
    parseTime,
    beforeInit() {
      this.url = 'api/logs/error'
      const sort = 'id,desc'
      const query = this.query
      const username = query.username
      const logType = query.logType
      this.params = { page: this.page, size: this.size, sort: sort }
      if (username && username) { this.params['username'] = username }
      if (logType !== '' && logType !== null) { this.params['logType'] = logType }
      return true
    },
    info(id) {
      this.dialog = true
      getErrDetail(id).then(res => {
        this.errorInfo = res.exception
      })
    }
  }
}
</script>

<style scoped>

</style>
