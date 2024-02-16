package com.mentoring.avro.config;

import com.mentoring.avro.model.Order;
import com.mentoring.avro.utils.AvroDeserializer;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EnableKafka
@Configuration("NotificationConfiguration")
public class OrderConsumerFactory {
    @Value("${spring.kafka.order.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.order.consumer.group-id.notification}")
    private String groupId;

    @Value("${spring.kafka.order.security.protocol}")
    private String securityProtocol;

    @Value("${spring.kafka.order.sasl.jaas.config}")
    private String jaasConfig;

    @Value("${spring.kafka.order.sasl.mechanism}")
    private String mechanism;

    @Bean("NotificationConsumerFactory")
    public ConsumerFactory<String, Order> createOrderConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AvroDeserializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(SaslConfigs.SASL_MECHANISM, mechanism);
        props.put(SaslConfigs.SASL_JAAS_CONFIG, jaasConfig);
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);



        return new DefaultKafkaConsumerFactory<>(props,new StringDeserializer(),
                new AvroDeserializer<>(Order.class));
    }

    @Bean("NotificationContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Order> createOrderKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Order> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createOrderConsumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }
}
