/*
 *  Copyright 2019-2020 Zheng Jie
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
package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2018-12-17
 */
@SuppressWarnings("all")
public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {

    /**
     * 根据菜单标题查询
     * @param title 菜单标题
     * @return /
     */
    Menu findByTitle(String title);

    /**
     * 根据组件名称查询
     * @param name 组件名称
     * @return /
     */
    Menu findByComponentName(String name);

    /**
     * 根据菜单的 PID 查询
     * @param pid /
     * @return /
     */
    List<Menu> findByPid(long pid);

    /**
     * 查询顶级菜单
     * @return /
     */
    List<Menu> findByPidIsNull();

    /**
     * 根据角色ID与菜单类型查询菜单
     * @param roleIds roleIDs
     * @param type 类型
     * @return /
     */
    LinkedHashSet<Menu> findByRoles_IdInAndTypeNotOrderByMenuSortAsc(Set<Long> roleIds, int type);

    /**
     * 获取节点数量
     * @param id /
     * @return
     */
    int countByPid(Long id);
}
