import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },

  // proxy: {
  //   '^/': {
  //     target: 'http://localhost:8080/',
  //     changeOrigin: true,
  //     ws: true,
  //     headers: {
  //       'Access-Control-Allow-Origin': '*',
  //       'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept',
  //       'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS'
  //     }
  //   }
  // }
  // devServer: {
  //   proxy:'http://localhost:8080/'
  //   //  {
  //   //     '^/': {
  //   //     target: 'http://localhost:8080/',
  //   //     ws: true,
  //   //     changeOrigin: true
  //   //     },
  //   // }
  // }
})

