import request from '@/utils/request'
import { APIPath } from '@/settings'

export const addReq = (data) => {
  return request({
    url: APIPath.CORE + APIPath.ARG,
    method: 'post',
    data: data
  })
}

export const updateReq = (data) => {
  return request({
    url: APIPath.CORE + APIPath.ARG,
    method: 'put',
    data: data
  })
}

export const listReq = (params) => {
  return request({
    url: APIPath.CORE + APIPath.ARG,
    method: 'get',
    params: params
  })
}

export const removeReq = (data) => {
  return request({
    url: APIPath.CORE + APIPath.ARG,
    method: 'delete',
    data: data
  })
}

export const getReq = (params) => {
  return request({
    url: APIPath.CORE + APIPath.ARG + APIPath.GET,
    method: 'get',
    params: params
  })
}

export const getTradeDateReq = (params) => {
  return request({
    url: APIPath.CORE + APIPath.ARG + '/trade_date',
    method: 'get',
    params: params
  })
}
