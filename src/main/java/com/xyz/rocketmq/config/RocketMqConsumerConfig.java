package com.xyz.rocketmq.config;

import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.xyz.rocketmq.service.impl.ConsumerMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 阿里云RocketMQ消费者配置
 */
@Configuration
public class RocketMqConsumerConfig {

    @Resource
    private RocketMqProperties rocketMqProperties;

    @Resource
    private ConsumerMessageListener consumerMessageListener;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ConsumerBean buildConsumer() {
        ConsumerBean consumerBean = new ConsumerBean();
        //配置文件
        Properties properties = rocketMqProperties.getMqProperties();
        properties.setProperty(PropertyKeyConst.GROUP_ID, rocketMqProperties.getGroupId());
        //将消费者线程数固定为20个 20为默认值
        properties.setProperty(PropertyKeyConst.ConsumeThreadNums, "20");
        consumerBean.setProperties(properties);
        //订阅关系
        Map<Subscription, MessageListener> subscriptionTable = new HashMap<Subscription, MessageListener>();
        Subscription subscription = new Subscription();
        subscription.setTopic(rocketMqProperties.getConsumerTopic());
        subscription.setExpression(rocketMqProperties.getConsumerTag());
        subscriptionTable.put(subscription, consumerMessageListener);
        //订阅多个topic如上面设置
        consumerBean.setSubscriptionTable(subscriptionTable);
        return consumerBean;
    }

}
