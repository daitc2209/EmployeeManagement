import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import RegisterEmpView from '../views/RegisterEmpView.vue'
import EditView from '../views/EditView.vue'
import CreateUserView from '../views/CreateUserView.vue'
import LoginView from '../views/LoginView.vue'
import LoginEmpView from '../views/LoginEmpView.vue'
import HomeViewEmp from '../views/HomeViewEmp.vue'
import HomeViewAdmin from '../views/HomeViewAdmin.vue'
import EditViewAdmin from '../views/EditViewAdmin.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'LoginView',
      component: LoginView
    },
    {
      path: '/loginEmp',
      name: 'LoginEmpView',
      component: LoginEmpView
    },
    {
      path: '/register',
      name: 'RegisterEmpView',
      component: RegisterEmpView
    },
    {
      path: '/edit/:id',
      name: 'EditView',
      component: EditView
    },
    {
      path: '/create',
      name: 'CreateUserView',
      component: CreateUserView
    },
    {
      path: '/home',
      name: 'home',
      component: HomeView
    },
    {
      path: '/homeEmp',
      name: 'homeEmp',
      component: HomeViewEmp
    },
    {
      path: '/homeAdmin',
      name: 'homeAdmin',
      component: HomeViewAdmin
    },
    {
      path: '/editAdmin/:id',
      name: 'EditViewAdmin',
      component: EditViewAdmin
    },
  ]
})

export default router
