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
package me.zhengjie.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表的数据信息
 * @author Zheng Jie
 * @date 2019-01-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableInfo {

    /** 表名称 */
    private Object tableName;

    /** 创建日期 */
    private Object createTime;

    /** 数据库引擎 */
    private Object engine;

    /** 编码集 */
    private Object coding;

    /** 备注 */
    private Object remark;


}
