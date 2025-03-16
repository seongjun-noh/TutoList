<template>
  <div class="student-filter">
    <div class="search-box">
      <input
        type="text"
        :value="search"
        @input="$emit('update:search', $event.target.value)"
        placeholder="이름 또는 연락처로 검색"
        class="search-input"
        aria-label="학생 검색"
      />
    </div>

    <div class="filter-options">
      <select
        :value="grade"
        @change="$emit('update:grade', $event.target.value)"
        class="filter-select"
        aria-label="학년 필터"
      >
        <option value="">전체 학년</option>
        <option value="중1">중1</option>
        <option value="중2">중2</option>
        <option value="중3">중3</option>
        <option value="고1">고1</option>
        <option value="고2">고2</option>
        <option value="고3">고3</option>
      </select>

      <select
        :value="subject"
        @change="$emit('update:subject', $event.target.value)"
        class="filter-select"
        aria-label="과목 필터"
      >
        <option value="">전체 과목</option>
        <option value="수학">수학</option>
        <option value="영어">영어</option>
        <option value="국어">국어</option>
        <option value="과학">과학</option>
        <option value="사회">사회</option>
      </select>
    </div>
  </div>
</template>

<script setup>
defineProps({
  search: {
    type: String,
    required: true
  },
  grade: {
    type: String,
    required: true
  },
  subject: {
    type: String,
    required: true
  }
})

defineEmits(['update:search', 'update:grade', 'update:subject', 'filter'])
</script>

<style>
.student-filter {
  display: flex;
  gap: 16px;
  margin-bottom: 24px;
  flex-wrap: wrap;
}

.search-box {
  flex: 1;
  min-width: 200px;
}

.search-input {
  width: 100%;
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 14px;
}

.search-input:focus {
  outline: 2px solid var(--primary-color-light);
  border-color: var(--primary-color);
}

.filter-options {
  display: flex;
  gap: 8px;
}

.filter-select {
  padding: 8px 24px 8px 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 14px;
  background-color: white;
  cursor: pointer;
}

.filter-select:focus {
  outline: 2px solid var(--primary-color-light);
  border-color: var(--primary-color);
}
</style> 