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

import me.zhengjie.annotation.rest.AnonymousAccess;
import me.zhengjie.utils.enums.RequestMethodEnum;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * @author Zheng Jie
 * @description 匿名标记工具
 * @date 2025-01-13
 **/
public class AnonTagUtils {

    /**
     * 获取匿名标记的URL
     * @param applicationContext /
     * @return /
     */
    public static Map<String, Set<String>> getAnonymousUrl(ApplicationContext applicationContext){
        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) applicationContext.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
        Map<String, Set<String>> anonymousUrls = new HashMap<>(8);
        // 获取匿名标记
        Set<String> get = new HashSet<>();
        Set<String> post = new HashSet<>();
        Set<String> put = new HashSet<>();
        Set<String> patch = new HashSet<>();
        Set<String> delete = new HashSet<>();
        Set<String> all = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
            if (null != anonymousAccess) {
                List<RequestMethod> requestMethods = new ArrayList<>(infoEntry.getKey().getMethodsCondition().getMethods());
                RequestMethodEnum request = RequestMethodEnum.find(requestMethods.isEmpty() ? RequestMethodEnum.ALL.getType() : requestMethods.get(0).name());
                if (infoEntry.getKey().getPatternsCondition()!=null) {
                    switch (Objects.requireNonNull(request)) {
                        case GET:
                            get.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                            break;
                        case POST:
                            post.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                            break;
                        case PUT:
                            put.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                            break;
                        case PATCH:
                            patch.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                            break;
                        case DELETE:
                            delete.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                            break;
                        default:
                            all.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                            break;
                    }
                }
            }
        }
        anonymousUrls.put(RequestMethodEnum.GET.getType(), get);
        anonymousUrls.put(RequestMethodEnum.POST.getType(), post);
        anonymousUrls.put(RequestMethodEnum.PUT.getType(), put);
        anonymousUrls.put(RequestMethodEnum.PATCH.getType(), patch);
        anonymousUrls.put(RequestMethodEnum.DELETE.getType(), delete);
        anonymousUrls.put(RequestMethodEnum.ALL.getType(), all);
        return anonymousUrls;
    }

    /**
     * 获取所有匿名标记的URL
     * @param applicationContext /
     * @return /
     */
    public static Set<String> getAllAnonymousUrl(ApplicationContext applicationContext){
        Set<String> allUrl = new HashSet<>();
        Map<String, Set<String>> anonymousUrls = getAnonymousUrl(applicationContext);
        for (String key : anonymousUrls.keySet()) {
            allUrl.addAll(anonymousUrls.get(key));
        }
        return allUrl;
    }
}
