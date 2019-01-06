package me.zhengjie.common.utils;

import freemarker.template.Configuration;
import java.io.FileOutputStream;

/**
 * @author jie
 * @date 2019-01-03
 */
public class FtlUtil {

    private static Configuration configuration;
    private static FileOutputStream fileOut = null;

    static {
        configuration=new Configuration(Configuration.VERSION_2_3_28);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(FtlUtil.class, "/template/generator");
        configuration.setClassForTemplateLoading(FtlUtil.class, "/template/email");
    }

    public static Configuration getConfig(){
        return configuration;
    }


}
