package me.zhengjie.modules.mnt.service;

import me.zhengjie.modules.mnt.domain.Deploy;
import me.zhengjie.modules.mnt.domain.DeployHistory;
import me.zhengjie.modules.mnt.service.dto.DeployDTO;
import me.zhengjie.modules.mnt.service.dto.DeployQueryCriteria;
import org.springframework.data.domain.Pageable;

/**
* @author zhanghouying
* @date 2019-08-24
*/
public interface DeployService {

    Object queryAll(DeployQueryCriteria criteria, Pageable pageable);

    Object queryAll(DeployQueryCriteria criteria);

    DeployDTO findById(Long id);

    DeployDTO create(Deploy resources);

    void update(Deploy resources);

    void delete(Long id);

	/**
	 * 部署文件到服务器
	 * @param fileSavePath 文件路径
	 * @param appId 应用ID
	 * @return /
	 */
	String deploy(String fileSavePath, Long appId);

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
}
