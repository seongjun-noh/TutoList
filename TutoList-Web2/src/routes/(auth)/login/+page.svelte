<!-- src/routes/login/+page.svelte -->
<svelte:head>
  <title>로그인</title>
  <meta name="description" content="TutoList 로그인 페이지" />
</svelte:head>

<script>
  import { User, KeyRound, BookOpen } from 'lucide-svelte';
  import ErrorMessage from '$components/ErrorMessage.svelte';
  import { isValidUsername, isValidPassword } from '$utils/validations';
  import TextInputWithIcon from '$lib/components/TextInputWithIcon.svelte';
  import apiClient from '$lib/utils/apiClient';
	import { user } from '$lib/stores/userStore';
	import { goto } from '$app/navigation';

  let loginErrorMessage = '';

  const onSubmitLoginForm = async function (event) {
    const formData = new FormData(event.target);    
    const data = Object.fromEntries(formData.entries());

    if (!isValidUsername(data.username) || !isValidPassword(data.password)) {
      loginErrorMessage = '아이디 또는 비밀번호가 올바르지 않습니다.';
      return;
    }

    try {
      const response = await apiClient.post('/login', data, {
        disableBlockUI: true,
        headers: {
          'Content-Type': 'application/json',
        },
      });

      $user = response.data.data;

      goto('/');
    } catch (error) {
      console.error('Login error:', error);
      throw error;
    }
  }
</script>

<div class="min-h-screen bg-gray-50 flex flex-col justify-center py-12 sm:px-6 lg:px-8">
  <div class="sm:mx-auto sm:w-full sm:max-w-md">
    <div class="flex justify-center">
      <BookOpen class="h-12 w-12 text-blue-600" />
    </div>
    <h2 class="mt-6 text-center text-3xl font-bold text-gray-900">
      TutoList에 오신 것을 환영합니다
    </h2>
    <p class="mt-2 text-center text-sm text-gray-600">
      더 나은 교육을 위한 첫걸음
    </p>
  </div>

  <div class="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
    <div class="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
      <form class="space-y-6" on:submit|preventDefault={onSubmitLoginForm}>
        <div class="space-y-4">
          <TextInputWithIcon 
            type="text" 
            name="username" 
            placeholder="아이디" 
            icon={User}
          />
          <TextInputWithIcon 
            type="password" 
            name="password" 
            placeholder="비밀번호" 
            icon={KeyRound}
          />
        </div>

        {#if loginErrorMessage}
          <ErrorMessage message={loginErrorMessage}/>
        {/if}

        <div class="flex items-center justify-between">
          <div class="flex items-center space-x-4">
            <div class="flex items-center">
              <input
                type="checkbox"
                id="keeplogin"
                name="rememberMe"
                value="keeplogin"
                class="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300 rounded"
              />
              <label for="keeplogin" class="ml-2 block text-sm text-gray-900">
                로그인 상태 유지
              </label>
            </div>
            <div class="flex items-center">
              <input
                type="checkbox"
                id="remember"
                name="remember"
                value="remember"
                class="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300 rounded"
              />
              <label for="remember" class="ml-2 block text-sm text-gray-900">
                아이디 저장
              </label>
            </div>
          </div>
        </div>

        <button
          type="submit"
          class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
        >
          로그인
        </button>
      </form>

      <div class="mt-6">
        <div class="relative">
          <div class="absolute inset-0 flex items-center">
            <div class="w-full border-t border-gray-300"></div>
          </div>
          <div class="relative flex justify-center text-sm">
            <span class="px-2 bg-white text-gray-500">또는</span>
          </div>
        </div>

        <div class="mt-6">
          <p class="text-center text-sm text-gray-600">
            계정이 없으신가요?
            <a href="/signup" class="font-medium text-blue-600 hover:text-blue-500">
              회원가입
            </a>
          </p>
        </div>
      </div>
    </div>
  </div>
</div>