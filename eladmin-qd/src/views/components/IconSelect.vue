<template>
  <div class="icons-container">
    <p class="warn-content">
      使用教程参考 <a href="https://panjiachen.github.io/vue-element-admin-site/guide/advanced/icon.html" target="_blank">Documentation</a>
    </p>
    <div class="icons-wrapper">
      <div v-for="item of iconsMap" :key="item" @click="handleClipboard(generateIconCode(item),$event)">
        <el-tooltip placement="top">
          <div slot="content">
            {{ generateIconCode(item) }}
          </div>
          <div class="icon-item">
            <svg-icon :icon-class="item" class-name="disabled" />
            <span>{{ item }}</span>
          </div>
        </el-tooltip>
      </div>
    </div>
  </div>
</template>

<script>
import icons from '@/components/IconSelect/requireIcons'
import clipboard from '@/utils/clipboard'

export default {
  name: 'Icons',
  data() {
    return {
      iconsMap: icons
    }
  },
  methods: {
    generateIconCode(symbol) {
      return `<svg-icon icon-class="${symbol}" />`
    },
    handleClipboard(text, event) {
      clipboard(text, event)
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.icons-container {
  margin: 10px 20px 0;
  overflow: hidden;
  .icons-wrapper {
    margin: 0 auto;
  }
  .icon-item {
    margin: 20px;
    height: 110px;
    text-align: center;
    width: 110px;
    float: left;
    font-size: 30px;
    color: #24292e;
    cursor: pointer;
  }
  span {
    display: block;
    font-size: 24px;
    margin-top: 10px;
  }
  .disabled{
    pointer-events: none;
  }
}
</style>
