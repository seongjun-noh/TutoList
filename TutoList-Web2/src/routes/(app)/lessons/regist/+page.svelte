<script>
  import { enhance } from '$app/forms';
	import { goto } from '$app/navigation';
	import apiClient from '$lib/utils/apiClient';
  import { ArrowLeft, Save, X } from 'lucide-svelte';
  import * as RRuleModule from 'rrule';
  const { RRule } = RRuleModule;

  // formData.lessonRule.startDate: 기본값은 오늘 날짜 (yyyy-mm-dd)
  let formData = {
    name: '',
    subjects: '',
    parentName: '',
    parentPhone: '',
    fee: {
      amount: '',
      type: 'PER_LESSON'
    },
    lessonRule: {
      freq: 'WEEKLY',
      interval: 1,
      byweekday: [], // 예: ["MO", "WE", "FR"]
      startDate: new Date().toISOString().slice(0,10), // 수업 시작일 (yyyy-mm-dd)
      startTime: "12:00",
      duration: 60 // 분 단위, 종료시간은 dtstart + duration
    },
    memo: ''
  };

  let subjectInput = '';

  const weekdays = [
    { value: 'MO', label: '월' },
    { value: 'TU', label: '화' },
    { value: 'WE', label: '수' },
    { value: 'TH', label: '목' },
    { value: 'FR', label: '금' },
    { value: 'SA', label: '토' },
    { value: 'SU', label: '일' }
  ];

  // rrule에서 사용하기 위한 요일 매핑 (최신 버전에서는 Weekday 클래스를 사용)
  const weekdaysMap = {
    MO: RRule.MO,  // 문서에 따라 요일 번호를 확인하세요.
    TU: RRule.TU,
    WE: RRule.WE,
    TH: RRule.TH,
    FR: RRule.FR,
    SA: RRule.SA,
    SU: RRule.SU
  };

  function handleWeekdayToggle(day) {
    formData.lessonRule.byweekday = formData.lessonRule.byweekday.includes(day)
      ? formData.lessonRule.byweekday.filter(d => d !== day)
      : [...formData.lessonRule.byweekday, day];
  }

  // dtstart를 계산하는 함수  
  // 1) formData.lessonRule.startDate (yyyy-mm-dd)와 수업 시작 시간(byhour/byminute)을 결합하여 기본 dtstart를 생성합니다.
  // 2) 선택된 요일(byweekday)이 있다면, dtstart의 요일이 포함되는지 확인하고, 그렇지 않으면 가장 가까운 선택 요일로 조정합니다.
  function getDtstart() {
    // startDate가 지정되어 있다면, 그 날짜를 사용 (입력 필드의 값은 yyyy-mm-dd 형식)
    let dtstart = formData.lessonRule.startDate
      ? new Date(formData.lessonRule.startDate)
      : new Date(`${dateStr}T${timeStr}`);

    if (formData.lessonRule.byweekday.length > 0) {
      // 요일 번호 매핑: (일요일:0, 월요일:1, ..., 토요일:6)
      const dayNumberMap = { SU: 0, MO: 1, TU: 2, WE: 3, TH: 4, FR: 5, SA: 6 };
      let allowedDays = formData.lessonRule.byweekday.map(day => dayNumberMap[day]);
      
      // 만약 dtstart의 요일이 allowedDays에 포함되지 않는다면, 다음 allowed 요일로 조정
      if (!allowedDays.includes(dtstart.getDay())) {
        const currentDay = dtstart.getDay();
        // 각 allowed day까지의 차이를 계산 (양수인 최소값)
        const diffs = allowedDays.map(d => (d - currentDay + 7) % 7).filter(diff => diff > 0);
        const diff = Math.min(...diffs);
        dtstart.setDate(dtstart.getDate() + diff);
      }
    }
    return dtstart;
  }

  // lessonRule 객체를 rrule 문자열로 변환하는 함수
  function convertLessonRuleToRRule() {
    const options = {
      freq: RRule.WEEKLY,
      interval: Number(formData.lessonRule.interval),
      byweekday: formData.lessonRule.byweekday.map(day => weekdaysMap[day])
    };

    const rule = new RRule(options);
    return rule.toString();
  }

  async function onSubmitRegistStudentForm() {
    const data = {
      name: formData.lessonName,
      subject: formData.subjects,
      startDateTime: toDateObj(formData.lessonRule.startDate, formData.lessonRule.startTime),
      duration: formData.lessonRule.duration,
      rrule: convertLessonRuleToRRule(),
      memo: formData.memo
    }

		try {
			const response = await apiClient.post('/lessons/regist', data, {
				disableBlockUI: true,
				headers: {
					'Content-Type': 'application/json',
				},
			});

      alert("수업을 등록하였습니다.");

			const lessonId = response.data.data.id;
      goto(`/lessons/${lessonId}`);
		} catch (error) {
			console.error('Error fetching calendar events:', error);
			throw error;
		}
  }

  const toDateObj = (dateStr, timeStr) => {
		const dateObj = new Date(`${dateStr}T${timeStr}`);

		return dateObj;
	}
</script>

<div class="max-w-4xl mx-auto p-6">
  <!-- 헤더 -->
  <div class="flex items-center justify-between mb-6">
    <div class="flex items-center gap-4">
      <button 
        on:click={() => history.back()} 
        class="hover:bg-gray-100 p-2 rounded-full"
      >
        <ArrowLeft size={24} />
      </button>
      <h1 class="text-2xl font-bold">수업 등록</h1>
    </div>
    <button
      type="submit"
      form="student-form"
      class="flex items-center gap-2 bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700"
    >
      <Save size={20} />
      저장하기
    </button>
  </div>

  <form 
    id="student-form"
    class="space-y-8"
    on:submit|preventDefault={onSubmitRegistStudentForm}
  >
    <!-- 수업 정보 -->
    <section class="bg-white p-6 rounded-lg shadow">
      <h2 class="text-lg font-semibold mb-4">수업 정보</h2>
      
      <div class="space-y-6">
        <!-- 수업 시작일 및 주기/요일 -->
        <div class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1" for="lessonName">
              수업명 *
            </label>
            <input
              id="lessonName"
              type="text"
              name="name"
              required
              bind:value={formData.lessonName}
              class="w-full px-3 py-2 border rounded-md focus:ring-1 focus:ring-blue-500"
              placeholder="수업명"
            />
          </div>
    
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1" for="subject">
              과목 *
            </label>
            <input
              id="subject"
              type="text"
              name="subject"
              required
              bind:value={formData.subjects}
              class="w-full px-3 py-2 border rounded-md focus:ring-1 focus:ring-blue-500"
              placeholder="과목"
              />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1" for="lesson-start-date">
              수업 시작일 *
            </label>
            <input
              id="lesson-start-date"
              type="date"
              name="lessonStartDate"
              bind:value={formData.lessonRule.startDate}
              class="w-full px-3 py-2 border rounded-md focus:ring-1 focus:ring-blue-500"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1" for="lesson-interval">
              수업 주기 *
            </label>
            <div class="grid grid-cols-2 gap-4">
              <select
                id="lesson-interval"
                name="lessonInterval"
                bind:value={formData.lessonRule.interval}
                class="w-full px-3 py-2 border rounded-md focus:ring-1 focus:ring-blue-500"
              >
                <option value={1}>매주</option>
                <option value={2}>격주</option>
                <option value={3}>3주마다</option>
                <option value={4}>4주마다</option>
              </select>
            </div>
          </div>

          <div>
            <div class="block text-sm font-medium text-gray-700 mb-1">
              수업 요일 *
            </div>
            <div class="flex flex-wrap gap-3">
              {#each weekdays as { value, label }}
                <button
                  type="button"
                  on:click={() => handleWeekdayToggle(value)}
                  class="px-3 py-1 rounded-md {formData.lessonRule.byweekday.includes(value) ? 'bg-blue-100 text-blue-700' : 'bg-gray-100 text-gray-700'}"
                >
                  {label}
                </button>
              {/each}
            </div>
          </div>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1" for="lesson-start-hour">
              수업 시작 시간 *
            </label>
            <input
                id="lesson-start-time"
                type="time"
                name="lessonStartTime"
                bind:value={formData.lessonRule.startTime}
                class="w-full px-3 py-2 border rounded-md focus:ring-1 focus:ring-blue-500"
                min="0"
                max="59"
                step="5"
              />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1" for="lesson-duration">
              수업 시간(분) *
            </label>
            <input
              id="lesson-duration"
              type="number"
              name="lessonDuration"
              bind:value={formData.lessonRule.duration}
              class="w-full px-3 py-2 border rounded-md focus:ring-1 focus:ring-blue-500"
              min="30"
              step="30"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1" for="memo">
              메모
            </label>
            <textarea
              id="memo"
              name="memo"
              bind:value={formData.memo}
              rows="3"
              class="w-full px-3 py-2 border rounded-md focus:ring-1 focus:ring-blue-500"
              placeholder="특이사항이나 참고사항을 입력하세요"
            ></textarea>
          </div>
        </div>
      </div>

      <!-- hidden fields -->
      <!-- lessonRule을 rrule 문자열로 전송 -->
      <input type="hidden" name="lessonRule" value={convertLessonRuleToRRule()} />
      <!-- fee는 그대로 JSON.stringify해서 전송 -->
      <input type="hidden" name="fee" value={JSON.stringify(formData.fee)} />
    </section>
  </form>
</div>
