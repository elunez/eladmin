<template>
  <div>
    <el-button type="primary" size="mini" @click="to">生成代码</el-button>
    <el-dialog :visible.sync="dialog" title="代码生成配置" append-to-body width="800px">
      <el-table v-loading="loading" :data="data" size="small" style="width: 100%;">
        <el-table-column label="序号" width="80" align="center">
          <template slot-scope="scope">
            <div>{{ scope.$index + 1 }}</div>
          </template>
        </el-table-column>
        <el-table-column prop="columnName" label="字段名称"/>
        <el-table-column prop="columnType" label="字段类型"/>
        <el-table-column prop="columnComment" label="字段标题">
          <template slot-scope="scope">
            <el-input v-model="data[scope.$index].columnComment" class="edit-input"/>
          </template>
        </el-table-column>
        <el-table-column label="查询方式">
          <template slot-scope="scope">
            <el-select v-model="data[scope.$index].columnQuery" class="edit-input" clearable placeholder="请选择">
              <el-option
                label="模糊查询"
                value="1"/>
              <el-option
                label="精确查询"
                value="2"/>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="columnShow" label="列表显示">
          <template slot-scope="scope">
            <el-tooltip :content="scope.row.columnShow === 'true' ?'显示':'不显示'" placement="top">
              <el-switch
                v-model="data[scope.$index].columnShow"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-value="true"
                inactive-value="false"/>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="cancel">取消</el-button>
        <el-button :loading="genLoading" type="primary" @click="doSubmit">生成</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import initData from '@/mixins/initData'
import { generator } from '@/api/generator'
export default {
  name: 'Generator',
  mixins: [initData],
  props: {
    name: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      genLoading: false, dialog: false, columnQuery: ''
    }
  },
  methods: {
    to() {
      this.dialog = true
      this.time = 130
      this.$nextTick(() => {
        this.init()
      })
    },
    beforeInit() {
      this.url = 'api/generator/columns'
      const tableName = this.name
      this.params = { tableName }
      return true
    },
    cancel() {
      this.dialog = false
    },
    doSubmit() {
      this.genLoading = true
      generator(this.data, this.name).then(res => {
        this.$notify({
          title: '生成成功',
          type: 'success',
          duration: 2500
        })
        this.dialog = false
        this.genLoading = false
      }).catch(err => {
        this.dialog = false
        this.genLoading = false
        console.log(err.response.data.message)
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
  .edit-input {
    .el-input__inner {
      border: none;
    }
  }
</style>
