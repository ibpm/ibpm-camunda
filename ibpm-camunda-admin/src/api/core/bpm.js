import request from '@/utils/request'
import { APIPath } from '@/settings'

export const getReq = (params) => {
  return request({
    url: APIPath.CORE + APIPath.BPM + APIPath.GET,
    method: 'get',
    params: params
  })
}

export const saveReq = (data) => {
  return request({
    url: APIPath.CORE + APIPath.BPM + '/draft',
    method: 'post',
    data: data
  })
}

export const createReq = (data) => {
  return request({
    url: APIPath.CORE + APIPath.BPM + '/create',
    method: 'post',
    data: data
  })
}

export const approveReq = (data) => {
  return request({
    url: APIPath.CORE + APIPath.BPM + '/approve',
    method: 'post',
    data: data
  })
}
