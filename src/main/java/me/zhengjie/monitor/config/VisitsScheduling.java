package me.zhengjie.monitor.config;

import me.zhengjie.monitor.service.VisitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author jie
 * @date 2018-12-25
 */
@Component
@Async
public class VisitsScheduling {

    @Autowired
    private VisitsService visitsService;

    /**
     * 每天0点运行
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void save(){
        visitsService.save();
    }
}
