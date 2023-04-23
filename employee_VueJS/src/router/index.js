import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import CreateView from '../views/CreateView.vue'
import EditView from '../views/EditView.vue'
import SignUpView from '../views/SignUpView.vue'
import LoginView from '../views/LoginView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'LoginView',
      component: LoginView
    },
    {
      path: '/create',
      name: 'CreateView',
      component: CreateView
    },
    {
      path: '/edit/:id',
      name: 'EditView',
      component: EditView
    },
    {
      path: '/register',
      name: 'SignUpView',
      component: SignUpView
    },
    {
      path: '/home',
      name: 'home',
      component: HomeView
    },
  ]
})

export default router
