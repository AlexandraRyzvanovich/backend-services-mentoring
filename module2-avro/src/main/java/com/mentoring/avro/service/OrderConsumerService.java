package com.mentoring.avro.service;

import com.mentoring.avro.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service("NotificationService")
public class OrderConsumerService {
    private static final Logger log = LoggerFactory.getLogger(OrderConsumerService.class);

    @KafkaListener(topics = "${spring.kafka.order.topic.create-order}", containerFactory="NotificationContainerFactory")
    public void createOrderListener(@Payload Order order, Acknowledgment ack) {
        log.info("Consumer. Notification service received order {} ", order);
        ack.acknowledge();
    }
}
