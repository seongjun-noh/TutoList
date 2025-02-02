<!-- src/lib/components/StudentProfile.svelte -->
<script>
  import { 
    Phone, 
    Mail, 
    Users, 
    BookOpenCheck,
    MessageSquare
  } from 'lucide-svelte';

  // 학생 데이터 props 받기
  export let student = {
    name: "김민준",
    grade: "고등학교 2학년",
    phone: "010-1234-5678",
    email: "student@email.com",
    startDate: "2024년 1월 15일",
    subject: "수학",
    frequency: "주 2회",
    schedule: "월/수 17:00-19:00",
    fee: "월 400,000원"
  };

  // 최근 진도 데이터
  const recentProgress = [
    { topic: "미분 공식 - 기본", completed: true },
    { topic: "도함수의 활용", completed: true },
    { topic: "적분 개념", completed: false }
  ];

  // 수업 이력 데이터
  const classHistory = [
    { 
      date: "2024-02-01", 
      topic: "도함수의 활용",
      startTime: "17:00",
      endTime: "19:00", 
      homework: "완료", 
      note: "문제 풀이 집중 연습",
      materials: ["기출문제.pdf", "연습문제.pdf"]
    },
    { 
      date: "2024-01-29", 
      topic: "미분 공식 활용",
      startTime: "17:00",
      endTime: "19:00", 
      homework: "완료", 
      note: "기본 개념 이해 양호",
      materials: ["강의자료.pdf"]
    },
    { 
      date: "2024-01-25", 
      topic: "미분 기본 공식",
      startTime: "17:00",
      endTime: "19:00", 
      homework: "미완료", 
      note: "추가 연습 문제 준비",
      materials: ["개념정리.pdf", "숙제.pdf"]
    }
  ];
</script>

<!-- 학생 프로필 섹션 -->
<div class="grid grid-cols-3 gap-6 mb-8">
  <div class="col-span-2 bg-white p-6 rounded-lg shadow-sm">
    <div class="flex items-start justify-between">
      <div class="flex items-center space-x-4">
        <div class="w-20 h-20 rounded-full bg-blue-100 flex items-center justify-center">
          <Users class="h-10 w-10 text-blue-600" />
        </div>
        <div>
          <h2 class="text-2xl font-bold text-gray-900">{student.name}</h2>
          <p class="text-gray-500">{student.grade}</p>
          <div class="mt-2 flex space-x-4">
            <div class="flex items-center text-gray-600">
              <Phone class="h-4 w-4 mr-2" />
              <span>{student.phone}</span>
            </div>
            <div class="flex items-center text-gray-600">
              <Mail class="h-4 w-4 mr-2" />
              <span>{student.email}</span>
            </div>
          </div>
        </div>
      </div>
      <div class="text-right">
        <div class="text-sm text-gray-500">수업 시작일</div>
        <div class="font-medium">{student.startDate}</div>
      </div>
    </div>
  </div>

  <!-- 수업 요약 정보 -->
  <div class="bg-white p-6 rounded-lg shadow-sm">
    <h3 class="text-lg font-medium mb-4">수업 요약</h3>
    <div class="space-y-4">
      <div class="flex justify-between items-center">
        <span class="text-gray-600">과목</span>
        <span class="font-medium">{student.subject}</span>
      </div>
      <div class="flex justify-between items-center">
        <span class="text-gray-600">수업 횟수</span>
        <span class="font-medium">{student.frequency}</span>
      </div>
      <div class="flex justify-between items-center">
        <span class="text-gray-600">수업 시간</span>
        <span class="font-medium">{student.schedule}</span>
      </div>
      <div class="flex justify-between items-center">
        <span class="text-gray-600">수업료</span>
        <span class="font-medium">{student.fee}</span>
      </div>
    </div>
  </div>
</div>

<!-- 진도 현황 섹션 -->
<div class="bg-white p-6 rounded-lg shadow-sm mb-8">
  <h3 class="text-lg font-medium mb-4">최근 진도</h3>
  <div class="space-y-3">
    {#each recentProgress as progress}
      <div class="flex items-center">
        <BookOpenCheck class="h-4 w-4 {progress.completed ? 'text-green-600' : 'text-gray-400'} mr-2" />
        <span class="text-sm">{progress.topic}</span>
      </div>
    {/each}
  </div>
</div>

<!-- 수업 이력 섹션 -->
<div class="bg-white rounded-lg shadow-sm">
  <div class="p-6">
    <h3 class="text-lg font-medium mb-4">수업 이력</h3>
    <div class="space-y-4">
      {#each classHistory as lesson}
        <div class="p-4 bg-gray-50 rounded-lg">
          <div class="flex items-center justify-between mb-3">
            <div class="flex items-center space-x-3">
              <span class="font-medium">{lesson.date}</span>
              <span class="text-gray-500">{lesson.startTime} - {lesson.endTime}</span>
            </div>
            <div class="flex items-center space-x-2">
              {#each lesson.materials as material}
                <span class="text-sm px-2 py-1 bg-blue-50 text-blue-600 rounded">
                  {material}
                </span>
              {/each}
            </div>
          </div>
          <div class="grid grid-cols-2 gap-4">
            <div>
              <div class="text-sm text-gray-500">진도</div>
              <div class="font-medium">{lesson.topic}</div>
            </div>
            <div>
              <div class="text-sm text-gray-500">숙제</div>
              <div class="font-medium {lesson.homework === '완료' ? 'text-green-600' : 'text-red-600'}">
                {lesson.homework}
              </div>
            </div>
          </div>
          <div class="mt-3">
            <div class="text-sm text-gray-500">수업 노트</div>
            <div class="flex items-center justify-between">
              <span class="text-sm">{lesson.note}</span>
              <button class="text-blue-600 hover:text-blue-700">
                <MessageSquare class="h-4 w-4" />
              </button>
            </div>
          </div>
        </div>
      {/each}
    </div>
  </div>
</div>