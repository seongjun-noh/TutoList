<!-- src/routes/students/+page.svelte -->
<script>
  import { Search, Plus, Users, Clock } from 'lucide-svelte';

  const lessons = [
    { name: "김민준", grade: "고등학교 2학년", subject: "수학", schedule: "월/수 17:00", progress: 85 },
    { name: "이서연", grade: "중학교 3학년", subject: "영어", schedule: "화/목 15:00", progress: 92 },
    { name: "박지훈", grade: "고등학교 1학년", subject: "수학", schedule: "월/금 19:00", progress: 78 },
    { name: "최예린", grade: "중학교 2학년", subject: "영어", schedule: "수/금 16:00", progress: 88 }
  ];

  let searchQuery = '';
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
        {#each lessons as lesson}
          <a 
            href="/lessons/{lesson.name}" 
            class="flex items-center justify-between p-4 bg-gray-50 rounded-lg hover:bg-gray-100 cursor-pointer"
          >
            <div class="flex items-center space-x-4">
              <div class="w-10 h-10 rounded-full bg-blue-100 flex items-center justify-center">
                <Users class="h-5 w-5 text-blue-600" />
              </div>
              <div>
                <div class="font-medium">{lesson.name}</div>
                <div class="text-sm text-gray-500">{lesson.grade}</div>
              </div>
            </div>
            <div class="flex items-center space-x-8">
              <div class="text-sm">
                <div class="font-medium">{lesson.subject}</div>
                <div class="text-gray-500">{lesson.schedule}</div>
              </div>
              <div class="w-32">
                <div class="text-sm font-medium mb-1">진도율</div>
                <div class="h-2 bg-gray-200 rounded-full">
                  <div 
                    class="h-2 bg-blue-600 rounded-full" 
                    style="width: {lesson.progress}%"
                  ></div>
                </div>
              </div>
            </div>
          </a>
        {/each}
      </div>
    </div>
  </div>
</main>