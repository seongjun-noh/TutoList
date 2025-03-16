<template>
  <div class="schedule-item">
    <div class="schedule-info">
      <div class="schedule-time">{{ time }}</div>
      <div class="schedule-details">{{ student }} · {{ subject }}</div>
    </div>
    <div class="schedule-status">{{ status }}</div>
    <div class="schedule-action">➡️</div>
  </div>
</template>

<script setup>
defineProps({
  time: {
    type: String,
    required: true
  },
  student: {
    type: String,
    required: true
  },
  subject: {
    type: String,
    required: true
  },
  status: {
    type: String,
    default: '예정됨'
  }
})
</script>

<style lang="scss" scoped>
.schedule-item {
  background-color: var(--color-bg-secondary);
  border-radius: var(--radius-lg);
  padding: var(--spacing-md) var(--spacing-lg);
  display: flex;
  justify-content: space-between;
  align-items: center;
  transition: all var(--transition-normal) var(--ease-out);
  border: 1px solid transparent;
  position: relative;
  overflow: hidden;
}

.schedule-item::before {
  content: '';
  position: absolute;
  left: 0;
  top: 0;
  height: 100%;
  width: 4px;
  background-color: var(--color-primary);
  opacity: 0;
  transition: opacity var(--transition-normal) var(--ease-out);
}

.schedule-item:hover {
  background-color: var(--color-bg-primary);
  border-color: var(--color-border);
  box-shadow: var(--shadow-md);
  transform: translateX(4px);
}

.schedule-item:hover::before {
  opacity: 1;
}

.schedule-info {
  display: flex;
  flex-direction: column;
  flex: 1;
}

.schedule-time {
  font-weight: bold;
  font-size: var(--font-size-sm);
  margin-bottom: var(--spacing-xs);
  color: var(--color-text-primary);
  display: flex;
  align-items: center;
}

.schedule-time::before {
  content: '🕒';
  margin-right: var(--spacing-xs);
  font-size: var(--font-size-md);
}

.schedule-details {
  font-size: var(--font-size-sm);
  color: var(--color-text-secondary);
  display: flex;
  align-items: center;
}

.schedule-details::before {
  content: '📚';
  margin-right: var(--spacing-xs);
  font-size: var(--font-size-md);
}

.schedule-status {
  font-size: var(--font-size-xs);
  background-color: var(--color-primary-light);
  color: var(--color-primary-dark);
  padding: var(--spacing-xs) var(--spacing-md);
  border-radius: var(--radius-full);
  font-weight: 500;
  margin: 0 var(--spacing-md);
  transition: all var(--transition-normal) var(--ease-out);
}

.schedule-item:hover .schedule-status {
  background-color: var(--color-primary);
  color: var(--color-text-inverse);
}

.schedule-action {
  width: 32px;
  height: 32px;
  border-radius: var(--radius-full);
  background-color: var(--color-bg-primary);
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all var(--transition-normal) var(--ease-out);
  border: 1px solid var(--color-border);
}

.schedule-item:hover .schedule-action {
  background-color: var(--color-primary);
  border-color: var(--color-primary);
  transform: scale(1.1);
}

.schedule-item:hover .schedule-action {
  color: var(--color-text-inverse);
}

@media (max-width: 768px) {
  .schedule-item {
    padding: var(--spacing-sm);
    flex-wrap: wrap;
  }
  
  .schedule-info {
    width: 100%;
    margin-bottom: var(--spacing-xs);
  }
  
  .schedule-status {
    margin: var(--spacing-xs) 0;
  }
}
</style>