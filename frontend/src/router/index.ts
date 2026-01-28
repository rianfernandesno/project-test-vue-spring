import { createRouter, createWebHistory } from 'vue-router'
import WelcomeView from '../views/home/WelcomeView.vue'
import Servicos from '../views/servicos/Servicos.vue'
import BarbeirosView from '../views/barbeiros/BarbeirosView.vue'

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
    }
  ]
})

export default router