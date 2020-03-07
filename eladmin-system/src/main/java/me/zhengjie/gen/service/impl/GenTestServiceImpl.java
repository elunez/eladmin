package me.zhengjie.gen.service.impl;

import me.zhengjie.gen.domain.GenTest;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.gen.repository.GenTestRepository;
import me.zhengjie.gen.service.GenTestService;
import me.zhengjie.gen.service.dto.GenTestDto;
import me.zhengjie.gen.service.dto.GenTestQueryCriteria;
import me.zhengjie.gen.service.mapper.GenTestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
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
* @date 2020-03-07
*/
@Service
//@CacheConfig(cacheNames = "genTest")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class GenTestServiceImpl implements GenTestService {

    private final GenTestRepository genTestRepository;

    private final GenTestMapper genTestMapper;

    public GenTestServiceImpl(GenTestRepository genTestRepository, GenTestMapper genTestMapper) {
        this.genTestRepository = genTestRepository;
        this.genTestMapper = genTestMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(GenTestQueryCriteria criteria, Pageable pageable){
        Page<GenTest> page = genTestRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(genTestMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<GenTestDto> queryAll(GenTestQueryCriteria criteria){
        return genTestMapper.toDto(genTestRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public GenTestDto findById(Integer id) {
        GenTest genTest = genTestRepository.findById(id).orElseGet(GenTest::new);
        ValidationUtil.isNull(genTest.getId(),"GenTest","id",id);
        return genTestMapper.toDto(genTest);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public GenTestDto create(GenTest resources) {
        return genTestMapper.toDto(genTestRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(GenTest resources) {
        GenTest genTest = genTestRepository.findById(resources.getId()).orElseGet(GenTest::new);
        ValidationUtil.isNull( genTest.getId(),"GenTest","id",resources.getId());
        genTest.copy(resources);
        genTestRepository.save(genTest);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            genTestRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<GenTestDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (GenTestDto genTest : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("名称", genTest.getName());
            map.put("性别", genTest.getSex());
            map.put(" createTime",  genTest.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}