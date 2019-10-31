package ${package}.service;

import ${package}.domain.${className};
import ${package}.service.dto.${className}DTO;
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
    * @param criteria 条件参数
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(${className}QueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<${className}DTO>
    */
    List<${className}DTO> queryAll(${className}QueryCriteria criteria);

    /**
     * 根据ID查询
     * @param ${pkChangeColName} ID
     * @return ${className}DTO
     */
    ${className}DTO findById(${pkColumnType} ${pkChangeColName});

    ${className}DTO create(${className} resources);

    void update(${className} resources);

    void delete(${pkColumnType} ${pkChangeColName});

    void download(List<${className}DTO> all, HttpServletResponse response) throws IOException;
}