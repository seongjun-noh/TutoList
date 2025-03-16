import axios from '@/plugins/axios'

export const studentApi = {
  // 학생 목록 조회
  getStudents() {
    return axios.get('/students')
  },

  // 학생 상세 조회
  getStudent(id) {
    return axios.get('/students/' + id)
  },

  // 학생 등록
  createStudent(studentData) {
    return axios.post('/students/create', studentData)
  },

  // 학생 정보 수정
  updateStudent(id, studentData) {
    return axios.put('/students/' + id, studentData)
  },

  // 학생 삭제
  deleteStudent(id) {
    return axios.delete('/students/' + id)
  }
} 