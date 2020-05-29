/** When your routing job is too long, you can split it into small modules**/

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
      path: 'job',
      component: () => import('@/views/core/job'),
      name: 'job',
      meta: {
        title: 'job',
        icon: 'job',
        noCache: false
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
    }
  ]
}
export default coreRouter
