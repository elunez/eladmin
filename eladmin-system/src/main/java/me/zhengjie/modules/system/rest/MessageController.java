package me.zhengjie.modules.system.rest;

import io.swagger.annotations.ApiOperation;
import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.system.domain.Menu;
import me.zhengjie.modules.system.domain.Message;
import me.zhengjie.modules.system.service.MessageService;
import me.zhengjie.modules.system.service.dto.MessageCriteria;
import me.zhengjie.modules.system.service.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
//    @PreAuthorize("hasAnyRole('ADMIN','MESSAGE_ALL','MESSAGE_SELECT')")
    public ResponseEntity queryMessageList(MessageCriteria criteria){
        return new ResponseEntity(messageService.queryAll(criteria), HttpStatus.OK);
    }

    @Log("查看消息")
    @GetMapping(value = "/message/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN','MESSAGE_ALL','MESSAGE_EDIT')")
    public ResponseEntity findById(@PathVariable Long id){
        MessageDTO messageDTO = messageService.findById(id);
        return new ResponseEntity(messageDTO, HttpStatus.OK);
    }

    @Log("删除消息")
    @ApiOperation(value = "删除消息")
    @DeleteMapping(value = "/message/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN','MESSAGE_ALL','MESSAGE_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        messageService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
