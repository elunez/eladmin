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
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.service.DataService;
import me.zhengjie.modules.system.service.DeptService;
import me.zhengjie.modules.system.service.RoleService;
import me.zhengjie.modules.system.service.datascope.DataScopeStrategy;
import me.zhengjie.modules.system.service.datascope.DataScopeStrategyFactory;
import me.zhengjie.modules.system.service.dto.RoleSmallDto;
import me.zhengjie.modules.system.service.dto.UserDto;
import me.zhengjie.utils.CacheKey;
import me.zhengjie.utils.RedisUtils;
import me.zhengjie.utils.enums.DataScopeEnum;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Zheng Jie
 * @description 数据权限服务实现
 * @date 2020-05-07
 **/
@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

    private final RedisUtils redisUtils;
    private final RoleService roleService;
    private final DeptService deptService;
    private final DataScopeStrategyFactory dataScopeStrategyFactory;

    /**
     * 用户角色和用户部门改变时需清理缓存
     * @param user /
     * @return /
     */
    @Override
    public List<Long> getDeptIds(UserDto user) {
        String key = CacheKey.DATA_USER + user.getId();
        List<Long> ids = redisUtils.getList(key, Long.class);
        if (CollUtil.isEmpty(ids)) {
            Set<Long> deptIds = new HashSet<>();
            List<RoleSmallDto> roleSet = roleService.findByUsersId(user.getId());
            for (RoleSmallDto role : roleSet) {
                DataScopeEnum dataScopeEnum = DataScopeEnum.find(role.getDataScope());
                DataScopeStrategy strategy = dataScopeStrategyFactory.getStrategy(Objects.requireNonNull(dataScopeEnum));
                if (strategy != null) {
                    strategy.apply(deptIds, user, role);
                }
            }
            ids = new ArrayList<>(deptIds);
            redisUtils.set(key, ids, 1, TimeUnit.DAYS);
        }
        return new ArrayList<>(ids);
    }
}
