<!-- src/App.vue -->
<template>
  <div>
    <router-view />
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { checkInitialAuth } from '@/plugins/auth'

const router = useRouter()

onMounted(() => {
  const isAuthenticated = checkInitialAuth()
  if (!isAuthenticated && router.currentRoute.value.name !== 'login') {
    router.push('/login')
  }
})
</script>

<style scoped>
nav {
  margin-bottom: 16px;
}
</style>
