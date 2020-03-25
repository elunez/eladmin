package me.zhengjie.modules.monitor.service;

import me.zhengjie.modules.monitor.domain.Server;
import me.zhengjie.modules.monitor.service.dto.ServerDTO;
import me.zhengjie.modules.monitor.service.dto.ServerQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* @author Zhang houying
* @date 2019-11-03
*/
public interface ServerService {

    /**
    * 查询数据分页
    * @param criteria 条件参数
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(ServerQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<ServerDTO>
    */
    List<ServerDTO> queryAll(ServerQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return ServerDTO
     */
    ServerDTO findById(Integer id);

    /**
     * 创建服务监控
     * @param resources /
     * @return /
     */
    ServerDTO create(Server resources);

    /**
     * 编辑服务监控
     * @param resources /
     */
    void update(Server resources);

    /**
     * 删除
     * @param id /
     */
    void delete(Set<Integer> id);

}
