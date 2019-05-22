import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/${changeClassName}',
    method: 'post',
    data
  })
}

export function del(${pkChangeColName}) {
  return request({
    url: 'api/${changeClassName}/' + ${pkChangeColName},
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
