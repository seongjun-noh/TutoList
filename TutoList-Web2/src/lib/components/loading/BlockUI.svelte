<script>
  import LoadingDots from '$lib/components/loading/LoadingDots.svelte';
  export let show = false;
</script>

{#if show}
  <div class="block-ui-overlay">
    <LoadingDots size="lg" />
  </div>
{/if}

<style>
  .block-ui-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(255, 255, 255, 0.9);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
  }
</style>