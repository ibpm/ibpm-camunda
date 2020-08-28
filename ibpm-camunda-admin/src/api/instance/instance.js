import request from '@/utils/request'
import { APIPath } from '@/settings'

export const listTodoReq = (params) => {
  return request({
    url: APIPath.CORE + APIPath.INSTANCE + '/todo',
    method: 'get',
    params: params
  })
}

export const listDoingReq = (params) => {
  return request({
    url: APIPath.CORE + APIPath.INSTANCE + '/doing',
    method: 'get',
    params: params
  })
}

export const listDoneReq = (params) => {
  return request({
    url: APIPath.CORE + APIPath.INSTANCE + '/done',
    method: 'get',
    params: params
  })
}

export const listReq = (params) => {
  return request({
    url: APIPath.CORE + APIPath.INSTANCE,
    method: 'get',
    params: params
  })
}

export const listChildrenReq = (params) => {
  return request({
    url: APIPath.CORE + APIPath.INSTANCE + '/children',
    method: 'get',
    params: params
  })
}

export const listRetryReq = (params) => {
  return request({
    url: APIPath.CORE + APIPath.INSTANCE + '/retry',
    method: 'get',
    params: params
  })
}

export const listRetryChildrenReq = (params) => {
  return request({
    url: APIPath.CORE + APIPath.INSTANCE + '/retry' + '/children',
    method: 'get',
    params: params
  })
}

export const listActReq = (params) => {
  return request({
    url: APIPath.CORE + APIPath.INSTANCE + '/act',
    method: 'get',
    params: params
  })
}

export const retryReq = (data) => {
  return request({
    url: APIPath.CORE + APIPath.INSTANCE + '/retry',
    method: 'post',
    data: data
  })
}

export const terminateReq = (data) => {
  return request({
    url: APIPath.CORE + APIPath.INSTANCE + '/terminate',
    method: 'post',
    data: data
  })
}

export const getArgsReq = (params) => {
  return request({
    url: APIPath.CORE + APIPath.INSTANCE + '/arg',
    method: 'get',
    params: params
  })
}

export const openFormReq = (params) => {
  return request({
    url: APIPath.CORE + APIPath.INSTANCE + '/openForm',
    method: 'get',
    params: params
  })
}
