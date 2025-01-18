<svelte:head>
	<title>login</title>
	<meta name="description" content="TutoList Loging Page" />
</svelte:head>

<script context="module">
  export const layout = null;

  const onSubmitLoginForm = async function (event) {
    event.preventDefault();

    const formData = new FormData(event.target);    
    const data = Object.fromEntries(formData.entries());
    
    try {
      const res = await fetch('/api/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
      });

      if (!res.ok) {
        throw new Error('Failed to submit data');
      }

      // Handle success, reset form, show feedback, etc.
      console.log('Post created successfully');
    } catch (error) {
      console.error('Error creating post:', error);
    }
  }
</script>

<div class="text-column">
  <h1>Login</h1>

  <form action="/login" method="post" on:submit={onSubmitLoginForm}>
    <label for="username">Username</label>
    <input type="text" id="username" name="username" required />

    <label for="password">Password</label>
    <input type="password" id="password" name="password" required />

    <button type="submit">Login</button>
  </form>
</div>