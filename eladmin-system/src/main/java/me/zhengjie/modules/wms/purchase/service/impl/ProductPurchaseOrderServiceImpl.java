package me.zhengjie.modules.wms.purchase.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.MeasureUnit;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheet;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheetProduct;
import me.zhengjie.modules.wms.outSourceProductSheet.request.OutSourceProcessSheetProductRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetDTO;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetProductDTO;
import me.zhengjie.modules.wms.purchase.domain.ProductPurchaseOrder;
import me.zhengjie.modules.wms.purchase.domain.ProductPurchaseOrderProduct;
import me.zhengjie.modules.wms.purchase.repository.ProductPurchaseOrderProductRepository;
import me.zhengjie.modules.wms.purchase.request.CreateProductPurchaseOrderRequest;
import me.zhengjie.modules.wms.purchase.request.ProductPurchaseOrderProductRequest;
import me.zhengjie.modules.wms.purchase.service.dto.ProductPurchaseOrderProductDTO;
import me.zhengjie.modules.wms.purchase.service.mapper.ProductPurchaseOrderProductMapper;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.purchase.repository.ProductPurchaseOrderRepository;
import me.zhengjie.modules.wms.purchase.service.ProductPurchaseOrderService;
import me.zhengjie.modules.wms.purchase.service.dto.ProductPurchaseOrderDTO;
import me.zhengjie.modules.wms.purchase.service.dto.ProductPurchaseOrderQueryCriteria;
import me.zhengjie.modules.wms.purchase.service.mapper.ProductPurchaseOrderMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
* @author jie
* @date 2019-10-06
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ProductPurchaseOrderServiceImpl implements ProductPurchaseOrderService {

    @Autowired
    private ProductPurchaseOrderRepository productPurchaseOrderRepository;

    @Autowired
    private ProductPurchaseOrderMapper productPurchaseOrderMapper;

    @Autowired
    private ProductPurchaseOrderProductRepository productPurchaseOrderProductRepository;

    @Autowired
    private ProductPurchaseOrderProductMapper productPurchaseOrderProductMapper;

    @Override
    public Object queryAll(ProductPurchaseOrderQueryCriteria criteria, Pageable pageable){
        Specification<ProductPurchaseOrder> specification = new Specification<ProductPurchaseOrder>() {
            @Override
            public Predicate toPredicate(Root<ProductPurchaseOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> targetPredicateList = new ArrayList<>();

                //状态
                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), 1);
                targetPredicateList.add(statusPredicate);

                if(CollectionUtils.isEmpty(targetPredicateList)){
                    return null;
                }else{
                    return criteriaBuilder.and(targetPredicateList.toArray(new Predicate[targetPredicateList.size()]));
                }
            }
        };

        Page<ProductPurchaseOrder> page = productPurchaseOrderRepository.findAll(specification, pageable);
        return PageUtil.toPage(page.map(productPurchaseOrderMapper::toDto));
    }

    @Override
    public Object queryAll(ProductPurchaseOrderQueryCriteria criteria){
        Specification<ProductPurchaseOrder> specification = new Specification<ProductPurchaseOrder>() {
            @Override
            public Predicate toPredicate(Root<ProductPurchaseOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> targetPredicateList = new ArrayList<>();

                //状态
                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), 1);
                targetPredicateList.add(statusPredicate);

                if(CollectionUtils.isEmpty(targetPredicateList)){
                    return null;
                }else{
                    return criteriaBuilder.and(targetPredicateList.toArray(new Predicate[targetPredicateList.size()]));
                }
            }
        };

        List<ProductPurchaseOrder> productPurchaseOrderList = productPurchaseOrderRepository.findAll(specification);
        return productPurchaseOrderMapper.toDto(productPurchaseOrderList);
    }

    @Override
    public ProductPurchaseOrderDTO findById(Long id) {
        Optional<ProductPurchaseOrder> productPurchaseOrder = productPurchaseOrderRepository.findById(id);
        ValidationUtil.isNull(productPurchaseOrder,"ProductPurchaseOrder","id",id);
        return productPurchaseOrderMapper.toDto(productPurchaseOrder.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductPurchaseOrderDTO create(CreateProductPurchaseOrderRequest createProductPurchaseOrderRequest) {
        ProductPurchaseOrder productPurchaseOrder = new ProductPurchaseOrder();
        BeanUtils.copyProperties(createProductPurchaseOrderRequest, productPurchaseOrder);

        String productPurchaseOrderCode = productPurchaseOrder.getProductPurchaseOrderCode();
        if(!StringUtils.hasLength(productPurchaseOrderCode)){
            throw new BadRequestException("产品采购单单据编号不能为空!");
        }

        productPurchaseOrder.setStatus(true);
        // 新增产品采购单
        productPurchaseOrderRepository.save(productPurchaseOrder);

        productPurchaseOrder = productPurchaseOrderRepository.findByProductPurchaseOrderCode(productPurchaseOrder.getProductPurchaseOrderCode());

        // 新增产品采购单产品信息
        List<ProductPurchaseOrderProductRequest> productPurchaseOrderProductRequestRequestList = createProductPurchaseOrderRequest.getProductPurchaseOrderProductList();
        if(CollectionUtils.isEmpty(productPurchaseOrderProductRequestRequestList)){
            throw new BadRequestException("产品采购单产品信息不能为空!");
        }

        for(ProductPurchaseOrderProductRequest productPurchaseOrderProductRequest : productPurchaseOrderProductRequestRequestList){
            ProductPurchaseOrderProduct productPurchaseOrderProduct = new ProductPurchaseOrderProduct();
            BeanUtils.copyProperties(productPurchaseOrderProductRequest, productPurchaseOrderProduct);
            productPurchaseOrderProduct.setStatus(true);
            productPurchaseOrderProduct.setProductPurchaseOrderId(productPurchaseOrder.getId());
            productPurchaseOrderProductRepository.save(productPurchaseOrderProduct);
        }


        ProductPurchaseOrderDTO productPurchaseOrderDTO = productPurchaseOrderMapper.toDto(productPurchaseOrder);

        List<ProductPurchaseOrderProduct> productPurchaseOrderProductList = productPurchaseOrderProductRepository.queryByProductPurchaseOrderIdAndStatusTrue(productPurchaseOrder.getId());
        if(!CollectionUtils.isEmpty(productPurchaseOrderProductList)){
            List<ProductPurchaseOrderProductDTO> productPurchaseOrderProductDTOList = new ArrayList<>();
            for(ProductPurchaseOrderProduct productPurchaseOrderProduct : productPurchaseOrderProductList){
                ProductPurchaseOrderProductDTO productPurchaseOrderProductDTO = new ProductPurchaseOrderProductDTO();
                BeanUtils.copyProperties(productPurchaseOrderProduct, productPurchaseOrderProductDTO);
                productPurchaseOrderProductDTOList.add(productPurchaseOrderProductDTO);
            }
            productPurchaseOrderDTO.setProductPurchaseOrderProductList(productPurchaseOrderProductDTOList);
        }

        return productPurchaseOrderDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProductPurchaseOrder resources) {
        Optional<ProductPurchaseOrder> optionalProductPurchaseOrder = productPurchaseOrderRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalProductPurchaseOrder,"ProductPurchaseOrder","id",resources.getId());
        ProductPurchaseOrder productPurchaseOrder = optionalProductPurchaseOrder.get();
        productPurchaseOrder.copy(resources);
        productPurchaseOrderRepository.save(productPurchaseOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        productPurchaseOrderRepository.deleteById(id);
    }
}