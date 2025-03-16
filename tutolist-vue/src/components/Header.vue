<template>
  <header class="header">
    <h1 class="page-title">{{ title }}</h1>
    <div class="header-right">
      <div class="date-display">{{ currentDate }}</div>
      <div class="notification">
        <i>🔔</i>
        <div v-if="notificationCount > 0" class="notification-badge">
          {{ notificationCount > 9 ? '9+' : notificationCount }}
        </div>
      </div>
      <button 
        class="logout-button" 
        @click="handleLogout"
        @keydown.enter="handleLogout"
        tabindex="0"
        aria-label="로그아웃"
      >
        <font-awesome-icon :icon="['fas', 'sign-out-alt']" />
        <span>로그아웃</span>
      </button>
    </div>
  </header>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { library } from '@fortawesome/fontawesome-svg-core'
import { faSignOutAlt } from '@fortawesome/free-solid-svg-icons'
import http from '@/plugins/axios'
import { clearAuth } from '@/plugins/auth'

library.add(faSignOutAlt)

const router = useRouter()

defineProps({
  title: {
    type: String,
    required: true
  },
  notificationCount: {
    type: Number,
    default: 0
  }
})

const currentDate = ref('')

// 현재 날짜를 YYYY년 MM월 DD일 요일 형식으로 반환
const formatDate = () => {
  const now = new Date()
  const year = now.getFullYear()
  const month = now.getMonth() + 1
  const day = now.getDate()
  const weekDays = ['일', '월', '화', '수', '목', '금', '토']
  const weekDay = weekDays[now.getDay()]
  
  return `${year}년 ${month}월 ${day}일 ${weekDay}요일`
}

const handleLogout = async () => {
  try {
    const refreshToken = localStorage.getItem('refreshToken') || sessionStorage.getItem('refreshToken')
    
    // 서버에 로그아웃 요청 (refreshToken 포함)
    await http.post('/auth/logout', {
      refreshToken
    })
  } catch (error) {
    console.error('로그아웃 요청 실패:', error)
  } finally {
    // 로컬 토큰 제거
    clearAuth()
    
    // 로그인 페이지로 리다이렉트
    router.push('/login')
  }
}

onMounted(() => {
  currentDate.value = formatDate()
})
</script>

<style lang="scss" scoped>
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 24px;
  padding: 16px 0;
  border-bottom: 1px solid var(--color-border);
}

.page-title {
  font-size: 24px;
  font-weight: bold;
  color: var(--color-text-primary);
}

.header-right {
  display: flex;
  align-items: center;
  gap: 24px;
}

.date-display {
  font-size: 14px;
  color: var(--color-text-secondary);
}

.notification {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: var(--color-bg-secondary);
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  cursor: pointer;
}

.notification-badge {
  position: absolute;
  top: 0;
  right: 0;
  width: 16px;
  height: 16px;
  border-radius: 50%;
  background-color: #FF5252;
  color: white;
  font-size: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.logout-button {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 16px;
  border: none;
  border-radius: var(--radius-md);
  background-color: var(--color-bg-accent);
  color: var(--color-text-secondary);
  font-size: 14px;
  font-weight: var(--font-medium);
  cursor: pointer;
  transition: all 0.2s ease;

  &:hover {
    background-color: var(--color-danger);
    color: white;
  }

  &:focus {
    outline: none;
    box-shadow: 0 0 0 2px var(--color-danger);
  }
}
</style>