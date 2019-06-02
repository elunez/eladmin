import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/users',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/users/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/users',
    method: 'put',
    data
  })
}

export function validPass(password) {
  const data = {
    password
  }
  return request({
    url: 'api/users/validPass/',
    method: 'post',
    data
  })
}

export function updatePass(password) {
  const data = {
    password
  }
  return request({
    url: 'api/users/updatePass/',
    method: 'post',
    data
  })
}

export function updateEmail(code, data) {
  return request({
    url: 'api/users/updateEmail/' + code,
    method: 'post',
    data
  })
}

