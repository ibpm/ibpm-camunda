import request from '@/utils/request'
import { APIPath } from '@/settings'

export const userCountReq = () => {
  return request({
    url: APIPath.STATISTICS + APIPath.COUNT + APIPath.USER,
    method: 'get'
  })
}

export const processCountReq = () => {
  return request({
    url: APIPath.STATISTICS + APIPath.COUNT + APIPath.JOB,
    method: 'get'
  })
}

export const instanceCountReq = (params) => {
  return request({
    url: APIPath.STATISTICS + APIPath.COUNT + '/instance',
    method: 'get',
    params: params
  })
}
