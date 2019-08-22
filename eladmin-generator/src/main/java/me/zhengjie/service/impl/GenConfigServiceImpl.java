package me.zhengjie.service.impl;

import me.zhengjie.domain.GenConfig;
import me.zhengjie.repository.GenConfigRepository;
import me.zhengjie.service.GenConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

/**
 * @author Zheng Jie
 * @date 2019-01-14
 */
@Service
public class GenConfigServiceImpl implements GenConfigService {

    @Autowired
    private GenConfigRepository genConfigRepository;

    @Override
    public GenConfig find() {
        Optional<GenConfig> genConfig = genConfigRepository.findById(1L);
        if(genConfig.isPresent()){
            return genConfig.get();
        } else {
            return new GenConfig();
        }
    }

    @Override
    public GenConfig update(GenConfig genConfig) {
        genConfig.setId(1L);
        // 自动设置Api路径，注释掉前需要同步取消前端的注释
        String separator = File.separator;
        String[] paths = null;
        if (separator.equals("\\")) {
            paths = genConfig.getPath().split("\\\\");
        } else paths = genConfig.getPath().split(File.separator);
        StringBuffer api = new StringBuffer();
        for (int i = 0; i < paths.length; i++) {
            api.append(paths[i]);
            api.append(separator);
            if(paths[i].equals("src")){
                api.append("api");
                break;
            }
        }
        genConfig.setApiPath(api.toString());
        return genConfigRepository.save(genConfig);
    }
}
