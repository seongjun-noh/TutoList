<template>
  <aside class="sidebar">
    <div class="logo">
      <div class="logo-container">
        <span class="logo-text">TutoList</span>
      </div>
    </div>
    
    <nav class="nav-menu">
      <div 
        v-for="item in menuItems" 
        :key="item.id"
        :class="['nav-item', { active: activeMenu === item.id }]"
        @click="onClickMenu(item.to)"
      >
        <div class="nav-icon">
          <i>{{ item.icon }}</i>
        </div>
        <span class="nav-text">{{ item.text }}</span>
      </div>
    </nav>
    
    <div class="profile">
      <div v-if="loading" class="profile-loading">
        <div class="loading-spinner"></div>
      </div>
      <template v-else-if="user">
        <div class="profile-image">
          <img 
            v-if="user.profileImage" 
            :src="user.profileImage" 
            :alt="user.name"
          />
          <span v-else>{{ user.name[0] }}</span>
        </div>
        <div class="profile-info">
          <span class="profile-name">{{ user.name }}</span>
        </div>
      </template>
      <div v-else class="profile-error">
        <span class="text-muted">프로필을 불러올 수 없습니다</span>
      </div>
    </div>
  </aside>
</template>

<script setup>
import router from '@/router'
import { ref } from 'vue'

defineProps({
  activeMenu: {
    type: String,
    required: true
  },
  user: {
    type: Object,
    default: null
  },
  loading: {
    type: Boolean,
    default: false
  }
})

const menuItems = ref([
  { id: 'dashboard', icon: '📊', text: '대시보드' , to: '/'},
  { id: 'students', icon: '👨‍🎓', text: '학생 관리', to: '/students'},
  { id: 'schedule', icon: '📅', text: '수업 일정', to: '/lessons'},
  { id: 'settings', icon: '⚙️', text: '설정', to: '/settings'}
])

const onClickMenu = function(to) {
  router.push(to)
}
</script>

<style lang="scss" scoped>
.sidebar {
  width: 240px;
  background-color: var(--color-bg-primary);
  border-right: 1px solid var(--color-border);
  padding: var(--spacing-lg) 0;
  display: flex;
  flex-direction: column;
  position: fixed;
  height: 100vh;
  transition: transform var(--transition-normal) var(--ease-in-out);
  box-shadow: var(--shadow);
}

.logo {
  height: 48px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: var(--spacing-xl);
}

.logo-container {
  background: linear-gradient(135deg, var(--color-primary), var(--color-primary-dark));
  padding: var(--spacing-sm) var(--spacing-md);
  border-radius: var(--radius-md);
  box-shadow: var(--shadow-md);
  transition: transform var(--transition-fast) var(--ease-out);
}

.logo-container:hover {
  transform: translateY(-2px);
}

.logo-text {
  color: var(--color-text-inverse);
  font-weight: bold;
  font-size: var(--font-size-lg);
}

.nav-menu {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  padding: 0 var(--spacing-sm);
}

.nav-item {
  display: flex;
  align-items: center;
  padding: var(--spacing-md) var(--spacing-lg);
  margin-bottom: var(--spacing-sm);
  cursor: pointer;
  border-radius: var(--radius-lg);
  transition: all var(--transition-normal) var(--ease-in-out);
  position: relative;
  overflow: hidden;
}

.nav-item::before {
  content: '';
  position: absolute;
  left: 0;
  top: 0;
  height: 100%;
  width: 0;
  background-color: var(--color-primary-light);
  opacity: 0.1;
  transition: width var(--transition-normal) var(--ease-in-out);
}

.nav-item:hover::before {
  width: 100%;
}

.nav-item.active {
  background-color: var(--color-primary-light);
  border-radius: var(--radius-lg);
}

.nav-item.active .nav-text {
  color: var(--color-primary-dark);
  font-weight: bold;
}

.nav-item.active .nav-icon {
  background-color: var(--color-primary);
  color: var(--color-text-inverse);
  transform: scale(1.1);
}

.nav-icon {
  width: 32px;
  height: 32px;
  border-radius: var(--radius-full);
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: var(--spacing-md);
  background-color: var(--color-bg-secondary);
  transition: all var(--transition-normal) var(--ease-out);
}

.nav-text {
  color: var(--color-text-secondary);
  font-size: var(--font-size-md);
  transition: color var(--transition-normal) var(--ease-in-out);
}

.profile {
  margin-top: auto;
  padding: var(--spacing-md);
  display: flex;
  align-items: center;
  background-color: var(--color-bg-tertiary);
  border-radius: var(--radius-lg);
  margin: var(--spacing-md);
  transition: transform var(--transition-normal) var(--ease-out);
}

.profile:hover {
  transform: translateY(-2px);
}

.profile-loading {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  padding: var(--spacing-4);
}

.loading-spinner {
  width: 24px;
  height: 24px;
  border: 2px solid var(--color-bg-secondary);
  border-top-color: var(--color-primary);
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

.profile-error {
  width: 100%;
  text-align: center;
  padding: var(--spacing-4);
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.profile-image {
  width: 40px;
  height: 40px;
  border-radius: var(--radius-full);
  background-color: var(--color-primary-light);
  margin-right: var(--spacing-md);
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: var(--shadow-sm);
  overflow: hidden;
  
  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  
  span {
    color: var(--color-primary);
    font-weight: var(--font-bold);
    font-size: var(--text-lg);
  }
}

.profile-info {
  display: flex;
  flex-direction: column;
}

.profile-name {
  font-weight: bold;
  font-size: var(--font-size-sm);
  color: var(--color-text-primary);
  margin-bottom: var(--spacing-xs);
}

@media (max-width: 768px) {
  .sidebar {
    transform: translateX(-100%);
    z-index: 1000;
  }
  
  .sidebar.show {
    transform: translateX(0);
  }
}
</style>