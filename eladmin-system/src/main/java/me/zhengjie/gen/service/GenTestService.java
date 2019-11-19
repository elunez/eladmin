package me.zhengjie.gen.service;

import me.zhengjie.gen.domain.GenTest;
import me.zhengjie.gen.service.dto.GenTestDTO;
import me.zhengjie.gen.service.dto.GenTestQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author Zheng Jie
* @date 2019-11-19
*/
public interface GenTestService {

    /**
    * 查询数据分页
    * @param criteria 条件参数
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(GenTestQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<GenTestDTO>
    */
    List<GenTestDTO> queryAll(GenTestQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return GenTestDTO
     */
    GenTestDTO findById(Long id);

    GenTestDTO create(GenTest resources);

    void update(GenTest resources);

    void delete(Long id);

    void download(List<GenTestDTO> all, HttpServletResponse response) throws IOException;
}