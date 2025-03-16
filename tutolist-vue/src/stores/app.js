import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAppStore = defineStore('app', () => {
  // 사이드바 상태
  const isSidebarOpen = ref(true)
  
  // 알림 상태
  const notifications = ref([])
  const unreadCount = ref(0)
  
  // 로딩 상태
  const isLoading = ref(false)
  
  // 에러 상태
  const error = ref(null)
  
  function toggleSidebar() {
    isSidebarOpen.value = !isSidebarOpen.value
  }
  
  function showLoading() {
    isLoading.value = true
  }
  
  function hideLoading() {
    isLoading.value = false
  }
  
  function setError(message) {
    error.value = message
  }
  
  function clearError() {
    error.value = null
  }
  
  function addNotification(notification) {
    notifications.value.unshift({
      id: Date.now(),
      timestamp: new Date(),
      read: false,
      ...notification
    })
    updateUnreadCount()
  }
  
  function markNotificationAsRead(id) {
    const notification = notifications.value.find(n => n.id === id)
    if (notification) {
      notification.read = true
      updateUnreadCount()
    }
  }
  
  function updateUnreadCount() {
    unreadCount.value = notifications.value.filter(n => !n.read).length
  }
  
  return {
    // 상태
    isSidebarOpen,
    notifications,
    unreadCount,
    isLoading,
    error,
    
    // 메서드
    toggleSidebar,
    showLoading,
    hideLoading,
    setError,
    clearError,
    addNotification,
    markNotificationAsRead
  }
}) 