// src/routes/+layout.js
import { redirect } from '@sveltejs/kit';

export async function load({ fetch }) {
  try {
    const res = await fetch('/api/check-session', {
      method: 'GET',
      credentials: 'include',
    });

    if (!res.ok) {
      console.error('Invalid session:', res.status);
      throw redirect(302, '/login'); // 서버에서 리다이렉트
    }

    const data = await res.json();
    if (!data?.data) {
      console.error('Session expired');
      throw redirect(302, '/login'); // 세션 만료 시 리다이렉트
    }

    return {
      sessionValid: true,
    };
  } catch (error) {
    console.error('Session check failed:', error);
    throw redirect(302, '/login'); // 에러 발생 시 리다이렉트
  }
}
