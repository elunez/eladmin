package me.zhengjie.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author jie
 * @date 2018-11-23
 * 统一异常处理
 */
@Getter
public class BadRequestException extends RuntimeException{
    public BadRequestException(String msg){
        super(msg);
    }
}
