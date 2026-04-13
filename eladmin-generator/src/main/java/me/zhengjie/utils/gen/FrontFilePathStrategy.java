package me.zhengjie.utils.gen;

import java.io.File;

public interface FrontFilePathStrategy {

    String getFilePath(String templateName, String apiPath, String path, String apiName);
}
