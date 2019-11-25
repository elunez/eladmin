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

    /**
	 * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    Object queryAll(DeployQueryCriteria criteria, Pageable pageable);

    /**
	 * queryAll 不分页
    * @param criteria
    * @return
    */
    public Object queryAll(DeployQueryCriteria criteria);

    /**
	 * findById
     * @param id
     * @return
     */
    DeployDTO findById(Long id);

    /**
	 * create
     * @CacheEvict(allEntries = true)
     * @param resources
     * @return
     */
    DeployDTO create(Deploy resources);

    /**
	 * update
     * @CacheEvict(allEntries = true)
     * @param resources
     */
    void update(Deploy resources);

    /**
	 * delete
     * @CacheEvict(allEntries = true)
     * @param id
     */
    void delete(Long id);

	/**
	 * 部署文件到服务器
	 * @param fileSavePath
	 * @param appId
	 * @return
	 */
	public String deploy(String fileSavePath, Long appId);

    /**
     * 查询部署状态
     * @param resources
     * @return
     */
    public String serverStatus(Deploy resources);
    /**
     * 启动服务
     * @param resources
     * @return
     */
    public String startServer(Deploy resources);
    /**
     * 停止服务
     * @param resources
     * @return
     */
    public String stopServer(Deploy resources);

    /**
     * 停止服务
     * @param resources
     * @return
     */
    public String serverReduction(DeployHistory resources);
}
