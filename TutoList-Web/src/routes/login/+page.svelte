<svelte:head>
	<title>login</title>
	<meta name="description" content="TutoList Loging Page" />
</svelte:head>

<script>
  import { t } from 'svelte-i18n';
  import ErrorMessage from '$components/ErrorMessage.svelte';
  import { isValidUsername, isValidPassword } from '$utils/validations';
	import TextInputWithIcon from '$lib/components/TextInputWithIcon.svelte';
	import axios from 'axios';
	import apiClient from '$lib/utils/apiClient';

  let loginErrorMessage = ''; // 비밀번호 오류 메시지 변수

  const onSubmitLoginForm = async function (event) {
    const formData = new FormData(event.target);    
    const data = Object.fromEntries(formData.entries());

    if (!isValidUsername(data.username) || !isValidPassword(data.password)) {
      loginErrorMessage = $t('login.fail');
      return;
    }

    apiClient.post('/login', data, {
      disableBlockUI: true,
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then((response) => {
      // 메인 페이지로 이동
      location.href = '/';
    })
    .catch((error) => {
      console.error('Login error:', error);
      throw error; // 에러를 호출한 곳으로 다시 던짐
    });
  }
</script>

<div class="flex justify-center items-center h-full flex-col gap-1">
  <div class="card bg-base-100 w-96 shadow">
    <div class="card-body">
      <div class="card-header">
        <h1 class="card-title">{$t('login.title')}</h1>
      </div>

      <form action="/login" method="post" on:submit|preventDefault={onSubmitLoginForm}>
        <div class="flex gap-5 flex-col">
          <div class="flex gap-4 flex-col">
            <TextInputWithIcon type="text" name="username" placeholder={$t('id')} icon="/src/lib/images/icon/people.svg"/>
            <TextInputWithIcon type="password" name="password" placeholder={$t('password')} icon="/src/lib/images/icon/key.svg"/>
          </div>
          <ErrorMessage message={loginErrorMessage}/>
          <button class="btn btn-primary grow" type="submit">{$t('login')}</button>
        </div>
      </form>
      <div class="flex gap-1 flex-col">
        <label>
          <input type="checkbox" id="keeplogin" name="rememberMe" value="keeplogin" />
          <span>{$t('login.keep')}</span>
        </label>
        <label>
          <input type="checkbox" id="remember" name="remember" value="remember" />
          <span>{$t('login.remember')}</span>
        </label>
      </div>
    </div>
  </div>

  <div>
    <a href="/users/signup">{$t('signup')}</a>
  </div>
</div>