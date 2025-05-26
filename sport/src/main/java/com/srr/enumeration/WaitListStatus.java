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
package com.srr.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing the possible states of a wait list entry
 * @author Chanheng
 * @date 2025-05-26
 */
@Getter
@AllArgsConstructor
public enum WaitListStatus {
    
    WAITING("Waiting"),
    PROMOTED("Promoted to participant"),
    CANCELLED("Cancelled by player"),
    EXPIRED("Expired due to event start");
    
    private final String description;
}
