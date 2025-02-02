import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vite';
import fs from 'fs';
import path from 'path';

export default defineConfig({
	plugins: [sveltekit()],
	resolve: {
    alias: {
      $components: '/src/lib/components',
			$utils: '/src/lib/utils',
			$images: '/src/lib/images'
    }
  },
	optimizeDeps: {
		include: ["rrule"],
	},
	server:{
		port:3000,
		strictPort:false,
		https: {
			key: fs.readFileSync(path.resolve('./.cert/key.pem')),
			cert: fs.readFileSync(path.resolve('./.cert/cert.pem'))
		},
		proxy: {
      '/api': {
        target: 'https://localhost:8080',
        changeOrigin: true,
				secure: false,
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
	},
	preview:{
		port:4173,
		strictPort:false,
	}
});
