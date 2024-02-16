package com.mentoring.avro.controller;

import com.mentoring.avro.model.Order;
import com.mentoring.avro.service.OrderProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RequestMapping("/orders")
@RestController
public class OrderController {
    private final OrderProducerService orderProducerService;

    public OrderController(OrderProducerService orderProducerService) {
        this.orderProducerService = orderProducerService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) throws ExecutionException, InterruptedException {
        orderProducerService.sendCreateOrderEvent(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
