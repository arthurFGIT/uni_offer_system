import AngebotViewVue from '@/views/AngebotView.vue'
import { useLogin } from '@/services/useLogin'
// const { logindata } = useLogin()
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: AngebotViewVue
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue')
    },
    {
      path: '/gebot/:angebotidstr',
      name: 'angebotidstr',
      component: () => import('../views/GebotView.vue'),
      props: true
    },
    {
      path: '/login',
      name: 'Login',

      component: () => import('../views/LoginView.vue')
    }
  ]
})
router.beforeEach( async (to, from) => {
  if (!useLogin().logindata.loggedin && to.name !== 'Login') {
  return { name: 'Login' }
  }
})

export default router
