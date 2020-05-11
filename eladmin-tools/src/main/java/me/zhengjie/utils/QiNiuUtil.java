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
package me.zhengjie.utils;

import com.qiniu.storage.Region;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 七牛云存储工具类
 * @author Zheng Jie
 * @date 2018-12-31
 */
public class QiNiuUtil {

    private static final String HUAD = "华东";

    private static final String HUAB = "华北";

    private static final String HUAN = "华南";

    private static final String BEIM = "北美";

    /**
     * 得到机房的对应关系
     * @param zone 机房名称
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
            // 否则就是东南亚
        } else {
            return Region.qvmHuadong();
        }
    }

    /**
     * 默认不指定key的情况下，以文件内容的hash值作为文件名
     * @param file 文件名
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
