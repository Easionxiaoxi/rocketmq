package com.xyz.rocketmq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerMessageTest {

    @Resource
    private ProducerMessage producerMessage;

    @Test
    public void sendMessage() {
        producerMessage.sendMessage("你好1001，我是RocketMQ");
    }
}