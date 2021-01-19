package com.rymcu.magnolia.receiver;

import com.rabbitmq.client.Channel;
import com.rymcu.magnolia.constant.RabbitQueueConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * 通用消息接收者
 *
 * @author ronger
 */
@Component
@RabbitListener(queues = RabbitQueueConstant.COMMON_QUEUE)
public class CommonReceiver {

    Logger logger = LoggerFactory.getLogger(CommonReceiver.class);

    @RabbitHandler
    public void receiveObjectMessage(String str, Channel channel, Message message) {
        logger.info("str: {},message: {},channel: {}", str, message, channel);
        //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
        try {
            // 休眠 100 ms
            Thread.sleep(100);
            // 发送消息回执
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            try {
                // 休眠 100 ms
                Thread.sleep(100);
                // 丢弃该消息
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            } catch (IOException | InterruptedException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
