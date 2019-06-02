<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="8" :lg="6" :xl="5">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>支付宝收款码</span>
          </div>
          <div>
            <div style="text-align: center">
              <el-upload
                :show-file-list="false"
                :on-success="handleSuccess"
                :on-error="handleError"
                :headers="headers"
                :data="dataAlipay"
                :action="imagesPayUploadApi"
                :before-upload="handleBeforeUpload"
                class="avatar-uploader"
                list-type="picture-card">
                <img v-if="payConfigPic.imgAlipay" :src="payConfigPic.imgAlipay" title="点击上传收款码图片" class="avatar">
                <i v-else class="el-icon-plus"/>
                <div slot="tip" class="el-upload__tip">{{ imgTip }}</div>
              </el-upload>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="8" :lg="6" :xl="5">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>微信支付收款码</span>
          </div>
          <div>
            <div style="text-align: center">
              <el-upload
                :show-file-list="false"
                :on-success="handleSuccess"
                :on-error="handleError"
                :headers="headers"
                :data="dataWechat"
                :action="imagesPayUploadApi"
                :before-upload="handleBeforeUpload"
                class="avatar-uploader"
                list-type="picture-card">
                <img v-if="payConfigPic.imgWechat" :src="payConfigPic.imgWechat" title="点击上传收款码图片" class="avatar">
                <i v-else class="el-icon-plus"/>
                <div slot="tip" class="el-upload__tip">{{ imgTip }}</div>
              </el-upload>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="8" :lg="6" :xl="5">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>QQ支付收款码</span>
          </div>
          <div>
            <div style="text-align: center">
              <el-upload
                :show-file-list="false"
                :on-success="handleSuccess"
                :on-error="handleError"
                :headers="headers"
                :data="dataQQ"
                :action="imagesPayUploadApi"
                :before-upload="handleBeforeUpload"
                class="avatar-uploader"
                list-type="picture-card">
                <img v-if="payConfigPic.imgQq" :src="payConfigPic.imgQq" title="点击上传收款码图片" class="avatar">
                <i v-else class="el-icon-plus"/>
                <div slot="tip" class="el-upload__tip">{{ imgTip }}</div>
              </el-upload>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="8" :lg="6" :xl="5">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>银联支付收款码</span>
          </div>
          <div>
            <div style="text-align: center">
              <el-upload
                :show-file-list="false"
                :on-success="handleSuccess"
                :on-error="handleError"
                :headers="headers"
                :data="dataUnionpay"
                :action="imagesPayUploadApi"
                :before-upload="handleBeforeUpload"
                class="avatar-uploader"
                list-type="picture-card">
                <img v-if="payConfigPic.imgUnionpay" :src="payConfigPic.imgUnionpay" title="点击上传收款码图片" class="avatar">
                <i v-else class="el-icon-plus"/>
                <div slot="tip" class="el-upload__tip">{{ imgTip }}</div>
              </el-upload>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getToken } from '@/utils/auth'
import { getPapConfig } from '@/api/payConfig'
export default {
  name: 'PayImgConfig',
  data() {
    return {
      imgTip: '推荐使用300*300px图片，不超过1M',
      dataAlipay: { type: 'alipay' },
      dataWechat: { type: 'wechat' },
      dataQQ: { type: 'qq' },
      dataUnionpay: { type: 'unionpay' },
      payConfigPic: {
      },
      headers: {
        'Authorization': 'Bearer ' + getToken()
      }
    }
  },
  computed: {
    ...mapGetters([
      'imagesPayUploadApi'
    ])
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
    getPapConfig().then(res => {
      if (res !== undefined && res) {
        this.payConfigPic = res
        this.sendMessage(this.payConfigPic)
      }
    }).catch(err => {
      console.log(err.response.data.message)
    })
    // store.dispatch('getPapConfig').then((res) => {
    //   console.log('==created res:', res)
    // })
  },
  beforeDestroy() {
    this.$bus.$off('messageKey')
  },
  methods: {
    handleSuccess(response, file, fileList) {
      this.$notify({
        title: '图片上传成功',
        type: 'success',
        duration: 2500
      })
      this.payConfigPic = response.data
      console.log('payConfigPic:', this.payConfigPic)
      this.sendMessage(this.payConfigPic)
    },
    sendMessage(data) {
      // console.log('==sendMessage==')
      this.$bus.$emit('messageKey', data)
    },
    handleBeforeUpload(file) {
      console.log(file.type)
      var isImage = false
      if (file.type === 'image/jpeg' || file.type === 'image/gif' || file.type === 'image/png') {
        isImage = true
      }
      const isLt1M = file.size / 1024 / 1024 < 1
      // return true
      if (!isImage) {
        this.$notify({
          title: '上传文件只能是图片格式!',
          type: 'error',
          duration: 2500
        })
      }
      if (!isLt1M) {
        this.$notify({
          title: '上传文件大小不能超过 1MB!',
          type: 'error',
          duration: 2500
        })
      }
      return isImage && isLt1M
    },
    // 监听上传失败
    handleError(e, file, fileList) {
      const msg = JSON.parse(e.message)
      this.$notify({
        title: msg.message,
        type: 'error',
        duration: 2500
      })
    }
  }
}
</script>

<style type="text/css">
  img{width: 100%;height: inherit;}
  .el-upload{width: 250px;overflow: hidden;height: 250px;}
  .el-upload--picture-card i {
    padding-top: 110px;
  }
</style>
