package com.mentoring.avro.service;

import com.mentoring.avro.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class OrderProducerService {
    private static final Logger log = LoggerFactory.getLogger(OrderProducerService.class);

    private final KafkaTemplate<String, Order> kafkaTemplate;

    private final String orderTopic;

    public OrderProducerService(KafkaTemplate<String, Order> createOrderKafkaTemplate,
                               @Value("${spring.kafka.order.topic.create-order}") String orderTopic) {
        this.kafkaTemplate = createOrderKafkaTemplate;
        this.orderTopic = orderTopic;
    }

    public boolean sendCreateOrderEvent(Order order) throws ExecutionException, InterruptedException {
        SendResult<String, Order> sendResult = kafkaTemplate.send(orderTopic, order).get();
        log.info("Producer. Create order {} event sent via Kafka", order);
        log.info(sendResult.toString());
        return true;
    }
}
