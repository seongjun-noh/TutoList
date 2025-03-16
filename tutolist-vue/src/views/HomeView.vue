<!-- App.vue -->
<template>
  <!-- 통계 카드 섹션 -->
  <div class="grid grid-4">
    <StatCard 
      v-for="stat in stats" 
      :key="stat.id" 
      :value="stat.value"
      :label="stat.label"
      :icon="stat.icon"
      :type="stat.type"
    />
  </div>
  
  <!-- 두 열 레이아웃 (오늘 일정 & 주간 일정) -->
  <div class="grid grid-2">
    <div class="card">
      <h2 class="title">오늘의 일정</h2>
      <div v-if="todaySchedules.length > 0" class="list">
        <ScheduleItem 
          v-for="schedule in todaySchedules" 
          :key="schedule.id"
          :time="schedule.time"
          :student="schedule.student"
          :subject="schedule.subject"
          :status="schedule.status"
        />
      </div>
      <EmptyState v-else message="오늘은 예정된 수업이 없습니다." />
      <button class="btn btn-secondary">전체 일정 보기</button>
    </div>
    
    <div class="card">
      <h2 class="title">이번 주 일정</h2>
      <WeeklyCalendar :week-data="weekData" />
      <button class="btn btn-secondary">주간 일정 보기</button>
    </div>
  </div>
  
  <!-- 두 열 레이아웃 (알림 & 빠른 액션) -->
  <div class="grid grid-2">
    <div class="card">
      <h2 class="title">알림 및 공지</h2>
      <div class="list">
        <NoticeItem 
          v-for="notice in notices" 
          :key="notice.id"
          :title="notice.title"
          :content="notice.content"
          :date="notice.date"
          :unread="notice.unread"
        />
      </div>
      <button class="btn btn-secondary">모든 알림 보기</button>
    </div>
    
    <div class="card">
      <h2 class="title">빠른 액션</h2>
      <div class="quick-actions">
        <ActionButton 
          v-for="action in quickActions" 
          :key="action.id"
          :icon="action.icon"
          :text="action.text"
          @click="action.onClick"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import StatCard from '@/components/StatCard.vue'
import ScheduleItem from '@/components/ScheduleItem.vue'
import WeeklyCalendar from '@/components/WeeklyCalendar.vue'
import NoticeItem from '@/components/NoticeItem.vue'
import ActionButton from '@/components/ActionButton.vue'
import EmptyState from '@/components/EmptyState.vue'

// 통계 데이터
const stats = ref([
  { id: 1, value: 12, label: '총 학생 수', icon: '👨‍🎓', type: 'default' },
  { id: 2, value: 8, label: '이번 주 수업', icon: '📅', type: 'default' },
  { id: 3, value: 3, label: '완료한 수업', icon: '✓', type: 'default' },
  { id: 4, value: 5, label: '미완료 과제', icon: '⚠️', type: 'warning' },
])

// 오늘의 일정 데이터
const todaySchedules = ref([
  { 
    id: 1, 
    time: '15:00 - 16:30', 
    student: '김학생', 
    subject: '고등수학', 
    status: '예정됨' 
  },
  { 
    id: 2, 
    time: '18:00 - 19:30', 
    student: '이학생', 
    subject: '중등과학', 
    status: '예정됨' 
  },
])

// 주간 일정 데이터
const weekData = ref([
  { day: '월', date: 4, count: 2, isToday: false },
  { day: '화', date: 5, count: 1, isToday: false },
  { day: '수', date: 6, count: 2, isToday: false },
  { day: '목', date: 7, count: 1, isToday: false },
  { day: '금', date: 8, count: 2, isToday: true },
])

// 알림 데이터
const notices = ref([
  { 
    id: 1, 
    title: '이학생 수업 일정 변경', 
    content: '금요일 수업이 토요일로 변경되었습니다.', 
    date: '오늘', 
    unread: true 
  },
  { 
    id: 2, 
    title: '김학생 과제 제출', 
    content: '수학 문제집 Chapter 5를 완료했습니다.', 
    date: '어제', 
    unread: false 
  },
])

// 빠른 액션 데이터
const quickActions = ref([
  { 
    id: 1, 
    icon: '+', 
    text: '새 학생', 
    onClick: () => console.log('새 학생 추가') 
  },
  { 
    id: 2, 
    icon: '+', 
    text: '새 수업', 
    onClick: () => console.log('새 수업 추가') 
  },
  { 
    id: 3, 
    icon: '✎', 
    text: '진도 기록', 
    onClick: () => console.log('진도 기록') 
  },
  { 
    id: 4, 
    icon: '📊', 
    text: '리포트', 
    onClick: () => console.log('리포트 생성') 
  },
])
</script>

<style lang="scss">
.quick-actions {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 1fr 1fr;
  gap: var(--spacing-4);
  height: 100%;
  min-height: 200px;
}

/* 반응형 스타일은 common.css에서 처리됨 */
</style>