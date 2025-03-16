<template>
  <div class="login-page">
    <!-- 좌측 브랜딩 영역 (데스크탑/태블릿 전용) -->
    <section class="brand-section">
      <div class="logo-container">
        <span class="logo-text">TutoList</span>
      </div>
      
      <h1 class="tagline">효율적인 과외 관리의 시작</h1>
      
      <div class="features-list">
        <div class="feature-item">
          <div class="feature-dot"></div>
          <span class="feature-text">학생 정보를 한눈에</span>
        </div>
        <div class="feature-item">
          <div class="feature-dot"></div>
          <span class="feature-text">수업 일정 간편 관리</span>
        </div>
        <div class="feature-item">
          <div class="feature-dot"></div>
          <span class="feature-text">진도 및 피드백 기록</span>
        </div>
        <div class="feature-item">
          <div class="feature-dot"></div>
          <span class="feature-text">모바일에서도 확인 가능</span>
        </div>
      </div>
      
      <div class="illustration">
        <div class="illustration-screen">
          <span class="illustration-text">학습 관리 대시보드</span>
        </div>
        <p class="illustration-text">체계적인 학생 및 수업 관리를 시작하세요</p>
      </div>
    </section>
    
    <!-- 우측 로그인 영역 -->
    <section class="login-section">
      <div class="login-card">
        <!-- 모바일에서만 표시되는 로고 (SCSS 미디어 쿼리로 표시 제어) -->
        <div class="mobile-logo">
          <div class="logo-container mobile-logo-bg">
            <span class="logo-text">TutoList</span>
          </div>
        </div>
        
        <div class="login-welcome">
          <h2 class="welcome-title">TutoList에 오신 것을 환영합니다</h2>
          <p class="welcome-subtitle">로그인하여 학생과 수업을 관리하세요</p>
        </div>
        
        <form @submit.prevent="login">
          <div class="form-group">
            <label for="email" class="form-label">이메일</label>
            <input type="text" id="email" class="form-input" v-model="email" placeholder="이메일 또는 아이디 입력">
          </div>
          
          <div class="form-group">
            <label for="password" class="form-label">비밀번호</label>
            <div class="password-container">
              <input ref="passwordInput" :type="passwordVisible ? 'text' : 'password'" id="password" class="form-input" v-model="password" placeholder="비밀번호 입력">
              <button type="button" class="password-toggle" @click="togglePassword">
                <font-awesome-icon :icon="passwordVisible ? ['far', 'eye-slash'] : ['far', 'eye']" />
              </button>
            </div>
          </div>
          <p>{{ errorMessage }}</p>
          <div class="remember-me">
            <input type="checkbox" id="remember" class="remember-checkbox" v-model="rememberMe">
            <label for="remember" class="remember-text">로그인 상태 유지</label>
          </div>
          
          <button type="submit" class="login-button">로그인</button>
        </form>
        
        <div class="auxiliary-links">
          <a href="#" class="auxiliary-link">비밀번호 찾기</a>
          <span class="separator">|</span>
          <a href="#" class="auxiliary-link signup-link">회원가입</a>
        </div>
      </div>
      
      <div class="footer">
        <p>© 2025 TutoList 시스템. 모든 권리 보유.</p>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { library } from '@fortawesome/fontawesome-svg-core'
import { faEye, faEyeSlash } from '@fortawesome/free-regular-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import http from '@/plugins/axios'
import router from '@/router'
import { setAuthToken } from '@/plugins/auth'

// 아이콘을 라이브러리에 추가 (전역 등록이 필요한 경우)
library.add(faEye, faEyeSlash)

// 사용자 입력 및 에러 메시지 상태
const email = ref('')
const password = ref('')
const errorMessage = ref('')
const rememberMe = ref(false)

const passwordInput = ref(null)
const passwordVisible = ref(false)

// 비밀번호 표시/숨기기 토글 함수
function togglePassword() {
  passwordVisible.value = !passwordVisible.value
}

const login = async () => {
  errorMessage.value = '' // 이전 에러 초기화
  try {
    const response = await http.post('/auth/login', {
      email: email.value,
      password: password.value,
      rememberMe: rememberMe.value
    })

    const data = response.data.data;
    
    // 로그인 상태 유지 설정에 따라 토큰 저장
    if (rememberMe.value) {
      setAuthToken(data.accessToken, data.refreshToken, 'localStorage')
    } else {
      setAuthToken(data.accessToken, data.refreshToken, 'sessionStorage')
    }
    
    // 리다이렉트 처리
    const redirectPath = router.currentRoute.value.query.redirect || '/'
    router.push(redirectPath)
    
  } catch (error) {
    if (error.status === 400) {
      errorMessage.value = '이메일 또는 비밀번호가 일치하지 않습니다.'
    } else {
      errorMessage.value = error.response && error.response.data && error.response.data.message
        ? error.response.data.message
        : '로그인에 실패했습니다. 다시 시도해주세요.'
    }
    console.error('로그인 에러:', error)
  }
}
</script>

<style lang="scss">
.login-page {
  display: flex;
  min-height: 100vh;
  background-color: #f5f5f5;
}

/* 좌측 브랜딩 영역 */
.brand-section {
  width: 45%;
  background: linear-gradient(135deg, #4A90E2, #2C5282);
  color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 60px 40px;
  position: relative;
  overflow: hidden;
}

.brand-section::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(45deg, rgba(255,255,255,0.1) 0%, rgba(255,255,255,0) 100%);
  pointer-events: none;
}

.brand-section .logo-container {
  background-color: rgba(255, 255, 255, 0.15);
  padding: 16px 32px;
  border-radius: 12px;
  margin-bottom: 50px;
  backdrop-filter: blur(10px);
  transition: transform 0.3s ease;
}

.brand-section .logo-container:hover {
  transform: translateY(-2px);
}

.brand-section .logo-text {
  font-size: 28px;
  font-weight: 700;
  letter-spacing: 0.5px;
}

.brand-section .tagline {
  font-size: 32px;
  font-weight: 700;
  margin-bottom: 48px;
  text-align: center;
  line-height: 1.4;
  text-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.brand-section .features-list {
  width: 100%;
  max-width: 360px;
  margin-bottom: 48px;
}

.brand-section .feature-item {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  font-size: 17px;
  opacity: 0.9;
}

.brand-section .feature-dot {
  width: 8px;
  height: 8px;
  background-color: white;
  border-radius: 50%;
  margin-right: 12px;
}

.brand-section .feature-text {
  font-weight: 500;
}

.illustration {
  margin-top: auto;
  text-align: center;
}

.illustration-screen {
  background: rgba(255, 255, 255, 0.1);
  padding: 24px;
  border-radius: 16px;
  backdrop-filter: blur(10px);
  margin-bottom: 24px;
}

.illustration-text {
  font-size: 15px;
  opacity: 0.8;
}

/* 로그인 섹션 */
.login-section {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40px;
  background-color: white;
}

.login-card {
  width: 100%;
  max-width: 440px;
  padding: 40px;
  background: white;
  border-radius: 20px;
  box-shadow: 0 10px 25px rgba(0,0,0,0.05);
}

.mobile-logo {
  display: none;
}

.login-welcome {
  text-align: center;
  margin-bottom: 32px;
}

.welcome-title {
  font-size: 24px;
  font-weight: 700;
  color: #2D3748;
  margin-bottom: 8px;
}

.welcome-subtitle {
  color: #718096;
  font-size: 16px;
}

.form-group {
  margin-bottom: 24px;
}

.form-label {
  display: block;
  margin-bottom: 8px;
  color: #4A5568;
  font-size: 14px;
  font-weight: 500;
}

.form-input {
  width: 100%;
  padding: 12px 16px;
  border: 1px solid #E2E8F0;
  border-radius: 12px;
  font-size: 16px;
  transition: all 0.3s ease;
}

.form-input:focus {
  outline: none;
  border-color: #4A90E2;
  box-shadow: 0 0 0 3px rgba(74,144,226,0.1);
}

.password-container {
  position: relative;
}

.password-toggle {
  position: absolute;
  right: 16px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  color: #718096;
  cursor: pointer;
  padding: 0;
}

.remember-me {
  display: flex;
  align-items: center;
  margin-bottom: 24px;
}

.remember-checkbox {
  margin-right: 8px;
}

.remember-text {
  color: #718096;
  font-size: 14px;
}

.login-button {
  width: 100%;
  padding: 14px;
  background: #4A90E2;
  color: white;
  border: none;
  border-radius: 12px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
}

.login-button:hover {
  background: #357ABD;
  transform: translateY(-1px);
}

.auxiliary-links {
  margin-top: 24px;
  text-align: center;
}

.auxiliary-link {
  color: #718096;
  text-decoration: none;
  font-size: 14px;
  transition: color 0.2s ease;
}

.auxiliary-link:hover {
  color: #4A90E2;
}

.separator {
  margin: 0 8px;
  color: #CBD5E0;
}

.signup-link {
  color: #4A90E2;
  font-weight: 500;
}

.footer {
  margin-top: 32px;
  text-align: center;
  color: #A0AEC0;
  font-size: 14px;
}

/* 반응형 디자인 */
@media (max-width: 1024px) {
  .brand-section {
    width: 40%;
  }
  
  .brand-section .tagline {
    font-size: 28px;
  }
}

@media (max-width: 768px) {
  .container {
    flex-direction: column;
  }
  
  .brand-section {
    display: none;
  }
  
  .login-section {
    padding: 24px;
  }
  
  .mobile-logo {
    display: block;
    text-align: center;
    margin-bottom: 32px;
  }
  
  .mobile-logo-bg {
    display: inline-block;
    background: linear-gradient(135deg, #4A90E2, #2C5282);
    margin: 0;
  }
  
  .login-card {
    box-shadow: none;
    padding: 0;
  }
}

@media (max-width: 480px) {
  .login-section {
    padding: 16px;
  }
  
  .welcome-title {
    font-size: 22px;
  }
  
  .form-input {
    font-size: 15px;
  }
}
</style>
