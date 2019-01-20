package me.zhengjie.rest;

import me.zhengjie.domain.Log;
import me.zhengjie.service.query.LogQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jie
 * @date 2018-11-24
 */
@RestController
@RequestMapping("api")
public class LogController {

    @Autowired
    private LogQueryService logQueryService;

    @GetMapping(value = "/logs")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getLogs(Log log, Pageable pageable){
        log.setLogType("INFO");
        return new ResponseEntity(logQueryService.queryAll(log,pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/logs/error")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getErrorLogs(Log log, Pageable pageable){
        log.setLogType("ERROR");
        return new ResponseEntity(logQueryService.queryAll(log,pageable), HttpStatus.OK);
    }
}
