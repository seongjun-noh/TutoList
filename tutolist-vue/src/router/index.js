// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import { checkInitialAuth } from '@/plugins/auth'
import HomeView from '@/views/HomeView.vue'
import LoginView from '@/views/LoginView.vue'
import StudentManagementView from '@/views/StudentManagementView.vue'
import StudentCreateView from '@/views/StudentCreateView.vue'
import StudentDetail from '@/views/StudentDetail.vue'
import LessonCalView from '@/views/LessonCalView.vue'
import EmptyLayout from '@/layouts/EmptyLayout.vue'
import DefaultLayout from '@/layouts/DefaultLayout.vue'

const router = createRouter({
  history: createWebHistory('/'),
  routes: [
    {
      path: '/',
      meta: { requiresAuth: true },
      component: DefaultLayout,
      props: {
        activeMenu: 'home',
        title: '대시보드',
        notificationCount: 3
      },
      children: [
        {
          path: '',
          name: 'home',
          component: HomeView
        },
        {
          path: 'students',
          name: 'students',
          component: StudentManagementView,
          props: {
            activeMenu: 'students',
            title: '학생 관리'
          }
        },
        {
          path: 'students/new',
          name: 'student-create',
          component: StudentCreateView,
          props: {
            activeMenu: 'students',
            title: '새 학생 등록'
          }
        },
        {
          path: 'students/:id',
          name: 'student-detail',
          component: StudentDetail,
          props: {
            activeMenu: 'students',
            title: '학생 상세 정보'
          }
        },
        {
          path: 'lessons',
          name: 'lessons',
          component: LessonCalView,
          props: {
            activeMenu: 'lessons',
            title: '수업 일정'
          }
        }
      ]
    },
    {
      path: '/login',
      component: EmptyLayout,
      children: [
        {
          path: '',
          name: 'login',
          component: LoginView,
          meta: { guest: true }
        }
      ]
    }
  ]
})

// 전역 네비게이션 가드
router.beforeEach((to, from, next) => {
  const isAuthenticated = checkInitialAuth()
  
  // 인증이 필요한 페이지에 접근하려 할 때
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!isAuthenticated) {
      next({
        path: '/login',
        query: { redirect: to.fullPath }
      })
    } else {
      next()
    }
  }
  
  // 로그인 페이지에 접근하려 할 때
  else if (to.matched.some(record => record.meta.guest)) {
    if (isAuthenticated) {
      next('/')
    } else {
      next()
    }
  }
  
  // 그 외의 페이지
  else {
    next()
  }
})

export default router
