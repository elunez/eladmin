package me.zhengjie.system.service.impl;

import cn.hutool.core.util.StrUtil;
import me.zhengjie.common.exception.EntityExistException;
import me.zhengjie.common.utils.ValidationUtil;
import me.zhengjie.system.domain.Menu;
import me.zhengjie.system.domain.Role;
import me.zhengjie.system.domain.vo.MenuMetaVo;
import me.zhengjie.system.domain.vo.MenuVo;
import me.zhengjie.system.repository.MenuRepository;
import me.zhengjie.system.service.MenuService;
import me.zhengjie.system.service.dto.MenuDTO;
import me.zhengjie.system.service.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public MenuDTO findById(long id) {
        Optional<Menu> menu = menuRepository.findById(id);
        ValidationUtil.isNull(menu,"Menu","id",id);
        return menuMapper.toDto(menu.get());
    }

    @Override
    public List<MenuDTO> findByRoles(Set<Role> roles) {
        Set<Menu> menus = menuRepository.findByRoles(roles);
        return menus.stream().map(menuMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public MenuDTO create(Menu resources) {
        if(menuRepository.findByName(resources.getName()) != null){
            throw new EntityExistException(Menu.class,"name",resources.getName());
        }
        return menuMapper.toDto(menuRepository.save(resources));
    }

    @Override
    public void update(Menu resources) {
        Optional<Menu> optionalPermission = menuRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalPermission,"Permission","id",resources.getId());

        Menu menu = optionalPermission.get();
        Menu menu1 = menuRepository.findByName(resources.getName());

        if(menu1 != null && !menu1.getId().equals(menu.getId())){
            throw new EntityExistException(Menu.class,"name",resources.getName());
        }

        menu.setName(resources.getName());
        menu.setIcon(resources.getIcon());
        menu.setIFrame(resources.getIFrame());
        menu.setPid(resources.getPid());
        menu.setSoft(resources.getSoft());
        menuRepository.save(menu);
    }

    @Override
    public void delete(Long id) {
        List<Menu> menuList = menuRepository.findByPid(id);
        for (Menu menu : menuList) {
            menuRepository.delete(menu);
        }
        menuRepository.deleteById(id);
    }

    @Override
    public Object getMenuTree(List<Menu> menus) {
        List<Map<String,Object>> list = new LinkedList<>();
        menus.forEach(menu -> {
                    if (menu!=null){
                        Map<String,Object> map = new HashMap<>();
                        map.put("id",menu.getId());
                        map.put("label",menu.getName());
                        list.add(map);
                    }
                }
        );
        return list;
    }

    @Override
    public List<Menu> findByPid(long pid) {
        return menuRepository.findByPid(pid);
    }

    @Override
    public Map buildTree(List<MenuDTO> menuDTOS) {
        List<MenuDTO> trees = new ArrayList<MenuDTO>();

        for (MenuDTO menuDTO : menuDTOS) {

            if ("0".equals(menuDTO.getPid().toString())) {
                trees.add(menuDTO);
            }

            for (MenuDTO it : menuDTOS) {
                if (it.getPid().equals(menuDTO.getId())) {
                    if (menuDTO.getChildren() == null) {
                        menuDTO.setChildren(new ArrayList<MenuDTO>());
                    }
                    menuDTO.getChildren().add(it);
                }
            }
        }

        Integer totalElements = menuDTOS!=null?menuDTOS.size():0;
        Map map = new HashMap();
        map.put("content",trees.size() == 0?menuDTOS:trees);
        map.put("totalElements",totalElements);
        return map;
    }

    @Override
    public List<MenuVo> buildMenus(List<MenuDTO> menuDTOS) {
        List<MenuVo> list = new LinkedList<>();
        menuDTOS.forEach(menuDTO -> {
            if (menuDTO!=null){
                List<MenuDTO> menuDTOList = menuDTO.getChildren();
                MenuVo menuVo = new MenuVo();
                menuVo.setName(menuDTO.getName());
                menuVo.setPath(menuDTO.getPath());
                if(!menuDTO.getIFrame()){
                    if(menuDTO.getPid().equals(0L)){
                        //一级目录需要加斜杠，不然访问不了
                        menuVo.setPath("/" + menuDTO.getPath());
                    }
                    menuVo.setComponent(StrUtil.isEmpty(menuDTO.getComponent())?"Layout":menuDTO.getComponent());
                }
                menuVo.setMeta(new MenuMetaVo(menuDTO.getName(),menuDTO.getIcon()));
                if(menuDTOList!=null && menuDTOList.size()!=0){
                    menuVo.setAlwaysShow(true);
                    menuVo.setChildren(buildMenus(menuDTOList));
                }
                list.add(menuVo);
            }
        }
        );
        return list;
    }
}
