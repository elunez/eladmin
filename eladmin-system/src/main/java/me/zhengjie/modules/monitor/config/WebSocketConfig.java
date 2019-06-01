package me.zhengjie.modules.monitor.config;

import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.monitor.domain.LogMessage;
import me.zhengjie.utils.thread.LocalExecutorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;

/**
 * 配置WebSocket消息代理端点，即stomp服务端
 *
 * @author jie
 * @reference https://cloud.tencent.com/developer/article/1096792
 * @date 2018-12-24
 */
@Slf4j
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private ExecutorService executorService = LocalExecutorManager.getExecutorService(LocalExecutorManager.WEB_SOCKET_CONFIG);

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    /**
     * 推送日志到/topic/pullLogger
     */
    @PostConstruct
    public void pushLogger() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        LogMessage logPoll = LoggerQueue.getInstance().poll();
                        log.info("==logPoll:{}", logPoll);
                        if (logPoll != null) {
                            // 格式化异常堆栈信息
                            if ("ERROR".equals(logPoll.getLevel()) && "me.zhengjie.common.exception.handler.GlobalExceptionHandler".equals(logPoll.getClassName())) {
                                logPoll.setBody("<pre>" + logPoll.getBody() + "</pre>");
                            }
                            if (logPoll.getClassName().equals("jdbc.resultsettable")) {
                                logPoll.setBody("<br><pre>" + logPoll.getBody() + "</pre>");
                            }
                            if (messagingTemplate != null) {
                                messagingTemplate.convertAndSend("/topic/logMsg", logPoll);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        executorService.submit(runnable);
    }
}