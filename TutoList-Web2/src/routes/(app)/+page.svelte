<!-- src/routes/+page.svelte -->
<script>
  import { 
    Users,
    Calendar,
    Clock,
    BookOpen
  } from 'lucide-svelte';

  const cards = [
    {
      title: '총 학생 수',
      value: '12명',
      subtext: '전월 대비 +2명',
      icon: Users
    },
    {
      title: '이번 달 수업',
      value: '48회',
      subtext: '목표 달성률 80%',
      icon: Calendar
    },
    {
      title: '예정된 수업',
      value: '5회',
      subtext: '오늘 2회 / 이번주 5회',
      icon: Clock
    },
    {
      title: '월 수익',
      value: '₩2,400,000',
      subtext: '전월 대비 +12%',
      icon: BookOpen
    }
  ];

  const todayClasses = [
    { time: "15:00", student: "김민준", subject: "수학", duration: "2시간" },
    { time: "17:30", student: "이서연", subject: "영어", duration: "1.5시간" }
  ];
</script>

<header class="h-16 bg-white border-b border-gray-200 flex items-center justify-between px-8">
  <h1 class="text-2xl font-bold text-gray-900">대시보드</h1>
  <button class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
    + 새 수업 등록
  </button>
</header>

<main class="flex-1 overflow-auto p-8">
  <!-- Summary Cards -->
  <div class="grid grid-cols-4 gap-6 mb-8">
    {#each cards as { title, value, subtext, icon: Icon }}
      <div class="bg-white p-6 rounded-lg shadow-sm">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-sm font-medium text-gray-600">{title}</h3>
          <Icon class="h-5 w-5 text-blue-600" />
        </div>
        <div class="text-2xl font-bold">{value}</div>
        <p class="text-sm text-gray-500">{subtext}</p>
      </div>
    {/each}
  </div>

  <!-- Charts and Schedule -->
  <div class="grid grid-cols-2 gap-6">
    <div class="bg-white p-6 rounded-lg shadow-sm">
      <h3 class="text-lg font-medium mb-4">월별 수업 현황</h3>
      <div class="h-64 bg-gray-50">
        <!-- Chart will be implemented -->
      </div>
    </div>

    <div class="bg-white p-6 rounded-lg shadow-sm">
      <h3 class="text-lg font-medium mb-4">오늘의 수업</h3>
      <div class="space-y-4">
        {#each todayClasses as lesson}
          <div class="flex items-center justify-between p-4 bg-gray-50 rounded-lg">
            <div class="flex items-center space-x-4">
              <div class="w-10 h-10 rounded-full bg-blue-100 flex items-center justify-center">
                <Clock class="h-5 w-5 text-blue-600" />
              </div>
              <div>
                <div class="font-medium">{lesson.student}</div>
                <div class="text-sm text-gray-500">{lesson.subject}</div>
              </div>
            </div>
            <div class="text-right">
              <div class="font-medium">{lesson.time}</div>
              <div class="text-sm text-gray-500">{lesson.duration}</div>
            </div>
          </div>
        {/each}
      </div>
    </div>
  </div>
</main>