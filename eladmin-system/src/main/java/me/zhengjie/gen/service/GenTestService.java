package me.zhengjie.gen.service;

import me.zhengjie.gen.domain.GenTest;
import me.zhengjie.gen.service.dto.GenTestDto;
import me.zhengjie.gen.service.dto.GenTestQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author Zheng Jie
* @date 2019-11-25
*/
public interface GenTestService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(GenTestQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<GenTestDto>
    */
    List<GenTestDto> queryAll(GenTestQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return GenTestDto
     */
    GenTestDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return GenTestDto
    */
    GenTestDto create(GenTest resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(GenTest resources);

    /**
    * 删除
    * @param id /
    */
    void delete(Long id);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<GenTestDto> all, HttpServletResponse response) throws IOException;
}