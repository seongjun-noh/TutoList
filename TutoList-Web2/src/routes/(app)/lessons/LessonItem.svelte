<script>
  import { translateRRule } from '$lib/utils/rruleUtil';
	import { formatTime } from '$lib/utils/dateTimeUtil';
  import { Users } from 'lucide-svelte';

  export let lesson;
</script>

<a 
  href="/lessons/{lesson.id}" 
  class="block p-4 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors"
>
  <div class="flex items-center justify-between">
    <div class="flex items-center gap-4">
      <div class="w-12 h-12 rounded-full bg-blue-100 flex items-center justify-center flex-shrink-0">
        <Users class="h-6 w-6 text-blue-600" />
      </div>
      <div class="flex flex-col min-w-0">
        <h3 class="font-medium text-gray-900 truncate">{lesson.name}</h3>
        <p class="text-sm text-gray-500 mt-1 truncate max-w-xs">{lesson.memo || ''}</p>
      </div>
    </div>
    
    <div class="flex flex-col items-end gap-2">
      <div class="flex items-center gap-2">
        <span class="text-gray-600">{lesson.subject}</span>
      </div>
      <div class="flex items-center gap-2">
        <time class="text-sm text-gray-600">{translateRRule(lesson.rrule)}</time>
        <span class="text-gray-300">•</span>
        <time class="text-sm text-gray-600">{formatTime(lesson.startTime, "HH:mm:SS", "A H시 m분")} ({lesson.duration}분)</time>
      </div>
    </div>
  </div>
</a>