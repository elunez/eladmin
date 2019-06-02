<template>
  <div class="app-container">
    <p class="warn-content">
      Markdown 基于
      <a href="https://github.com/hinesboy/mavonEditor" target="_blank">mavonEditor</a>
    </p>
    <mavon-editor ref="md" :style="'height:' + height" @imgAdd="imgAdd" @imgDel="imgDel"/>
  </div>
</template>

<script>
import axios from 'axios'
import { mapGetters } from 'vuex'
import { getToken } from '@/utils/auth'
import { del } from '@/api/picture'
export default {
  name: 'Markdown',
  data() {
    return {
      height: document.documentElement.clientHeight - 200 + 'px',
      data: null,
      images: {}
    }
  },
  computed: {
    ...mapGetters([
      'imagesUploadApi'
    ])
  },
  mounted() {
    this.$refs.md.$refs.toolbar_left.img_file = []
    const that = this
    window.onresize = function temp() {
      that.height = document.documentElement.clientHeight - 200 + 'px'
    }
  },
  methods: {
    imgAdd(pos, $file) {
      var formdata = new FormData()
      formdata.append('file', $file)
      axios({
        url: this.imagesUploadApi,
        method: 'post',
        data: formdata,
        headers: { 'Content-Type': 'multipart/form-data', 'Authorization': 'Bearer ' + getToken() }
      }).then((data) => {
        this.data = data.data
        this.$refs.md.$img2Url(pos, this.data.data[0])

        this.images[this.data.data[0]] = this.data
      }).catch((error) => {
        console.log('image upload error', error)
        this.$refs.md.$refs.toolbar_left.$imgDel(pos)
      })
    },
    imgDel(file, pos) {
      const image = this.images[file[1]]
      if (image) {
        del(image.id).then(res => {
        }).catch(err => {
          console.log(err.response.data.message)
        })
      }
    }
  }
}
</script>

<style scoped>
</style>
