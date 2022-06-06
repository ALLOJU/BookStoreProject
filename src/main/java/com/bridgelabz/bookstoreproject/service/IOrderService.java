package com.bridgelabz.bookstoreproject.service;

import com.bridgelabz.bookstoreproject.dto.OrderDto;
import com.bridgelabz.bookstoreproject.dto.ResponseDto;
import com.bridgelabz.bookstoreproject.model.OrderData;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IOrderService {
    ResponseEntity<ResponseDto> placeOrder(OrderDto orderDto);

    List<OrderData> allOrders();


    OrderData getOrderById(int userId);

    ResponseEntity<ResponseDto> cancelOrder(int orderId,int userId);
    ResponseEntity<ResponseDto> verify(String token);
}
