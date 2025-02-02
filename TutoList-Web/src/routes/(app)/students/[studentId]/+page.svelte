<script>
  import { onMount } from "svelte";
  import { page } from '$app/stores';
	import apiClient from "$lib/utils/apiClient";

  const studentId = $page.params.studentId;
  let studentInfo;
  let studentEventInfoList;

  onMount(async () => {
    await fetchStudentInfo();
  });

  const fetchStudentInfo = async () => {
    const url = `/students/${studentId}`

    try {
      const response = await apiClient.get(url, {
        headers: {
          'Content-Type': 'application/json',
        },
      });

      const data = response.data.data;

      studentInfo = data;
    } catch (error) {
      console.error('Add Event error:', error);
      console.log("존재하지 않는 학생")
      throw error;
    }
  }
  
  const fetchStudentScheduleInfo = async () => {
    const url = `/students/${studentId}/events`

    try {
      const response = await apiClient.get(url, {
        headers: {
          'Content-Type': 'application/json',
        },
      });

      const data = response.data.data;

      studentEventInfoList = data;
    } catch (error) {
      console.error('Add Event error:', error);
      throw error;
    }
  }
</script>

<div>
  {#if studentInfo}
    <h1>{studentInfo.name}</h1>


  {:else}
    <p>Loading...</p>
  {/if}
</div>
