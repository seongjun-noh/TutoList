<template>
  <div v-if="modelValue" class="modal-overlay" @click="handleClose">
    <div class="modal-content" @click.stop>
      <div class="modal-header">
        <h2>{{ isEdit ? '수업 일정 수정' : '새 수업 일정' }}</h2>
        <button class="close-button" @click="handleClose" aria-label="닫기">×</button>
      </div>
      
      <form @submit.prevent="handleSubmit" class="lesson-form">
        <div class="form-group">
          <label for="title">수업 제목</label>
          <input
            id="title"
            v-model="formData.title"
            type="text"
            required
            placeholder="수업 제목을 입력하세요"
          >
        </div>

        <div class="form-group">
          <label for="student">학생</label>
          <select id="student" v-model="formData.studentId" required>
            <option value="">학생을 선택하세요</option>
            <option v-for="student in students" :key="student.id" :value="student.id">
              {{ student.name }}
            </option>
          </select>
        </div>

        <div class="form-row">
          <div class="form-group">
            <label for="startDate">시작 일시</label>
            <input
              id="startDate"
              v-model="formData.startDate"
              type="datetime-local"
              required
            >
          </div>

          <div class="form-group">
            <label for="endDate">종료 일시</label>
            <input
              id="endDate"
              v-model="formData.endDate"
              type="datetime-local"
              required
            >
          </div>
        </div>

        <div class="form-group">
          <label for="description">수업 내용</label>
          <textarea
            id="description"
            v-model="formData.description"
            rows="4"
            placeholder="수업 내용을 입력하세요"
          ></textarea>
        </div>

        <div class="form-actions">
          <button type="button" class="btn-cancel" @click="handleClose">취소</button>
          <button type="submit" class="btn-submit">
            {{ isEdit ? '수정' : '추가' }}
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'

export default {
  name: 'LessonFormModal',
  props: {
    modelValue: {
      type: Boolean,
      required: true
    },
    editData: {
      type: Object,
      default: null
    }
  },
  emits: ['update:modelValue', 'submit'],
  setup(props, { emit }) {
    const students = ref([])
    const formData = ref({
      title: '',
      studentId: '',
      startDate: '',
      endDate: '',
      description: ''
    })

    const isEdit = computed(() => !!props.editData)

    const fetchStudents = async () => {
      try {
        const response = await axios.get('/api/students')
        if (response.data.success) {
          students.value = response.data.data
        }
      } catch (error) {
        console.error('학생 목록을 불러오는데 실패했습니다:', error)
      }
    }

    const handleClose = () => {
      emit('update:modelValue', false)
    }

    const handleSubmit = async () => {
      try {
        const endpoint = isEdit.value ? `/api/lessons/${props.editData.id}` : '/api/lessons'
        const method = isEdit.value ? 'put' : 'post'
        
        const response = await axios[method](endpoint, formData.value)
        
        if (response.data.success) {
          emit('submit', response.data.data)
          handleClose()
        }
      } catch (error) {
        console.error('수업 일정 저장에 실패했습니다:', error)
      }
    }

    onMounted(() => {
      fetchStudents()
      if (props.editData) {
        formData.value = { ...props.editData }
      }
    })

    return {
      students,
      formData,
      isEdit,
      handleClose,
      handleSubmit
    }
  }
}
</script>

<style>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  width: 100%;
  max-width: 500px;
  max-height: 90vh;
  overflow-y: auto;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.close-button {
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
  padding: 0 8px;
}

.lesson-form {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.form-row {
  display: flex;
  gap: 16px;
}

.form-row .form-group {
  flex: 1;
}

input, select, textarea {
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

textarea {
  resize: vertical;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 20px;
}

.btn-cancel, .btn-submit {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
}

.btn-cancel {
  background-color: #f0f0f0;
}

.btn-submit {
  background-color: #007bff;
  color: white;
}

.btn-submit:hover {
  background-color: #0056b3;
}
</style> 