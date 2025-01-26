import { redirect } from '@sveltejs/kit';

export async function load({ fetch }) {
  try {
    const res = await fetch('/api/login/check', {
      method: 'GET',
      credentials: 'include',
    });

    if (!res.ok) {
      console.error('Invalid session:', res.status);
      throw redirect(302, '/login'); // 서버에서 리다이렉트
    }

    return {
      sessionValid: true,
    };
  } catch (error) {
    console.error('Session check failed:', error);
    throw redirect(302, '/login'); // 에러 발생 시 리다이렉트
  }
}