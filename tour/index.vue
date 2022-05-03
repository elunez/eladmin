<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="id">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="name" prop="name">
            <el-input v-model="form.name" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="startDate" prop="startDate">
            <el-input v-model="form.startDate" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="period" prop="period">
            <el-input v-model="form.period" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="location" prop="location">
            <el-input v-model="form.location" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="tourCode" prop="tourCode">
            <el-input v-model="form.tourCode" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="tourType" prop="tourType">
            <el-input v-model="form.tourType" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="description">
            <el-input v-model="form.description" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="extraTourDetail" prop="extraTourDetail">
            <el-input v-model="form.extraTourDetail" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="extraRoomDetail" prop="extraRoomDetail">
            <el-input v-model="form.extraRoomDetail" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="images" prop="images">
            <el-input v-model="form.images" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="id" />
        <el-table-column prop="name" label="name" />
        <el-table-column prop="startDate" label="startDate" />
        <el-table-column prop="period" label="period" />
        <el-table-column prop="location" label="location" />
        <el-table-column prop="tourCode" label="tourCode" />
        <el-table-column prop="tourType" label="tourType" />
        <el-table-column prop="description" label="description" />
        <el-table-column prop="extraTourDetail" label="extraTourDetail" />
        <el-table-column prop="extraRoomDetail" label="extraRoomDetail" />
        <el-table-column prop="images" label="images" />
        <el-table-column v-if="checkPer(['admin','mTour:edit','mTour:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudMTour from '@/api/mTour'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, name: null, startDate: null, period: null, location: null, tourCode: null, tourType: null, description: null, extraTourDetail: null, extraRoomDetail: null, images: null }
export default {
  name: 'MTour',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'tour', url: 'api/mTour', idField: 'id', sort: 'id,desc', crudMethod: { ...crudMTour }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'mTour:add'],
        edit: ['admin', 'mTour:edit'],
        del: ['admin', 'mTour:del']
      },
      rules: {
        name: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        startDate: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        period: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        location: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        tourCode: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        tourType: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        extraTourDetail: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        extraRoomDetail: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        images: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ]
      }    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
