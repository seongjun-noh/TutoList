<template>
  <div class="student-create">
    <div class="student-create__header">
      <h1>새 학생 등록</h1>
    </div>

    <form @submit.prevent="handleSubmit" class="student-form">
      <div class="form-group">
        <label for="name">이름 *</label>
        <input
          id="name"
          v-model="form.name"
          type="text"
          required
          class="form-input"
          :class="{ 'is-invalid': errors.name }"
          @input="validateName"
        />
        <p v-if="errors.name" class="error-message">{{ errors.name }}</p>
      </div>

      <div class="form-group">
        <label for="schoolGrade">학교 / 학년 *</label>
        <input
          id="schoolGrade"
          v-model="form.schoolGrade"
          type="text"
          required
          class="form-input"
          :class="{ 'is-invalid': errors.schoolGrade }"
          @input="validateName"
        />
        <p v-if="errors.schoolGrade" class="error-message">{{ errors.schoolGrade }}</p>
      </div>

      <div class="form-group">
        <label for="contact">연락처 *</label>
        <input
          id="contact"
          v-model="form.contact"
          type="tel"
          required
          placeholder="010-0000-0000"
          class="form-input"
          :class="{ 'is-invalid': errors.contact }"
          @input="validateContact"
        />
        <p v-if="errors.contact" class="error-message">{{ errors.contact }}</p>
      </div>

      <div class="form-group">
        <label for="subjects">과목 *</label>
        <div class="subjects-input-container">
          <div class="subjects-tags">
            <span 
              v-for="subject in form.subjects" 
              :key="subject" 
              class="subject-tag"
            >
              {{ subject }}
              <button 
                type="button"
                class="remove-tag"
                @click="removeSubject(subject)"
                aria-label="과목 삭제"
              >
                ×
              </button>
            </span>
          </div>
          <input
            id="subjects"
            v-model="subjectInput"
            type="text"
            class="form-input subjects-input"
            :class="{ 'is-invalid': errors.subjects }"
            placeholder="과목을 입력하고 Enter 또는 쉼표(,)를 입력하세요"
            @keydown.enter.prevent="addSubject"
            @keydown="handleKeydown"
            @input="handleSubjectInput"
          />
        </div>
        <div class="subject-suggestions" v-if="filteredSuggestions.length > 0">
          <button
            v-for="suggestion in filteredSuggestions"
            :key="suggestion"
            type="button"
            class="suggestion-item"
            @click="selectSuggestion(suggestion)"
          >
            {{ suggestion }}
          </button>
        </div>
        <p v-if="errors.subjects" class="error-message">{{ errors.subjects }}</p>
      </div>

      <div class="form-group">
        <label for="memo">메모</label>
        <textarea
          id="memo"
          v-model="form.memo"
          class="form-textarea"
          rows="4"
          placeholder="학생에 대한 특이사항이나 메모를 입력하세요."
        ></textarea>
      </div>

      <div class="form-actions">
        <button 
          type="button" 
          class="btn-secondary" 
          @click="handleCancel"
          :disabled="isSubmitting"
        >
          취소
        </button>
        <button 
          type="submit" 
          class="btn-primary" 
          :disabled="isSubmitting || !isFormValid"
        >
          {{ isSubmitting ? '등록 중...' : '등록하기' }}
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useStudentStore } from '@/stores/student'

const router = useRouter()
const studentStore = useStudentStore()

const suggestedSubjects = [
  '수학', '영어', '국어', '과학', '사회',
  '물리', '화학', '생명과학', '지구과학',
  '한국사', '세계사', '경제', '정치와법',
  '제2외국어', '한문', '정보', '음악', '미술'
]

const form = ref({
  name: '',
  schoolGrade: '',
  contact: '',
  subjects: [],
  parentContact: '',
  memo: ''
})

const errors = ref({
  name: '',
  schoolGrade: '',
  contact: '',
  subjects: '',
  parentContact: ''
})

const subjectInput = ref('')
const isSubmitting = ref(false)

const filteredSuggestions = computed(() => {
  if (!subjectInput.value) return []
  const input = subjectInput.value.toLowerCase()
  return suggestedSubjects
    .filter(subject => 
      subject.toLowerCase().includes(input) && 
      !form.value.subjects.includes(subject)
    )
    .slice(0, 5)
})

const validateName = () => {
  if (!form.value.name.trim()) {
    errors.value.name = '이름을 입력해주세요.'
    return false
  }
  if (form.value.name.length > 10) {
    errors.value.name = '이름은 10자 이내로 입력해주세요.'
    return false
  }
  errors.value.name = ''
  return true
}

const validateContact = () => {
  const contactRegex = /^010-\d{4}-\d{4}$/
  if (!form.value.contact) {
    errors.value.contact = '연락처를 입력해주세요.'
    return false
  }
  if (!contactRegex.test(form.value.contact)) {
    errors.value.contact = '올바른 연락처 형식이 아닙니다. (예: 010-0000-0000)'
    return false
  }
  errors.value.contact = ''
  return true
}

const validateSubjects = () => {
  if (form.value.subjects.length === 0) {
    errors.value.subjects = '최소 1개 이상의 과목을 입력해주세요.'
    return false
  }
  errors.value.subjects = ''
  return true
}

const addSubject = () => {
  const subject = subjectInput.value.trim()
  if (subject && !form.value.subjects.includes(subject)) {
    form.value.subjects.push(subject)
    subjectInput.value = ''
    validateSubjects()
  }
}

const removeSubject = (subject) => {
  form.value.subjects = form.value.subjects.filter(s => s !== subject)
  validateSubjects()
}

const selectSuggestion = (subject) => {
  if (!form.value.subjects.includes(subject)) {
    form.value.subjects.push(subject)
    subjectInput.value = ''
    validateSubjects()
  }
}

const handleKeydown = (event) => {
  if (event.key === ',') {
    event.preventDefault()
    addSubject()
  }
}

const handleSubjectInput = (event) => {
  const value = event.target.value
  if (value.endsWith(',')) {
    subjectInput.value = value.slice(0, -1)
    addSubject()
  }
}

const isFormValid = computed(() => {
  return (
    form.value.name &&
    form.value.schoolGrade &&
    form.value.contact &&
    form.value.subjects.length > 0 &&
    !errors.value.name &&
    !errors.value.schoolGrade &&
    !errors.value.contact &&
    !errors.value.subjects &&
    !errors.value.parentContact
  )
})

const handleSubmit = async () => {
  if (!isFormValid.value || isSubmitting.value) return

  isSubmitting.value = true

  try {
    await studentStore.createStudent({
      ...form.value,
      createdAt: new Date().toISOString()
    })
    router.push('/students')
  } catch (error) {
    console.error('학생 등록 실패:', error)
  } finally {
    isSubmitting.value = false
  }
}

const handleCancel = () => {
  router.push('/students')
}
</script>

<style>
.student-create {
  padding: 24px;
  max-width: 800px;
  margin: 0 auto;
}

.student-create__header {
  margin-bottom: 32px;
}

.student-create__header h1 {
  font-size: 24px;
  font-weight: bold;
}

.student-form {
  background: white;
  padding: 24px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 24px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
}

.form-input,
.form-select,
.form-textarea {
  width: 100%;
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 14px;
}

.form-input:focus,
.form-select:focus,
.form-textarea:focus {
  outline: 2px solid var(--primary-color-light);
  border-color: var(--primary-color);
}

.form-input.is-invalid,
.form-select.is-invalid {
  border-color: #dc3545;
}

.subjects-input-container {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 8px;
  background: white;
}

.subjects-input-container:focus-within {
  outline: 2px solid var(--primary-color-light);
  border-color: var(--primary-color);
}

.subjects-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 8px;
}

.subjects-input {
  border: none;
  padding: 4px;
  width: 100%;
}

.subjects-input:focus {
  outline: none;
  border: none;
}

.subject-tag {
  display: inline-flex;
  align-items: center;
  background: var(--primary-color-light);
  color: var(--primary-color);
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 14px;
  gap: 4px;
}

.remove-tag {
  background: none;
  border: none;
  color: var(--primary-color);
  cursor: pointer;
  padding: 0;
  font-size: 16px;
  line-height: 1;
}

.remove-tag:hover {
  color: var(--primary-color-dark);
}

.subject-suggestions {
  margin-top: 8px;
  border: 1px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
}

.suggestion-item {
  width: 100%;
  padding: 8px 12px;
  border: none;
  background: white;
  text-align: left;
  cursor: pointer;
}

.suggestion-item:hover {
  background: var(--primary-color-light);
}

.error-message {
  color: #dc3545;
  font-size: 12px;
  margin-top: 4px;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 32px;
}

.btn-primary,
.btn-secondary {
  padding: 8px 24px;
  border-radius: 8px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-primary {
  background-color: var(--primary-color);
  color: white;
  border: none;
}

.btn-primary:hover:not(:disabled) {
  background-color: var(--primary-color-dark);
}

.btn-secondary {
  background-color: white;
  color: #6c757d;
  border: 1px solid #ddd;
}

.btn-secondary:hover:not(:disabled) {
  background-color: #f8f9fa;
}

.btn-primary:disabled,
.btn-secondary:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}
</style> 