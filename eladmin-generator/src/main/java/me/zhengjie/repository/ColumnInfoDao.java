package me.zhengjie.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import me.zhengjie.domain.ColumnInfo;
import me.zhengjie.repository.jpa.ColumnInfoRepository;
import me.zhengjie.repository.mp.ColumnInfoService;
import me.zhengjie.utils.WhereFun;
import me.zhengjie.utils.WrapperUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author liaojinlong
 * @since 2020/6/28 14:59
 */
@Component
public class ColumnInfoDao {
    private Boolean dbType = false;
    private ColumnInfoService columnInfoService;
    private ColumnInfoRepository columnInfoRepository;

    public ColumnInfoDao(ColumnInfoService columnInfoService, ColumnInfoRepository columnInfoRepository) {
        this.columnInfoService = columnInfoService;
        this.columnInfoRepository = columnInfoRepository;
    }

    public List<ColumnInfo> findByTableNameOrderByIdAsc(String tableName) {
        if (false) {
            return columnInfoRepository.findByTableNameOrderByIdAsc(tableName);
        } else {
            return columnInfoService
                    .selectList(Wrappers.<ColumnInfo>query().eq(true, "TABLE_NAME", tableName));
        }
    }

    public List<ColumnInfo> saveAll(List<ColumnInfo> columnInfos) {
        if (true) {
            return columnInfoRepository.saveAll(columnInfos);
        } else {
            columnInfos.forEach(columnInfo -> {
                columnInfoService.insert(columnInfo);
            });
            return columnInfos;
        }
    }

    public void delete(ColumnInfo columnInfo) {
        if (dbType) {
            columnInfoRepository.delete(columnInfo);
        } else {
            columnInfoService.delete(WrapperUtils.excute(columnInfo, Wrappers.query(), WhereFun.DEFAULT));
        }
    }

    public void save(ColumnInfo columnInfo) {
        if (dbType) {
            columnInfoRepository.save(columnInfo);
        } else {
            columnInfoService.insert(columnInfo);
        }
    }

    public void update(ColumnInfo columnInfo) {
        if (dbType) {
            columnInfoRepository.saveAndFlush(columnInfo);
        } else {
            columnInfoService.updateById(columnInfo);
        }
    }
}
