package com.bookingservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bookingservice.client.InventoryServiceClient;
import com.bookingservice.entity.Customer;
import com.bookingservice.event.BookingEvent;
import com.bookingservice.mapper.BookingMapper;
import com.bookingservice.repository.CustomerRepository;
import com.bookingservice.request.BookingRequest;
import com.bookingservice.response.BookingResponse;
import com.bookingservice.response.InventoryResponse;

import lombok.extern.slf4j.Slf4j;

@Service
// Logger with Lombok
@Slf4j
public class BookingService {

    private final CustomerRepository customerRepository;
    private final InventoryServiceClient inventoryServiceClient;
    private final BookingMapper bookingMapper;
    private final KafkaTemplate<String, BookingEvent> kafkaTemplate;
    @Value("${kafka.topic.booking}")
    private String bookingTopic;

    //Manual Logger Creation below
    //private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    public BookingService(final CustomerRepository customerRepository,
            final InventoryServiceClient inventoryServiceClient,
            final BookingMapper bookingMapper,
            final KafkaTemplate<String, BookingEvent> kafkaTemplate) {
        this.customerRepository = customerRepository;
        this.inventoryServiceClient = inventoryServiceClient;
        this.bookingMapper = bookingMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public BookingResponse createBooking(BookingRequest request) {
        final Customer customer = customerRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        final InventoryResponse inventoryResponse = inventoryServiceClient.getInventory(request.getEventId());

        log.info("Inventory Service Response" + inventoryResponse);

        if (inventoryResponse.getCapacity() < request.getTicketCount()) {
            throw new RuntimeException("Not enough inventory!");
        }

        final BookingEvent bookingEvent = bookingMapper.toBookingEvent(request, customer, inventoryResponse);

        kafkaTemplate.send(bookingTopic, bookingEvent);

        return BookingResponse.builder()
                .userId(bookingEvent.getUserId())
                .eventId(bookingEvent.getEventId())
                .ticketCount(bookingEvent.getTicketCount())
                .totalPrice(bookingEvent.getTotalPrice())
                .build();
    }

}
