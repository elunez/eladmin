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
package me.zhengjie.utils;

import com.qiniu.storage.Region;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * S3 cloud storage utility class
 * @author Zheng Jie
 * @date 2018-12-31
 */
public class S3Util {

    private static final String HUAD = "East China";

    private static final String HUAB = "North China";

    private static final String HUAN = "South China";

    private static final String BEIM = "North America";

    /**
     * Get the corresponding relationship of the machine room
     * @param zone machine room name
     * @return Region
     */
    public static Region getRegion(String zone){

        if(HUAD.equals(zone)){
            return Region.huadong();
        } else if(HUAB.equals(zone)){
            return Region.huabei();
        } else if(HUAN.equals(zone)){
            return Region.huanan();
        } else if (BEIM.equals(zone)){
            return Region.beimei();
            // Otherwise, it is Southeast Asia
        } else {
            return Region.qvmHuadong();
        }
    }

    /**
     * By default, if no key is specified, the hash value of the file content is used as the file name
     * @param file file name
     * @return String
     */
    public static String getKey(String file){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return FileUtil.getFileNameNoEx(file) + "-" +
                sdf.format(date) +
                "." +
                FileUtil.getExtensionName(file);
    }
}
