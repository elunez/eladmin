package me.zhengjie.modules.mnt.service;

import me.zhengjie.modules.mnt.domain.Deploy;
import me.zhengjie.modules.mnt.domain.DeployHistory;
import me.zhengjie.modules.mnt.service.dto.DeployDto;
import me.zhengjie.modules.mnt.service.dto.DeployQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
* @author zhanghouying
* @date 2019-08-24
*/
public interface DeployService {

    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(DeployQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部数据
     * @param criteria 条件
     * @return /
     */
    List<DeployDto> queryAll(DeployQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    DeployDto findById(Long id);

    /**
     * 创建
     * @param resources /
     * @return /
     */
    DeployDto create(Deploy resources);


    /**
     * 编辑
     * @param resources /
     */
    void update(Deploy resources);

    /**
     * 删除
     * @param ids /
     */
    void delete(Set<Long> ids);

	/**
	 * 部署文件到服务器
	 * @param fileSavePath 文件路径
	 * @param appId 应用ID
     */
	void deploy(String fileSavePath, Long appId);

    /**
     * 查询部署状态
     * @param resources /
     * @return /
     */
    String serverStatus(Deploy resources);
    /**
     * 启动服务
     * @param resources /
     * @return /
     */
    String startServer(Deploy resources);
    /**
     * 停止服务
     * @param resources /
     * @return /
     */
    String stopServer(Deploy resources);

    /**
     * 停止服务
     * @param resources /
     * @return /
     */
    String serverReduction(DeployHistory resources);

    /**
     * 导出数据
     * @param queryAll /
     * @param response /
     * @throws IOException /
     */
    void download(List<DeployDto> queryAll, HttpServletResponse response) throws IOException;
}
