package me.zhengjie.modules.system.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.config.DataScope;
import me.zhengjie.domain.Picture;
import me.zhengjie.domain.VerificationCode;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.vo.UserPassVo;
import me.zhengjie.modules.system.service.DeptService;
import me.zhengjie.modules.system.service.RoleService;
import me.zhengjie.modules.system.service.dto.RoleSmallDTO;
import me.zhengjie.modules.system.service.dto.UserQueryCriteria;
import me.zhengjie.service.PictureService;
import me.zhengjie.service.VerificationCodeService;
import me.zhengjie.utils.*;
import me.zhengjie.modules.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private DataScope dataScope;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Log("查询用户")
    @GetMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    public ResponseEntity getUsers(UserQueryCriteria criteria, Pageable pageable){
        Set<Long> deptSet = new HashSet<>();
        Set<Long> result = new HashSet<>();

        if (!ObjectUtils.isEmpty(criteria.getDeptId())) {
            deptSet.add(criteria.getDeptId());
            deptSet.addAll(dataScope.getDeptChildren(deptService.findByPid(criteria.getDeptId())));
        }

        // 数据权限
        Set<Long> deptIds = dataScope.getDeptIds();

        // 查询条件不为空并且数据权限不为空则取交集
        if (!CollectionUtils.isEmpty(deptIds) && !CollectionUtils.isEmpty(deptSet)){

            // 取交集
            result.addAll(deptSet);
            result.retainAll(deptIds);

            // 若无交集，则代表无数据权限
            criteria.setDeptIds(result);
            if(result.size() == 0){
                return new ResponseEntity(PageUtil.toPage(null,0),HttpStatus.OK);
            } else return new ResponseEntity(userService.queryAll(criteria,pageable),HttpStatus.OK);
        // 否则取并集
        } else {
            result.addAll(deptSet);
            result.addAll(deptIds);
            criteria.setDeptIds(result);
            return new ResponseEntity(userService.queryAll(criteria,pageable),HttpStatus.OK);
        }
    }

    @Log("新增用户")
    @PostMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
    public ResponseEntity create(@Validated @RequestBody User resources){
        checkLevel(resources);
        return new ResponseEntity(userService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改用户")
    @PutMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_EDIT')")
    public ResponseEntity update(@Validated(User.Update.class) @RequestBody User resources){
        checkLevel(resources);
        userService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除用户")
    @DeleteMapping(value = "/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        Integer currentLevel =  Collections.min(roleService.findByUsers_Id(SecurityUtils.getUserId()).stream().map(RoleSmallDTO::getLevel).collect(Collectors.toList()));
        Integer optLevel =  Collections.min(roleService.findByUsers_Id(id).stream().map(RoleSmallDTO::getLevel).collect(Collectors.toList()));

        if (currentLevel > optLevel) {
            throw new BadRequestException("角色权限不足");
        }
        userService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改密码
     * @param user
     * @return
     */
    @PostMapping(value = "/users/updatePass")
    public ResponseEntity updatePass(@RequestBody UserPassVo user){
        UserDetails userDetails = SecurityUtils.getUserDetails();
        if(!userDetails.getPassword().equals(EncryptUtils.encryptPassword(user.getOldPass()))){
            throw new BadRequestException("修改失败，旧密码错误");
        }
        if(userDetails.getPassword().equals(EncryptUtils.encryptPassword(user.getNewPass()))){
            throw new BadRequestException("新密码不能与旧密码相同");
        }
        userService.updatePass(userDetails.getUsername(),EncryptUtils.encryptPassword(user.getNewPass()));
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改头像
     * @param file
     * @return
     */
    @PostMapping(value = "/users/updateAvatar")
    public ResponseEntity updateAvatar(@RequestParam MultipartFile file){
        Picture picture = pictureService.upload(file, SecurityUtils.getUsername());
        userService.updateAvatar(SecurityUtils.getUsername(),picture.getUrl());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改邮箱
     * @param user
     * @param user
     * @return
     */
    @Log("修改邮箱")
    @PostMapping(value = "/users/updateEmail/{code}")
    public ResponseEntity updateEmail(@PathVariable String code,@RequestBody User user){
        UserDetails userDetails = SecurityUtils.getUserDetails();
        if(!userDetails.getPassword().equals(EncryptUtils.encryptPassword(user.getPassword()))){
            throw new BadRequestException("密码错误");
        }
        VerificationCode verificationCode = new VerificationCode(code, ElAdminConstant.RESET_MAIL,"email",user.getEmail());
        verificationCodeService.validated(verificationCode);
        userService.updateEmail(userDetails.getUsername(),user.getEmail());
        return new ResponseEntity(HttpStatus.OK);
    }



    /**
     * 如果当前用户的角色级别低于创建用户的角色级别，则抛出权限不足的错误
     * @param resources
     */
    private void checkLevel(User resources) {
        Integer currentLevel =  Collections.min(roleService.findByUsers_Id(SecurityUtils.getUserId()).stream().map(RoleSmallDTO::getLevel).collect(Collectors.toList()));
        Integer optLevel = roleService.findByRoles(resources.getRoles());
        if (currentLevel > optLevel) {
            throw new BadRequestException("角色权限不足");
        }
    }
}
