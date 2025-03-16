<script>
	import { onMount } from 'svelte';
	import { Calendar, Search, Plus, Clock } from 'lucide-svelte';
	import { Calendar as FullCalendar } from '@fullcalendar/core';
	import dayGridPlugin from '@fullcalendar/daygrid';
	import timeGridPlugin from '@fullcalendar/timegrid';
	import listPlugin from '@fullcalendar/list';
	import interactionPlugin from '@fullcalendar/interaction';
	import rrulePlugin from '@fullcalendar/rrule';
	import AddEventModal from '$lib/components/add-event/AddEventModal.svelte';
	import apiClient from '$lib/utils/apiClient';
	import LoadingDots from '$lib/components/loading/LoadingDots.svelte';

	let openAddEventModal = false;
	let selectedDate;
	let calendar;

	onMount(async () => {
		await initializeCalendar();
	});

	async function initializeCalendar() {
		let calendarEl = document.getElementById('calendar');
		if (!calendarEl) return;

		calendar = new FullCalendar(calendarEl, {
			plugins: [dayGridPlugin, timeGridPlugin, listPlugin, interactionPlugin, rrulePlugin],
			initialView: 'dayGridMonth',
			height: 'auto',
			headerToolbar: {
				left: 'prev,next today',
				center: 'title',
				right: 'dayGridMonth,timeGridWeek,listWeek'
			},
			buttonText: {
				today: 'Today',
				month: 'Month',
				week: 'Week',
				list: 'List'
			},
			dateClick: (info) => {
				openAddEventModal = true;
				selectedDate = info.dateStr;
			},
			datesSet: async (info) => {
				await fetchCalendarEvents(info.startStr, info.endStr);
			}
		});
		
		calendar.render();

		const style = document.createElement('style');
		style.textContent = `
			.fc {
				--fc-border-color: #e2e8f0;
				--fc-button-bg-color: #2563eb;
				--fc-button-border-color: #2563eb;
				--fc-button-hover-bg-color: #1d4ed8;
				--fc-button-hover-border-color: #1d4ed8;
				--fc-button-active-bg-color: #1e40af;
				--fc-button-active-border-color: #1e40af;
				--fc-event-bg-color: #2563eb;
				--fc-event-border-color: #2563eb;
				--fc-today-bg-color: #eff6ff;
			}
			.fc .fc-button {
				padding: 0.5rem 1rem;
				font-weight: 500;
				border-radius: 0.5rem;
				text-transform: none;
			}
			.fc .fc-toolbar-title {
				font-size: 1.25rem;
				font-weight: 600;
				color: #1e293b;
			}
		`;
		document.head.appendChild(style);
	}

	async function fetchCalendarEvents(startDateStr, endDateStr) {
		const params = { startDate: startDateStr, endDate: endDateStr };
		try {
			const response = await apiClient.get('/calendar/event', {
				params,
				disableBlockUI: true,
				headers: { 'Content-Type': 'application/json' }
			});
			calendar.removeAllEvents();
			response.data.data.events.forEach(event => calendar.addEvent(event));
		} catch (error) {
			console.error('Error fetching calendar events:', error);
		}
	}

	const onSaveCalendarEvent = (calendarEvent) => {
		calendar.addEvent(calendarEvent);
	}
</script>

<div class="flex flex-col h-full">
	<header class="h-16 bg-white border-b border-gray-200 flex items-center justify-between px-8">
		<h1 class="text-2xl font-bold text-gray-900">일정관리</h1>
		<div class="flex items-center space-x-4">
			<div class="relative">
				<Search class="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-gray-400" />
				<input
					type="text"
					placeholder="일정 검색..."
					class="pl-10 pr-4 py-2 border border-gray-200 rounded-lg w-64 focus:outline-none focus:ring-2 focus:ring-blue-500"
				/>
			</div>
			<button 
				class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 flex items-center"
				on:click={() => openAddEventModal = true}
			>
				<Plus class="h-4 w-4 mr-2" />
				새 일정 등록
			</button>
		</div>
	</header>

	<main class="flex-1 overflow-auto p-8">
		<div class="grid grid-cols-4 gap-6 mb-8">
			<div class="bg-white p-6 rounded-lg shadow-sm">
				<div class="flex items-center justify-between mb-4">
					<h3 class="text-sm font-medium text-gray-600">이번 달 일정</h3>
					<Clock class="h-4 w-4 text-blue-600" />
				</div>
				<div class="text-2xl font-bold">24건</div>
				<p class="text-xs text-gray-500">활성 일정 기준</p>
			</div>
		</div>

		<div class="bg-white rounded-lg shadow-sm">
			<div class="p-6">
				<div id="calendar"></div>
			</div>
		</div>
	</main>

	{#if openAddEventModal}
		<AddEventModal 
			selectedDate={selectedDate} 
			bind:isOpen={openAddEventModal} 
			onSave={onSaveCalendarEvent}
		/>
	{/if}
</div>