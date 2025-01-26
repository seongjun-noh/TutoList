<script>
  export let icon;
  export let value;
</script>

<label class="input input-bordered flex items-center gap-2">
  <img src="{icon}" alt="Icon" class="h-4 w-4 opacity-70" />
  <input class="grow" bind:value={value} {...$$restProps}/>
</label>