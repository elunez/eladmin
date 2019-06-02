import request from '@/utils/request'

export function getErrDetail(id) {
  return request({
    url: 'api/logs/error/' + id,
    method: 'get'
  })
}
