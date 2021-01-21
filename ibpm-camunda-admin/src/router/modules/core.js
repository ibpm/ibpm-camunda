/** When your routing process is too long, you can split it into small modules**/

import Layout from '@/layout'

const coreRouter = {
  name: 'core',
  path: '/core',
  component: Layout,
  redirect: '/core/index',
  alwaysShow: true, // will always show the root menu
  meta: {
    title: 'core',
    icon: 'core'
  },
  children: [
    {
      path: 'process',
      component: () => import('@/views/core/process'),
      name: 'process',
      meta: {
        title: 'process',
        icon: 'process',
        noCache: false
      }
    },
    {
      path: 'flow/:key',
      component: () => import('@/views/core/flow'),
      name: 'flow',
      hidden: true,
      meta: {
        title: route => `${route.params.key}`,
        notMenu: true,
        noCache: false
      }
    },
    {
      path: 'start',
      component: () => import('@/views/core/start'),
      name: 'start',
      meta: {
        title: 'start',
        icon: 'start'
      }
    },
    {
      path: 'todo',
      component: () => import('@/views/core/todo'),
      name: 'todo',
      meta: {
        title: 'todo',
        icon: 'todo'
      }
    },
    {
      path: 'doing',
      component: () => import('@/views/core/doing'),
      name: 'doing',
      meta: {
        title: 'doing',
        icon: 'doing'
      }
    },
    {
      path: 'done',
      component: () => import('@/views/core/done'),
      name: 'done',
      meta: {
        title: 'done',
        icon: 'done'
      }
    },
    {
      path: 'draft',
      component: () => import('@/views/core/draft'),
      name: 'draft',
      meta: {
        title: 'draft',
        icon: 'el-icon-edit-outline'
      }
    },
    {
      path: 'calendar',
      component: () => import('@/views/core/calendar'),
      name: 'calendar',
      meta: {
        title: 'calendar',
        icon: 'calendar',
        noCache: false,
        buttons: ['saveDate']
      }
    }
  ]
}
export default coreRouter
