<script>
	import { isLoading } from "$lib/stores/loadingStore";
  import "../app.css";

	import Footer from './(app)/Footer.svelte';
  import Header from './Header.svelte';

  import { onMount } from 'svelte';
  import { themeChange } from 'theme-change';

  onMount(() => {
    // 두 번째 인자를 true로 설정해 브라우저에서만 실행하도록 함
    themeChange(false);
  });
</script>

<div>
  <Header />
    <slot />
  <Footer />

  {#if $isLoading}
    <div class="block-ui-overlay">
      Loading, please wait...
    </div>
  {/if}
</div>

<style>
  .block-ui-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
    color: white;
    font-size: 1.5rem;
  }
</style>