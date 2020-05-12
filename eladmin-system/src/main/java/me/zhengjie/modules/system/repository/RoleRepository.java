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

import me.zhengjie.modules.system.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
@SuppressWarnings("all")
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    /**
     * 根据名称查询
     * @param name /
     * @return /
     */
    Role findByName(String name);

    /**
     * 根据用户ID查询
     * @param id 用户ID
     * @return
     */
    Set<Role> findByUsers_Id(Long id);

    /**
     * 解绑角色菜单
     * @param id 菜单ID
     */
    @Modifying
    @Query(value = "delete from sys_roles_menus where menu_id = ?1",nativeQuery = true)
    void untiedMenu(Long id);
}
