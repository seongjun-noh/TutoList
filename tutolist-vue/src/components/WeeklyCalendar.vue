<template>
  <div class="calendar-view">
    <div class="weekday">
      <div 
        v-for="(item, index) in weekData" 
        :key="index" 
        class="weekday-item"
      >
        {{ item.day }}
      </div>
    </div>
    <div class="calendar-days">
      <div 
        v-for="(item, index) in weekData" 
        :key="index" 
        class="day-item"
      >
        <div :class="['day-circle', { active: item.isToday }]">
          {{ item.date }}
        </div>
        <div class="day-dots">
          <div 
            v-for="i in item.count" 
            :key="i" 
            class="day-dot"
          ></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  weekData: {
    type: Array,
    required: true
  }
})
</script>

<style lang="scss" scoped>
.calendar-view {
  background-color: var(--color-bg-secondary);
  border-radius: var(--radius-sm);
  padding: 16px;
  margin-bottom: 16px;
  flex-grow: 1;
}

.weekday {
  display: flex;
  justify-content: space-between;
  margin-bottom: 16px;
}

.weekday-item {
  width: 40px;
  text-align: center;
  font-size: 12px;
  color: var(--color-text-secondary);
}

.calendar-days {
  display: flex;
  justify-content: space-between;
}

.day-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.day-circle {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: var(--color-bg-primary);
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 8px;
  font-size: 14px;
  color: var(--color-text-secondary);
}

.day-circle.active {
  background-color: var(--color-primary);
  color: white;
}

.day-dots {
  display: flex;
  gap: 4px;
}

.day-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: var(--color-primary);
}
</style>

