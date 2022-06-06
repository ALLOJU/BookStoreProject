package com.bridgelabz.bookstoreproject.repository;

import com.bridgelabz.bookstoreproject.model.OrderData;
import com.bridgelabz.bookstoreproject.model.UserRegistrationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderData, Integer> {



}
