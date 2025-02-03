<script>
	import { isLoading } from "$lib/stores/loadingStore";
	import BlockUi from "$lib/components/loading/BlockUI.svelte";
  import "../app.css";

  import { onMount } from 'svelte';
  import { themeChange } from 'theme-change';

  onMount(() => {
    themeChange(false);
  });
</script>

<div>
  <slot />
  <BlockUi show={$isLoading} />
</div>