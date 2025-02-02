<script>
	import apiClient from "$lib/utils/apiClient";
	import { onMount } from "svelte";

  let studentList;

  onMount(() => {
    fetchStudentList();
  });

  const fetchStudentList = async () => {
    try {
      const response = await apiClient.get('/students', {
        headers: {
          'Content-Type': 'application/json',
        },
      });

      const data = response.data.data;

      studentList = data.students;
    } catch (error) {
      console.error('Add Event error:', error);
      throw error;
    }
  }
</script>

<div class="overflow-x-auto">
  <a class="btn" href="/students/create">
    등록
  </a>

  <table class="table">
    <!-- head -->
    <thead>
      <tr>
        <th>
          <label>
            <input type="checkbox" class="checkbox" />
          </label>
        </th>
        <th>Name</th>
        <th>Job</th>
        <th>Favorite Color</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      {#each studentList as student}
        <tr>
          <th>
            <label>
              <input type="checkbox" class="checkbox" />
            </label>
          </th>
          <td>
            <div class="flex items-center gap-3">
              <div>
                <div class="font-bold">{student.name}</div>
                <div class="text-sm opacity-50">United States</div>
              </div>
            </div>
          </td>
          <td>
            Zemlak, Daniel and Leannon
            <br />
            <span class="badge badge-ghost badge-sm">Desktop Support Technician</span>
          </td>
          <td>Purple</td>
          <th>
            <a class="btn btn-ghost btn-xs" href="/students/{student.id}">details</a>
          </th>
        </tr>
      {/each}
    </tbody>
    <!-- foot -->
    <tfoot>
      <tr>
        <th></th>
        <th>Name</th>
        <th>Job</th>
        <th>Favorite Color</th>
        <th></th>
      </tr>
    </tfoot>
  </table>
</div>