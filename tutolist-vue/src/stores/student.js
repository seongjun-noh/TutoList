import { defineStore } from 'pinia'
import { studentApi } from '@/api/studentApi'

export const useStudentStore = defineStore('student', {
  state: () => ({
    students: [],
    loading: false,
    error: null
  }),

  getters: {
    getStudentById: (state) => (id) => {
      return state.students.find(student => student.id === id)
    }
  },

  actions: {
    async fetchStudents() {
      this.loading = true
      this.error = null
      
      try {
        const response = await studentApi.getStudents()
        if (response.data.success) {
          this.students = response.data.data
        } else {
          throw new Error(response.data.message)
        }
      } catch (error) {
        this.error = error.message || '학생 목록을 불러오는데 실패했습니다.'
        throw error
      } finally {
        this.loading = false
      }
    },

    async createStudent(studentData) {
      this.loading = true
      this.error = null
      
      try {
        const response = await studentApi.createStudent(studentData)
        if (response.data.success) {
          this.students.push(response.data.data)
          return response.data.data
        } else {
          throw new Error(response.data.message)
        }
      } catch (error) {
        this.error = error.message || '학생 등록에 실패했습니다.'
        throw error
      } finally {
        this.loading = false
      }
    },

    async updateStudent(id, studentData) {
      this.loading = true
      this.error = null
      
      try {
        const response = await studentApi.updateStudent(id, studentData)
        if (response.data.success) {
          const index = this.students.findIndex(student => student.id === id)
          if (index !== -1) {
            this.students[index] = response.data.data
          }
          return response.data.data
        } else {
          throw new Error(response.data.message)
        }
      } catch (error) {
        this.error = error.message || '학생 정보 수정에 실패했습니다.'
        throw error
      } finally {
        this.loading = false
      }
    },

    async deleteStudent(id) {
      this.loading = true
      this.error = null
      
      try {
        const response = await studentApi.deleteStudent(id)
        if (response.data.success) {
          this.students = this.students.filter(student => student.id !== id)
          return true
        } else {
          throw new Error(response.data.message)
        }
      } catch (error) {
        this.error = error.message || '학생 삭제에 실패했습니다.'
        throw error
      } finally {
        this.loading = false
      }
    }
  }
}) 