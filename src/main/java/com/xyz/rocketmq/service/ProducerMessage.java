package com.xyz.rocketmq.service;

/**
 * 阿里云RocketMQ消息生产者
 */
public interface ProducerMessage {
    /**
     * 发送消息
     * @param msg 消息
     * @return Boolean
     */
    Boolean sendMessage(String msg);
}
