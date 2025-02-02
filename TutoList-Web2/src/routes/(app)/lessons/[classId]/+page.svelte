<!-- src/routes/students/[id]/+page.svelte -->
<script>
  import { page } from '$app/stores';
  import { 
    ChevronLeft,
    Edit,
    Trash2,
    Phone,
    Mail
  } from 'lucide-svelte';
  import StudentProfile from '$lib/components/StudentProfile.svelte';
  
  export let data; // 페이지 데이터
</script>

<header class="h-16 bg-white border-b border-gray-200 flex items-center justify-between px-8">
  <div class="flex items-center">
    <a href="/students" class="flex items-center text-gray-600 hover:text-gray-900">
      <ChevronLeft class="h-5 w-5 mr-2" />
      <span class="text-sm">수업 목록으로 돌아가기</span>
    </a>
  </div>
  <div class="flex items-center space-x-4">
    <button class="px-4 py-2 text-gray-700 border border-gray-300 rounded-lg hover:bg-gray-50 flex items-center">
      <Edit class="h-4 w-4 mr-2" />
      정보 수정
    </button>
    <button class="px-4 py-2 text-red-600 border border-red-200 rounded-lg hover:bg-red-50 flex items-center">
      <Trash2 class="h-4 w-4 mr-2" />
      수업 삭제
    </button>
  </div>
</header>

<main class="flex-1 overflow-auto p-8">
  <StudentProfile student={data.student} />
</main>