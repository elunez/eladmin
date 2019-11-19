package me.zhengjie.gen.service.impl;

import me.zhengjie.gen.domain.GenTest;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.gen.repository.GenTestRepository;
import me.zhengjie.gen.service.GenTestService;
import me.zhengjie.gen.service.dto.GenTestDTO;
import me.zhengjie.gen.service.dto.GenTestQueryCriteria;
import me.zhengjie.gen.service.mapper.GenTestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @author Zheng Jie
* @date 2019-11-19
*/
@Service
@CacheConfig(cacheNames = "genTest")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class GenTestServiceImpl implements GenTestService {

    private final GenTestRepository genTestRepository;

    private final GenTestMapper genTestMapper;

    public GenTestServiceImpl(GenTestRepository genTestRepository, GenTestMapper genTestMapper) {
        this.genTestRepository = genTestRepository;
        this.genTestMapper = genTestMapper;
    }

    @Override
    @Cacheable
    public Map<String,Object> queryAll(GenTestQueryCriteria criteria, Pageable pageable){
        Page<GenTest> page = genTestRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(genTestMapper::toDto));
    }

    @Override
    @Cacheable
    public List<GenTestDTO> queryAll(GenTestQueryCriteria criteria){
        return genTestMapper.toDto(genTestRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "#p0")
    public GenTestDTO findById(Long id) {
        GenTest genTest = genTestRepository.findById(id).orElseGet(GenTest::new);
        ValidationUtil.isNull(genTest.getId(),"GenTest","id",id);
        return genTestMapper.toDto(genTest);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public GenTestDTO create(GenTest resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId()); 
        return genTestMapper.toDto(genTestRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(GenTest resources) {
        GenTest genTest = genTestRepository.findById(resources.getId()).orElseGet(GenTest::new);
        ValidationUtil.isNull( genTest.getId(),"GenTest","id",resources.getId());
        genTest.copy(resources);
        genTestRepository.save(genTest);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        genTestRepository.deleteById(id);
    }


    @Override
    public void download(List<GenTestDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (GenTestDTO genTest : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("名称", genTest.getName());
            map.put("状态", genTest.getStatus());
            map.put("日期", genTest.getDate());
            map.put("创建日期", genTest.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}