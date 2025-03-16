<!-- src/routes/users/signup/+page.svelte -->
<script>
  import { BookOpen, User, Mail, KeyRound } from 'lucide-svelte';
  import ErrorMessage from '$components/ErrorMessage.svelte';
  import { isValidUsername, isValidPassword } from '$utils/validations';
  // import { isValidUsername, isValidPassword, isValidEmail, isValidName } from '$utils/validations';
  import TextInputWithIcon from '$lib/components/TextInputWithIcon.svelte';
  import apiClient from '$lib/utils/apiClient';
	import { goto } from '$app/navigation';

  let username = '';
  let password = '';
  let passwordConfirm = '';
  let email = '';
  let name = '';
  let signupErrorMessage = '';
  let agreed = false;

  const onSubmitSignupForm = async function (event) {
    const formData = new FormData(event.target);    
    const data = Object.fromEntries(formData.entries());

    if (!isValidUsername(data.username)) {
      signupErrorMessage = '아이디는 6~20자의 영문, 숫자만 가능합니다.';
      return;
    }

    if (!isValidPassword(data.password)) {
      signupErrorMessage = '비밀번호는 8~20자의 영문, 숫자, 특수문자를 포함해야 합니다.';
      return;
    }

    if (password !== passwordConfirm) {
      signupErrorMessage = '비밀번호가 일치하지 않습니다.';
      return;
    }

    // if (!isValidEmail(data.email)) {
    //   signupErrorMessage = '유효한 이메일 주소를 입력해주세요.';
    //   return;
    // }

    // if (!isValidName(data.name)) {
    //   signupErrorMessage = '이름을 올바르게 입력해주세요.';
    //   return;
    // }

    if (!agreed) {
      signupErrorMessage = '이용약관에 동의해주세요.';
      return;
    }

    try {
      await apiClient.post('/signup', data, {
        disableBlockUI: true,
        headers: { 'Content-Type': 'application/json' },
      });
      
      goto('/login');
    } catch (error) {
      console.error('Signup error:', error);
      signupErrorMessage = '회원가입에 실패했습니다. 다시 시도해 주세요.';
    }
  }
</script>

<div class="min-h-screen bg-gray-50 flex flex-col justify-center py-12 sm:px-6 lg:px-8">
  <div class="sm:mx-auto sm:w-full sm:max-w-md">
    <div class="flex justify-center">
      <BookOpen class="h-12 w-12 text-blue-600" />
    </div>
    <h2 class="mt-6 text-center text-3xl font-bold text-gray-900">
      회원가입
    </h2>
    <p class="mt-2 text-center text-sm text-gray-600">
      TutoList와 함께 시작하세요
    </p>
  </div>

  <div class="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
    <div class="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
      <form class="space-y-6" on:submit|preventDefault={onSubmitSignupForm}>
        <div class="space-y-4">
          <TextInputWithIcon 
            type="text" 
            name="username" 
            bind:value={username}
            placeholder="아이디"
            icon={User}
          />
          <TextInputWithIcon 
            type="password" 
            name="password" 
            bind:value={password}
            placeholder="비밀번호"
            icon={KeyRound}
          />
          <TextInputWithIcon 
            type="password" 
            name="passwordConfirm" 
            bind:value={passwordConfirm}
            placeholder="비밀번호 확인"
            icon={KeyRound}
          />
          <TextInputWithIcon 
            type="text" 
            name="name" 
            bind:value={name}
            placeholder="이름"
            icon={User}
          />
          <TextInputWithIcon 
            type="email" 
            name="email" 
            bind:value={email}
            placeholder="이메일"
            icon={Mail}
          />
        </div>

        {#if signupErrorMessage}
          <ErrorMessage message={signupErrorMessage}/>
        {/if}

        <div class="flex items-center">
          <input
            id="agree"
            name="agree"
            type="checkbox"
            bind:checked={agreed}
            class="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300 rounded"
          />
          <label for="agree" class="ml-2 block text-sm text-gray-900">
            <a href="/terms" class="font-medium text-blue-600 hover:text-blue-500">이용약관</a> 및
            <a href="/privacy" class="font-medium text-blue-600 hover:text-blue-500">개인정보 처리방침</a>에 동의합니다
          </label>
        </div>

        <button
          type="submit"
          class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
        >
          회원가입
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
            이미 계정이 있으신가요?
            <a href="/login" class="font-medium text-blue-600 hover:text-blue-500">
              로그인
            </a>
          </p>
        </div>
      </div>
    </div>
  </div>
</div>