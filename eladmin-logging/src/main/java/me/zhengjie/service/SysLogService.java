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
package me.zhengjie.service;

import me.zhengjie.domain.SysLog;
import me.zhengjie.service.dto.SysLogQueryCriteria;
import me.zhengjie.service.dto.SysLogSmallDto;
import me.zhengjie.utils.PageResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Zheng Jie
 * @date 2018-11-24
 */
public interface SysLogService {

    /**
     * Paginated query
     * @param criteria Query criteria
     * @param pageable Pagination parameters
     * @return /
     */
    Object queryAll(SysLogQueryCriteria criteria, Pageable pageable);

    /**
     * Query all data
     * @param criteria Query criteria
     * @return /
     */
    List<SysLog> queryAll(SysLogQueryCriteria criteria);

    /**
     * Query user logs
     * @param criteria Query criteria
     * @param pageable Pagination parameters
     * @return /
     */
    PageResult<SysLogSmallDto> queryAllByUser(SysLogQueryCriteria criteria, Pageable pageable);

    /**
     * Save log data
     * @param username User
     * @param browser Browser
     * @param ip Request IP
     * @param joinPoint /
     * @param sysLog Log entity
     */
    @Async
    void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, SysLog sysLog);

    /**
     * Query exception details
     * @param id Log ID
     * @return Object
     */
    Object findByErrDetail(Long id);

    /**
     * Export logs
     * @param sysLogs Data to export
     * @param response /
     * @throws IOException /
     */
    void download(List<SysLog> sysLogs, HttpServletResponse response) throws IOException;

    /**
     * Delete all error logs
     */
    void delAllByError();

    /**
     * Delete all INFO logs
     */
    void delAllByInfo();
}
