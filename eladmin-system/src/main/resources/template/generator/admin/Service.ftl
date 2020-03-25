package ${package}.service;

import ${package}.domain.${className};
import ${package}.service.dto.${className}Dto;
import ${package}.service.dto.${className}QueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author ${author}
* @date ${date}
*/
public interface ${className}Service {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(${className}QueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<${className}Dto>
    */
    List<${className}Dto> queryAll(${className}QueryCriteria criteria);

    /**
     * 根据ID查询
     * @param ${pkChangeColName} ID
     * @return ${className}Dto
     */
    ${className}Dto findById(${pkColumnType} ${pkChangeColName});

    /**
    * 创建
    * @param resources /
    * @return ${className}Dto
    */
    ${className}Dto create(${className} resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(${className} resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(${pkColumnType}[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<${className}Dto> all, HttpServletResponse response) throws IOException;
}