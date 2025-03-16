<script>
	import { goto } from '$app/navigation';
  import { page } from '$app/stores';
  import { user } from '$lib/stores/userStore';
	import apiClient from '$lib/utils/apiClient.js';
  import {
    Calendar,
    Users,
    BookOpen,
    Settings,
    LayoutDashboard,
    LogOut
  } from 'lucide-svelte';

  export let data;
  
  const { userInfo } = data;

  const onclickLogoutButton = async () => {
    try {
			const response = await apiClient.post('/logout');

			goto('/login');
		} catch (error) {
			console.error('Error logout fail:', error);
			throw error;
		}
  };
</script>

<div class="flex h-screen bg-gray-50">
  <!-- Sidebar -->
  <div class="w-64 bg-white border-r border-gray-200 flex flex-col">
    <div class="h-16 flex items-center px-6 border-b border-gray-200">
      <BookOpen class="h-6 w-6 text-blue-600" />
      <span class="ml-3 text-xl font-bold text-gray-900">TutoList</span>
    </div>
    
    <div class="flex-1 flex flex-col p-4">
      <div class="flex items-center space-x-3 mb-6">
        <div class="w-10 h-10 rounded-full bg-gray-200"></div>
        <div>
          <div class="font-medium">{userInfo.name} 선생님</div>
          <!-- <div class="text-sm text-gray-500">수학 교사</div> -->
        </div>
      </div>
      
      <nav class="space-y-1 flex-1">
        {#each [
          { href: '/', icon: LayoutDashboard, text: '대시보드' },
          { href: '/lessons', icon: Users, text: '수업관리' },
          { href: '/calendar', icon: Calendar, text: '일정관리' },
        ] as item}
          <a  
            href={item.href}
            class="flex items-center space-x-3 px-4 py-3 rounded-lg cursor-pointer
              {$page.url.pathname === item.href ? 
                'bg-blue-50 text-blue-600' : 
                'text-gray-700 hover:bg-gray-50'}"
          >
            <svelte:component this={item.icon} class="h-5 w-5" />
            <span class="font-medium">{item.text}</span>
          </a>
        {/each}
      </nav>

      <!-- Logout Button -->
      <button
        on:click={onclickLogoutButton}
        class="flex items-center space-x-2 px-4 py-3 rounded-lg cursor-pointer text-gray-600 hover:text-gray-600 mt-auto text-sm transition-colors"
      >
        <LogOut class="h-4 w-4" />
        <span>로그아웃</span>
      </button>
    </div>
  </div>

  <!-- Main Content -->
  <div class="flex-1 flex flex-col overflow-hidden">
    <slot />
  </div>
</div>