import request from '@/utils/request'
import { APIPath } from '@/settings'

export const listReq = (params) => {
  return request({
    url: APIPath.SYS + APIPath.USER,
    method: 'get',
    params: params
  })
}

export const addReq = (data) => {
  return request({
    url: APIPath.SYS + APIPath.USER,
    method: 'post',
    data: data
  })
}

export const updateReq = (data) => {
  return request({
    url: APIPath.SYS + APIPath.USER,
    method: 'put',
    data: data
  })
}

export const removeReq = (data) => {
  return request({
    url: APIPath.SYS + APIPath.USER,
    method: 'delete',
    data: data
  })
}

export const getReq = (params) => {
  return request({
    url: APIPath.SYS + APIPath.USER + APIPath.GET,
    method: 'get',
    params: params
  })
}

export const updatePasswordReq = (data) => {
  return request({
    url: APIPath.SYS + APIPath.USER + '/password',
    method: 'put',
    data: data
  })
}

export const getRolesReq = (params) => {
  return request({
    url: APIPath.SYS + APIPath.USER + '/role',
    method: 'get',
    params: params
  })
}

export const updateRoleReq = (data) => {
  return request({
    url: APIPath.SYS + APIPath.USER + '/role',
    method: 'put',
    data: data
  })
}

