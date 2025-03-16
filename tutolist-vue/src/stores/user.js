import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import http from '@/plugins/axios'

export const useUserStore = defineStore('user', () => {
  const user = ref(null)
  const loading = ref(false)
  const error = ref(null)
  const lastFetchTime = ref(null)
  
  // 캐시 유효 시간 (5분)
  const CACHE_DURATION = 5 * 60 * 1000
  
  const isAuthenticated = computed(() => !!user.value)
  
  const shouldRefetch = computed(() => {
    if (!lastFetchTime.value) return true
    return Date.now() - lastFetchTime.value > CACHE_DURATION
  })
  
  async function fetchUserInfo(force = false) {
    // 캐시가 유효하고 강제 새로고침이 아닌 경우
    if (!force && !shouldRefetch.value && user.value) {
      return user.value
    }
    
    loading.value = true
    error.value = null
    
    try {
      const response = await http.get('/auth/me')
      const apiResponse = response.data

      if (!apiResponse.success) {
        throw new Error(apiResponse.message || '사용자 정보를 가져오는데 실패했습니다')
      }

      user.value = apiResponse.data
      lastFetchTime.value = Date.now()
      return user.value
    } catch (err) {
      error.value = err.response?.data?.message || err.message || '사용자 정보를 가져오는데 실패했습니다'
      throw error.value
    } finally {
      loading.value = false
    }
  }
  
  function clearUser() {
    user.value = null
    lastFetchTime.value = null
    error.value = null
  }
  
  return {
    user,
    loading,
    error,
    isAuthenticated,
    fetchUserInfo,
    clearUser
  }
}) 