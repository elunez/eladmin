package me.zhengjie.modules.quartz.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.quartz.domain.QuartzLog;
import me.zhengjie.modules.quartz.repository.QuartzLogRepository;
import me.zhengjie.modules.quartz.service.QuartzLogService;
import org.springframework.stereotype.Service;

/**
 * @author Emil.Zhang
 * 2022-03-24
 */
@RequiredArgsConstructor
@Service(value = "quartzLogService")
public class QuartzLogServiceImpl implements QuartzLogService {

    private final QuartzLogRepository quartzLogRepository;

    @Override
    public void create(QuartzLog resources) {
        quartzLogRepository.save(resources);
    }
}
