<!-- src/lib/components/TextInputWithIcon.svelte -->
<script>
  export let icon;
  export let value;
</script>

<label class="input input-bordered flex items-center gap-2">
  <svelte:component this={icon} class="h-4 w-4 opacity-70" />
  <input class="grow" bind:value={value} {...$$restProps}/>
</label>