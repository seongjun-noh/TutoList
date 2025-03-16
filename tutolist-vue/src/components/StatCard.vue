<template>
  <div class="card stat-card" :class="{ 'warning': type === 'warning' }">
    <div class="stat-info">
      <div class="stat-value">{{ value }}</div>
      <div class="stat-label">{{ label }}</div>
    </div>
    <div class="stat-icon">{{ icon }}</div>
  </div>
</template>

<script setup>
defineProps({
  value: {
    type: [Number, String],
    required: true
  },
  label: {
    type: String,
    required: true
  },
  icon: {
    type: String,
    required: true
  },
  type: {
    type: String,
    default: 'default'
  }
})
</script>

<style lang="scss" scoped>
.card {
  background-color: var(--color-bg-primary);
  border-radius: var(--radius-lg);
  padding: var(--spacing-lg);
  box-shadow: var(--shadow);
  transition: all var(--transition-normal) var(--ease-out);
  position: relative;
  overflow: hidden;
}

.card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 4px;
  background: linear-gradient(to right, var(--color-primary-light), var(--color-primary));
  opacity: 0;
  transition: opacity var(--transition-normal) var(--ease-out);
}

.card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-lg);
}

.card:hover::before {
  opacity: 1;
}

.stat-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.stat-info {
  display: flex;
  flex-direction: column;
  position: relative;
  z-index: 1;
}

.stat-value {
  font-size: var(--font-size-2xl);
  font-weight: bold;
  margin-bottom: var(--spacing-xs);
  color: var(--color-text-primary);
  transition: color var(--transition-normal) var(--ease-out);
}

.stat-label {
  font-size: var(--font-size-sm);
  color: var(--color-text-secondary);
  transition: color var(--transition-normal) var(--ease-out);
}

.stat-icon {
  width: 48px;
  height: 48px;
  border-radius: var(--radius-full);
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: var(--color-primary-light);
  color: var(--color-primary);
  font-size: var(--font-size-xl);
  transition: all var(--transition-normal) var(--ease-out);
  position: relative;
  z-index: 1;
}

.card:hover .stat-icon {
  transform: scale(1.1) rotate(10deg);
  background-color: var(--color-primary);
  color: var(--color-text-inverse);
}

.warning::before {
  background: linear-gradient(to right, var(--color-warning), var(--color-accent));
}

.warning .stat-icon {
  background-color: var(--color-accent-light);
  color: var(--color-accent);
}

.warning:hover .stat-icon {
  background-color: var(--color-accent);
  color: var(--color-text-inverse);
}

@media (max-width: 768px) {
  .card {
    padding: var(--spacing-md);
  }
  
  .stat-value {
    font-size: var(--font-size-xl);
  }
  
  .stat-icon {
    width: 40px;
    height: 40px;
    font-size: var(--font-size-lg);
  }
}
</style>