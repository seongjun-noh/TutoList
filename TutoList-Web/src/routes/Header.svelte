<script>
	import { page } from '$app/state';
	import { t } from 'svelte-i18n';
	import logo from '$lib/images/svelte-logo.svg';
	import github from '$lib/images/github.svg';
	import { onMount } from 'svelte';
	import apiClient from '$lib/utils/apiClient';

	const onClickLogoutBtn = () => {
		apiClient.post('/logout', {
			withCredentials: true,
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then((response) => {
      // 메인 페이지로 이동
      location.href = '/login';
    })
    .catch((error) => {
      console.error('Logout error:', error);
      throw error; // 에러를 호출한 곳으로 다시 던짐
    });
	}

	let currentTheme = 'system';

	const setTheme = (theme) => {
		currentTheme = theme;
		document.documentElement.setAttribute('data-theme', theme);
		localStorage.setItem('theme', theme);
	};

	onMount(() => {
		const savedTheme = localStorage.getItem('theme');
		if (savedTheme) {
			setTheme(savedTheme);
		} else {
			const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
			const defaultTheme = prefersDark ? 'dark' : 'light';
			setTheme(defaultTheme);
		}
	});
</script>

<header class="lg:border-b">
	<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
	<div class="navbar bg-base-100">
		<div class="flex-1">
			<a class="btn btn-ghost text-xl" href="/">
				TutoList
			</a>
		</div>

		<div class="flex">
			<a href="/calendar">calendar</a>
			<a href="/students">students</a>
		</div>
		
		<div title="Change Theme" class="dropdown dropdown-end">
			<div tabindex="0" role="button" class="btn btn-ghost">
				<span class="hidden font-normal md:inline">테마 변경</span>
			</div>

			<div class="dropdown-content bg-base-200 text-base-content mt-2 w-32 p-2 shadow flex flex-col gap-2">
				<button
					on:click={() => setTheme('system')}
					class="btn"
					aria-current={currentTheme === 'system' ? 'true' : 'false'}>
					{#if currentTheme === 'system'}
						<img src="/src/lib/images/icon/check.svg" alt="Icon" />
					{/if}
					Default
				</button>
				<button
					on:click={() => setTheme('light')}
					class="btn"
					aria-current={currentTheme === 'light' ? 'true' : 'false'}>
					{#if currentTheme === 'light'}
						<img src="/src/lib/images/icon/check.svg" alt="Icon" />
					{/if}
					Light
				</button>
				<button
					on:click={() => setTheme('dark')}
					class="btn"
					aria-current={currentTheme === 'dark' ? 'true' : 'false'}>
					{#if currentTheme === 'dark'}
						<img src="/src/lib/images/icon/check.svg" alt="Icon" />
					{/if}
					Dark
				</button>
			</div>
		</div>
		<button on:click|preventDefault={onClickLogoutBtn}>{$t('logout')}</button>
	</div>
</div>
</header>

<style>
</style>
