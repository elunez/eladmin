package me.zhengjie.exception;

import lombok.Getter;

/**
 * 异步任务返回错误，用于定时任务执行时返回可记录到日志的错误
 * 方便通过页面上的日志查看功能查看具体原因
 *
 * @author Emil.Zhang
 * 2022-03-24
 */
@Getter
public class TaskException extends RuntimeException {

    private String param;

    /**
     * 带基本信息的异步错误
     *
     * @param msg 错误信息
     */
    public TaskException(String msg) {
        super(msg);
    }

    /**
     * 含有额外参数的异步错误
     *
     * @param msg   错误信息
     * @param param 接口调用参数
     */
    public TaskException(String msg, String param) {
        super(msg);
        this.param = param;
    }
}
