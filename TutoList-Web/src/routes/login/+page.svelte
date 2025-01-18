<svelte:head>
	<title>login</title>
	<meta name="description" content="TutoList Loging Page" />
</svelte:head>

<script>
  export const layout = null;

  let passwordError = ''; // 비밀번호 오류 메시지 변수

  const onSubmitLoginForm = async function (event) {
    const formData = new FormData(event.target);    
    const data = Object.fromEntries(formData.entries());

    if (data.password.length < 6) {
      passwordError = '비밀번호가 일치하지 않습니다.';
      return;
    }
    
    fetch('/api/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      credentials: 'include', // 세션 쿠키를 포함
      body: JSON.stringify(data)
    })
    .then((res) => {
      if (!res.ok) {
        throw new Error('Failed to submit data');
      }
      
      location.href = '/';
    })
    .catch((error) => {
      console.error('Error creating post:', error);
    });
  }
</script>

<div class="flex justify-center items-center h-full">
  <div class="card bg-base-100 w-96 shadow">
    <div class="card-body">
      <div class="card-header">
        <h1 class="card-title">Login</h1>
      </div>

      <form action="/login" method="post" on:submit|preventDefault={onSubmitLoginForm}>
        <div class="flex gap-5 flex-col">
          <div class="flex gap-4 flex-col">
            <label class="input input-bordered flex items-center gap-2">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 16 16"
                fill="currentColor"
                class="h-4 w-4 opacity-70">
                <path
                  d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6ZM12.735 14c.618 0 1.093-.561.872-1.139a6.002 6.002 0 0 0-11.215 0c-.22.578.254 1.139.872 1.139h9.47Z" />
              </svg>
              <input type="text" class="grow" name="username" placeholder="아이디" />
            </label>
            <label class="input input-bordered flex items-center gap-2" class:input-error={passwordError}>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 16 16"
                fill="currentColor"
                class="h-4 w-4 opacity-70">
                <path
                  fill-rule="evenodd"
                  d="M14 6a4 4 0 0 1-4.899 3.899l-1.955 1.955a.5.5 0 0 1-.353.146H5v1.5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1-.5-.5v-2.293a.5.5 0 0 1 .146-.353l3.955-3.955A4 4 0 1 1 14 6Zm-4-2a.75.75 0 0 0 0 1.5.5.5 0 0 1 .5.5.75.75 0 0 0 1.5 0 2 2 0 0 0-2-2Z"
                  clip-rule="evenodd" />
              </svg>
              <input type="password" class="grow" name="password" placeholder="비밀번호" />
            </label>
            {#if passwordError}
              <span class="label-text-alt text-error">{passwordError}</span>
            {/if}
          </div>
      
          <button class="btn grow" type="submit">로그인</button>
        </div>
      </form>
      <div class="flex gap-1 flex-col">
        <label>
          <input type="checkbox" id="keeplogin" name="keeplogin" value="keeplogin" />
          <span>로그인 유지</span>
        </label>
        <label>
          <input type="checkbox" id="remember" name="remember" value="remember" />
          <span>ID 기억하기</span>
        </label>
      </div>
    </div>
  </div>
</div>