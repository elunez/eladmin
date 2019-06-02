import request from '@/utils/request'

export function login(username, password) {
  return request({
    url: 'auth/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

export function getInfo() {
  return request({
    url: 'auth/info',
    method: 'get'
  })
}

export function sendCode(data) {
  return request({
    url: 'auth/reg/sendcode',
    method: 'post',
    data
  })
}

export function reg(code, data) {
  return request({
    url: 'auth/reg/' + code,
    method: 'post',
    data
  })
}
