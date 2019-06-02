import request from '@/utils/request'

export function getPapConfig() {
  return request({
    url: 'api/pay/pic',
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: 'api/payConfig',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/payConfig/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/payConfig',
    method: 'put',
    data
  })
}
