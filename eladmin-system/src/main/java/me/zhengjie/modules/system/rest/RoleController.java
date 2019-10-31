package me.zhengjie.modules.system.rest;

import cn.hutool.core.lang.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.system.domain.Role;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.service.RoleService;
import me.zhengjie.modules.system.service.dto.RoleQueryCriteria;
import me.zhengjie.modules.system.service.dto.RoleSmallDTO;
import me.zhengjie.utils.SecurityUtils;
import me.zhengjie.utils.ThrowableUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
@Api(tags = "系统：角色管理")
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    private static final String ENTITY_NAME = "role";

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation("获取单个role")
    @GetMapping(value = "/{id}")
    @PreAuthorize("@el.check('roles:list')")
    public ResponseEntity getRoles(@PathVariable Long id){
        return new ResponseEntity<>(roleService.findById(id), HttpStatus.OK);
    }

    @Log("导出角色数据")
    @ApiOperation("导出角色数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('role:list')")
    public void download(HttpServletResponse response, RoleQueryCriteria criteria) throws IOException {
        roleService.download(roleService.queryAll(criteria), response);
    }

    @ApiOperation("返回全部的角色")
    @GetMapping(value = "/all")
    @PreAuthorize("@el.check('roles:list','user:add','user:edit')")
    public ResponseEntity getAll(@PageableDefault(value = 2000, sort = {"level"}, direction = Sort.Direction.ASC) Pageable pageable){
        return new ResponseEntity<>(roleService.queryAll(pageable),HttpStatus.OK);
    }

    @Log("查询角色")
    @ApiOperation("查询角色")
    @GetMapping
    @PreAuthorize("@el.check('roles:list')")
    public ResponseEntity getRoles(RoleQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(roleService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @ApiOperation("获取用户级别")
    @GetMapping(value = "/level")
    public ResponseEntity getLevel(){
        List<Integer> levels = roleService.findByUsers_Id(SecurityUtils.getUserId()).stream().map(RoleSmallDTO::getLevel).collect(Collectors.toList());
        return new ResponseEntity<>(Dict.create().set("level", Collections.min(levels)),HttpStatus.OK);
    }

    @Log("新增角色")
    @ApiOperation("新增角色")
    @PostMapping
    @PreAuthorize("@el.check('roles:add')")
    public ResponseEntity create(@Validated @RequestBody Role resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity<>(roleService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改角色")
    @ApiOperation("修改角色")
    @PutMapping
    @PreAuthorize("@el.check('roles:edit')")
    public ResponseEntity update(@Validated(Role.Update.class) @RequestBody Role resources){
        roleService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("修改角色菜单")
    @ApiOperation("修改角色菜单")
    @PutMapping(value = "/menu")
    @PreAuthorize("@el.check('roles:edit')")
    public ResponseEntity updateMenu(@RequestBody Role resources){
        roleService.updateMenu(resources,roleService.findById(resources.getId()));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除角色")
    @ApiOperation("删除角色")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("@el.check('roles:del')")
    public ResponseEntity delete(@PathVariable Long id){
        try {
            roleService.delete(id);
        }catch (Throwable e){
            ThrowableUtil.throwForeignKeyException(e, "该角色存在用户关联，请取消关联后再试");
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
