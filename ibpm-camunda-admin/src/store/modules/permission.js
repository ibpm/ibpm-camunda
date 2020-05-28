import { asyncRouterMap, /* devRouterMap,*/ constantRouterMap } from '@/router'
import langSelect from '../../lang/index'

/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(resourceNames, route) {
  if (route.meta && !route.meta.notMenu) { // is menu
    return resourceNames.some(resourceName => (route.name || route.path.substring(1)) === resourceName)
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRouterMap
 * @param roles
 */
function filterAsyncRoutes(routes, resourceNames) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(resourceNames, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, resourceNames)
      }
      res.push(tmp)
    }
  })

  return res
}

const state = {
  routes: constantRouterMap,
  addRoutes: []
}
const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRouterMap.concat(routes)
  }
}
const actions = {
  generateRoutes({ commit }) {
    return new Promise((resolve, reject) => {
      // const roleNames = JSON.parse(sessionStorage.roleNames)
      if (!sessionStorage.resourceNames || sessionStorage.resourceNames.length === 0) {
        reject(langSelect.t('tip.noResources'))
      }
      const resourceNames = JSON.parse(sessionStorage.resourceNames)
      const accessedRouter = filterAsyncRoutes(asyncRouterMap, resourceNames)
      /* exclude all dev menus from super admin
      if (roleNames.includes('admin')) {
        accessedRouter = asyncRouterMap.concat(devRouterMap)
      } else {
        accessedRouter = filterAsyncRoutes(asyncRouterMap, resourceNames)
      }*/
      commit('SET_ROUTES', accessedRouter)
      resolve(constantRouterMap.concat(accessedRouter))
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

