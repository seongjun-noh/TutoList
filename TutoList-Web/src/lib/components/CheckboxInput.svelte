<script>
  export let labelText='';
  export let checked;
</script>

<div class="form-control min-w-fit">
  <label class="label cursor-pointer gap-2">
    <input type="checkbox" class="checkbox" bind:checked={checked}/>
    <span class="label-text">{labelText}</span>
  </label>
</div>