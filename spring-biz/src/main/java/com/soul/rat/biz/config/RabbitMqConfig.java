package com.soul.rat.biz.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbit mq
 *
 * @author zhujx
 */
@Configuration
public class RabbitMqConfig {

    public static final String MY_QUEUE = "myQueue";

    public static final String MY_DIRECT_EXCHANGE = "myDirectExchange";

    public static final String MY_DIRECT_ROUTING = "myDirectRouting";

    /**
     * 定义队列
     */
    @Bean
    public Queue myQueue() {
        /*
         * 参数介绍：
         * 1、name: 队列的名称；
         * 2、durable: 是否持久化,true:rabbitmq服务器重启仍然存在；
         * 3、exclusive: 是否独享、排外的 true只能声明队列者使用；
         * 4、autoDelete: 是否自动删除，true队列不在使用时 自动删除该队列；
         * 5、arguments：队列的其他属性参数，有如下可选项：
         * （1）x-message-ttl：消息的过期时间，单位：毫秒；
         * （2）x-expires：队列过期时间，队列在多长时间未被访问将被删除，单位：毫秒；
         * （3）x-max-length：队列最大长度，超过该最大值，则将从队列头部开始删除消息；
         * （4）x-max-length-bytes：队列消息内容占用最大空间，受限于内存大小，超过该阈值则从队列头部开始删除消息；
         * （5）x-overflow：设置队列溢出行为。这决定了当达到队列的最大长度时消息会发生什么。有效值是drop-head、reject-publish或reject-publish-dlx。仲裁队列类型仅支持drop-head；
         * （6）x-dead-letter-exchange：死信交换器名称，过期或被删除（因队列长度超长或因空间超出阈值）的消息可指定发送到该交换器中；
         * （7）x-dead-letter-routing-key：死信消息路由键，在消息发送到死信交换器时会使用该路由键，如果不设置，则使用消息的原来的路由键值
         * （8）x-single-active-consumer：表示队列是否是单一活动消费者，true时，注册的消费组内只有一个消费者消费消息，其他被忽略，false时消息循环分发给所有消费者(默认false)
         * （9）x-max-priority：队列要支持的最大优先级数;如果未设置，队列将不支持消息优先级；
         * （10）x-queue-mode（Lazy mode）：将队列设置为延迟模式，在磁盘上保留尽可能多的消息，以减少RAM的使用;如果未设置，队列将保留内存缓存以尽可能快地传递消息；
         * （11）x-queue-master-locator：在集群模式下设置镜像队列的主节点信息。
         */
        return new Queue(MY_QUEUE, true);
    }

    /**
     * 定义交换机
     */
    @Bean
    DirectExchange myDirectExchange() {
        return new DirectExchange(MY_DIRECT_EXCHANGE, true, false);
    }

    /**
     * 绑定  将队列和交换机绑定
     */
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(myQueue()).to(myDirectExchange()).with(MY_DIRECT_ROUTING);
    }
}