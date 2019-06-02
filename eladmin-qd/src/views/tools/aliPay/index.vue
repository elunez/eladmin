<template>
  <el-tabs v-model="activeName" style="padding-left: 5px;">
    <el-tab-pane label="参数配置" name="first">
      <Config/>
    </el-tab-pane>
    <el-tab-pane label="支付测试" name="second">
      <ToPay/>
    </el-tab-pane>
    <el-tab-pane label="使用说明" name="third">
      <Description/>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import Config from './module/config'
import ToPay from './module/toPay'
import Description from './module/description'
export default {
  components: { Config, ToPay, Description },
  data() {
    return {
      activeName: 'second'
    }
  }
}
</script>

<style scoped>
</style>
