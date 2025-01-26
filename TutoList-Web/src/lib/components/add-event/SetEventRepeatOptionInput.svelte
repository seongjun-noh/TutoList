<script>
	import Modal from "$components/Modal.svelte";
	import TextInput from "$components/TextInput.svelte";
	import * as RRuleModule from 'rrule';
	import { t } from "svelte-i18n";
	
	const { RRule } = RRuleModule;

	export let rruleStr; 

	let repeatCycle = 1;
	let repeatCycleUnit = RRule.DAILY; // 초기값 RRule 열거형 사용
	let repeatEndCondition = null; // 종료 조건 열거형 값
	let selectedDays = [];
	let repeatEndDate = null;
	let repeatEndCount = 1;

	let days = [
		{ value: RRule.SU, label: "일" },
		{ value: RRule.MO, label: "월" },
		{ value: RRule.TU, label: "화" },
		{ value: RRule.WE, label: "수" },
		{ value: RRule.TH, label: "목" },
		{ value: RRule.FR, label: "금" },
		{ value: RRule.SA, label: "토" },
	];

	// RRule 종료 조건을 위한 옵션
	const endConditions = [
		{ value: null, label: "없음" },
		{ value: "until", label: "날짜" },
		{ value: "count", label: "횟수" },
	];

	$: {
		// RRule 옵션 구성
		const options = {
			freq: repeatCycleUnit,
			interval: repeatCycle,
		};

		// 반복 주기에 따른 추가 설정
		if (repeatCycleUnit === RRule.WEEKLY) {
			options.byweekday = selectedDays.map(day => day.value);
		}

		// 반복 종료 조건 추가
		if (repeatEndCondition === "until") {
			options.until = new Date(repeatEndDate);
		} else if (repeatEndCondition === "count") {
			options.count = repeatEndCount;
		}

		// RRule 생성
		const rule = new RRule(options);

		rruleStr = rule.toString();
	}
</script>

<div class="flex flex-col gap-4 w-full">
	<!-- 반복 주기 -->
	<div class="flex gap-2">
		<TextInput type="number" name="repeatCycle" bind:value={repeatCycle} placeholder="반복 주기" />
		<select bind:value={repeatCycleUnit}>
			<option value={RRule.DAILY}>일</option>
			<option value={RRule.WEEKLY}>주</option>
			<option value={RRule.MONTHLY}>월</option>
			<option value={RRule.YEARLY}>년</option>
		</select>
	</div>

	<!-- 반복 요일 -->
	{#if repeatCycleUnit === RRule.WEEKLY}
		<div class="flex flex-wrap gap-2">
			{#each days as day}
				<label>
					<input type="checkbox" bind:group={selectedDays} value={day} />
					{day.label}
				</label>
			{/each}
		</div>
	{/if}

	<!-- 반복 종료 -->
	<div class="flex gap-2">
		<select bind:value={repeatEndCondition} class="select select-bordered">
			{#each endConditions as condition}
				<option value={condition.value}>{condition.label}</option>
			{/each}
		</select>
		{#if repeatEndCondition === "until"}
			<TextInput type="date" name="repeatEndDate" bind:value={repeatEndDate} />
		{:else if repeatEndCondition === "count"}
			<TextInput type="number" name="repeatEndCount" bind:value={repeatEndCount}/>
		{/if}
	</div>
</div>