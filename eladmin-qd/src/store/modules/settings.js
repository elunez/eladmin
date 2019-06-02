import Config from '@/config'
import variables from '@/styles/element-variables.scss'

const settings = {
  state: {
    showRightPanel: false,
    tagsView: Config.tagsView,
    fixedHeader: Config.fixedHeader,
    sidebarLogo: Config.sidebarLogo,
    theme: variables.theme,
    settingBtn: Config.settingBtn,
    uniqueOpened: Config.uniqueOpened
  },
  mutations: {
    CHANGE_SETTING: (state, { key, value }) => {
      if (state.hasOwnProperty(key)) {
        state[key] = value
      }
    }
  },
  actions: {
    changeSetting({ commit }, data) {
      commit('CHANGE_SETTING', data)
    }
  }
}
export default settings
