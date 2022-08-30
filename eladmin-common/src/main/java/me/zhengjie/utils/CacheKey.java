/*
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.zhengjie.utils;

/**
 * 实际上缓存中的记录的Key为 PROJECT + xxx_KEY + :: + ID|NAME + : + 数据
 * <p>
 * 即最终保存的key为 baiKe-user-id-1
 *
 * @author: liaojinlong
 * @date: 2020/6/11 15:49
 * @apiNote: 关于缓存的Key集合
 */
public interface CacheKey {

    /**
     * 项目名称
     */
    String PROJECT = "eladmin-";
    /**
     * 用户部分
     */
    String USER_KEY = "user";
    /**
     * 目录部分
     */
    String MENU_KEY = "menu";
    /**
     * 角色部分
     */
    String ROLE_KEY = "role";
    /**
     * 部门部分
     */
    String DEPT_KEY = "dept";
    /**
     * 岗位
     */
    String JOB_KEY = "job";
    /**
     * 数据部分
     */
    String DATA_KEY = "data";
    /**
     * 鉴权部分
     */
    String AUTH_KEY = "auth";
    /**
     * 数据字典
     */
    String DICT_KEY = "dict";
    /**
     * 支付宝
     */
    String ALI_PAY = "aliPay";
    /**
     * 邮箱
     */
    String EMAIL = "email";
    /**
     * 七牛云
     */
    String QI_NIU = "qiNiu";
    /**
     * 通用ID部分
     */
    String ID = "id";
    /**
     * 通用名称部分
     */
    String NAME = "name";
    /**
     * 配置
     */
    String CONFIG = "config";

    /**
     * 依据传入的 key 拼接为 PROJECT + xx_KEY
     * 例如 baiKe-user
     *
     * @param key xx_KEY
     * @return /
     */
    static String projectAndKey(String key) {
        return String.format(
                "%1$s%2$s",
                PROJECT,
                key
        );
    }

    /**
     * 一般用于手动操作Redis时，拼接存入的Key
     * <p>
     * 依据传入的 key 和 target 拼接为 PROJECT + xx_KEY + target::
     * <p>
     * 例如 返回 baiKe-user::id:，后面自行拼接区分的标识，例如ID值
     *
     * @param key    xx_KEY
     * @param target target
     * @return PROJECT-key::target:
     */
    static String keyAndTarget(String key, String target) {
        return String.format(
                "%1$s::%2$s:",
                projectAndKey(key),
                target
        );
    }
}
