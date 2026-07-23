package com.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventoryservice.entity.Venue;

public interface VenueRepository extends JpaRepository<Venue, Long> {

}
