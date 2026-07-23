package com.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventoryservice.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
