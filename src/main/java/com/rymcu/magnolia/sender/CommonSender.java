package com.rymcu.magnolia.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * 通用消息发送者
 *
 * @author ronger
 */
@Component
public class CommonSender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    Logger logger = LoggerFactory.getLogger(CommonSender.class);

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 通过构造函数注入 RabbitTemplate 依赖
     *
     * @param rabbitTemplate
     */
    @Autowired
    public CommonSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        // 设置消息到达 exchange 时，要回调的方法，每个 RabbitTemplate 只支持一个 ConfirmCallback
        rabbitTemplate.setConfirmCallback(this);
        // 设置消息无法到达 queue 时，要回调的方法
        rabbitTemplate.setReturnCallback(this);
    }

    public void send(String routingKry, String message) {
        //构建回调返回的数据
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        this.rabbitTemplate.convertAndSend(routingKry, (Object) message, correlationData);
        logger.info("Confirm Send ok," + new Date() + "," + message);
    }

    /**
     * 消息回调确认方法
     * 如果消息没有到exchange,则confirm回调,ack=false
     * 如果消息到达exchange,则confirm回调,ack=true
     *
     * @param correlationData
     * @param isSendSuccess
     * @param message
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean isSendSuccess, String message) {
        logger.info("correlationData: {}, isSendSuccess: {}, message: {}", correlationData, isSendSuccess, message);
    }

    /**
     * exchange到queue成功,则不回调return
     * exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.info("returnedMessage: {}", message);
    }
}
