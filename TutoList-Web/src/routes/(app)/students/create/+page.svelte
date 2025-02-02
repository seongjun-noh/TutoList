<script>
	import ErrorMessage from "$lib/components/ErrorMessage.svelte";
	import TextInput from "$lib/components/TextInput.svelte";
	import apiClient from "$lib/utils/apiClient";

  let studentName;

  const onSubmitRegistForm = () => {
    const requestBody = {
      name: studentName
    }

    try {
			const response = apiClient.post('/students/create', requestBody, {
				disableBlockUI: true,
				headers: {
					'Content-Type': 'application/json',
				},
			});

      alert("등록이 안료되었습니다.");
      location.href = "/students"
		} catch (error) {
			console.error('Add Event error:', error);
      throw error;
		}
  }
</script>

<div class="flex justify-center items-center h-full flex-col gap-1">
  <div class="card bg-base-100 w-96 shadow">
    <div class="card-body">
      <div class="card-header">
        <h1 class="card-title">학생등록</h1>
      </div>
      <form class="flex flex-col gap-5" on:submit|preventDefault={onSubmitRegistForm}>
        <div class="flex flex-col gap-4">
          <input type="text" name="role" value="TEACHER" hidden/>
          <TextInput name="studentName" placeholder="이름" bind:value={studentName} requied/>
          <ErrorMessage message=""/>
        </div>

        <button type="submit" class="btn btn-primary" >등록하기</button>
      </form>
    </div>
  </div>
</div>