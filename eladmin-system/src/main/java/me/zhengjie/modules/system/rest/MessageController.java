package me.zhengjie.modules.system.rest;

import io.swagger.annotations.ApiOperation;
import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.system.domain.Menu;
import me.zhengjie.modules.system.domain.Message;
import me.zhengjie.modules.system.service.MessageService;
import me.zhengjie.modules.system.service.dto.MessageCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 黄星星
 * @date 2020-02-27
 */
@RestController
@RequestMapping("api")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @ApiOperation(value = "我的消息列表")
    @GetMapping(value = "/queryMessageList")
    @PreAuthorize("hasAnyRole('ADMIN','MESSAGE_ALL','MESSAGE_ALL')")
    public ResponseEntity queryMessageList(MessageCriteria criteria){
        return new ResponseEntity(messageService.queryAll(criteria), HttpStatus.OK);
    }

    @Log("修改消息")
    @PutMapping(value = "/message")
    @PreAuthorize("hasAnyRole('ADMIN','MESSAGE_ALL','MESSAGE_EDIT')")
    public ResponseEntity update(@Validated(Menu.Update.class) @RequestBody Message resources){
        messageService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除消息")
    @ApiOperation(value = "删除消息")
    @DeleteMapping(value = "/messagee/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MESSAGE_ALL','MESSAGE_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        messageService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
