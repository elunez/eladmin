package me.zhengjie.utils.gen;

import me.zhengjie.domain.GenConfig;
import org.springframework.util.ObjectUtils;

import java.io.File;

public class ServiceImplFilePathStrategy implements FilePathStrategy {

    @Override
    public String getFilePath(String templateName, GenConfig genConfig, String className, String rootPath) {
        String projectPath = rootPath + File.separator + genConfig.getModuleName();
        String packagePath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator;
        if (!ObjectUtils.isEmpty(genConfig.getPack())) {
            packagePath += genConfig.getPack().replace(".", File.separator) + File.separator;
        }
        return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
    }
}
