<template>
  <div class="student-detail">
    <div class="student-detail__header">
      <div class="student-detail__header-left">
        <h1 class="student-detail__title">{{ student.name }}</h1>
        <p class="student-detail__subtitle">{{ student.schoolGrade }}학년</p>
      </div>
      <div class="student-detail__actions">
        <button 
          class="btn btn-secondary"
          @click="handleEdit"
          tabindex="0"
          aria-label="학생 정보 수정"
        >
          <i class="fas fa-edit"></i> 수정
        </button>
        <button 
          class="btn btn-danger"
          @click="handleDelete"
          tabindex="0"
          aria-label="학생 삭제"
        >
          <i class="fas fa-trash"></i> 삭제
        </button>
      </div>
    </div>

    <div class="student-detail__content">
      <div class="student-detail__section">
        <h2 class="student-detail__section-title">
          <i class="fas fa-user"></i> 기본 정보
        </h2>
        <div class="student-detail__info">
          <div class="info-item">
            <span class="info-label">연락처</span>
            <span class="info-value">{{ student.contact }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">과목</span>
            <div class="info-value subjects-list">
              <span 
                v-for="subject in student.subjects" 
                :key="subject"
                class="subject-tag"
              >
                {{ subject }}
              </span>
            </div>
          </div>
        </div>
      </div>

      <div class="student-detail__section">
        <h2 class="student-detail__section-title">
          <i class="fas fa-calendar"></i> 수업 정보
        </h2>
        <div class="student-detail__schedule">
          <div class="empty-state" v-if="!hasSchedule">
            <i class="fas fa-calendar-times"></i>
            <p>등록된 수업 일정이 없습니다.</p>
          </div>
          <!-- 수업 일정 정보가 들어갈 자리 -->
        </div>
      </div>

      <div class="student-detail__section">
        <h2 class="student-detail__section-title">
          <i class="fas fa-chart-line"></i> 학습 진도
        </h2>
        <div class="student-detail__progress">
          <div class="empty-state" v-if="!hasProgress">
            <i class="fas fa-chart-bar"></i>
            <p>등록된 학습 진도가 없습니다.</p>
          </div>
          <!-- 학습 진도 정보가 들어갈 자리 -->
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import { studentApi } from '@/api/studentApi'

const route = useRoute()
const router = useRouter()
const student = ref({
  name: '',
  schoolGrade: '',
  contact: '',
  subjects: []
})

const hasSchedule = computed(() => false) // 추후 실제 데이터로 대체
const hasProgress = computed(() => false) // 추후 실제 데이터로 대체

const fetchStudentDetail = async () => {
  try {
    const response = await studentApi.getStudent(route.params.id)
    if (response.data.success) {
      student.value = response.data.data
    }
  } catch (error) {
    console.error('학생 정보를 불러오는데 실패했습니다:', error)
  }
}

const handleEdit = () => {
  router.push(`/students/${route.params.id}/edit`)
}

const handleDelete = async () => {
  if (confirm('정말로 이 학생을 삭제하시겠습니까?')) {
    try {
      const response = await axios.delete(`/api/students/${route.params.id}`)
      if (response.data.success) {
        router.push('/students')
      }
    } catch (error) {
      console.error('학생 삭제에 실패했습니다:', error)
    }
  }
}

onMounted(() => {
  fetchStudentDetail()
})
</script>

<style scoped>
.student-detail {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.student-detail__header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid #eee;
}

.student-detail__header-left {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.student-detail__title {
  font-size: 28px;
  font-weight: 600;
  color: #333;
  margin: 0;
}

.student-detail__subtitle {
  font-size: 16px;
  color: #666;
  margin: 0;
}

.student-detail__actions {
  display: flex;
  gap: 10px;
}

.student-detail__section {
  background: #fff;
  border-radius: 12px;
  padding: 24px;
  margin-bottom: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  transition: transform 0.2s, box-shadow 0.2s;
}

.student-detail__section:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.12);
}

.student-detail__section-title {
  font-size: 20px;
  font-weight: 500;
  color: #333;
  margin-bottom: 20px;
  display: flex;
  align-items: center;
  gap: 8px;
}

.student-detail__section-title i {
  color: #007bff;
}

.student-detail__info {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 24px;
}

.info-item {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.info-label {
  font-size: 14px;
  color: #666;
  font-weight: 500;
}

.info-value {
  font-size: 16px;
  color: #333;
  font-weight: 500;
}

.subjects-list {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.subject-tag {
  background-color: #e3f2fd;
  color: #1976d2;
  padding: 4px 12px;
  border-radius: 16px;
  font-size: 14px;
  font-weight: 500;
}

.btn {
  padding: 8px 16px;
  border-radius: 6px;
  border: none;
  cursor: pointer;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.2s;
  display: flex;
  align-items: center;
  gap: 6px;
}

.btn i {
  font-size: 14px;
}

.btn-secondary {
  background-color: #f5f5f5;
  color: #333;
}

.btn-secondary:hover {
  background-color: #e0e0e0;
}

.btn-danger {
  background-color: #ff4444;
  color: white;
}

.btn-danger:hover {
  background-color: #ff3333;
}

.btn:focus {
  outline: 2px solid #007bff;
  outline-offset: 2px;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40px;
  color: #999;
  text-align: center;
}

.empty-state i {
  font-size: 48px;
  margin-bottom: 16px;
}

.empty-state p {
  font-size: 16px;
  margin: 0;
}
</style> 