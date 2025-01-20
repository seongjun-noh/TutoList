import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vite';

export default defineConfig({
	plugins: [sveltekit()],
	resolve: {
    alias: {
      $components: '/src/lib/components',
			$utils: '/src/lib/utils',
			$images: '/src/lib/images'
    }
  },
	server:{
		port:3000,
		strictPort:false,
		proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
	},
	preview:{
		port:4173,
		strictPort:false,
	}
});
