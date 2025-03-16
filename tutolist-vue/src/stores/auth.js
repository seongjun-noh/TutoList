import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import axios from 'axios'
import { useUserStore } from './user'

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('token'))
  const isAuthenticated = computed(() => !!token.value)
  
  // axios 인터셉터 설정
  axios.interceptors.request.use(config => {
    if (token.value) {
      config.headers.Authorization = `Bearer ${token.value}`
    }
    return config
  })
  
  async function login(email, password) {
    try {
      const response = await axios.post('/api/auth/login', {
        email,
        password
      })
      
      token.value = response.data.token
      localStorage.setItem('token', token.value)
      
      // 로그인 후 사용자 정보 가져오기
      const userStore = useUserStore()
      await userStore.fetchUserInfo(true)
      
      return response.data
    } catch (error) {
      console.error('로그인 실패:', error)
      throw error
    }
  }
  
  async function logout() {
    try {
      await axios.post('/api/auth/logout')
    } catch (error) {
      console.error('로그아웃 API 호출 실패:', error)
    } finally {
      // 로컬 상태 초기화
      token.value = null
      localStorage.removeItem('token')
      
      // 사용자 정보 초기화
      const userStore = useUserStore()
      userStore.clearUser()
    }
  }
  
  function checkAuth() {
    if (!token.value) {
      return false
    }
    return true
  }
  
  return {
    token,
    isAuthenticated,
    login,
    logout,
    checkAuth
  }
}) 