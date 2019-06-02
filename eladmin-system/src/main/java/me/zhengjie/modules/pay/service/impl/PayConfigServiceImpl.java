package me.zhengjie.modules.pay.service.impl;

import cn.hutool.core.util.IdUtil;
import me.zhengjie.modules.pay.domain.PayConfig;
import me.zhengjie.modules.pay.repository.PayConfigRepository;
import me.zhengjie.modules.pay.service.PayConfigService;
import me.zhengjie.modules.pay.service.dto.PayConfigDTO;
import me.zhengjie.modules.pay.service.mapper.PayConfigMapper;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author xpay
 * @date 2019-06-02
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PayConfigServiceImpl implements PayConfigService {

    @Resource
    private PayConfigRepository payConfigRepository;

    @Resource
    private PayConfigMapper payConfigMapper;


    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.static.path}")
    private String staticPath;
    @Value("${server.url}")
    private String serverUrl;

    @Override
    public PayConfigDTO findById(Long id) {
        Optional<PayConfig> payConfig = payConfigRepository.findById(id);
        ValidationUtil.isNull(payConfig, "PayConfig", "id", id);
        return payConfigMapper.toDto(payConfig.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayConfigDTO create(PayConfig resources) {
        return payConfigMapper.toDto(payConfigRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PayConfig resources) {
        Optional<PayConfig> optionalPayConfig = payConfigRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalPayConfig, "PayConfig", "id", resources.getId());

        PayConfig payConfig = optionalPayConfig.get();
        // 此处需自己修改
        resources.setId(payConfig.getId());
        payConfigRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        payConfigRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public PayConfig upload(MultipartFile multipartFile, Long uid, String type) {

        PayConfig payConfig = payConfigRepository.findByUid(uid);
        if (null == payConfig) {
            payConfig = new PayConfig();
            payConfig.setPrivateKey(IdUtil.simpleUUID());
        }

        String fileServerPath = FileUtil.toFile(multipartFile, serverUrl + staticPath, uploadPath);
        payConfig.setUid(uid);
        if ("alipay".equalsIgnoreCase(type)) {
            payConfig.setImgAlipay(fileServerPath);
        }
        if ("wechat".equalsIgnoreCase(type)) {
            payConfig.setImgWechat(fileServerPath);
        }
        if ("qq".equalsIgnoreCase(type)) {
            payConfig.setImgQq(fileServerPath);
        }
        if ("unionpay".equalsIgnoreCase(type)) {
            payConfig.setImgUnionpay(fileServerPath);
        }
        payConfigRepository.save(payConfig);
        return payConfig;

    }
}