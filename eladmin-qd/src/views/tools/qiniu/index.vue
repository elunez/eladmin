<template>
  <el-tabs v-model="activeName" style="padding-left: 5px;">
    <el-tab-pane label="存储配置" name="first">
      <Config/>
    </el-tab-pane>
    <el-tab-pane label="文件列表" name="second">
      <List/>
    </el-tab-pane>
    <el-tab-pane label="使用说明" name="third">
      <Description/>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import Config from './module/config'
import List from './module/list'
import Description from './module/description'
export default {
  components: { Config, List, Description },
  data() {
    return {
      activeName: 'second'
    }
  }
}
</script>

<style scoped>
</style>
