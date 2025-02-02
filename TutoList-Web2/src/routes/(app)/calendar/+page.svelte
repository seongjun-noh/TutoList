<script>
	import { onMount } from 'svelte';
	
	import { Calendar } from '@fullcalendar/core';
	import dayGridPlugin from '@fullcalendar/daygrid';
	import timeGridPlugin from '@fullcalendar/timegrid';
	import listPlugin from '@fullcalendar/list';
	import interactionPlugin from '@fullcalendar/interaction';
	import rrulePlugin from '@fullcalendar/rrule'
	import Modal from '$lib/components/Modal.svelte';
	import TextInput from '$lib/components/TextInput.svelte';
	import AddEventModal from '$lib/components/add-event/AddEventModal.svelte';
	import apiClient from '$lib/utils/apiClient';

	let openAddEventModal = false;
	let selectedDate;

	let calendar;

	onMount(() => {
		let calendarEl = document.getElementById('calendar');
		calendar = new Calendar(calendarEl, {
			plugins: [ dayGridPlugin, timeGridPlugin, listPlugin, interactionPlugin, rrulePlugin ],
			initialView: 'dayGridMonth',
			headerToolbar: {
				left: 'prev,next today',
				center: 'title',
				right: 'dayGridMonth,timeGridWeek,listWeek'
			},
			dateClick: function(info) {
				openAddEventModal = true;
				selectedDate = info.dateStr;
			},
			datesSet: async function (info) {
				const startDateStr = info.startStr;
				const endDateStr = info.endStr;

				const events = await fetchCalendarEvents(startDateStr, endDateStr);

				calendar.removeAllEvents();
				events.forEach(event => {
					calendar.addEvent(event);
				});
			}
		});
		calendar.render();
	});

	const onChangeReapeatCheckBox = (event) => {
		openRepeatModal = event.target.checked;
	}

	const fetchCalendarEvents = async (startDateStr, endDateStr) => {
		const params = {
			startDate: startDateStr,
			endDate: endDateStr,
		};

		try {
			const response = await apiClient.get('/calendar/event', {
				params: params,
				disableBlockUI: true,
				headers: {
					'Content-Type': 'application/json',
				},
			});

			return response.data.data.events;
		} catch (error) {
			console.error('Error fetching calendar events:', error);
			throw error;
		}
	};

	const onSaveCalendarEvent = (calendarEvent) => {
		calendar.addEvent(calendarEvent);
	}
</script>

<div>
	<div id="calendar">
	</div>
	
	{#if openAddEventModal}
		<AddEventModal selectedDate={selectedDate} bind:isOpen={openAddEventModal} onSave={(calendarEvent) => onSaveCalendarEvent(calendarEvent)}/>
	{/if}
</div>