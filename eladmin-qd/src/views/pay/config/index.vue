<template>
  <el-tabs v-model="activeName" style="padding-left: 5px;">
    <el-tab-pane label="收款二维码配置" name="first">
      <ConfigPic/>
    </el-tab-pane>
    <el-tab-pane label="接口密钥配置" name="second">
      <Config/>
    </el-tab-pane>
    <el-tab-pane label="使用说明" name="third">
      <Help/>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import ConfigPic from './module/configPic'
import Config from './module/config'
import Help from './module/help'
export default {
  components: { ConfigPic, Config, Help },
  data() {
    return {
      activeName: 'first'
    }
  }
}
</script>

<style scoped>
</style>
