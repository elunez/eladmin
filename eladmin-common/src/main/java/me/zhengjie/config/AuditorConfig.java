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
package me.zhengjie.config;

import me.zhengjie.utils.SecurityUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import java.util.Optional;

/**
 * @description  : Set audit
 * @author  : Dong ZhaoYang
 * @date : 2019/10/28
 */
@Component("auditorAware")
public class AuditorConfig implements AuditorAware<String> {

    /**
     * Return operator identification information
     *
     * @return /
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            // Here you should obtain specific information according to actual business situation
            return Optional.of(SecurityUtils.getCurrentUsername());
        }catch (Exception ignored){}
        // For scheduled tasks or cases where Token is not used
        return Optional.of("System");
    }
}
