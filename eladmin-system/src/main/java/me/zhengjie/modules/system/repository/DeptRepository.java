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

import me.zhengjie.modules.system.domain.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Set;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@SuppressWarnings("all")
public interface DeptRepository extends JpaRepository<Dept, Long>, JpaSpecificationExecutor<Dept> {

    /**
     * 根据 PID 查询
     * @param id pid
     * @return /
     */
    List<Dept> findByPid(Long id);

    /**
     * 根据ID查询名称
     * @param id ID
     * @return /
     */
    @Query(value = "select name from sys_dept where dept_id = ?1",nativeQuery = true)
    String findNameById(Long id);

    /**
     * 根据角色ID 查询
     * @param id 角色ID
     * @return /
     */
    Set<Dept> findByRoles_Id(Long id);
}