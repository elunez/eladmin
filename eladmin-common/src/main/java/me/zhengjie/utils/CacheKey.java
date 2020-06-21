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
 * @author: liaojinlong
 * @date: 2020/6/11 15:49
 * @apiNote: 关于缓存的Key集合
 */
public interface CacheKey {

    /**
     * 内置 用户、岗位、应用、菜单、角色 相关key
     */
    String USER_MODIFY_TIME_KEY = "user:modify:time:key:";
    String APP_MODIFY_TIME_KEY = "app:modify:time:key:";
    String JOB_MODIFY_TIME_KEY = "job:modify:time:key:";
    String MENU_MODIFY_TIME_KEY = "menu:modify:time:key:";
    String ROLE_MODIFY_TIME_KEY = "role:modify:time:key:";
    String DEPT_MODIFY_TIME_KEY = "dept:modify:time:key:";

    /**
     * 用户
     */
    String USER_ID = "user::id:";
    String USER_NAME = "user::username:";
    /**
     * 数据
     */
    String DATE_USER = "data::user:";
    /**
     * 菜单
     */
    String MENU_USER = "menu::user:";
    /**
     * 角色授权
     */
    String ROLE_AUTH = "role::auth:";
    /**
     * 角色信息
     */
    String ROLE_ID = "role::id:";
}
