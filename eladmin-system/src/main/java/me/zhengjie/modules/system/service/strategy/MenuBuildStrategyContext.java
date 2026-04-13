package me.zhengjie.modules.system.service.strategy;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.domain.vo.MenuMetaVo;
import me.zhengjie.modules.system.domain.vo.MenuVo;
import me.zhengjie.modules.system.service.dto.MenuDto;
import me.zhengjie.utils.StringUtils;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MenuBuildStrategyContext {
    
    private final MenuComponentStrategy componentStrategy;
    
    public List<MenuVo> buildMenus(List<MenuDto> menuDtos) {
        List<MenuVo> list = new LinkedList<>();
        for (MenuDto menuDTO : menuDtos) {
            if (menuDTO == null) {
                continue;
            }
            MenuVo menuVo = buildMenuVo(menuDTO);
            list.add(menuVo);
        }
        return list;
    }
    
    private MenuVo buildMenuVo(MenuDto menuDTO) {
        MenuVo menuVo = new MenuVo();
        menuVo.setName(buildName(menuDTO));
        menuVo.setPath(buildPath(menuDTO));
        menuVo.setHidden(menuDTO.getHidden());
        
        buildComponent(menuVo, menuDTO);
        menuVo.setMeta(new MenuMetaVo(menuDTO.getTitle(), menuDTO.getIcon(), !menuDTO.getCache()));
        
        buildChildren(menuVo, menuDTO);
        
        return menuVo;
    }
    
    private String buildName(MenuDto menuDTO) {
        return ObjectUtil.isNotEmpty(menuDTO.getComponentName()) 
                ? menuDTO.getComponentName() 
                : menuDTO.getTitle();
    }
    
    private String buildPath(MenuDto menuDTO) {
        return menuDTO.getPid() == null 
                ? "/" + menuDTO.getPath() 
                : menuDTO.getPath();
    }
    
    private void buildComponent(MenuVo menuVo, MenuDto menuDTO) {
        if (menuDTO.getIFrame()) {
            return;
        }
        String component = componentStrategy.getComponent(menuDTO);
        if (component != null) {
            menuVo.setComponent(component);
        }
    }
    
    private void buildChildren(MenuVo menuVo, MenuDto menuDTO) {
        List<MenuDto> menuDtoList = menuDTO.getChildren();
        
        if (CollectionUtil.isNotEmpty(menuDtoList)) {
            menuVo.setAlwaysShow(true);
            menuVo.setRedirect("noredirect");
            menuVo.setChildren(buildMenus(menuDtoList));
        } else if (componentStrategy.isRootMenu(menuDTO)) {
            handleRootMenuWithoutChildren(menuVo, menuDTO);
        }
    }
    
    private void handleRootMenuWithoutChildren(MenuVo menuVo, MenuDto menuDTO) {
        MenuVo childMenuVo = createChildMenuVo(menuDTO, menuVo);
        menuVo.setName(null);
        menuVo.setMeta(null);
        menuVo.setComponent("Layout");
        List<MenuVo> children = new ArrayList<>();
        children.add(childMenuVo);
        menuVo.setChildren(children);
    }
    
    private MenuVo createChildMenuVo(MenuDto menuDTO, MenuVo parentMenuVo) {
        MenuVo childMenuVo = new MenuVo();
        childMenuVo.setMeta(parentMenuVo.getMeta());
        
        if (!menuDTO.getIFrame()) {
            childMenuVo.setPath("index");
            childMenuVo.setName(parentMenuVo.getName());
            childMenuVo.setComponent(parentMenuVo.getComponent());
        } else {
            childMenuVo.setPath(menuDTO.getPath());
        }
        
        return childMenuVo;
    }
}
