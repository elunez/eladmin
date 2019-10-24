package me.zhengjie.service;

import me.zhengjie.domain.QiniuConfig;
import me.zhengjie.domain.QiniuContent;
import me.zhengjie.service.dto.QiniuQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Zheng Jie
 * @date 2018-12-31
 */
@CacheConfig(cacheNames = "qiNiu")
public interface QiNiuService {

    /**
     * 查询文件
     * @param criteria 条件参数
     * @param pageable 分页参数
     * @return Object
     */
    @Cacheable
    Object queryAll(QiniuQueryCriteria criteria, Pageable pageable);

    /**
     * 查配置
     * @return Cacheable
     */
    @Cacheable(cacheNames = "qiNiuConfig", key = "'1'")
    QiniuConfig find();

    /**
     * 修改配置
     * @param qiniuConfig
     * @return QiniuConfig
     */
    @CachePut(cacheNames = "qiNiuConfig", key = "'1'")
    QiniuConfig update(QiniuConfig qiniuConfig);

    /**
     * 上传文件
     * @param file
     * @param qiniuConfig
     * @return QiniuContent
     */
    @CacheEvict(allEntries = true)
    QiniuContent upload(MultipartFile file, QiniuConfig qiniuConfig);

    /**
     * 查询文件
     * @param id
     * @return QiniuContent
     */
    @Cacheable(key = "'content:'+#p0")
    QiniuContent findByContentId(Long id);

    /**
     * 下载文件
     * @param content
     * @param config
     * @return String
     */
    String download(QiniuContent content, QiniuConfig config);

    /**
     * 删除文件
     * @param content 文件
     * @param config 配置
     */
    @CacheEvict(allEntries = true)
    void delete(QiniuContent content, QiniuConfig config);

    /**
     * 同步数据
     * @param config 配置
     */
    @CacheEvict(allEntries = true)
    void synchronize(QiniuConfig config);

    /**
     * 删除文件
     * @param ids 文件ID数组
     * @param config 配置
     */
    @CacheEvict(allEntries = true)
    void deleteAll(Long[] ids, QiniuConfig config);

    /**
     * 更新数据
     * @param type 类型
     */
    @CacheEvict(allEntries = true)
    void update(String type);
}
