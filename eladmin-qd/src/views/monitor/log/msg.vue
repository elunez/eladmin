<template>
  <div :style="'min-width:' + width" class="container">
    <el-tooltip :content="content" class="lock item" effect="dark" placement="left">
      <el-button type="info" size="mini" circle @click="doLock"><svg-icon :icon-class="ico"/></el-button>
    </el-tooltip>
    <div id="console" :style="'height:'+ height" class="console">
      <div v-for="item in data" :key="item.time">
        <span>{{ item.name }}</span>
        <span style="color:#CD0066 ">{{ parseTime(item.timestamp)+' ' }}</span>
        <span style="color: #00CD00">{{ item.threadName+' ' }}</span>
        <span :style="'color:'+ getColor(item.level) ">
          {{ item.level+' ' }}
        </span>
        <span style="color: #DE00CC">{{ item.className+' ' }}</span>
        <span v-html="item.body"/>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'
import { parseTime } from '@/utils/index'
export default {
  name: 'Msg',
  data() {
    return {
      ico: 'unlock', unlock: true, content: '锁定滚动条',
      height: document.documentElement.clientHeight - 140 + 'px;',
      width: document.documentElement.clientWidth - 210 + 'px;',
      data: [{ name: 'xpay-', timestamp: new Date(), threadName: 'system-prompt-message', level: 'INFO', className: 'me.zhengjie.AppRun' + ' :', body: 'Welcome, no log output' }],
      // level
      INFO: '#0000ff', WARN: '#FFFF00', ERROR: '#FF0000', DEBUG: '#DEA000'
    }
  },
  computed: {
    ...mapGetters([
      'socketApi'
    ])
  },
  // 监听控制滚动条
  watch: {
    data: {
      handler(val, oldVal) {
        this.$nextTick(() => {
          if (this.unlock) {
            var div = document.getElementById('console')
            div.scrollTop = div.scrollHeight
          }
        })
      }
    }
  },
  mounted: function() {
    this.initWebSocket()
    const that = this
    window.onresize = function temp() {
      that.height = document.documentElement.clientHeight - 94.5 + 'px;'
    }
  },
  beforeDestroy: function() {
    // 页面离开时断开连接,清除定时器
    this.disconnect()
    window.clearInterval(this.timer)
  },
  methods: {
    parseTime,
    initWebSocket() {
      this.connection(this)
    },
    connection(_this) {
      const socket = new SockJS(this.socketApi)// 连接服务端提供的通信接口，连接以后才可以订阅广播消息和个人消息
      // 获取STOMP子协议的客户端对象
      this.stompClient = Stomp.over(socket)
      // 定义客户端的认证信息,按需求配置
      var headers = {
        token: 'k1'
      }
      // 向服务器发起websocket连接
      this.stompClient.connect(headers, (frame) => {
        this.stompClient.subscribe('/topic/logMsg', (msg) => { // 订阅服务端提供的某个topic
          var content = JSON.parse(msg.body)
          content.name = 'xpay-'
          console.log(_this.data)
          this.data.push(content)
        })
      }, (err) => {
        // 连接发生错误时的处理函数
        console.log(err)
      })
    },
    // 断开连接
    disconnect() {
      if (this.stompClient != null) {
        this.stompClient.disconnect()
        window.clearInterval(this.timer)
      }
    },
    getColor(level) {
      if (level === 'INFO') {
        return this.INFO
      } else if (level === 'WARN') {
        return this.WARN
      } else if (level === 'DEBUG') {
        return this.DEBUG
      } else {
        return this.ERROR
      }
    },
    doLock() {
      if (this.unlock) {
        this.content = '解除锁定'
        this.ico = 'lock'
      } else {
        this.content = '锁定滚动条'
        this.ico = 'unlock'
      }
      this.unlock = !this.unlock
    }
  }
}
</script>

<style scoped>
  button,input,textarea {
    outline: 0
  }

  .container {
    width: 100%;
    margin: 5px
  }

  .container .console {
    font-family: "Interstate", "Hind", -apple-system, BlinkMacSystemFont, Segoe UI, Helvetica, Arial, sans-serif, Apple Color Emoji, Segoe UI Emoji, Segoe UI Symbol;
    overflow-y: scroll;
    background: #494949;
    color: #f7f7f7;
    padding: 10px;
    font-size: 14px;
    border-radius: 3px 1px 3px 3px;
  }

  .lock {
    position: fixed;
    right: 45px;
    bottom: 6.8%;
    z-index: 100000
  }
</style>
