package com.bridgelabz.bookstoreproject.service;

import com.bridgelabz.bookstoreproject.dto.Cartdto;
import com.bridgelabz.bookstoreproject.dto.ResponseDto;
import com.bridgelabz.bookstoreproject.model.CartData;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface ICartService {

    ResponseEntity<ResponseDto> addToCart(Cartdto cartDto);

    List<CartData> showAllItems();

    List<CartData> findAllInCart(int cartId);

    void deleteFromCart(int cartId);

    void deleteAll();

    CartData updateQuantity(int cartId, int quantity);
}
