package com.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookingservice.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
