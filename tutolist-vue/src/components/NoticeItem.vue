<template>
  <div class="notice-item" :class="{ 'has-indicator': unread }">
    <div v-if="unread" class="notice-indicator"></div>
    <div class="notice-title">{{ title }}</div>
    <div class="notice-content">{{ content }}</div>
    <div class="notice-date">{{ date }}</div>
  </div>
</template>

<script setup>
defineProps({
  title: {
    type: String,
    required: true
  },
  content: {
    type: String,
    required: true
  },
  date: {
    type: String,
    required: true
  },
  unread: {
    type: Boolean,
    default: false
  }
})
</script>

<style lang="scss" scoped>
.notice-item {
  background-color: var(--color-bg-secondary);
  border-radius: var(--radius-sm);
  padding: 12px 16px;
  position: relative;
}

.notice-indicator {
  position: absolute;
  left: 8px;
  top: 50%;
  transform: translateY(-50%);
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: #FF5252;
}

.notice-item.has-indicator {
  padding-left: 24px;
}

.notice-title {
  font-weight: bold;
  font-size: 14px;
  margin-bottom: 4px;
  color: var(--color-text-primary);
}

.notice-content {
  font-size: 12px;
  color: var(--color-text-secondary);
}

.notice-date {
  position: absolute;
  top: 12px;
  right: 16px;
  font-size: 12px;
  color: var(--color-text-tertiary);
}
</style>
