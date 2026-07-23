package com.bookingservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.bookingservice.response.InventoryResponse;

@Service
public class InventoryServiceClient {

    private final RestClient restClient;

    public InventoryServiceClient(@Value("${inventory.service.url}") String baseUrl) {

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public InventoryResponse getInventory(Long eventId) {
        return restClient.get()
                .uri("/events/{id}", eventId)
                .retrieve()
                .body(InventoryResponse.class);
    }

}
