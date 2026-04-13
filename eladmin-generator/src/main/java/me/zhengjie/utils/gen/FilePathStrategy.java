package me.zhengjie.utils.gen;

import me.zhengjie.domain.GenConfig;

import java.io.File;

public interface FilePathStrategy {

    String getFilePath(String templateName, GenConfig genConfig, String className, String rootPath);
}
