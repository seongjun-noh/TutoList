import axios from 'axios';
import { isLoading } from '$lib/stores/loadingStore';
import { goto } from '$app/navigation';

const apiClient = axios.create({
    baseURL: '/api', // 모든 요청의 기본 URL
    timeout: 5000, // 요청 타임아웃 (밀리초)
    withCredentials: true,
});

// 요청 인터셉터
apiClient.interceptors.request.use(
    (config) => {
        // console.log('Request:', config);

        if (config.disableBlockUI) {
            isLoading.set(true);
        }

        return config;
    },
    (error) => {
        // console.error('Request error:', error);

        isLoading.set(false);

        return Promise.reject(error);
    }
);

// 응답 인터셉터
apiClient.interceptors.response.use(
    (response) => {
        // console.log('Response:', response);

        if (response.config.disableBlockUI) {
            isLoading.set(false);
        }

        return response;
    },
    (error) => {
        // console.error('Response error:', error);

        isLoading.set(false);

        if (error.response && error.response.status === 401) {
            const currentPath = window.location.pathname; // 현재 페이지 경로

            if (currentPath !== '/login') {
                // 현재 페이지가 '/login'이 아니면 리다이렉트
                goto('/login');
            }
        }

        return Promise.reject(error);
    }
);

export default apiClient;
