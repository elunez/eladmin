<template>
  <el-dialog :visible.sync="dialog" title="生成器配置" append-to-body width="550px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="78px">
      <el-form-item label="作者名称" prop="author">
        <el-input v-model="form.author" style="width: 420px;"/>
      </el-form-item>
      <el-form-item label="去表前缀" prop="prefix">
        <el-input v-model="form.prefix" placeholder="默认不去除表前缀" style="width: 420px;"/>
      </el-form-item>
      <el-form-item label="模块名称" prop="moduleName">
        <el-input v-model="form.moduleName" style="width: 420px;"/>
      </el-form-item>
      <el-form-item label="至于包下" prop="pack">
        <el-input v-model="form.pack" style="width: 420px;"/>
      </el-form-item>
      <el-form-item label="前端路径" prop="path">
        <el-input v-model="form.path" style="width: 420px;"/>
      </el-form-item>
      <el-form-item label="API路径" prop="apiPath">
        <el-input v-model="form.apiPath" style="width: 420px;"/>
      </el-form-item>
      <el-form-item label="是否覆盖" prop="cover">
        <el-radio v-model="form.cover" label="true">是</el-radio>
        <el-radio v-model="form.cover" label="false">否</el-radio>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { update } from '@/api/genConfig'
export default {
  data() {
    return {
      loading: false, dialog: false,
      form: { author: '', pack: '', path: '', moduleName: '', cover: 'false', apiPath: '', prefix: '' },
      rules: {
        author: [
          { required: true, message: '作者不能为空', trigger: 'blur' }
        ],
        pack: [
          { required: true, message: '包路径不能为空', trigger: 'blur' }
        ],
        moduleName: [
          { required: true, message: '包路径不能为空', trigger: 'blur' }
        ],
        path: [
          { required: true, message: '前端代码生成路径不能为空', trigger: 'blur' }
        ],
        apiPath: [
          { required: true, message: '前端Api文件生成路径不能为空', trigger: 'blur' }
        ],
        cover: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    cancel() {
      this.resetForm()
    },
    doSubmit() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true
          this.doUpdate()
        } else {
          return false
        }
      })
    },
    doUpdate() {
      update(this.form).then(res => {
        this.resetForm()
        this.$notify({
          title: '更新成功',
          type: 'success',
          duration: 2500
        })
        this.loading = false
      }).catch(err => {
        this.loading = false
        console.log(err.response.data.message)
      })
    },
    resetForm() {
      this.dialog = false
      this.$refs['form'].resetFields()
      this.form = { author: '', pack: '', path: '', moduleName: '', cover: 'false', apiPath: '', prefix: '' }
    }
  }
}
</script>

<style scoped>

</style>
