package com.orderservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.bookingservice.event.BookingEvent;
import com.orderservice.client.InventoryServiceClient;
import com.orderservice.entity.Order;
import com.orderservice.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryServiceClient inventoryServiceClient;

    public OrderService(OrderRepository orderRepository, InventoryServiceClient inventoryServiceClient) {
        this.orderRepository = orderRepository;
        this.inventoryServiceClient = inventoryServiceClient;
    }

    @KafkaListener(topics = "${kafka.topic.booking}", groupId = "${spring.application.name}")
    public void orderEvent(BookingEvent bookingEvent) {
        log.info("Order recieved {}", bookingEvent);
        Order order = createOrder(bookingEvent);

        orderRepository.saveAndFlush(order);

        inventoryServiceClient.updateInventory(order.getEventId(), order.getTicketCount());
        log.info(null);
    }

    private Order createOrder(BookingEvent bookingEvent) {
        return Order.builder()
                .customerId(bookingEvent.getUserId())
                .eventId(bookingEvent.getEventId())
                .ticketCount(bookingEvent.getTicketCount())
                .totalPrice(bookingEvent.getTotalPrice())
                .build();
    }

}
