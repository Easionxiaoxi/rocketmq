package com.xyz.rocketmq.service.impl;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.aliyun.openservices.ons.api.exception.ONSClientException;
import com.xyz.rocketmq.config.RocketMqProperties;
import com.xyz.rocketmq.service.ProducerMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * 阿里云RocketMQ消息生产者
 */
@Service
public class ProducerMessageImpl implements ProducerMessage {
    /**
     * 阿里云RocketMQ配置
     */
    @Resource
    private RocketMqProperties rocketMqProperties;
    /**
     * 阿里云RocketMQ消息生产者
     */
    @Resource
    private ProducerBean producer;

    /**
     * 发送消息
     *
     * @param msg 消息
     * @return Boolean
     */
    @Override
    public Boolean sendMessage(String msg) {
        // 指定发送消息的topic、tag、body
        Message message = new Message(rocketMqProperties.getProducerTopic(), rocketMqProperties.getProducerTag(), msg.getBytes(StandardCharsets.UTF_8));
        // 指定消息的业务属性key，全局唯一
        message.setKey(UUID.randomUUID().toString());
        try {
            // 生产者发送异步消息
            producer.sendAsync(message, new SendCallback() {
                /**
                 * 生产者消息发送成功后的回调
                 */
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("生产者发送消息成功，发送结果：" + sendResult);
                }
                /**
                 * 生产者消息发送失败后的回调
                 */
                @Override
                public void onException(OnExceptionContext onExceptionContext) {
                    System.out.println("生产者发送消息失败，失败原因：" + onExceptionContext.getException().getMessage());
                }
            });
        } catch (ONSClientException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}
