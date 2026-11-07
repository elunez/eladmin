# Vulnerability Report

## Title

eladmin `/api/users/resetPwd` Missing Authorization Check Leads to Arbitrary User Password Reset

## Affected Project

- Project: eladmin
- Repository: https://github.com/elunez/eladmin
- Affected version: v2.7 and earlier
- Affected endpoint: `PUT /api/users/resetPwd`
- Affected file: `eladmin-system/src/main/java/me/zhengjie/modules/system/rest/UserController.java`

## Vulnerability Type

Broken Access Control / Privilege Escalation / Account Takeover

## Description

The `/api/users/resetPwd` endpoint is used to reset user passwords. However, the corresponding `resetPwd` method in `UserController.java` does not enforce permission checks such as `@PreAuthorize("@el.check('user:edit')")`.

As a result, any authenticated low-privileged user can send a request to this endpoint and reset the password of arbitrary users, including the administrator account.

## Vulnerable Code

```java
@ApiOperation("重置密码")
@PutMapping(value = "/resetPwd")
public ResponseEntity<Object> resetPwd(@RequestBody Set<Long> ids) {
    String pwd = passwordEncoder.encode("123456");
    userService.resetPwd(ids, pwd);
    return new ResponseEntity<>(HttpStatus.OK);
}
