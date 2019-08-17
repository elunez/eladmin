package me.zhengjie.modules.wms.outSourceProductSheet.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheet;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheetProduct;
import me.zhengjie.modules.wms.outSourceProductSheet.repository.OutSourceProcessSheetProductRepository;
import me.zhengjie.modules.wms.outSourceProductSheet.request.CreateOutSourceProcessSheetRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.request.OutSourceProcessSheetProductRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.request.UpdateOutSourceProcessSheetRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetProductDTO;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.outSourceProductSheet.repository.OutSourceProcessSheetRepository;
import me.zhengjie.modules.wms.outSourceProductSheet.service.OutSourceProcessSheetService;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetDTO;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetQueryCriteria;
import me.zhengjie.modules.wms.outSourceProductSheet.service.mapper.OutSourceProcessSheetMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
* @author jie
* @date 2019-08-17
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class OutSourceProcessSheetServiceImpl implements OutSourceProcessSheetService {

    @Autowired
    private OutSourceProcessSheetRepository outSourceProcessSheetRepository;

    @Autowired
    private OutSourceProcessSheetProductRepository outSourceProcessSheetProductRepository;

    @Autowired
    private OutSourceProcessSheetMapper outSourceProcessSheetMapper;

    @Override
    public Object queryAll(OutSourceProcessSheetQueryCriteria criteria, Pageable pageable){
        Page<OutSourceProcessSheet> page = outSourceProcessSheetRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(outSourceProcessSheetMapper::toDto));
    }

    @Override
    public Object queryAll(OutSourceProcessSheetQueryCriteria criteria){
        return outSourceProcessSheetMapper.toDto(outSourceProcessSheetRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public OutSourceProcessSheetDTO findById(Long id) {
        Optional<OutSourceProcessSheet> sOutSourceProcessSheet = outSourceProcessSheetRepository.findById(id);
        ValidationUtil.isNull(sOutSourceProcessSheet,"SOutSourceProcessSheet","id",id);
        return outSourceProcessSheetMapper.toDto(sOutSourceProcessSheet.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OutSourceProcessSheetDTO create(CreateOutSourceProcessSheetRequest createOutSourceProcessSheetRequest) {
        OutSourceProcessSheet outSourceProcessSheet = new OutSourceProcessSheet();
        BeanUtils.copyProperties(createOutSourceProcessSheetRequest, outSourceProcessSheet);

        String outSourceProcessSheetCode = outSourceProcessSheet.getOutSourceProcessSheetCode();
        if(!StringUtils.hasLength(outSourceProcessSheetCode)){
            throw new BadRequestException("委外加工单单据编号不能为空!");
        }

        outSourceProcessSheet.setStatus(true);
        // 新增委外加工单
        outSourceProcessSheetRepository.save(outSourceProcessSheet);

        outSourceProcessSheet = outSourceProcessSheetRepository.findByOutSourceProcessSheetCode(outSourceProcessSheetCode);

        // 新增委外加工单产品信息
        List<OutSourceProcessSheetProductRequest> outSourceProcessSheetProductRequestList = createOutSourceProcessSheetRequest.getOutSourceProcessSheetProductList();
        if(CollectionUtils.isEmpty(outSourceProcessSheetProductRequestList)){
            throw new BadRequestException("委外加工单产品信息不能为空!");
        }

        for(OutSourceProcessSheetProductRequest outSourceProcessSheetProductRequest : outSourceProcessSheetProductRequestList){
            OutSourceProcessSheetProduct outSourceProcessSheetProduct = new OutSourceProcessSheetProduct();
            BeanUtils.copyProperties(outSourceProcessSheetProductRequest, outSourceProcessSheetProduct);
            outSourceProcessSheetProduct.setStatus(true);
            outSourceProcessSheetProduct.setOutSourceProcessSheetId(outSourceProcessSheet.getId());
            outSourceProcessSheetProductRepository.save(outSourceProcessSheetProduct);
        }


        OutSourceProcessSheetDTO outSourceProcessSheetDTO = outSourceProcessSheetMapper.toDto(outSourceProcessSheet);

        List<OutSourceProcessSheetProduct> outSourceProcessSheetProductList = outSourceProcessSheetProductRepository.queryByOutSourceProcessSheetIdAndStatusTrue(outSourceProcessSheet.getId());
        if(!CollectionUtils.isEmpty(outSourceProcessSheetProductList)){
            List<OutSourceProcessSheetProductDTO> outSourceProcessSheetProductDTOList = new ArrayList<>();
            for(OutSourceProcessSheetProduct outSourceProcessSheetProduct : outSourceProcessSheetProductList){
                OutSourceProcessSheetProductDTO outSourceProcessSheetProductDTO = new OutSourceProcessSheetProductDTO();
                BeanUtils.copyProperties(outSourceProcessSheetProduct, outSourceProcessSheetProductDTO);
                outSourceProcessSheetProductDTOList.add(outSourceProcessSheetProductDTO);
            }
            outSourceProcessSheetDTO.setOutSourceProcessSheetProductList(outSourceProcessSheetProductDTOList);
        }

        return outSourceProcessSheetDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UpdateOutSourceProcessSheetRequest updateOutSourceProcessSheetRequest) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        outSourceProcessSheetRepository.deleteById(id);
    }
}