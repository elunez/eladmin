import request from '@/utils/request'

export function del(id) {
  return request({
    url: 'api/pictures/' + id,
    method: 'delete'
  })
}

export function delAll(ids) {
  return request({
    url: 'api/pictures/',
    method: 'delete',
    data: ids
  })
}
