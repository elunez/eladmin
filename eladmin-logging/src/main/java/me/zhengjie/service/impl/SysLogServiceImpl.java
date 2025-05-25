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
package me.zhengjie.service.impl;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import me.zhengjie.domain.SysLog;
import me.zhengjie.repository.LogRepository;
import me.zhengjie.service.SysLogService;
import me.zhengjie.service.dto.SysLogQueryCriteria;
import me.zhengjie.service.dto.SysLogSmallDto;
import me.zhengjie.service.mapstruct.LogErrorMapper;
import me.zhengjie.service.mapstruct.LogSmallMapper;
import me.zhengjie.utils.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author Zheng Jie
 * @date 2018-11-24
 */
@Service
@RequiredArgsConstructor
public class SysLogServiceImpl implements SysLogService {

    private final LogRepository logRepository;
    private final LogErrorMapper logErrorMapper;
    private final LogSmallMapper logSmallMapper;
    // Define sensitive field constant array
    private static final String[] SENSITIVE_KEYS = {"password"};

    @Override
    public Object queryAll(SysLogQueryCriteria criteria, Pageable pageable) {
        Page<SysLog> page = logRepository.findAll(((root, criteriaQuery, cb) -> QueryHelp.getPredicate(root, criteria, cb)), pageable);
        String status = "ERROR";
        if (status.equals(criteria.getLogType())) {
            return PageUtil.toPage(page.map(logErrorMapper::toDto));
        }
        return PageUtil.toPage(page);
    }

    @Override
    public List<SysLog> queryAll(SysLogQueryCriteria criteria) {
        return logRepository.findAll(((root, criteriaQuery, cb) -> QueryHelp.getPredicate(root, criteria, cb)));
    }

    @Override
    public PageResult<SysLogSmallDto> queryAllByUser(SysLogQueryCriteria criteria, Pageable pageable) {
        Page<SysLog> page = logRepository.findAll(((root, criteriaQuery, cb) -> QueryHelp.getPredicate(root, criteria, cb)), pageable);
        return PageUtil.toPage(page.map(logSmallMapper::toDto));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, SysLog sysLog) {
        if (sysLog == null) {
            throw new IllegalArgumentException("Log cannot be null!");
        }

        // Get method signature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        me.zhengjie.annotation.Log aopLog = method.getAnnotation(me.zhengjie.annotation.Log.class);

        // Method path
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";

        // Get parameters
        JSONObject params = getParameter(method, joinPoint.getArgs());

        // Fill in basic information
        sysLog.setRequestIp(ip);
        sysLog.setAddress(StringUtils.getCityInfo(sysLog.getRequestIp()));
        sysLog.setMethod(methodName);
        sysLog.setUsername(username);
        sysLog.setParams(JSON.toJSONString(params));
        sysLog.setBrowser(browser);
        sysLog.setDescription(aopLog.value());

        // If the username is not obtained, try to get it from the parameters
        if(StringUtils.isBlank(sysLog.getUsername())){
            sysLog.setUsername(params.getString("username"));
        }

        // Save
        logRepository.save(sysLog);
    }

    /**
     * Get request parameters based on method and input parameters
     */
    private JSONObject getParameter(Method method, Object[] args) {
        JSONObject params = new JSONObject();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            // Filter out MultiPartFile
            if (args[i] instanceof MultipartFile) {
                continue;
            }
            // Filter out HttpServletResponse
            if (args[i] instanceof HttpServletResponse) {
                continue;
            }
            // Filter out HttpServletRequest
            if (args[i] instanceof HttpServletRequest) {
                continue;
            }
            // Use parameters annotated with RequestBody as request parameters
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                params.putAll((JSONObject) JSON.toJSON(args[i]));
            } else {
                String key = parameters[i].getName();
                params.put(key, args[i]);
            }
        }
        // Traverse sensitive field array and replace value
        Set<String> keys = params.keySet();
        for (String key : SENSITIVE_KEYS) {
            if (keys.contains(key)) {
                params.put(key, "******");
            }
        }
        // Return parameters
        return params;
    }

    @Override
    public Object findByErrDetail(Long id) {
        SysLog sysLog = logRepository.findById(id).orElseGet(SysLog::new);
        ValidationUtil.isNull(sysLog.getId(), "Log", "id", id);
        byte[] details = sysLog.getExceptionDetail();
        return Dict.create().set("exception", new String(ObjectUtil.isNotNull(details) ? details : "".getBytes()));
    }

    @Override
    public void download(List<SysLog> sysLogs, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SysLog sysLog : sysLogs) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("Username", sysLog.getUsername());
            map.put("IP", sysLog.getRequestIp());
            map.put("IP Source", sysLog.getAddress());
            map.put("Description", sysLog.getDescription());
            map.put("Browser", sysLog.getBrowser());
            map.put("Request Time/ms", sysLog.getTime());
            map.put("Exception Details", new String(ObjectUtil.isNotNull(sysLog.getExceptionDetail()) ? sysLog.getExceptionDetail() : "".getBytes()));
            map.put("Creation Date", sysLog.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByError() {
        logRepository.deleteByLogType("ERROR");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByInfo() {
        logRepository.deleteByLogType("INFO");
    }
}
