import { redirect } from '@sveltejs/kit';

/** 
 * @type {import('@sveltejs/kit').Handle} 
 */
export async function handle({ event, resolve }) {
  const { cookies, url } = event;

  // 로그인 페이지는 예외 처리
  if (url.pathname === '/login' || url.pathname === '/api/login/check') {
    return resolve(event);
  }

  const session = cookies.get('JSESSIONID'); // 세션 쿠키 확인
  const isValidSession = await checkSessionValidity(event);

  // 세션이 없거나 유효하지 않으면 로그인 페이지로 리디렉션
  if (!session || !isValidSession) {
    throw redirect(302, '/login');
  }

  // 인증된 사용자이거나, 인증이 필요 없는 경로인 경우 요청 계속 처리
  return resolve(event);
}

async function checkSessionValidity(event) {
  try {
    const response = await event.fetch('/api/check-session', {
      method: 'GET',
      credentials: 'include', // 세션 쿠키 포함
    });

    if (!response.ok) {
      console.error('Invalid session:', response.status);
      return false;
    } 
    
    const responseBody = await response.json();
    if (responseBody.data === false) {
      console.error('Session expired');
      return false;
    }

    return true;
  } catch (error) {
    console.error('Session check failed:', error);
    return false;
  }
}
