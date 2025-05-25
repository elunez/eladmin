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
 * Code generation
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
     * Get backend code template name
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
     * Get frontend code template name
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
        // Get backend template
        List<String> templates = getAdminTemplateNames();
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
        for (String templateName : templates) {
            Map<String, Object> map = new HashMap<>(1);
            Template template = engine.getTemplate("admin/" + templateName + ".ftl");
            map.put("content", template.render(genMap));
            map.put("name", templateName);
            genList.add(map);
        }
        // Get frontend template
        templates = getFrontTemplateNames();
        for (String templateName : templates) {
            Map<String, Object> map = new HashMap<>(1);
            Template template = engine.getTemplate("front/" + templateName + ".ftl");
            map.put(templateName, template.render(genMap));
            map.put("content", template.render(genMap));
            map.put("name", templateName);
            genList.add(map);
        }
        return genList;
    }

    public static String download(List<ColumnInfo> columns, GenConfig genConfig) throws IOException {
        // Concatenated path: /tmpeladmin-gen-temp/. This path requires root privileges to create on Linux, non-root users will encounter permission errors and fail. Change to: /tmp/eladmin-gen-temp/
        // String tempPath =SYS_TEM_DIR + "eladmin-gen-temp" + File.separator + genConfig.getTableName() + File.separator;
        String tempPath = SYS_TEM_DIR + "eladmin-gen-temp" + File.separator + genConfig.getTableName() + File.separator;
        Map<String, Object> genMap = getGenMap(columns, genConfig);
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
        // Generate backend code
        List<String> templates = getAdminTemplateNames();
        for (String templateName : templates) {
            Template template = engine.getTemplate("admin/" + templateName + ".ftl");
            String filePath = getAdminFilePath(templateName, genConfig, genMap.get("className").toString(), tempPath + "eladmin" + File.separator);
            assert filePath != null;
            File file = new File(filePath);
            // If not overwriting
            if (!genConfig.getCover() && FileUtil.exist(file)) {
                continue;
            }
            // Generate code
            genFile(file, template, genMap);
        }
        // Generate frontend code
        templates = getFrontTemplateNames();
        for (String templateName : templates) {
            Template template = engine.getTemplate("front/" + templateName + ".ftl");
            String path = tempPath + "eladmin-web" + File.separator;
            String apiPath = path + "src" + File.separator + "api" + File.separator;
            String srcPath = path + "src" + File.separator + "views" + File.separator + genMap.get("changeClassName").toString() + File.separator;
            String filePath = getFrontFilePath(templateName, apiPath, srcPath, genMap.get("changeClassName").toString());
            assert filePath != null;
            File file = new File(filePath);
            // If not overwriting
            if (!genConfig.getCover() && FileUtil.exist(file)) {
                continue;
            }
            // Generate code
            genFile(file, template, genMap);
        }
        return tempPath;
    }

    public static void generatorCode(List<ColumnInfo> columnInfos, GenConfig genConfig) throws IOException {
        Map<String, Object> genMap = getGenMap(columnInfos, genConfig);
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
        // Generate backend code
        List<String> templates = getAdminTemplateNames();
        for (String templateName : templates) {
            Template template = engine.getTemplate("admin/" + templateName + ".ftl");
            String rootPath = System.getProperty("user.dir");
            String filePath = getAdminFilePath(templateName, genConfig, genMap.get("className").toString(), rootPath);

            assert filePath != null;
            File file = new File(filePath);

            // If not overwriting
            if (!genConfig.getCover() && FileUtil.exist(file)) {
                continue;
            }
            // Generate code
            genFile(file, template, genMap);
        }

        // Generate frontend code
        templates = getFrontTemplateNames();
        for (String templateName : templates) {
            Template template = engine.getTemplate("front/" + templateName + ".ftl");
            String filePath = getFrontFilePath(templateName, genConfig.getApiPath(), genConfig.getPath(), genMap.get("changeClassName").toString());

            assert filePath != null;
            File file = new File(filePath);

            // If not overwriting
            if (!genConfig.getCover() && FileUtil.exist(file)) {
                continue;
            }
            // Generate code
            genFile(file, template, genMap);
        }
    }

    // Get template data
    private static Map<String, Object> getGenMap(List<ColumnInfo> columnInfos, GenConfig genConfig) {
        // Store template field data
        Map<String, Object> genMap = new HashMap<>(16);
        // Interface alias
        genMap.put("apiAlias", genConfig.getApiAlias());
        // Package name
        genMap.put("package", genConfig.getPack());
        // Module name
        genMap.put("moduleName", genConfig.getModuleName());
        // Author
        genMap.put("author", genConfig.getAuthor());
        // Creation date
        genMap.put("date", LocalDate.now().toString());
        // Table name
        genMap.put("tableName", genConfig.getTableName());
        // Class name starting with uppercase
        String className = StringUtils.toCapitalizeCamelCase(genConfig.getTableName());
        // Class name starting with lowercase
        String changeClassName = StringUtils.toCamelCase(genConfig.getTableName());
        // Determine whether to remove table prefix
        if (StringUtils.isNotEmpty(genConfig.getPrefix())) {
            className = StringUtils.toCapitalizeCamelCase(StrUtil.removePrefix(genConfig.getTableName(), genConfig.getPrefix()));
            changeClassName = StringUtils.toCamelCase(StrUtil.removePrefix(genConfig.getTableName(), genConfig.getPrefix()));
            changeClassName = StringUtils.uncapitalize(changeClassName);
        }
        // Save class name
        genMap.put("className", className);
        // Save lowercase class name
        genMap.put("changeClassName", changeClassName);
        // Timestamp field exists
        genMap.put("hasTimestamp", false);
        // Timestamp field exists in query class
        genMap.put("queryHasTimestamp", false);
        // BigDecimal field exists
        genMap.put("hasBigDecimal", false);
        // BigDecimal field exists in query class
        genMap.put("queryHasBigDecimal", false);
        // Whether to create query
        genMap.put("hasQuery", false);
        // Auto increment primary key
        genMap.put("auto", false);
        // Dictionary exists
        genMap.put("hasDict", false);
        // Date annotation exists
        genMap.put("hasDateAnnotation", false);
        // Save field information
        List<Map<String, Object>> columns = new ArrayList<>();
        // Save query field information
        List<Map<String, Object>> queryColumns = new ArrayList<>();
        // Store dictionary information
        List<String> dicts = new ArrayList<>();
        // Store between information
        List<Map<String, Object>> betweens = new ArrayList<>();
        // Store non-empty field information
        List<Map<String, Object>> isNotNullColumns = new ArrayList<>();

        for (ColumnInfo column : columnInfos) {
            Map<String, Object> listMap = new HashMap<>(16);
            // Field description
            listMap.put("remark", column.getRemark());
            // Field type
            listMap.put("columnKey", column.getKeyType());
            // Primary key type
            String colType = ColUtil.cloToJava(column.getColumnType());
            // Lowercase field name
            String changeColumnName = StringUtils.toCamelCase(column.getColumnName());
            // Uppercase field name
            String capitalColumnName = StringUtils.toCapitalizeCamelCase(column.getColumnName());
            if (PK.equals(column.getKeyType())) {
                // Store primary key type
                genMap.put("pkColumnType", colType);
                // Store lowercase field name
                genMap.put("pkChangeColName", changeColumnName);
                // Store uppercase field name
                genMap.put("pkCapitalColName", capitalColumnName);
            }
            // Whether Timestamp type field exists
            if (TIMESTAMP.equals(colType)) {
                genMap.put("hasTimestamp", true);
            }
            // Whether BigDecimal type field exists
            if (BIGDECIMAL.equals(colType)) {
                genMap.put("hasBigDecimal", true);
            }
            // Primary key is auto increment
            if (EXTRA.equals(column.getExtra())) {
                genMap.put("auto", true);
            }
            // Primary key exists in dictionary
            if (StringUtils.isNotBlank(column.getDictName())) {
                genMap.put("hasDict", true);
                if(!dicts.contains(column.getDictName()))
                    dicts.add(column.getDictName());
            }

            // Store field type
            listMap.put("columnType", colType);
            // Store original field name
            listMap.put("columnName", column.getColumnName());
            // Not empty
            listMap.put("istNotNull", column.getNotNull());
            // Field list display
            listMap.put("columnShow", column.getListShow());
            // Form display
            listMap.put("formShow", column.getFormShow());
            // Form component type
            listMap.put("formType", StringUtils.isNotBlank(column.getFormType()) ? column.getFormType() : "Input");
            // Lowercase field name
            listMap.put("changeColumnName", changeColumnName);
            // Uppercase field name
            listMap.put("capitalColumnName", capitalColumnName);
            // Dictionary name
            listMap.put("dictName", column.getDictName());
            // Date annotation
            listMap.put("dateAnnotation", column.getDateAnnotation());
            if (StringUtils.isNotBlank(column.getDateAnnotation())) {
                genMap.put("hasDateAnnotation", true);
            }
            // Add non-empty field information
            if (column.getNotNull()) {
                isNotNullColumns.add(listMap);
            }
            // Determine whether query exists, if so, set query fields in columnQuery
            if (!StringUtils.isBlank(column.getQueryType())) {
                // Query type
                listMap.put("queryType", column.getQueryType());
                // Whether query exists
                genMap.put("hasQuery", true);
                if (TIMESTAMP.equals(colType)) {
                    // Query stores Timestamp type
                    genMap.put("queryHasTimestamp", true);
                }
                if (BIGDECIMAL.equals(colType)) {
                    // Query stores BigDecimal type
                    genMap.put("queryHasBigDecimal", true);
                }
                if ("between".equalsIgnoreCase(column.getQueryType())) {
                    betweens.add(listMap);
                } else {
                    // Add to query list
                    queryColumns.add(listMap);
                }
            }
            // Add to field list
            columns.add(listMap);
        }
        // Save field list
        genMap.put("columns", columns);
        // Save query list
        genMap.put("queryColumns", queryColumns);
        // Save field list
        genMap.put("dicts", dicts);
        // Save query list
        genMap.put("betweens", betweens);
        // Save non-empty field information
        genMap.put("isNotNullColumns", isNotNullColumns);
        return genMap;
    }

    /**
     * Define backend file path and name
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
     * Define frontend file path and name
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
        // Generate target file
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
