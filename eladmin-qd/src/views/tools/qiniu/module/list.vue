<template>
  <div class="app-container">
    <eHeader :query="query"/>
    <!--表格渲染-->
    <el-table v-loading="loading" ref="table" :data="data" size="small" style="width: 100%;">
      <el-table-column type="selection" width="55"/>
      <el-table-column :show-overflow-tooltip="true" label="文件名">
        <template slot-scope="scope">
          <span>{{ scope.row.key }}</span>
        </template>
      </el-table-column>
      <el-table-column :show-overflow-tooltip="true" label="文件类型">
        <template slot-scope="scope">
          <span>{{ getExtensionName(scope.row.key) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="bucket" label="空间名称"/>
      <el-table-column prop="size" label="文件大小"/>
      <el-table-column prop="type" label="空间类型"/>
      <el-table-column width="180px" prop="updateTime" label="创建日期">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="checkPermission(['ADMIN','PICTURE_ALL','PICTURE_DELETE'])" label="操作" width="130px" align="center">
        <template slot-scope="scope">
          <el-button
            :loading="downloadLoading"
            size="mini"
            type="primary"
            icon="el-icon-download"
            @click="download(scope.row.id)"/>
          <el-popover
            v-permission="['ADMIN','PICTURE_ALL','PICTURE_DELETE']"
            :ref="scope.row.id"
            placement="top"
            width="180">
            <p>确定删除本条数据吗？</p>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消</el-button>
              <el-button :loading="delLoading" type="primary" size="mini" @click="subDelete(scope.row.id)">确定</el-button>
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
import { del, download } from '@/api/qiniu'
import { parseTime } from '@/utils/index'
import eHeader from './module/header'
export default {
  components: { eHeader },
  mixins: [initData],
  data() {
    return {
      url: '',
      // 新窗口的引用
      newWin: null,
      downloadLoading: false, delLoading: false, sup_this: this
    }
  },
  watch: {
    url(newVal, oldVal) {
      if (newVal && this.newWin) {
        this.newWin.sessionStorage.clear()
        this.newWin.location.href = newVal
        // 重定向后把url和newWin重置
        this.url = ''
        this.newWin = null
      }
    }
  },
  created() {
    this.$nextTick(() => {
      this.init()
    })
  },
  methods: {
    parseTime,
    checkPermission,
    beforeInit() {
      this.url = 'api/qiNiuContent'
      const sort = 'id,desc'
      const query = this.query
      const value = query.value
      this.params = { page: this.page, size: this.size, sort: sort }
      if (value) { this.params['key'] = value }
      return true
    },
    subDelete(id) {
      this.delLoading = true
      del(id).then(res => {
        this.delLoading = false
        this.$refs[id].doClose()
        this.init()
        this.$notify({
          title: '删除成功',
          type: 'success',
          duration: 2500
        })
      }).catch(err => {
        this.delLoading = false
        this.$refs[id].doClose()
        console.log(err.response.data.message)
      })
    },
    download(id) {
      this.downloadLoading = true
      // 先打开一个空的新窗口，再请求
      this.newWin = window.open()
      download(id).then(res => {
        this.downloadLoading = false
        this.url = res.url
      }).catch(err => {
        this.downloadLoading = false
        console.log(err.response.data.message)
      })
    },
    getExtensionName(name) {
      const dot = name.lastIndexOf('.')
      if ((dot > -1) && (dot < (name.length - 1))) {
        return name.substring(dot + 1)
      }
      return name
    }
  }
}
</script>

<style scoped>

</style>
