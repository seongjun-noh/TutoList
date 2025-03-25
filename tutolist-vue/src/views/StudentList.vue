<template>
  <div class="student-list">
    <div v-if="students.length === 0" class="empty-state">
      <p>등록된 학생이 없습니다.</p>
    </div>
    
    <div v-else class="student-grid">
      <div
        v-for="student in students"
        :key="student.id"
        class="student-card"
        @click="$emit('select-student', student.id)"
        @keydown.enter="$emit('select-student', student.id)"
        @keydown.space.prevent="$emit('select-student', student.id)"
        tabindex="0"
        role="button"
        :aria-label="'학생 정보: ' + student.name"
      >
        <div class="student-info">
          <h3>{{ student.name }}</h3>
          <p class="grade">{{ student.grade }}</p>
        </div>
        
        <div class="student-details">
          <p class="contact">{{ student.contact }}</p>
          <div class="subjects">
            <span 
              v-for="subject in student.subjects" 
              :key="subject"
              class="subject-tag"
            >
              {{ subject }}
            </span>
          </div>
        </div>

        <div class="next-class" v-if="student.nextClass">
          <p>다음 수업</p>
          <p class="next-class-time">{{ formatNextClass(student.nextClass) }}</p>
        </div>
      </div>
    </div>

    <StudentDetail 
      :student-id="selectedStudentId"
      @edit-student="handleEditStudent"
      @delete-student="handleDeleteStudent"
    />
  </div>
</template>

<script setup>
import { studentApi } from '@/api/studentApi'
import { format } from 'date-fns'
import { ko } from 'date-fns/locale'
import { onMounted, ref } from 'vue'

defineEmits(['select-student'])

const students = ref([])
const selectedStudentId = ref(null)

const formatNextClass = (nextClass) => {
  if (!nextClass) return ''
  return format(new Date(nextClass), 'M월 d일 (E) HH:mm', { locale: ko })
}

const fetchStudents = async () => {
  try {
    const response = await studentApi.getStudents()
    students.value = response.data.data.content
  } catch (error) {
    console.error('학생 목록 가져오기 실패:', error)
  }
}

const handleEditStudent = () => {
  // 학생 정보 수정 로직
}

const handleDeleteStudent = () => {
  // 학생 삭제 후 처리 로직
}

onMounted(() => {
  fetchStudents()
})
</script>

<style>
.student-list {
  width: 100%;
}

.empty-state {
  text-align: center;
  padding: 48px;
  background: #f8f9fa;
  border-radius: 8px;
  color: #6c757d;
}

.student-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 24px;
}

.student-card {
  background: white;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 16px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.student-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.student-card:focus {
  outline: 2px solid var(--primary-color-light);
  outline-offset: 2px;
}

.student-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.student-info h3 {
  font-size: 18px;
  font-weight: bold;
}

.grade {
  background: var(--primary-color-light);
  color: var(--primary-color);
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 14px;
}

.student-details {
  margin-bottom: 12px;
}

.contact {
  color: #6c757d;
  font-size: 14px;
  margin-bottom: 8px;
}

.subjects {
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
}

.subject-tag {
  background: #f8f9fa;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
  color: #495057;
}

.next-class {
  margin-top: 12px;
  padding-top: 12px;
  border-top: 1px solid #eee;
}

.next-class p {
  font-size: 12px;
  color: #6c757d;
}

.next-class-time {
  color: var(--primary-color);
  font-weight: 500;
  margin-top: 4px;
}
</style> 