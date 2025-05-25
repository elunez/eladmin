<template>
  <div class="app-container">
    <!-- Toolbar -->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- Search -->
        <label class="el-form-item-label">Name</label>
        <el-input v-model="query.name" clearable placeholder="Name" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">Creation Time</label>
        <el-input v-model="query.createTime" clearable placeholder="Creation Time" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">Enabled</label>
        <el-input v-model="query.enabled" clearable placeholder="Enabled" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <!-- If you want to add more buttons to the toolbar, you can use slots. slot = 'left' or 'right' -->
      <crudOperation :permission="permission" />
      <!-- Form component -->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="id">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="Name" prop="name">
            <el-input v-model="form.name" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="Description">
            <el-input v-model="form.description" :rows="3" type="textarea" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="Creation Time">
            <el-date-picker v-model="form.createTime" type="datetime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="Update Time">
            <el-date-picker v-model="form.updateTime" type="datetime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="Icon">
            <el-input v-model="form.icon" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="Sort">
            <el-input v-model="form.sort" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="Enabled">
                Dictionary not set, please manually set Radio
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">Cancel</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">Confirm</el-button>
        </div>
      </el-dialog>
      <!-- Table rendering -->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="id" />
        <el-table-column prop="name" label="Name" />
        <el-table-column prop="description" label="Description" />
        <el-table-column prop="createTime" label="Creation Time" />
        <el-table-column prop="updateTime" label="Update Time" />
        <el-table-column prop="icon" label="Icon" />
        <el-table-column prop="sort" label="Sort" />
        <el-table-column prop="enabled" label="Enabled" />
        <el-table-column v-if="checkPer(['admin','sport:edit','sport:del'])" label="Operation" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!-- Pagination component -->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudSport from '@/api/sport'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, name: null, description: null, createTime: null, updateTime: null, icon: null, sort: null, enabled: null }
export default {
  name: 'Sport',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'Sport', url: 'api/sport', idField: 'id', sort: 'id,desc', crudMethod: { ...crudSport }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'sport:add'],
        edit: ['admin', 'sport:edit'],
        del: ['admin', 'sport:del']
      },
      rules: {
        name: [
          { required: true, message: 'Name cannot be empty', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'name', display_name: 'Name' },
        { key: 'createTime', display_name: 'Creation Time' },
        { key: 'enabled', display_name: 'Enabled' }
      ]
    }
  },
  methods: {
    // Hook: executed before getting table data, return false to prevent data from being retrieved
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
