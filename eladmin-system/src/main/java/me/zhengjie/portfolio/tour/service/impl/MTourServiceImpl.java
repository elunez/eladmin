/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.portfolio.tour.service.impl;

import me.zhengjie.portfolio.tour.domain.MTour;
import me.zhengjie.portfolio.tour.repository.MTourRepository;
import me.zhengjie.portfolio.tour.service.MTourService;
import me.zhengjie.portfolio.tour.service.dto.MTourDto;
import me.zhengjie.portfolio.tour.service.dto.MTourQueryCriteria;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.portfolio.tour.service.mapstruct.MTourMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
* @website https://el-admin.vip
* @description 服务实现
* @author smk
* @date 2022-05-03
**/
@Service
@RequiredArgsConstructor
public class MTourServiceImpl implements MTourService {

    private final MTourRepository mTourRepository;
    private final MTourMapper mTourMapper;

    @Override
    public Map<String,Object> queryAll(MTourQueryCriteria criteria, Pageable pageable){
        Page<MTour> page = mTourRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(mTourMapper::toDto));
    }

    @Override
    public List<MTourDto> queryAll(MTourQueryCriteria criteria){
        return mTourMapper.toDto(mTourRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public MTourDto findById(Long id) {
        MTour mTour = mTourRepository.findById(id).orElseGet(MTour::new);
        ValidationUtil.isNull(mTour.getId(),"MTour","id",id);
        return mTourMapper.toDto(mTour);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MTourDto create(MTour resources) {
        return mTourMapper.toDto(mTourRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MTour resources) {
        MTour mTour = mTourRepository.findById(resources.getId()).orElseGet(MTour::new);
        ValidationUtil.isNull( mTour.getId(),"MTour","id",resources.getId());
        mTour.copy(resources);
        mTourRepository.save(mTour);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            mTourRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<MTourDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MTourDto mTour : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" name",  mTour.getName());
            map.put(" startDate",  mTour.getStartDate());
            map.put(" period",  mTour.getPeriod());
            map.put(" location",  mTour.getLocation());
            map.put(" tourCode",  mTour.getTourCode());
            map.put(" tourType",  mTour.getTourType());
            map.put(" description",  mTour.getDescription());
            map.put(" extraTourDetail",  mTour.getExtraTourDetail());
            map.put(" extraRoomDetail",  mTour.getExtraRoomDetail());
            map.put(" images",  mTour.getImages());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}