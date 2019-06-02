package me.zhengjie.modules.pay.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.pay.domain.PayConfig;
import me.zhengjie.modules.pay.repository.PayConfigRepository;
import me.zhengjie.modules.pay.service.PayConfigService;
import me.zhengjie.modules.pay.service.dto.PayConfigDTO;
import me.zhengjie.modules.pay.service.query.PayConfigQueryService;
import me.zhengjie.utils.SecurityUtils;
import me.zhengjie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xpay
 * @date 2019-06-02
 */
@RestController
@RequestMapping("api")
public class PayConfigController {

    @Autowired
    private PayConfigService payConfigService;

    @Autowired
    private PayConfigQueryService payConfigQueryService;

    @Resource
    private PayConfigRepository payConfigRepository;

    @Log("查询个人支付图片")
    @PreAuthorize("hasAnyRole('ADMIN','PAYCONFIG_ALL','PAYCONFIG_SELECT')")
    @GetMapping(value = "/pay/pic")
    public ResponseEntity getPayPic() {
        Long userId = SecurityUtils.getUserId();
        PayConfig payConfig = payConfigRepository.findByUid(userId);
        return new ResponseEntity(payConfig, HttpStatus.OK);
    }

    @Log("上传个人支付图片")
    @PreAuthorize("hasAnyRole('ADMIN','PAYCONFIG_ALL','PAYCONFIG_UPLOAD')")
    @PostMapping(value = "/pay/pic")
    public ResponseEntity uploadPayPic(@RequestParam MultipartFile file, HttpServletRequest request) throws MaxUploadSizeExceededException {
        String type = request.getParameter("type");
        if (StringUtils.isBlank(type)) {
            throw new BadRequestException("上传失败，图片类型未指定");
        }
        Long userId = SecurityUtils.getUserId();
        PayConfig payConfig = payConfigService.upload(file, userId, type);
        Map<String, Object> map = new HashMap<>();
        map.put("errno", 0);
        map.put("data", payConfig);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Log("查询PayConfig")
    @GetMapping(value = "/payConfig")
    @PreAuthorize("hasAnyRole('ADMIN','PAYCONFIG_ALL','PAYCONFIG_SELECT')")
    public ResponseEntity getPayConfigs(PayConfigDTO resources, Pageable pageable) {
        return new ResponseEntity(payConfigQueryService.queryAll(resources, pageable), HttpStatus.OK);
    }

    @Log("新增PayConfig")
    @PostMapping(value = "/payConfig")
    @PreAuthorize("hasAnyRole('ADMIN','PAYCONFIG_ALL','PAYCONFIG_CREATE')")
    public ResponseEntity create(@Validated @RequestBody PayConfig resources) {
        return new ResponseEntity(payConfigService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改PayConfig")
    @PutMapping(value = "/payConfig")
    @PreAuthorize("hasAnyRole('ADMIN','PAYCONFIG_ALL','PAYCONFIG_EDIT')")
    public ResponseEntity update(@Validated @RequestBody PayConfig resources) {
        payConfigService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除PayConfig")
    @DeleteMapping(value = "/payConfig/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PAYCONFIG_ALL','PAYCONFIG_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        payConfigService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}