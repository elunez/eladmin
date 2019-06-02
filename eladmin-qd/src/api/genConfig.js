import request from '@/utils/request'

export function get() {
  return request({
    url: 'api/genConfig',
    method: 'get'
  })
}

export function update(data) {
  return request({
    url: 'api/genConfig',
    data,
    method: 'put'
  })
}
