<script>
	import { page } from '$app/state';
	import logo from '$lib/images/svelte-logo.svg';
	import github from '$lib/images/github.svg';
</script>

<header class="lg:border-b">
	<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
	<div class="navbar bg-base-100">
		<div class="flex-1">
			<a class="btn btn-ghost text-xl" href="/">
				TutoList
			</a>
		</div>
	
		<select class="select select-bordered" data-choose-theme data-key="selected-theme" bind:this={page.theme}>
			<option value="">OS Default</option>
			<option value="light">Light</option>
			<option value="dark">Dark</option>
		</select>
	</div>
</div>
</header>

<style>
</style>
