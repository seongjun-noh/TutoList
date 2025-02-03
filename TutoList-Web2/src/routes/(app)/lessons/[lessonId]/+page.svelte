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
  import StudentProfile from './StudentProfile.svelte';
	import { onMount } from 'svelte';
	import apiClient from '$lib/utils/apiClient.js';
  
  let lesson;

  onMount(async () => {
    await fetchLesson();
  });

  async function fetchLesson() {
    const url = `/lessons/${$page.params.lessonId}`
    try {
			const response = await apiClient.get(url);

			lesson = response.data.data;
      console.log(lesson);
		} catch (error) {
			console.error('Error fetching calendar events:', error);
			throw error;
		}
  }
</script>

<header class="h-16 bg-white border-b border-gray-200 flex items-center justify-between px-8">
  <div class="flex items-center">
    <a href="/lessons" class="flex items-center text-gray-600 hover:text-gray-900">
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
  {#if lesson}
    <StudentProfile lesson={lesson} />
  {/if}
</main>