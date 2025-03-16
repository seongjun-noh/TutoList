// src/plugins/axiosInstance.js

import axios from 'axios'

// API 기본 URL 설정
const API_URL = process.env.VUE_APP_API_URL || 'http://localhost:8080/api'

// axios 인스턴스 생성
const instance = axios.create({
  baseURL: API_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 개발 환경에서 API URL 로깅
if (process.env.NODE_ENV === 'development') {
  console.log('API URL:', API_URL)
}

// 요청 인터셉터
instance.interceptors.request.use(
  (config) => {
    const accessToken = localStorage.getItem('accessToken')
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// 응답 인터셉터
instance.interceptors.response.use(
  (response) => {
    return response
  },
  (error) => {
    if (error.response) {
      // 서버 응답이 있는 경우
      const { status, data } = error.response
      
      if (status === 401) {
        // 인증 오류 처리
        localStorage.removeItem('token')
        window.location.href = '/login'
      }
      
      return Promise.reject(data)
    }
    
    // 서버 응답이 없는 경우 (네트워크 오류 등)
    return Promise.reject({
      success: false,
      message: '서버와 통신할 수 없습니다. 잠시 후 다시 시도해주세요.',
      data: null
    })
  }
)

export default instance
