import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'

import './assets/styles/reset.css'
import './assets/styles/common.css'

import axios from './plugins/axios'
import FontAwesomeIcon from './plugins/fontawesome'

// 앱 생성 및 플러그인 등록
const app = createApp(App)
const pinia = createPinia()

// Font awesome icon
app.component('font-awesome-icon', FontAwesomeIcon)

// 라우터
app.use(pinia)
app.use(router)

// Axios
app.config.globalProperties.$axios = axios

// 마운트
app.mount('#app')
