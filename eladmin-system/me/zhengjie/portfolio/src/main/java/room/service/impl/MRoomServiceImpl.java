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
package room.service.impl;

import room.domain.MRoom;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import room.repository.MRoomRepository;
import room.service.MRoomService;
import room.service.dto.MRoomDto;
import room.service.dto.MRoomQueryCriteria;
import room.service.mapstruct.MRoomMapper;
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
public class MRoomServiceImpl implements MRoomService {

    private final MRoomRepository mRoomRepository;
    private final MRoomMapper mRoomMapper;

    @Override
    public Map<String,Object> queryAll(MRoomQueryCriteria criteria, Pageable pageable){
        Page<MRoom> page = mRoomRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(mRoomMapper::toDto));
    }

    @Override
    public List<MRoomDto> queryAll(MRoomQueryCriteria criteria){
        return mRoomMapper.toDto(mRoomRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public MRoomDto findById(Long id) {
        MRoom mRoom = mRoomRepository.findById(id).orElseGet(MRoom::new);
        ValidationUtil.isNull(mRoom.getId(),"MRoom","id",id);
        return mRoomMapper.toDto(mRoom);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MRoomDto create(MRoom resources) {
        return mRoomMapper.toDto(mRoomRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MRoom resources) {
        MRoom mRoom = mRoomRepository.findById(resources.getId()).orElseGet(MRoom::new);
        ValidationUtil.isNull( mRoom.getId(),"MRoom","id",resources.getId());
        mRoom.copy(resources);
        mRoomRepository.save(mRoom);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            mRoomRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<MRoomDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MRoomDto mRoom : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" type",  mRoom.getType());
            map.put(" size",  mRoom.getSize());
            map.put(" airConditional",  mRoom.getAirConditional());
            map.put(" fan",  mRoom.getFan());
            map.put(" freeParking",  mRoom.getFreeParking());
            map.put(" description",  mRoom.getDescription());
            map.put(" bad",  mRoom.getBad());
            map.put(" freeBreakfast",  mRoom.getFreeBreakfast());
            map.put(" image",  mRoom.getImage());
            map.put(" extraInformation",  mRoom.getExtraInformation());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}