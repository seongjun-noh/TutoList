<template>
  <div class="student-management">
    <div class="student-management__header">
      <h1>학생 관리</h1>
      <button 
        class="btn-primary" 
        @click="handleAddStudent"
        @keydown.enter="handleAddStudent"
        @keydown.space.prevent="handleAddStudent"
        tabindex="0"
        aria-label="새 학생 추가"
      >
        + 새 학생 추가
      </button>
    </div>

    <StudentFilter 
      v-model:search="searchQuery"
      v-model:grade="selectedGrade"
      v-model:subject="selectedSubject"
      @filter="handleFilter"
    />

    <StudentList
      :students="filteredStudents"
      @select-student="handleSelectStudent"
    />
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import StudentFilter from '@/components/student/StudentFilter.vue'
import StudentList from '@/components/student/StudentList.vue'
import { useStudentStore } from '@/stores/student'

const router = useRouter()
const studentStore = useStudentStore()

const searchQuery = ref('')
const selectedGrade = ref('')
const selectedSubject = ref('')

const filteredStudents = computed(() => {
  return studentStore.students.filter(student => {
    const matchesSearch = student.name.includes(searchQuery.value) ||
      student.contact.includes(searchQuery.value)
    const matchesGrade = !selectedGrade.value || student.grade === selectedGrade.value
    const matchesSubject = !selectedSubject.value || student.subjects.includes(selectedSubject.value)
    
    return matchesSearch && matchesGrade && matchesSubject
  })
})

const handleAddStudent = () => {
  router.push('/students/new')
}

const handleSelectStudent = (studentId) => {
  router.push(`/students/${studentId}`)
}

const handleFilter = () => {
  // 필터링은 computed로 자동 처리됨
}
</script>

<style>
.student-management {
  padding: 24px;
}

.student-management__header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.student-management__header h1 {
  font-size: 24px;
  font-weight: bold;
}

.btn-primary {
  background-color: var(--primary-color);
  color: white;
  padding: 8px 16px;
  border-radius: 8px;
  border: none;
  cursor: pointer;
  font-weight: 500;
  transition: background-color 0.2s;
}

.btn-primary:hover {
  background-color: var(--primary-color-dark);
}

.btn-primary:focus {
  outline: 2px solid var(--primary-color-light);
  outline-offset: 2px;
}
</style> 