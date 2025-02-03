<!-- src/routes/students/+page.svelte -->
<script>
  import { Search, Plus, Users, Clock } from 'lucide-svelte';
	import LessonItem from './LessonItem.svelte';
	import apiClient from '$lib/utils/apiClient';
	import { onMount } from 'svelte';
	import LoadingDots from '$lib/components/loading/LoadingDots.svelte';

  let lessons

  let searchQuery = '';

  onMount(async () => {
    await fetchLessons();
  });

  async function fetchLessons() {
    try {
			const response = await apiClient.get('/lessons');

			lessons = response.data.data;
		} catch (error) {
			console.error('Error fetching calendar events:', error);
			throw error;
		}
  }
</script>

<header class="h-16 bg-white border-b border-gray-200 flex items-center justify-between px-8">
  <h1 class="text-2xl font-bold text-gray-900">수업관리</h1>
  <div class="flex items-center space-x-4">
    <div class="relative">
      <Search class="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-gray-400" />
      <input
        type="text"
        bind:value={searchQuery}
        placeholder="수업 검색..."
        class="pl-10 pr-4 py-2 border border-gray-200 rounded-lg w-64 focus:outline-none focus:ring-2 focus:ring-blue-500"
      />
    </div>
    <a class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 flex items-center" href="/lessons/regist">
      <Plus class="h-4 w-4 mr-2" />
      새 수업 등록
    </a>
  </div>
</header>

<main class="flex-1 overflow-auto p-8">
  <!-- Summary Cards -->
  <div class="grid grid-cols-4 gap-6 mb-8">
    <div class="bg-white p-6 rounded-lg shadow-sm">
      <div class="flex items-center justify-between mb-4">
        <h3 class="text-sm font-medium text-gray-600">총 학생 수</h3>
        <Users class="h-4 w-4 text-blue-600" />
      </div>
      <div class="text-2xl font-bold">12명</div>
      <p class="text-xs text-gray-500">활성 학생 기준</p>
    </div>
    <!-- 다른 카드들... -->
  </div>

  <!-- Student List -->
  <div class="bg-white rounded-lg shadow-sm">
    <div class="p-6">
      <h2 class="text-lg font-medium mb-4">수업 목록</h2>
      <div class="space-y-4">
        {#if lessons == null}
          <div class="flex justify-center p-4">
            <LoadingDots size="sm" />
          </div>
        {:else if lessons.length === 0}
          <div class="py-12 flex flex-col items-center justify-center text-center">
            <div class="bg-gray-50 p-4 rounded-full mb-4">
              <Users class="h-12 w-12 text-gray-400" />
            </div>
            <h3 class="text-lg font-medium text-gray-900 mb-2">아직 등록된 수업이 없습니다</h3>
            <p class="text-sm text-gray-500 mb-6 max-w-sm">
              새로운 수업을 등록하여 학생들의 학습 여정을 시작해보세요.
            </p>
            <a href="/lessons/regist" class="inline-flex items-center px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors">
              <Plus class="h-4 w-4 mr-2" />
              첫 수업 등록하기
            </a>
          </div>
        {:else}
          <div class="space-y-4">
            {#each lessons as lesson}
              <LessonItem lesson={lesson} />
            {/each}
          </div>
        {/if}
      </div>
    </div>
  </div>
</main>