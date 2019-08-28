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

    public static final String HUAD = "华东";

    public static final String HUAB = "华北";

    public static final String HUAN = "华南";

    public static final String BEIM = "北美";

    /**
     * 得到机房的对应关系
     * @param zone
     * @return
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
     * @param file
     * @return
     */
    public static String getKey(String file){
        StringBuffer key = new StringBuffer(FileUtil.getFileNameNoEx(file));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        key.append("-");
        key.append(sdf.format(date));
        key.append(".");
        key.append(FileUtil.getExtensionName(file));
        return key.toString();
    }
}
