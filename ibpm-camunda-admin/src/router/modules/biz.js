/** When your routing process is too long, you can split it into small modules**/

import Layout from '@/layout'

const bizRouter = {
  name: 'biz',
  path: '/biz',
  component: Layout,
  redirect: '/biz/index',
  alwaysShow: true, // will always show the root menu
  meta: {
    title: 'biz',
    icon: 'example'
  },
  children: [
    {
      path: 'demo01Form',
      component: () => import('@/views/biz/01/demo01Form'),
      name: 'demo01Form',
      hidden: true,
      meta: {
        title: 'demo01Form',
        notMenu: true,
        noCache: false
      }
    },
    {
      path: 'demo02Form',
      component: () => import('@/views/biz/02/demo02Form'),
      name: 'demo02Form',
      hidden: true,
      meta: {
        title: 'demo02Form',
        notMenu: true,
        noCache: false
      }
    }
  ]
}
export default bizRouter
