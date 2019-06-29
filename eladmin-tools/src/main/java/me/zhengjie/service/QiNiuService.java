package me.zhengjie.service;

import me.zhengjie.domain.QiniuConfig;
import me.zhengjie.domain.QiniuContent;
import me.zhengjie.service.dto.QiniuQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Zheng Jie
 * @date 2018-12-31
 */
@CacheConfig(cacheNames = "qiNiu")
public interface QiNiuService {

    /**
     * 查询文件
     * @param criteria
     * @param pageable
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(QiniuQueryCriteria criteria, Pageable pageable);

    /**
     * 查配置
     * @return
     */
    @Cacheable(cacheNames = "qiNiuConfig", key = "'1'")
    QiniuConfig find();

    /**
     * 修改配置
     * @param qiniuConfig
     * @return
     */
    @CachePut(cacheNames = "qiNiuConfig", key = "'1'")
    QiniuConfig update(QiniuConfig qiniuConfig);

    /**
     * 上传文件
     * @param file
     * @param qiniuConfig
     * @return
     */
    @CacheEvict(allEntries = true)
    QiniuContent upload(MultipartFile file, QiniuConfig qiniuConfig);

    /**
     * 查询文件
     * @param id
     * @return
     */
    @Cacheable(key = "'content:'+#p0")
    QiniuContent findByContentId(Long id);

    /**
     * 下载文件
     * @param content
     * @param config
     * @return
     */
    String download(QiniuContent content, QiniuConfig config);

    /**
     * 删除文件
     * @param content
     * @param config
     * @return
     */
    @CacheEvict(allEntries = true)
    void delete(QiniuContent content, QiniuConfig config);

    /**
     * 同步数据
     * @param config
     */
    @CacheEvict(allEntries = true)
    void synchronize(QiniuConfig config);

    /**
     * 删除文件
     * @param ids
     * @param config
     */
    @CacheEvict(allEntries = true)
    void deleteAll(Long[] ids, QiniuConfig config);
}
