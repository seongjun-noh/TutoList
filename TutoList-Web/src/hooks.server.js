import { locale } from 'svelte-i18n';

/** @type {import('@sveltejs/kit').Handle} */
export async function handle({ event, resolve }) {
  const langHeader = event.request.headers.get('accept-language');
  const lang = langHeader ? langHeader.split(',')[0] : null;

  if (lang) {
    locale.set(lang);
  }

  return resolve(event);
}
