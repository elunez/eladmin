package me.zhengjie.modules.system.service.strategy;

import me.zhengjie.modules.system.domain.vo.MenuVo;
import me.zhengjie.modules.system.service.dto.MenuDto;

public interface MenuBuildStrategy {
    
    boolean supports(MenuDto menuDto);
    
    void buildComponent(MenuVo menuVo, MenuDto menuDto);
    
    void buildChildren(MenuVo menuVo, MenuDto menuDto, MenuBuildStrategyContext context);
}
