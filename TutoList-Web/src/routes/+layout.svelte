<script>
  import "../app.css";

	import Footer from './Footer.svelte';
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
</div>