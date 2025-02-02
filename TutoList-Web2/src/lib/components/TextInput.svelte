<script>
  export let addClass = ''
  export let value = '';
</script>

<input
  class="input input-bordered w-full {addClass}"
  bind:value = {value}
  {...$$restProps} 
/>
