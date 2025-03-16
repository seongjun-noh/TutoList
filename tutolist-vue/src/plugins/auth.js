// src/plugins/auth.js

import axios from 'axios'

// 토큰 갱신 중 여부와 대기 요청들을 저장할 변수
let isRefreshing = false
let refreshSubscribers = []

// 토큰 갱신이 완료되면 대기 중인 요청에 새로운 토큰을 전달하는 함수
export function onRefreshed(newToken) {
  refreshSubscribers.forEach((callback) => callback(newToken))
  refreshSubscribers = []
}

// 대기 요청을 추가하는 함수
export function addRefreshSubscriber(callback) {
  refreshSubscribers.push(callback)
}

// 토큰 갱신 요청 함수 (axios 인스턴스를 파라미터로 전달)
export function refreshToken(axiosInstance) {
  const refresh_token = localStorage.getItem('refreshToken')
  // refresh token 엔드포인트에 POST 요청 (API 스펙에 맞게 수정)
  return axiosInstance.post('/auth/refresh', { refresh_token })
}

// 로그인 성공 시 토큰을 저장하는 함수
export function setAuthToken(accessToken, refreshToken, storageType = 'localStorage') {
  const storage = storageType === 'sessionStorage' ? sessionStorage : localStorage;
  
  storage.setItem('accessToken', accessToken)
  if (refreshToken) {
    storage.setItem('refreshToken', refreshToken)
  }
  axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`
}

// isRefreshing 변수 getter/setter
export function getIsRefreshing() {
  return isRefreshing
}

export function setIsRefreshing(value) {
  isRefreshing = value
}

// 초기 로그인 상태 확인
export const checkInitialAuth = () => {
  const accessToken = localStorage.getItem('accessToken') || sessionStorage.getItem('accessToken')
  const refreshToken = localStorage.getItem('refreshToken') || sessionStorage.getItem('refreshToken')
  
  if (accessToken && refreshToken) {
    setAuthToken(accessToken, refreshToken)
    return true
  }
  return false
}

// 로그아웃 함수
export const clearAuth = () => {
  localStorage.removeItem('accessToken')
  localStorage.removeItem('refreshToken')
  sessionStorage.removeItem('accessToken')
  sessionStorage.removeItem('refreshToken')
  delete axios.defaults.headers.common['Authorization']
}
