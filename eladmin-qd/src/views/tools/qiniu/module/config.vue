<template>
  <el-form ref="form" :model="form" :rules="rules" style="margin-top: 6px;" size="small" label-width="130px">
    <el-form-item label="Access Key" prop="accessKey">
      <el-input v-model="form.accessKey" style="width: 35%"/>
      <span style="color: #C0C0C0;margin-left: 10px;">accessKey，在安全中心，秘钥管理中查看</span>
    </el-form-item>
    <el-form-item label="Secret Key" prop="secretKey">
      <el-input v-model="form.secretKey" type="password" style="width: 35%;"/>
      <span style="color: #C0C0C0;margin-left: 10px;">secretKey，在安全中心，秘钥管理中查看</span>
    </el-form-item>
    <el-form-item label="空间名称" prop="bucket">
      <el-input v-model="form.bucket" style="width: 35%;"/>
      <span style="color: #C0C0C0;margin-left: 10px;">存储空间名称作为唯一的 Bucket 识别符</span>
    </el-form-item>
    <el-form-item label="外链域名" prop="host">
      <el-input v-model="form.host" style="width: 35%;"/>
      <span style="color: #C0C0C0;margin-left: 10px;">外链域名，可自定义，需在七牛云绑定</span>
    </el-form-item>
    <el-form-item label="存储区域" prop="port">
      <el-select v-model="form.zone" placeholder="请选择存储区域">
        <el-option
          v-for="item in zones"
          :key="item"
          :label="item"
          :value="item"/>
      </el-select>
      <span style="color: #C0C0C0;margin-left: 10px;">北美区域尚未支持自定义数据处理服务，一旦创建区域无法修改，请谨慎选择</span>
    </el-form-item>
    <el-form-item label="空间类型" prop="host">
      <el-radio v-model="form.type" label="公开">公开</el-radio>
      <el-radio v-model="form.type" label="私有" >私有</el-radio>
      <span style="color: #C0C0C0;margin-left: 10px;">公开和私有仅对 Bucket 的读文件生效，修改、删除、写入等对 Bucket 的操作均需要拥有者的授权才能进行操作</span>
    </el-form-item>
    <el-button :loading="loading" style="margin-left:5%;" size="medium" type="primary" @click="doSubmit">保存配置</el-button>
  </el-form>
</template>

<script>
import { get, update } from '@/api/qiniu'
export default {
  name: 'Config',
  data() {
    return {
      zones: ['华东', '华北', '华南', '北美', '东南亚'],
      loading: false, form: { accessKey: '', secretKey: '', bucket: '', host: '', zone: '', type: '' },
      rules: {
        accessKey: [
          { required: true, message: '请输入accessKey', trigger: 'blur' }
        ],
        secretKey: [
          { required: true, message: '请输入secretKey', trigger: 'blur' }
        ],
        bucket: [
          { required: true, message: '请输入空间名称', trigger: 'blur' }
        ],
        host: [
          { required: true, message: '请输入外链域名', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '空间类型不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      get().then(res => {
        this.form = res
      })
    },
    doSubmit() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true
          update(this.form).then(res => {
            this.$notify({
              title: '修改成功',
              type: 'success',
              duration: 2500
            })
            this.loading = false
          }).catch(err => {
            this.loading = false
            console.log(err.response.data.message)
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
