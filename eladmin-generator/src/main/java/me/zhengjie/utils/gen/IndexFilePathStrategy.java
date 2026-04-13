package me.zhengjie.utils.gen;

import java.io.File;

public class IndexFilePathStrategy implements FrontFilePathStrategy {

    @Override
    public String getFilePath(String templateName, String apiPath, String path, String apiName) {
        return path + File.separator + "index.vue";
    }
}
