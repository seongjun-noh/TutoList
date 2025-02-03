<script>
  // 크기 조절을 위한 prop
  export let size = 'md';
  
  const sizes = {
    sm: {
      dot: 'w-2 h-2',
      gap: 'gap-1',
      text: 'text-sm'
    },
    md: {
      dot: 'w-3 h-3',
      gap: 'gap-2',
      text: 'text-base'
    },
    lg: {
      dot: 'w-4 h-4',
      gap: 'gap-2',
      text: 'text-lg'
    }
  };
</script>

<div class="loading-container">
  <div class="loading-dots {sizes[size].gap}">
    <div class="dot {sizes[size].dot}"></div>
    <div class="dot {sizes[size].dot}"></div>
    <div class="dot {sizes[size].dot}"></div>
  </div>
  <slot>
    <div class="loading-text {sizes[size].text}">Loading...</div>
  </slot>
</div>

<style>
  .loading-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 0.5rem;
  }

  .loading-dots {
    display: flex;
  }

  .dot {
    background-color: #3b82f6;
    border-radius: 50%;
    animation: bounce 0.5s ease-in-out infinite;
  }

  .dot:nth-child(2) {
    animation-delay: 0.1s;
    background-color: #60a5fa;
  }

  .dot:nth-child(3) {
    animation-delay: 0.2s;
    background-color: #93c5fd;
  }

  .loading-text {
    color: #3b82f6;
    font-weight: 500;
  }

  @keyframes bounce {
    0%, 100% { transform: translateY(0); }
    50% { transform: translateY(-8px); }
  }

  @media (prefers-reduced-motion: reduce) {
    .dot { animation: none; }
  }
</style>