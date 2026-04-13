package me.zhengjie.utils.gen;

import java.io.File;

public class ApiFilePathStrategy implements FrontFilePathStrategy {

    @Override
    public String getFilePath(String templateName, String apiPath, String path, String apiName) {
        return apiPath + File.separator + apiName + ".js";
    }
}
