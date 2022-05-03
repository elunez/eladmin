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
package me.zhengjie.portfolio.tour.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
* @website https://el-admin.vip
* @description /
* @author smk
* @date 2022-05-03
**/
@Data
public class MTourDto implements Serializable {

    private Long id;

    private String name;

    private Date startDate;

    private Integer period;

    private String location;

    private String tourCode;

    private String tourType;

    private String description;

    private HashMap<String, String> extraTourDetail;

    private HashMap<String, String> extraRoomDetail;

    private List<String> images;
}