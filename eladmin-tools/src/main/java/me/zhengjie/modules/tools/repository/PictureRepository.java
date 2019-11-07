package me.zhengjie.modules.tools.repository;

import me.zhengjie.modules.tools.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Zheng Jie
 * @date 2018-12-27
 */
public interface PictureRepository extends JpaRepository<Picture,Long>, JpaSpecificationExecutor<Picture> {

    Picture findByMd5Code(String code);
}
