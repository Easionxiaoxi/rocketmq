package com.xyz.rocketmq.config;

import com.aliyun.openservices.ons.api.bean.ProducerBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 阿里云RocketMQ生产者配置
 */
@Configuration
public class RocketMqProducerConfig {

    @Resource
    private RocketMqProperties rocketMqProperties;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ProducerBean buildProducer() {
        ProducerBean producer = new ProducerBean();
        producer.setProperties(rocketMqProperties.getMqProperties());
        return producer;
    }
}
