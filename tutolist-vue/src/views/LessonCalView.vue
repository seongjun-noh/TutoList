<template>
  <div class="calendar-container">
    <div class="calendar-header">
      <h2>수업 일정</h2>
      <div class="calendar-controls">
        <button @click="handlePrevMonth" class="control-btn">
          이전 달
        </button>
        <span class="current-month">{{ currentMonthName }}</span>
        <button @click="handleNextMonth" class="control-btn">
          다음 달
        </button>
      </div>
    </div>
    
    <calendar-view
      :show-date="selectedDate"
      :items="lessons"
      :show-times="true"
      :time-format-options="{ hour: '2-digit', minute: '2-digit' }"
      @click-date="handleClickDate"
      @click-item="handleClickLesson"
    >
      <template #item="{ item }">
        <div 
          class="lesson-item"
          :style="{ backgroundColor: item.color || '#42b983' }"
        >
          <div class="lesson-title">{{ item.title }}</div>
          <div class="lesson-time">
            {{ formatTime(item.startDate) }} - {{ formatTime(item.endDate) }}
          </div>
        </div>
      </template>
    </calendar-view>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue'
import { CalendarView } from 'vue-simple-calendar'
import '../../node_modules/vue-simple-calendar/dist/vue-simple-calendar.css'
import axios from 'axios'

export default {
  name: 'LessonCalView',
  components: {
    CalendarView
  },
  setup() {
    const selectedDate = ref(new Date())
    const lessons = ref([])

    const currentMonthName = computed(() => {
      return new Intl.DateTimeFormat('ko-KR', {
        year: 'numeric',
        month: 'long'
      }).format(selectedDate.value)
    })

    const fetchLessons = async () => {
      try {
        const response = await axios.get('/api/lessons')
        if (response.data.success) {
          lessons.value = response.data.data.map(lesson => ({
            id: lesson.id,
            startDate: new Date(lesson.startTime),
            endDate: new Date(lesson.endTime),
            title: lesson.title,
            color: lesson.color
          }))
        } else {
          console.error('수업 정보를 불러오는데 실패했습니다:', response.data.message)
        }
      } catch (error) {
        console.error('수업 정보를 불러오는 중 오류가 발생했습니다:', error)
      }
    }

    const handlePrevMonth = () => {
      const newDate = new Date(selectedDate.value)
      newDate.setMonth(newDate.getMonth() - 1)
      selectedDate.value = newDate
    }

    const handleNextMonth = () => {
      const newDate = new Date(selectedDate.value)
      newDate.setMonth(newDate.getMonth() + 1)
      selectedDate.value = newDate
    }

    const handleClickDate = (date) => {
      // 날짜 클릭 시 처리 로직
      console.log('Clicked date:', date)
    }

    const handleClickLesson = (lesson) => {
      // 수업 클릭 시 처리 로직
      console.log('Clicked lesson:', lesson)
    }

    const formatTime = (date) => {
      return new Intl.DateTimeFormat('ko-KR', {
        hour: '2-digit',
        minute: '2-digit'
      }).format(new Date(date))
    }

    onMounted(() => {
      fetchLessons()
    })

    return {
      selectedDate,
      lessons,
      currentMonthName,
      handlePrevMonth,
      handleNextMonth,
      handleClickDate,
      handleClickLesson,
      formatTime
    }
  }
}
</script>

<style scoped>
.calendar-container {
  padding: 20px;
  height: calc(100vh - 100px);
}

.calendar-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.calendar-controls {
  display: flex;
  align-items: center;
  gap: 16px;
}

.control-btn {
  padding: 8px 16px;
  border: none;
  border-radius: 8px;
  background-color: #42b983;
  color: white;
  cursor: pointer;
  transition: background-color 0.2s;
}

.control-btn:hover {
  background-color: #3aa876;
}

.current-month {
  font-size: 1.2em;
  font-weight: bold;
}

.lesson-item {
  padding: 4px 8px;
  border-radius: 4px;
  color: white;
  font-size: 0.9em;
}

.lesson-title {
  font-weight: bold;
  margin-bottom: 2px;
}

.lesson-time {
  font-size: 0.8em;
}

:deep(.cv-header) {
  background-color: #f8f9fa;
  padding: 10px;
}

:deep(.cv-day) {
  border: 1px solid #e9ecef;
}

:deep(.cv-day.today) {
  background-color: #e8f5e9;
}

:deep(.cv-item) {
  cursor: pointer;
}
</style>
