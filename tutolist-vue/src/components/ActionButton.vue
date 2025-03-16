<template>
  <div class="action-button" @click="$emit('click')">
    <div class="action-icon">{{ icon }}</div>
    <div class="action-text">{{ text }}</div>
  </div>
</template>

<script setup>
defineProps({
  icon: {
    type: String,
    required: true
  },
  text: {
    type: String,
    required: true
  }
})

defineEmits(['click'])
</script>

<style lang="scss" scoped>
.action-button {
  background-color: var(--color-primary-light);
  border-radius: var(--radius-sm);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 16px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.action-button:hover {
  background-color: #D7EAD9;
}

.action-icon {
  font-size: 24px;
  color: var(--color-primary);
  margin-bottom: 8px;
}

.action-text {
  font-size: 14px;
  color: var(--color-primary);
}
</style>