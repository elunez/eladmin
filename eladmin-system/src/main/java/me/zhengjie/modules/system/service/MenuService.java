package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.Menu;
import me.zhengjie.modules.system.service.dto.MenuDto;
import me.zhengjie.modules.system.service.dto.MenuQueryCriteria;
import me.zhengjie.modules.system.service.dto.RoleSmallDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2018-12-17
 */
public interface MenuService {

    /**
     * 查询全部数据
     * @param criteria 条件
     * @return /
     */
    List<MenuDto> queryAll(MenuQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    MenuDto findById(long id);

    /**
     * 创建
     * @param resources /
     * @return /
     */
    MenuDto create(Menu resources);

    /**
     * 编辑
     * @param resources /
     */
    void update(Menu resources);

    /**
     * 获取待删除的菜单
     * @param menuList /
     * @param menuSet /
     * @return /
     */
    Set<Menu> getDeleteMenus(List<Menu> menuList, Set<Menu> menuSet);

    /**
     * 获取菜单树
     * @param menus /
     * @return /
     */
    Object getMenuTree(List<Menu> menus);

    /**
     * 根据pid查询
     * @param pid /
     * @return /
     */
    List<Menu> findByPid(long pid);

    /**
     * 构建菜单树
     * @param menuDtos 原始数据
     * @return /
     */
    Map<String,Object> buildTree(List<MenuDto> menuDtos);

    /**
     * 根据角色查询
     * @param roles /
     * @return /
     */
    List<MenuDto> findByRoles(List<RoleSmallDto> roles);

    /**
     * 构建菜单树
     * @param menuDtos /
     * @return /
     */
    Object buildMenus(List<MenuDto> menuDtos);

    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    Menu findOne(Long id);

    /**
     * 删除
     * @param menuSet /
     */
    void delete(Set<Menu> menuSet);

    /**
     * 导出
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<MenuDto> queryAll, HttpServletResponse response) throws IOException;
}
