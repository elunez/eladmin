/*
 *  Copyright 2019-2025 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.modules.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.domain.Menu;
import me.zhengjie.modules.system.domain.Role;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.domain.vo.MenuMetaVo;
import me.zhengjie.modules.system.domain.vo.MenuVo;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.modules.system.repository.MenuRepository;
import me.zhengjie.modules.system.repository.UserRepository;
import me.zhengjie.modules.system.service.MenuService;
import me.zhengjie.modules.system.service.RoleService;
import me.zhengjie.modules.system.service.dto.MenuDto;
import me.zhengjie.modules.system.service.dto.MenuQueryCriteria;
import me.zhengjie.modules.system.service.dto.RoleSmallDto;
import me.zhengjie.modules.system.service.mapstruct.MenuMapper;
import me.zhengjie.utils.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final MenuMapper menuMapper;
    private final RoleService roleService;
    private final RedisUtils redisUtils;

    private static final String HTTP_PRE = "http://";
    private static final String HTTPS_PRE = "https://";
    private static final String YES_STR = "是";
    private static final String NO_STR = "否";
    private static final String BAD_REQUEST = "外链必须以http://或者https://开头";
    
    @Override
    public List<MenuDto> queryAll(MenuQueryCriteria criteria, Boolean isQuery) throws Exception {
        Sort sort = Sort.by(Sort.Direction.ASC, "menuSort");
        if(Boolean.TRUE.equals(isQuery)){
            criteria.setPidIsNull(true);
            List<Field> fields = QueryHelp.getAllFields(criteria.getClass(), new ArrayList<>());
            for (Field field : fields) {
                //设置对象的访问权限，保证对private的属性的访问
                field.setAccessible(true);
                Object val = field.get(criteria);
                if("pidIsNull".equals(field.getName())){
                    continue;
                }
                if (ObjectUtil.isNotNull(val)) {
                    criteria.setPidIsNull(null);
                    break;
                }
            }
        }
        return menuMapper.toDto(menuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),sort));
    }

    @Override
    public MenuDto findById(long id) {
        String key = CacheKey.MENU_ID + id;
        Menu menu = redisUtils.get(key, Menu.class);
        if(menu == null){
            menu = menuRepository.findById(id).orElseGet(Menu::new);
            ValidationUtil.isNull(menu.getId(),"Menu","id",id);
            redisUtils.set(key, menu, 1, TimeUnit.DAYS);
        }
        return menuMapper.toDto(menu);
    }

    /**
     * 用户角色改变时需清理缓存
     * @param currentUserId /
     * @return /
     */
    @Override
    public List<MenuDto> findByUser(Long currentUserId) {
        String key = CacheKey.MENU_USER + currentUserId;
        List<Menu> menus = redisUtils.getList(key, Menu.class);
        if (CollUtil.isEmpty(menus)){
            List<RoleSmallDto> roles = roleService.findByUsersId(currentUserId);
            Set<Long> roleIds = roles.stream().map(RoleSmallDto::getId).collect(Collectors.toSet());
            LinkedHashSet<Menu> data = menuRepository.findByRoleIdsAndTypeNot(roleIds, 2);
            menus = new ArrayList<>(data);
            redisUtils.set(key, menus, 1, TimeUnit.DAYS);
        }
        return menus.stream().map(menuMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Menu resources) {
        if(menuRepository.findByTitle(resources.getTitle()) != null){
            throw new EntityExistException(Menu.class,"title",resources.getTitle());
        }
        if(StringUtils.isNotBlank(resources.getComponentName())){
            if(menuRepository.findByComponentName(resources.getComponentName()) != null){
                throw new EntityExistException(Menu.class,"componentName",resources.getComponentName());
            }
        }
        if (Long.valueOf(0L).equals(resources.getPid())) {
            resources.setPid(null);
        }
        if(resources.getIFrame()){
            if (!(resources.getPath().toLowerCase().startsWith(HTTP_PRE)||resources.getPath().toLowerCase().startsWith(HTTPS_PRE))) {
                throw new BadRequestException(BAD_REQUEST);
            }
        }
        menuRepository.save(resources);
        // 计算子节点数目
        resources.setSubCount(0);
        // 更新父节点菜单数目
        updateSubCnt(resources.getPid());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Menu resources) {
        if(resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        Menu menu = menuRepository.findById(resources.getId()).orElseGet(Menu::new);
        ValidationUtil.isNull(menu.getId(),"Permission","id",resources.getId());

        if(resources.getIFrame()){
            if (!(resources.getPath().toLowerCase().startsWith(HTTP_PRE)||resources.getPath().toLowerCase().startsWith(HTTPS_PRE))) {
                throw new BadRequestException(BAD_REQUEST);
            }
        }
        Menu menu1 = menuRepository.findByTitle(resources.getTitle());

        if(menu1 != null && !menu1.getId().equals(menu.getId())){
            throw new EntityExistException(Menu.class,"title",resources.getTitle());
        }

        if(resources.getPid().equals(0L)){
            resources.setPid(null);
        }

        // 记录的父节点ID
        Long oldPid = menu.getPid();
        Long newPid = resources.getPid();

        if(StringUtils.isNotBlank(resources.getComponentName())){
            menu1 = menuRepository.findByComponentName(resources.getComponentName());
            if(menu1 != null && !menu1.getId().equals(menu.getId())){
                throw new EntityExistException(Menu.class,"componentName",resources.getComponentName());
            }
        }
        menu.setTitle(resources.getTitle());
        menu.setComponent(resources.getComponent());
        menu.setPath(resources.getPath());
        menu.setIcon(resources.getIcon());
        menu.setIFrame(resources.getIFrame());
        menu.setPid(resources.getPid());
        menu.setMenuSort(resources.getMenuSort());
        menu.setCache(resources.getCache());
        menu.setHidden(resources.getHidden());
        menu.setComponentName(resources.getComponentName());
        menu.setPermission(resources.getPermission());
        menu.setType(resources.getType());
        menuRepository.save(menu);
        // 计算父级菜单节点数目
        updateSubCnt(oldPid);
        updateSubCnt(newPid);
        // 清理缓存
        delCaches(resources.getId());
    }

    @Override
    public Set<Menu> getChildMenus(List<Menu> menuList, Set<Menu> menuSet) {
        for (Menu menu : menuList) {
            menuSet.add(menu);
            List<Menu> menus = menuRepository.findByPidOrderByMenuSort(menu.getId());
            if(CollUtil.isNotEmpty(menus)){
                getChildMenus(menus, menuSet);
            }
        }
        return menuSet;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Menu> menuSet) {
        for (Menu menu : menuSet) {
            // 清理缓存
            delCaches(menu.getId());
            roleService.untiedMenu(menu.getId());
            menuRepository.deleteById(menu.getId());
            updateSubCnt(menu.getPid());
        }
    }

    @Override
    public List<MenuDto> getMenus(Long pid) {
        List<Menu> menus;
        if(pid != null && !pid.equals(0L)){
            menus = menuRepository.findByPidOrderByMenuSort(pid);
        } else {
            menus = menuRepository.findByPidIsNullOrderByMenuSort();
        }
        return menuMapper.toDto(menus);
    }

    @Override
    public List<MenuDto> getSuperior(MenuDto menuDto, List<Menu> menus) {
        if(menuDto.getPid() == null){
            menus.addAll(menuRepository.findByPidIsNullOrderByMenuSort());
            return menuMapper.toDto(menus);
        }
        menus.addAll(menuRepository.findByPidOrderByMenuSort(menuDto.getPid()));
        return getSuperior(findById(menuDto.getPid()), menus);
    }

    @Override
    public List<MenuDto> buildTree(List<MenuDto> menuDtos) {
        List<MenuDto> trees = new ArrayList<>();
        Set<Long> ids = new HashSet<>();
        for (MenuDto menuDTO : menuDtos) {
            if (menuDTO.getPid() == null) {
                trees.add(menuDTO);
            }
            for (MenuDto it : menuDtos) {
                if (menuDTO.getId().equals(it.getPid())) {
                    if (menuDTO.getChildren() == null) {
                        menuDTO.setChildren(new ArrayList<>());
                    }
                    menuDTO.getChildren().add(it);
                    ids.add(it.getId());
                }
            }
        }
        if(trees.isEmpty()){
            trees = menuDtos.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
        }
        return trees;
    }

    @Override
    public List<MenuVo> buildMenus(List<MenuDto> menuDtos) {
        List<MenuVo> list = new LinkedList<>();
        menuDtos.forEach(menuDTO -> {
                    if (menuDTO!=null){
                        List<MenuDto> menuDtoList = menuDTO.getChildren();
                        MenuVo menuVo = new MenuVo();
                        menuVo.setName(ObjectUtil.isNotEmpty(menuDTO.getComponentName())  ? menuDTO.getComponentName() : menuDTO.getTitle());
                        // 一级目录需要加斜杠，不然会报警告
                        menuVo.setPath(menuDTO.getPid() == null ? "/" + menuDTO.getPath() :menuDTO.getPath());
                        menuVo.setHidden(menuDTO.getHidden());
                        // 如果不是外链
                        if(!menuDTO.getIFrame()){
                            if(menuDTO.getPid() == null){
                                menuVo.setComponent(StringUtils.isEmpty(menuDTO.getComponent())?"Layout":menuDTO.getComponent());
                                // 如果不是一级菜单，并且菜单类型为目录，则代表是多级菜单
                            }else if(menuDTO.getType() == 0){
                                menuVo.setComponent(StringUtils.isEmpty(menuDTO.getComponent())?"ParentView":menuDTO.getComponent());
                            }else if(StringUtils.isNoneBlank(menuDTO.getComponent())){
                                menuVo.setComponent(menuDTO.getComponent());
                            }
                        }
                        menuVo.setMeta(new MenuMetaVo(menuDTO.getTitle(),menuDTO.getIcon(),!menuDTO.getCache()));
                        if(CollectionUtil.isNotEmpty(menuDtoList)){
                            menuVo.setAlwaysShow(true);
                            menuVo.setRedirect("noredirect");
                            menuVo.setChildren(buildMenus(menuDtoList));
                            // 处理是一级菜单并且没有子菜单的情况
                        } else if(menuDTO.getPid() == null){
                            MenuVo menuVo1 = getMenuVo(menuDTO, menuVo);
                            menuVo.setName(null);
                            menuVo.setMeta(null);
                            menuVo.setComponent("Layout");
                            List<MenuVo> list1 = new ArrayList<>();
                            list1.add(menuVo1);
                            menuVo.setChildren(list1);
                        }
                        list.add(menuVo);
                    }
                }
        );
        return list;
    }

    @Override
    public Menu findOne(Long id) {
        Menu menu = menuRepository.findById(id).orElseGet(Menu::new);
        ValidationUtil.isNull(menu.getId(),"Menu","id",id);
        return menu;
    }

    @Override
    public void download(List<MenuDto> menuDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MenuDto menuDTO : menuDtos) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("菜单标题", menuDTO.getTitle());
            map.put("菜单类型", menuDTO.getType() == null ? "目录" : menuDTO.getType() == 1 ? "菜单" : "按钮");
            map.put("权限标识", menuDTO.getPermission());
            map.put("外链菜单", menuDTO.getIFrame() ? YES_STR : NO_STR);
            map.put("菜单可见", menuDTO.getHidden() ? NO_STR : YES_STR);
            map.put("是否缓存", menuDTO.getCache() ? YES_STR : NO_STR);
            map.put("创建日期", menuDTO.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    private void updateSubCnt(Long menuId){
        if(menuId != null){
            int count = menuRepository.countByPid(menuId);
            menuRepository.updateSubCntById(count, menuId);
        }
    }

    /**
     * 清理缓存
     * @param id 菜单ID
     */
    public void delCaches(Long id){
        List<User> users = userRepository.findByMenuId(id);
        redisUtils.del(CacheKey.MENU_ID + id);
        redisUtils.delByKeys(CacheKey.MENU_USER, users.stream().map(User::getId).collect(Collectors.toSet()));
        // 清除 Role 缓存
        List<Role> roles = roleService.findInMenuId(new ArrayList<Long>(){{
            add(id);
        }});
        redisUtils.delByKeys(CacheKey.ROLE_ID, roles.stream().map(Role::getId).collect(Collectors.toSet()));
    }

    /**
     * 构建前端路由
     * @param menuDTO /
     * @param menuVo /
     * @return /
     */
    private static MenuVo getMenuVo(MenuDto menuDTO, MenuVo menuVo) {
        MenuVo menuVo1 = new MenuVo();
        menuVo1.setMeta(menuVo.getMeta());
        // 非外链
        if(!menuDTO.getIFrame()){
            menuVo1.setPath("index");
            menuVo1.setName(menuVo.getName());
            menuVo1.setComponent(menuVo.getComponent());
        } else {
            menuVo1.setPath(menuDTO.getPath());
        }
        return menuVo1;
    }
}
