import { init, register, getLocaleFromNavigator } from 'svelte-i18n';

const defaultLocale = 'ko';

register('ko', () => import('./locales/ko.json'));
// register('jp', () => import('./locales/jp'));

init({
	fallbackLocale: defaultLocale,
	initialLocale: getLocaleFromNavigator()
});