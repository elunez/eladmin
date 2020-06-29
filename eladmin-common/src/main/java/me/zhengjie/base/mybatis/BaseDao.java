package me.zhengjie.base.mybatis;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.google.common.collect.Sets;
import me.zhengjie.utils.WhereFun;
import me.zhengjie.utils.WrapperUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * mybatisPlus & JPA 适配
 *
 * @author liaojinlong
 * @since 2020/6/29 10:22
 */
public class BaseDao<I extends IService<T>, J extends JpaRepository<T, ID>, T, ID extends Serializable> {
    protected I mpService;
    protected J jpaRepository;
    @Value("${db.type.switch:false}")
    protected boolean dbSwitch;

    public BaseDao(I mpService, J jpaRepository) {
        this.mpService = mpService;
        this.jpaRepository = jpaRepository;
    }

    public void save(T columnInfo) {
        if (dbSwitch) {
            jpaRepository.save(columnInfo);
        } else {
            mpService.save(columnInfo);
        }
    }

    public List<T> saveAll(List<T> entities) {
        if (dbSwitch) {
            return jpaRepository.saveAll(entities);
        } else {
            mpService.saveBatch(entities);
            return entities;
        }
    }

    public void delete(T entity) {
        if (dbSwitch) {
            jpaRepository.delete(entity);
        } else {
            mpService.remove(WrapperUtils.excute(entity, Wrappers.query(), WhereFun.DEFAULT));
        }
    }

    public void deleteById(ID id) {
        if (dbSwitch) {
            jpaRepository.deleteById(id);
        } else {
            mpService.removeById(id);
        }
    }

    public void update(T columnInfo) {
        if (dbSwitch) {
            jpaRepository.saveAndFlush(columnInfo);
        } else {
            mpService.saveOrUpdate(columnInfo);
        }
    }

    public void batUpdate(List<T> entities) {
        if (dbSwitch) {
            jpaRepository.saveAll(entities);
        } else {
            mpService.saveOrUpdateBatch(entities);
        }
    }

    public T selectById(ID id) {
        T t;
        if (dbSwitch) {
            t = jpaRepository.getOne(id);
        } else {
            t = mpService.getById(id);
        }
        return t;
    }

    public List<T> selectAllById(Iterable<ID> ids) {
        List<T> t;
        if (dbSwitch) {
            t = jpaRepository.findAllById(ids);
        } else {
            t = mpService.listByIds(Sets.newHashSet(ids));
        }
        return t;
    }

    public I getMpService() {
        return mpService;
    }

    public void setMpService(I mpService) {
        this.mpService = mpService;
    }

    public J getJpaRepository() {
        return jpaRepository;
    }

    public void setJpaRepository(J jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public boolean isDbSwitch() {
        return dbSwitch;
    }

    public void setDbSwitch(boolean dbSwitch) {
        this.dbSwitch = dbSwitch;
    }
}
