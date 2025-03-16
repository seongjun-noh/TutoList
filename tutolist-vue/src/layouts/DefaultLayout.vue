<!-- DefaultLayout.vue -->
<template>
  <div class="layout">
    <Sidebar 
      :active-menu="activeMenu" 
      :user="userStore.user"
      :loading="userStore.loading"
    />
    <div class="layout-content">
      <Header :title="title" :notification-count="notificationCount" />
      <main class="layout-main">
        <router-view />
      </main>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import Header from '@/components/Header.vue'
import Sidebar from '@/components/Sidebar.vue'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()

defineProps({
  activeMenu: {
    type: String,
    required: true
  },
  title: {
    type: String,
    required: true
  },
  notificationCount: {
    type: Number,
    default: 0
  }
})

onMounted(async () => {
  try {
    await userStore.fetchUserInfo()
  } catch (error) {
    console.error('사용자 정보를 가져오는데 실패했습니다:', error)
  }
})
</script>

<style lang="scss">
.layout {
  display: flex;
  min-height: 100vh;
  background-color: var(--color-bg-secondary);
}

.layout-content {
  flex: 1;
  margin-left: var(--sidebar-width);
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.layout-main {
  flex: 1;
  padding: var(--spacing-6);
  background-color: var(--color-bg-secondary);
  overflow-y: auto;
}

/* 반응형 스타일 */
@media (max-width: 768px) {
  .layout-content {
    margin-left: 0;
  }
  
  .layout-main {
    padding: var(--spacing-4);
  }
}
</style> 