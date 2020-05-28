import request from '@/utils/request'
import { APIPath } from '@/settings'

export const listTimeZoneReq = () => {
  return request({
    url: APIPath.CORE + APIPath.TOOL + '/timeZone',
    method: 'get'
  })
}

export const listSchedulerServiceReq = () => {
  return request({
    url: APIPath.CORE + APIPath.TOOL + '/scheduler',
    method: 'get'
  })
}

export const listExecutorServiceReq = () => {
  return request({
    url: APIPath.CORE + APIPath.TOOL + '/executor',
    method: 'get'
  })
}

export const getServerInfoReq = (params) => {
  return request({
    url: APIPath.CORE + APIPath.TOOL + '/serverInfo',
    method: 'get',
    params: params
  })
}

export const pingReq = (params) => {
  return request({
    url: APIPath.CORE + APIPath.TOOL + '/ping',
    method: 'get',
    params: params
  })
}

export const currentTimeReq = (params) => {
  return request({
    url: APIPath.CORE + APIPath.TOOL + '/time',
    method: 'get',
    params: params
  })
}

export const argTypesReq = (params) => {
  return request({
    url: APIPath.CORE + APIPath.TOOL + '/argTypes',
    method: 'get',
    params: params
  })
}

export const tradeDateUnitsReq = (params) => {
  return request({
    url: APIPath.CORE + APIPath.TOOL + '/tradeDateUnits',
    method: 'get',
    params: params
  })
}
