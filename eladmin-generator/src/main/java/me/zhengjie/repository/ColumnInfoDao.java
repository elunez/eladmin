/*
 *  Copyright 2019-2020
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
package me.zhengjie.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import me.zhengjie.base.mybatis.BaseDao;
import me.zhengjie.domain.ColumnInfo;
import me.zhengjie.repository.jpa.ColumnInfoRepository;
import me.zhengjie.repository.mp.ColumnInfoService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * JPA 与Mybatis 适配DAO
 *
 * @author liaojinlong
 * @since 2020/6/28 14:59
 */
@Component
public class ColumnInfoDao extends BaseDao<ColumnInfoService, ColumnInfoRepository, ColumnInfo, Long> {

    public ColumnInfoDao(ColumnInfoService baseService, ColumnInfoRepository jpaRepository) {
        super(baseService, jpaRepository);
    }

    public List<ColumnInfo> findByTableNameOrderByIdAsc(String tableName) {
        if (dbSwitch) {
            return jpaRepository.findByTableNameOrderByIdAsc(tableName);
        } else {
            return mpService
                    .list(Wrappers.<ColumnInfo>query().eq(true, "TABLE_NAME", tableName));
        }
    }

}
