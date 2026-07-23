package com.orderservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class InventoryServiceClient {

    private final RestClient restClient;

    public InventoryServiceClient(@Value("${inventory.service.url}") String baseUrl) {

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public ResponseEntity<Void> updateInventory(
            final Long eventId,
            final Long ticketCount) {
        restClient.put()
                .uri("/event/{eventId}/capacity/{ticketCount}", eventId, ticketCount)
                .retrieve()
                .toBodilessEntity();
        return ResponseEntity.ok().build();
    }

}
