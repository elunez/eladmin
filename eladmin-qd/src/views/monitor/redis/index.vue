<template>
  <div class="app-container">
    <eHeader :query="query"/>
    <!--表格渲染-->
    <el-table v-loading="loading" :data="data" size="small" style="width: 100%;">
      <el-table-column label="序号" width="80" align="center">
        <template slot-scope="scope">
          <div>{{ scope.$index + 1 }}</div>
        </template>
      </el-table-column>
      <el-table-column :show-overflow-tooltip="true" prop="key" label="KEY"/>
      <el-table-column prop="value" label="VALUE">
        <template slot-scope="scope">
          <div style="word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
            {{ scope.row.value }}
          </div>
        </template>
      </el-table-column>
      <el-table-column v-if="checkPermission(['ADMIN','REDIS_ALL','REDIS_EDIT','REDIS_DELETE'])" label="操作" width="130px" align="center">
        <template slot-scope="scope">
          <edit v-permission="['ADMIN','REDIS_ALL','REDIS_EDIT']" :data="scope.row" :sup_this="sup_this"/>
          <el-popover
            v-permission="['ADMIN','REDIS_ALL','REDIS_DELETE']"
            :ref="scope.$index"
            placement="top"
            width="180">
            <p>确定删除本条数据吗？</p>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" type="text" @click="$refs[scope.$index].doClose()">取消</el-button>
              <el-button :loading="delLoading" type="primary" size="mini" @click="subDelete(scope.$index, scope.row)">确定</el-button>
            </div>
            <el-button slot="reference" type="danger" icon="el-icon-delete" size="mini"/>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>
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
import checkPermission from '@/utils/permission' // 权限判断函数
import initData from '@/mixins/initData'
import { del } from '@/api/redis'
import eHeader from './module/header'
import edit from './module/edit'
export default {
  components: { eHeader, edit },
  mixins: [initData],
  data() {
    return {
      delLoading: false, sup_this: this, permissions: []
    }
  },
  created() {
    this.$nextTick(() => {
      this.init()
    })
  },
  methods: {
    checkPermission,
    beforeInit() {
      this.url = 'api/redis'
      const query = this.query
      const value = query.value
      this.params = { page: this.page, size: this.size }
      if (value) {
        this.params['key'] = value
      } else {
        this.params['key'] = '*'
      }
      return true
    },
    subDelete(index, row) {
      this.delLoading = true
      del(row.key).then(res => {
        this.delLoading = false
        this.$refs[index].doClose()
        this.init()
        this.$notify({
          title: '删除成功',
          type: 'success',
          duration: 2500
        })
      }).catch(err => {
        this.delLoading = false
        this.$refs[index].doClose()
        console.log(err.response.data.message)
      })
    }
  }
}
</script>

<style scoped>

</style>
