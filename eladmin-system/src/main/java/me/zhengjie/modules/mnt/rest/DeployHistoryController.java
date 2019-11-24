package me.zhengjie.modules.mnt.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.mnt.domain.DeployHistory;
import me.zhengjie.modules.mnt.service.DeployHistoryService;
import me.zhengjie.modules.mnt.service.dto.DeployHistoryQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@Api(tags = "部署历史管理")
@RestController
@RequestMapping("/api/deployHistory")
public class DeployHistoryController {

    @Autowired
    private DeployHistoryService deployhistoryService;

    @Log("查询DeployHistory")
    @ApiOperation(value = "查询DeployHistory")
    @GetMapping
	@PreAuthorize("@el.check('deployHistory:list')")
    public ResponseEntity getDeployHistorys(DeployHistoryQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(deployhistoryService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("删除DeployHistory")
    @ApiOperation(value = "删除DeployHistory")
	@DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('deployHistory:del')")
    public ResponseEntity delete(@PathVariable String id){
        deployhistoryService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
