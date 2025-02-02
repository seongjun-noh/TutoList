<script>
	import Modal from "$components/Modal.svelte";
	import TextInput from "$components/TextInput.svelte";
	import { t } from "svelte-i18n";
	import SetEventRepeatOptionInput from "./SetEventRepeatOptionInput.svelte";
	import { CalendarEvent as CalendarEventModel } from "$lib/models/CalEvent";
	import apiClient from "$lib/utils/apiClient";
	import { all } from "axios";
	import ErrorMessage from "../ErrorMessage.svelte";
	import CheckboxInput from "../CheckboxInput.svelte";

	export let selectedDate;
	export let isOpen = false;
	export let onClose;
	export let onSave;

	let title = '';
	let startDate = selectedDate;
	let startTime = '12:00';
	let endDate = selectedDate;
	let endTime = '13:00';
	let allDay = true;
	let repeat = false;
	let rrule = null;

	let isInvalidDateRange = false;

	$: {
		const start = toDateObj(allDay, startDate, startTime);
		const end = toDateObj(allDay, endDate, endTime);
		isInvalidDateRange = !validDateRange(allDay, start, end);

		if (!repeat) {
			rrule = null;
		}
	}

	const handleOnSubmit = (event) => {
		const start = toDateObj(allDay, startDate, startTime);
		const end = toDateObj(allDay, endDate, endTime);

		if (!validDateRange(allDay, start, end)) {
			return false;
		}

		const calendarEvent = new CalendarEventModel({
			title,
			start,
			end,
			allDay,
			repeat,
			rrule
		});

		if (onSave) {
			onSave(calendarEvent);
		}

		try {
			const response = apiClient.post('/calendar/event/create', calendarEvent, {
				disableBlockUI: true,
				withCredentials: true,
				headers: {
					'Content-Type': 'application/json',
				},
			});

			isOpen = false;
		} catch (error) {
			console.error('Add Event error:', error);
      throw error;
		}
	};

	const validDateRange = (allDay, start, end) => {
		if (start > end) {
			return false;
		}

		return true;
	}

	const toDateObj = (allDay, dateStr, timeStr) => {
		const dateObj = allDay
			? new Date(dateStr)
			: new Date(`${dateStr}T${timeStr}`);

		return dateObj;
	}

	const onClickCloseButton = () => {
		onClose;

		isOpen = false;
	}
</script>

<Modal bind:isOpen={isOpen} title="{$t("event")} {$t("add")}">
	<form on:submit|preventDefault={handleOnSubmit}>
		<div class="flex flex-col gap-4 py-4">
			<TextInput type="text" name="title" placeholder="{$t("title")}" bind:value={title} required />

			<div>
				<div class="flex gap-2">
					<div class="w-full flex flex-col gap-1">
						<TextInput type="date" name="startDate" placeholder="{$t("start")} {$t("date")}" bind:value={startDate} required />
						{#if !allDay}
							<TextInput type="time" name="startTime" placeholder="{$t("start")} {$t("time")}" bind:value={startTime} required />
						{/if}
					</div>
					~
					<div class="w-full flex flex-col gap-1">
						<TextInput type="date" name="endDate" placeholder="{$t("end")} {$t("date")}" bind:value={endDate} addClass={isInvalidDateRange ? "input-error" : ''} required />
						{#if !allDay}
							<TextInput type="time" name="endTime" placeholder="{$t("end")} {$t("time")}" bind:value={endTime} addClass={isInvalidDateRange ? "input-error" : ''} required />
						{/if}
					</div>
				</div>
				{#if isInvalidDateRange}
					<ErrorMessage message='종료시간은 시작시간보다 빠를 수 없습니다.'/>
				{/if}
			</div>

			<div class="flex gap-4 flex-col">
				<div class="flex">
					<CheckboxInput labelText={$t("allDay")} bind:checked={allDay}/>
				</div>
				<div class="flex">
					<CheckboxInput labelText={$t("repeat")} bind:checked={repeat}/>
					{#if repeat}
						<SetEventRepeatOptionInput bind:rruleStr={rrule}/>
					{/if}
				</div>
			</div>
		</div>

		<div class="flex justify-end gap-2">
			<button class="btn" type="button" on:click={onClickCloseButton}>{$t("cancel")}</button>
			<button class="btn" type="submit">{$t("save")}</button>
		</div>
	</form>
</Modal>
