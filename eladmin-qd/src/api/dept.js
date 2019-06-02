import request from '@/utils/request'

export function getDepts(params) {
  return request({
    url: 'api/dept',
    method: 'get',
    params
  })
}

export function add(data) {
  return request({
    url: 'api/dept',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/dept/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/dept',
    method: 'put',
    data
  })
}
