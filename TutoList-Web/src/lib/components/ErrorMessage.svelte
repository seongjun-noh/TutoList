<script>
  export let message = '';
</script>

{#if message}
  <span class="label-text-alt text-error">{message}</span>
{/if}