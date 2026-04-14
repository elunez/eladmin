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

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.template.*;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.domain.GenConfig;
import me.zhengjie.domain.ColumnInfo;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.*;

import static me.zhengjie.utils.FileUtil.SYS_TEM_DIR;

/**
 * 代码生成
 *
 * @author Zheng Jie
 * @date 2019-01-02
 */
@Slf4j
@SuppressWarnings({"unchecked", "all"})
public class GenUtil {

    private static final String TIMESTAMP = "Timestamp";

    private static final String BIGDECIMAL = "BigDecimal";

    public static final String PK = "PRI";

    public static final String EXTRA = "auto_increment";

    /**
     * 获取后端代码模板名称
     *
     * @return List
     */
    private static List<String> getAdminTemplateNames() {
        List<String> templateNames = new ArrayList<>();
        templateNames.add("Entity");
        templateNames.add("Dto");
        templateNames.add("Mapper");
        templateNames.add("Controller");
        templateNames.add("QueryCriteria");
        templateNames.add("Service");
        templateNames.add("ServiceImpl");
        templateNames.add("Repository");
        return templateNames;
    }

    /**
     * 获取前端代码模板名称
     *
     * @return List
     */
    private static List<String> getFrontTemplateNames() {
        List<String> templateNames = new ArrayList<>();
        templateNames.add("index");
        templateNames.add("api");
        return templateNames;
    }

    public static List<Map<String, Object>> preview(List<ColumnInfo> columns, GenConfig genConfig) {
        Map<String, Object> genMap = getGenMap(columns, genConfig);
        List<Map<String, Object>> genList = new ArrayList<>();
        TemplateEngine engine = createTemplateEngine();
        processAdminTemplatesPreview(genMap, genList, engine);
        processFrontTemplatesPreview(genMap, genList, engine);
        return genList;
    }

    private static TemplateEngine createTemplateEngine() {
        return TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
    }

    private static void processAdminTemplatesPreview(Map<String, Object> genMap, List<Map<String, Object>> genList, TemplateEngine engine) {
        for (String templateName : getAdminTemplateNames()) {
            Template template = engine.getTemplate("admin/" + templateName + ".ftl");
            Map<String, Object> map = createPreviewMap(template.render(genMap), templateName);
            genList.add(map);
        }
    }

    private static void processFrontTemplatesPreview(Map<String, Object> genMap, List<Map<String, Object>> genList, TemplateEngine engine) {
        for (String templateName : getFrontTemplateNames()) {
            Template template = engine.getTemplate("front/" + templateName + ".ftl");
            Map<String, Object> map = createFrontPreviewMap(template.render(genMap), templateName);
            genList.add(map);
        }
    }

    private static Map<String, Object> createPreviewMap(String content, String templateName) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("content", content);
        map.put("name", templateName);
        return map;
    }

    private static Map<String, Object> createFrontPreviewMap(String content, String templateName) {
        Map<String, Object> map = new HashMap<>(3);
        map.put(templateName, content);
        map.put("content", content);
        map.put("name", templateName);
        return map;
    }

    public static String download(List<ColumnInfo> columns, GenConfig genConfig) throws IOException {
        String tempPath = SYS_TEM_DIR + "eladmin-gen-temp" + File.separator + genConfig.getTableName() + File.separator;
        Map<String, Object> genMap = getGenMap(columns, genConfig);
        TemplateEngine engine = createTemplateEngine();
        generateAdminCodeForDownload(genConfig, genMap, engine, tempPath);
        generateFrontCodeForDownload(genConfig, genMap, engine, tempPath);
        return tempPath;
    }

    private static void generateAdminCodeForDownload(GenConfig genConfig, Map<String, Object> genMap, TemplateEngine engine, String tempPath) throws IOException {
        String basePath = tempPath + "eladmin" + File.separator;
        String className = genMap.get("className").toString();
        for (String templateName : getAdminTemplateNames()) {
            Template template = engine.getTemplate("admin/" + templateName + ".ftl");
            String filePath = getAdminFilePath(templateName, genConfig, className, basePath);
            generateSingleFile(genConfig, genMap, template, filePath);
        }
    }

    private static void generateFrontCodeForDownload(GenConfig genConfig, Map<String, Object> genMap, TemplateEngine engine, String tempPath) throws IOException {
        String changeClassName = genMap.get("changeClassName").toString();
        String path = tempPath + "eladmin-web" + File.separator;
        String apiPath = path + "src" + File.separator + "api" + File.separator;
        String srcPath = path + "src" + File.separator + "views" + File.separator + changeClassName + File.separator;
        for (String templateName : getFrontTemplateNames()) {
            Template template = engine.getTemplate("front/" + templateName + ".ftl");
            String filePath = getFrontFilePath(templateName, apiPath, srcPath, changeClassName);
            generateSingleFile(genConfig, genMap, template, filePath);
        }
    }

    public static void generatorCode(List<ColumnInfo> columnInfos, GenConfig genConfig) throws IOException {
        Map<String, Object> genMap = getGenMap(columnInfos, genConfig);
        TemplateEngine engine = createTemplateEngine();
        generateAdminCode(genConfig, genMap, engine);
        generateFrontCode(genConfig, genMap, engine);
    }

    private static void generateAdminCode(GenConfig genConfig, Map<String, Object> genMap, TemplateEngine engine) throws IOException {
        String rootPath = System.getProperty("user.dir");
        String className = genMap.get("className").toString();
        for (String templateName : getAdminTemplateNames()) {
            Template template = engine.getTemplate("admin/" + templateName + ".ftl");
            String filePath = getAdminFilePath(templateName, genConfig, className, rootPath);
            generateSingleFile(genConfig, genMap, template, filePath);
        }
    }

    private static void generateFrontCode(GenConfig genConfig, Map<String, Object> genMap, TemplateEngine engine) throws IOException {
        String changeClassName = genMap.get("changeClassName").toString();
        for (String templateName : getFrontTemplateNames()) {
            Template template = engine.getTemplate("front/" + templateName + ".ftl");
            String filePath = getFrontFilePath(templateName, genConfig.getApiPath(), genConfig.getPath(), changeClassName);
            generateSingleFile(genConfig, genMap, template, filePath);
        }
    }

    private static void generateSingleFile(GenConfig genConfig, Map<String, Object> genMap, Template template, String filePath) throws IOException {
        assert filePath != null;
        File file = new File(filePath);
        if (!genConfig.getCover() && FileUtil.exist(file)) {
            return;
        }
        genFile(file, template, genMap);
    }

    private static Map<String, Object> getGenMap(List<ColumnInfo> columnInfos, GenConfig genConfig) {
        Map<String, Object> genMap = new HashMap<>(16);
        initBasicConfig(genMap, genConfig);
        processClassNames(genMap, genConfig);
        initFlags(genMap);
        ColumnProcessContext context = initProcessContext();
        processColumns(columnInfos, genMap, context);
        putCollectionsToGenMap(genMap, context);
        return genMap;
    }

    private static void initBasicConfig(Map<String, Object> genMap, GenConfig genConfig) {
        genMap.put("apiAlias", genConfig.getApiAlias());
        genMap.put("package", genConfig.getPack());
        genMap.put("moduleName", genConfig.getModuleName());
        genMap.put("author", genConfig.getAuthor());
        genMap.put("date", LocalDate.now().toString());
        genMap.put("tableName", genConfig.getTableName());
    }

    private static void processClassNames(Map<String, Object> genMap, GenConfig genConfig) {
        String tableName = genConfig.getTableName();
        String className = StringUtils.toCapitalizeCamelCase(tableName);
        String changeClassName = StringUtils.toCamelCase(tableName);
        if (StringUtils.isNotEmpty(genConfig.getPrefix())) {
            String nameWithoutPrefix = StrUtil.removePrefix(tableName, genConfig.getPrefix());
            className = StringUtils.toCapitalizeCamelCase(nameWithoutPrefix);
            changeClassName = StringUtils.toCamelCase(nameWithoutPrefix);
            changeClassName = StringUtils.uncapitalize(changeClassName);
        }
        genMap.put("className", className);
        genMap.put("changeClassName", changeClassName);
    }

    private static void initFlags(Map<String, Object> genMap) {
        genMap.put("hasTimestamp", false);
        genMap.put("queryHasTimestamp", false);
        genMap.put("hasBigDecimal", false);
        genMap.put("queryHasBigDecimal", false);
        genMap.put("hasQuery", false);
        genMap.put("auto", false);
        genMap.put("hasDict", false);
        genMap.put("hasDateAnnotation", false);
    }

    private static ColumnProcessContext initProcessContext() {
        return new ColumnProcessContext();
    }

    private static void processColumns(List<ColumnInfo> columnInfos, Map<String, Object> genMap, ColumnProcessContext context) {
        for (ColumnInfo column : columnInfos) {
            processSingleColumn(column, genMap, context);
        }
    }

    private static void processSingleColumn(ColumnInfo column, Map<String, Object> genMap, ColumnProcessContext context) {
        Map<String, Object> listMap = new HashMap<>(16);
        String colType = ColUtil.cloToJava(column.getColumnType());
        String changeColumnName = StringUtils.toCamelCase(column.getColumnName());
        String capitalColumnName = StringUtils.toCapitalizeCamelCase(column.getColumnName());
        processPrimaryKey(column, genMap, colType, changeColumnName, capitalColumnName);
        updateColumnFlags(genMap, colType, column, context.dicts);
        fillColumnMap(listMap, column, colType, changeColumnName, capitalColumnName);
        processDateAnnotation(genMap, column);
        processNotNullColumn(context.isNotNullColumns, listMap, column.getNotNull());
        processQueryType(column, genMap, colType, listMap, context.queryColumns, context.betweens);
        context.columns.add(listMap);
    }

    private static void processPrimaryKey(ColumnInfo column, Map<String, Object> genMap, String colType, String changeColumnName, String capitalColumnName) {
        if (PK.equals(column.getKeyType())) {
            genMap.put("pkColumnType", colType);
            genMap.put("pkChangeColName", changeColumnName);
            genMap.put("pkCapitalColName", capitalColumnName);
        }
    }

    private static void updateColumnFlags(Map<String, Object> genMap, String colType, ColumnInfo column, List<String> dicts) {
        if (TIMESTAMP.equals(colType)) {
            genMap.put("hasTimestamp", true);
        }
        if (BIGDECIMAL.equals(colType)) {
            genMap.put("hasBigDecimal", true);
        }
        if (EXTRA.equals(column.getExtra())) {
            genMap.put("auto", true);
        }
        if (StringUtils.isNotBlank(column.getDictName())) {
            genMap.put("hasDict", true);
            if (!dicts.contains(column.getDictName())) {
                dicts.add(column.getDictName());
            }
        }
    }

    private static void fillColumnMap(Map<String, Object> listMap, ColumnInfo column, String colType, String changeColumnName, String capitalColumnName) {
        listMap.put("remark", column.getRemark());
        listMap.put("columnKey", column.getKeyType());
        listMap.put("columnType", colType);
        listMap.put("columnName", column.getColumnName());
        listMap.put("istNotNull", column.getNotNull());
        listMap.put("columnShow", column.getListShow());
        listMap.put("formShow", column.getFormShow());
        listMap.put("formType", StringUtils.isNotBlank(column.getFormType()) ? column.getFormType() : "Input");
        listMap.put("changeColumnName", changeColumnName);
        listMap.put("capitalColumnName", capitalColumnName);
        listMap.put("dictName", column.getDictName());
        listMap.put("dateAnnotation", column.getDateAnnotation());
    }

    private static void processDateAnnotation(Map<String, Object> genMap, ColumnInfo column) {
        if (StringUtils.isNotBlank(column.getDateAnnotation())) {
            genMap.put("hasDateAnnotation", true);
        }
    }

    private static void processNotNullColumn(List<Map<String, Object>> isNotNullColumns, Map<String, Object> listMap, boolean notNull) {
        if (notNull) {
            isNotNullColumns.add(listMap);
        }
    }

    private static void processQueryType(ColumnInfo column, Map<String, Object> genMap, String colType, Map<String, Object> listMap, List<Map<String, Object>> queryColumns, List<Map<String, Object>> betweens) {
        if (!StringUtils.isBlank(column.getQueryType())) {
            listMap.put("queryType", column.getQueryType());
            genMap.put("hasQuery", true);
            updateQueryTypeFlags(genMap, colType);
            if ("between".equalsIgnoreCase(column.getQueryType())) {
                betweens.add(listMap);
            } else {
                queryColumns.add(listMap);
            }
        }
    }

    private static void updateQueryTypeFlags(Map<String, Object> genMap, String colType) {
        if (TIMESTAMP.equals(colType)) {
            genMap.put("queryHasTimestamp", true);
        }
        if (BIGDECIMAL.equals(colType)) {
            genMap.put("queryHasBigDecimal", true);
        }
    }

    private static void putCollectionsToGenMap(Map<String, Object> genMap, ColumnProcessContext context) {
        genMap.put("columns", context.columns);
        genMap.put("queryColumns", context.queryColumns);
        genMap.put("dicts", context.dicts);
        genMap.put("betweens", context.betweens);
        genMap.put("isNotNullColumns", context.isNotNullColumns);
    }

    private static class ColumnProcessContext {
        List<Map<String, Object>> columns = new ArrayList<>();
        List<Map<String, Object>> queryColumns = new ArrayList<>();
        List<String> dicts = new ArrayList<>();
        List<Map<String, Object>> betweens = new ArrayList<>();
        List<Map<String, Object>> isNotNullColumns = new ArrayList<>();
    }

    /**
     * 定义后端文件路径以及名称
     */
    private static String getAdminFilePath(String templateName, GenConfig genConfig, String className, String rootPath) {
        String projectPath = rootPath + File.separator + genConfig.getModuleName();
        String packagePath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator;
        if (!ObjectUtils.isEmpty(genConfig.getPack())) {
            packagePath += genConfig.getPack().replace(".", File.separator) + File.separator;
        }

        if ("Entity".equals(templateName)) {
            return packagePath + "domain" + File.separator + className + ".java";
        }

        if ("Controller".equals(templateName)) {
            return packagePath + "rest" + File.separator + className + "Controller.java";
        }

        if ("Service".equals(templateName)) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if ("ServiceImpl".equals(templateName)) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if ("Dto".equals(templateName)) {
            return packagePath + "service" + File.separator + "dto" + File.separator + className + "Dto.java";
        }

        if ("QueryCriteria".equals(templateName)) {
            return packagePath + "service" + File.separator + "dto" + File.separator + className + "QueryCriteria.java";
        }

        if ("Mapper".equals(templateName)) {
            return packagePath + "service" + File.separator + "mapstruct" + File.separator + className + "Mapper.java";
        }

        if ("Repository".equals(templateName)) {
            return packagePath + "repository" + File.separator + className + "Repository.java";
        }

        return null;
    }

    /**
     * 定义前端文件路径以及名称
     */
    private static String getFrontFilePath(String templateName, String apiPath, String path, String apiName) {

        if ("api".equals(templateName)) {
            return apiPath + File.separator + apiName + ".js";
        }

        if ("index".equals(templateName)) {
            return path + File.separator + "index.vue";
        }

        return null;
    }

    private static void genFile(File file, Template template, Map<String, Object> map) throws IOException {
        // 生成目标文件
        Writer writer = null;
        try {
            FileUtil.touch(file);
            writer = new FileWriter(file);
            template.render(map, writer);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            assert writer != null;
            writer.close();
        }
    }
}
