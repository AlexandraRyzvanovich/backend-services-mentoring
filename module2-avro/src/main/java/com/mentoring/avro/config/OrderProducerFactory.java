package com.mentoring.avro.config;

import com.mentoring.avro.utils.AvroSerializer;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class OrderProducerFactory {
    @Value("${spring.kafka.order.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.order.security.protocol}")
    private String securityProtocol;

    @Value("${spring.kafka.order.sasl.jaas.config}")
    private String jaasConfig;

    @Value("${spring.kafka.order.sasl.mechanism}")
    private String mechanism;

    @Bean
    public <K, V> ProducerFactory<K, V> createOrderProducerFactory(){
        Map<String,Object> config = new HashMap<>();
        config.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(SaslConfigs.SASL_MECHANISM, mechanism);
        config.put(SaslConfigs.SASL_JAAS_CONFIG, jaasConfig);
        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class);

        return new DefaultKafkaProducerFactory(config);
    }

    @Bean
    public <K, V> KafkaTemplate<K, V> kafkaTemplate(){
        return new KafkaTemplate<>(createOrderProducerFactory());
    }
}
