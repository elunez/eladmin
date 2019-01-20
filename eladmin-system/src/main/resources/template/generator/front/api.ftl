import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/${changeClassName}',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/${changeClassName}/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/${changeClassName}',
    method: 'put',
    data
  })
}
