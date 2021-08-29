package com.xyz.rocketmq.service.impl;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import org.springframework.stereotype.Component;

/**
 * 阿里云RocketMQ消费者消息监听
 */
@Component
public class ConsumerMessageListener implements MessageListener {

    @Override
    public Action consume(Message message, ConsumeContext context) {
        try {
            String msg = new String(message.getBody());
            System.out.println("普通消息的消费: " + message);
            System.out.println("消息内容是：" + msg);
            //消费成功ACK确认
            return Action.CommitMessage;
        } catch (Exception e) {
            //消费失败重发
            return Action.ReconsumeLater;
        }
    }
}
