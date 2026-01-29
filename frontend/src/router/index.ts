import { createRouter, createWebHistory } from 'vue-router'
import WelcomeView from '../views/home/WelcomeView.vue'
import Servicos from '../views/servicos/Servicos.vue'
import BarbeirosView from '../views/barbeiros/BarbeirosView.vue'
import AdminLayout from '../views/admin/AdminLayout.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: WelcomeView
    },
    {
      path: "/servicos",
      name: 'servicos',
      component: Servicos
    },
    {
      path: "/barbeiros",
      name: 'barbeiros',
      component: BarbeirosView
    },
    {
      path: '/admin',
      component: AdminLayout,
      children: [
        {
          path: '',
          name: 'admin-usuarios',
          component: () => import('../views/admin/UsuariosViewAdmin.vue')
        },
        {
          path: 'servicos',
          name: 'admin-servicos',
          component: () => import('../views/admin/ServicosViewAdmin.vue')
        },
        {
          path: 'barbeiros',
          name: 'admin-barbeiros',
          component: () => import('../views/admin/BarbeirosViewAdmin.vue')
        }
      ]
    }
  ]
})

export default router