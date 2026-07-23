package com.bookingservice.mapper;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.bookingservice.entity.Customer;
import com.bookingservice.event.BookingEvent;
import com.bookingservice.request.BookingRequest;
import com.bookingservice.response.InventoryResponse;

@Component
public class BookingMapper {

    public BookingEvent toBookingEvent(BookingRequest request,
            Customer customer,
            InventoryResponse inventoryResponse) {
        return BookingEvent.builder()
                .userId(customer.getId())
                .eventId(request.getEventId())
                .ticketCount(request.getTicketCount())
                .totalPrice(
                        inventoryResponse.getTicketPrice()
                                .multiply(BigDecimal.valueOf(request.getTicketCount())))
                .build();
    }

}
