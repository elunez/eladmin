package me.zhengjie.modules.system.service.strategy;

import me.zhengjie.modules.system.domain.vo.MenuVo;
import me.zhengjie.modules.system.service.dto.MenuDto;
import me.zhengjie.utils.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class MenuComponentStrategy {
    
    public String getComponent(MenuDto menuDto) {
        if (menuDto.getPid() == null) {
            return getRootComponent(menuDto);
        } else {
            return getChildComponent(menuDto);
        }
    }
    
    private String getRootComponent(MenuDto menuDto) {
        if (StringUtils.isEmpty(menuDto.getComponent())) {
            return "Layout";
        }
        return menuDto.getComponent();
    }
    
    private String getChildComponent(MenuDto menuDto) {
        Integer type = menuDto.getType();
        if (type == null || type == 0) {
            return StringUtils.isEmpty(menuDto.getComponent()) ? "ParentView" : menuDto.getComponent();
        }
        if (StringUtils.isNoneBlank(menuDto.getComponent())) {
            return menuDto.getComponent();
        }
        return null;
    }
    
    public boolean isRootMenu(MenuDto menuDto) {
        return menuDto.getPid() == null;
    }
}
