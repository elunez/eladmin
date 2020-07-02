package me.zhengjie.base.mybatis;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.google.common.collect.Sets;
import me.zhengjie.utils.WhereFun;
import me.zhengjie.utils.WrapperUtils;
import me.zhengjie.utils.enums.DbType;
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
    protected DbType dbType = DbType.JPA;

    public BaseDao(I mpService, J jpaRepository) {
        this.mpService = mpService;
        this.jpaRepository = jpaRepository;
    }

    public T save(T t) {
        T t1;
        switch (dbType) {
            case JPA:
                t1 = jpaRepository.save(t);
                break;
            case MYBATIS:
                mpService.save(t);
                t1 = t;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dbType);
        }
        return t1;
    }

    public List<T> saveAll(List<T> entities) {
        List<T> result;
        switch (dbType) {
            case JPA:
                result = jpaRepository.saveAll(entities);
                break;
            case MYBATIS:
                mpService.saveBatch(entities);
                result = entities;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dbType);
        }
        return result;
    }

    public void delete(T entity) {
        switch (dbType) {
            case JPA:
                jpaRepository.delete(entity);
                break;
            case MYBATIS:
                mpService.remove(WrapperUtils.excute(entity, Wrappers.query(), WhereFun.DEFAULT));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dbType);
        }
    }

    public void deleteById(ID id) {
        switch (dbType) {
            case JPA:
                jpaRepository.deleteById(id);
                break;
            case MYBATIS:
                mpService.removeById(id);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dbType);
        }
    }

    public void update(T columnInfo) {
        switch (dbType) {
            case JPA:
                jpaRepository.saveAndFlush(columnInfo);
                break;
            case MYBATIS:
                mpService.saveOrUpdate(columnInfo);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dbType);
        }
    }

    public void batUpdate(List<T> entities) {
        switch (dbType) {
            case JPA:
                jpaRepository.saveAll(entities);
                break;
            case MYBATIS:
                mpService.saveOrUpdateBatch(entities);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dbType);
        }
    }

    public T selectById(ID id) {
        T t;
        switch (dbType) {
            case JPA:
                t = jpaRepository.getOne(id);
                break;
            case MYBATIS:
                t = mpService.getById(id);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dbType);
        }
        return t;
    }

    public List<T> selectAllById(Iterable<ID> ids) {
        List<T> t;
        switch (dbType) {
            case JPA:
                t = jpaRepository.findAllById(ids);
                break;
            case MYBATIS:
                t = mpService.listByIds(Sets.newHashSet(ids));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dbType);
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

    public DbType getDbType() {
        return dbType;
    }

    public void setDbType(DbType dbType) {
        this.dbType = dbType;
    }
}
