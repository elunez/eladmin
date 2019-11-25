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
export function delAll(ids) {
  return request({
    url: 'api/${changeClassName}/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/${changeClassName}',
    method: 'put',
    data
  })
}

export default { add, edit, del, delAll }
