<script>
  import ErrorMessage from '$components/ErrorMessage.svelte';
	import TextInput from '$components/TextInput.svelte';
  import { isValidPassword, isValidUsername } from '$utils/validations';
  import { t } from 'svelte-i18n';

  let usernameError = '';
  let passwordError = '';

  const onInputUsername = (e) => {
    const username = e.target.value;

    if (!isValidUsername(username)) {
      usernameError = $t('errors.invalid.username');
    } else {
      usernameError = '';
    }
  };

  const onInputPassword = (e) => {
    const password = e.target.value;

    if (!isValidPassword(password)) {
      passwordError = $t('errors.invalid.password');
    } else {
      passwordError = '';
    }
  };

  const onSubmitSignup = async (e) => {
    const formData = new FormData(event.target);    
    const data = Object.fromEntries(formData.entries());

    if (!isValidUsername(data.username)) {
      usernameError = $t('errors.invalid.username');
      return;
    }

    if (!isValidPassword(data.password)) {
      passwordError = $t('errors.invalid.password');
      return;
    }
    
    fetch('/api/users/signup', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    })
    .then((res) => res.json())
    .then((data) => {
      if (!data.ok) {
        if (data.message === 'ERROR_EXISTS_USERNAME') {
          usernameError = $t('errors.exists.username');
        } else {
          alert($t('signup.fail'));
        }

        return;
      }
      
      alert($t('signup.success'));
      location.href = '/login';
    })
    .catch((error) => {
      console.error('Error creating post:', error);
    });
  };
</script>

<div class="flex justify-center items-center h-full flex-col gap-1">
  <div class="card bg-base-100 w-96 shadow">
    <div class="card-body">
      <div class="card-header">
        <h1 class="card-title">{$t('signup.title')}</h1>
      </div>
      <form class="flex flex-col gap-5" on:submit="{onSubmitSignup}">
        <div class="flex flex-col gap-4">
          <input type="text" name="role" value="TEACHER" hidden/>
          <TextInput type="text" name="username" placeholder="{$t('id')}" required oninput={onInputUsername}/>
          <ErrorMessage message="{usernameError}"/>
          <TextInput type="password" name="password" placeholder="{$t('password')}" required oninput="{onInputPassword}"/>
          <ErrorMessage message="{passwordError}"/>
          <TextInput type="text" name="name" placeholder="{$t('name')}" required/>
        </div>

        <button type="submit" class="btn btn-primary">{$t('signup')}</button>
      </form>
    </div>
  </div>
</div>