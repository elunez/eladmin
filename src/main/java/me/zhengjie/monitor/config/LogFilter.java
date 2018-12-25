package me.zhengjie.monitor.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import me.zhengjie.monitor.domain.LogMessage;

import java.text.DateFormat;
import java.util.Date;

/**
 * 定义Logfilter拦截输出日志
 * @author jie
 * @date 2018-12-24
 */
public class LogFilter extends Filter<ILoggingEvent>{

    @Override
    public FilterReply decide(ILoggingEvent event) {
        String exception = "";
        IThrowableProxy iThrowableProxy1 = event.getThrowableProxy();
        if(iThrowableProxy1!=null){
            exception = "<span class='excehtext'>"+iThrowableProxy1.getClassName()+" "+iThrowableProxy1.getMessage()+"</span></br>";
            for(int i=0; i<iThrowableProxy1.getStackTraceElementProxyArray().length;i++){
                exception += "<span class='excetext'>"+iThrowableProxy1.getStackTraceElementProxyArray()[i].toString()+"</span></br>";
            }
        }
        LogMessage loggerMessage = new LogMessage(
                event.getMessage()
                , DateFormat.getDateTimeInstance().format(new Date(event.getTimeStamp())),
                event.getThreadName(),
                event.getLoggerName(),
                event.getLevel().levelStr,
                exception
        );
        LoggerQueue.getInstance().push(loggerMessage);
        return FilterReply.ACCEPT;
    }
}