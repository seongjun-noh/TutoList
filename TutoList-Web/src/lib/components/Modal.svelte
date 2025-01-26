<script>
  export let isOpen = false; // 모달 표시 여부
  export let title = ""; // 모달 제목
  export let addClass = ""
</script>

{#if isOpen}
  <dialog class="modal {addClass}" open>
    <div class="modal-box">
      {#if title}
        <h3 class="text-lg font-bold">{title}</h3>
      {/if}
      <div>
        <slot></slot>
      </div>
    </div>
  </dialog>
{/if}