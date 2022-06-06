package com.bridgelabz.bookstoreproject.controller;

import com.bridgelabz.bookstoreproject.dto.Cartdto;
import com.bridgelabz.bookstoreproject.dto.ResponseDto;
import com.bridgelabz.bookstoreproject.model.CartData;
import com.bridgelabz.bookstoreproject.model.UserRegistrationData;
import com.bridgelabz.bookstoreproject.repository.CartRepository;
import com.bridgelabz.bookstoreproject.service.ICartService;
import com.bridgelabz.bookstoreproject.util.TokenUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public @Data class CartController {

    @Autowired
    public ICartService cartService;
    @Autowired
    CartRepository cartRepo;

    @Autowired
    TokenUtil tokenUtil;

    /**
     * addToCart- inserting data from cartdto to database using service layer
     * @param cartDto - input parameter
     * @return - returns response with inserted data
     */
    @PostMapping("/add")
    ResponseEntity<ResponseDto> addToCart(@RequestBody Cartdto cartDto)
    {
        return cartService.addToCart(cartDto);

    }

    /**
     * getAllItems - get all the details from service layer
     * @return - returns list of cart details
     */
    @GetMapping("/getAll")
    public ResponseEntity<ResponseDto> getAllItems() {
        List<CartData> allItems = cartService.showAllItems();
        ResponseDto dto = new ResponseDto("All Books Retrieved successfully:", allItems);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
//    @GetMapping("/get/{userId}")
//    ResponseEntity<ResponseDto> findAllCartsByUser(@PathVariable("userId") int userId) {
//        List<CartData> allItemsForUser = cartService.findAllInCart(userId);
//        System.out.println(allItemsForUser);
//        ResponseDto response = new ResponseDto("All Items in Cart for user ", allItemsForUser);
//        return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//    }

    /**
     * findAllCartsByUser - get cart details for the particular token which is passed as path variable in uri.
     * here im taken token for the security,then decode the token for getting cart id.
     * @param token - input parameter as token.
     * @return - returns cart details of particular cart id.
     */
    @GetMapping("/get/{token}")
ResponseEntity<ResponseDto> findAllCartsByUser(@PathVariable("token") String token) {
    int cartId = tokenUtil.decodeToken(token);
    System.out.println("cart id"+cartId);
    List<CartData> allItemsForUser = cartService.findAllInCart(cartId);
    System.out.println(allItemsForUser);
    ResponseDto response = new ResponseDto("All Items in Cart for user ", allItemsForUser);
    return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
}

    /**
     * removeFromCart - remove items from the cart using token
     * @param token -input parameter as token.
     * @return
     */
    @DeleteMapping("/remove/{token}")
    ResponseEntity<ResponseDto> removeFromCart(@PathVariable("token") String token) {
        int cartId = tokenUtil.decodeToken(token);
        cartService.deleteFromCart(cartId);
        ResponseDto response = new ResponseDto("Delete call success for item Removed From Cart ", "deleted id:" + cartId);
        return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
    }

    /**
     * removeAllFromCart - it removes all items from cart using cart service
     * @return
     */
    @DeleteMapping("/removeAll")
    ResponseEntity<ResponseDto> removeAllFromCart() {
        cartService.deleteAll();
        ResponseDto response = new ResponseDto("All Items deleted from cart");
        return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
    }

    /**
     * updateCart - it updates cart details like quantity.
     * here im passing cart id for the which cart to update data.
     * @param token -input parameter as token.
     * @param quantity - input parameter as quantity.
     * @return
     */
    @PutMapping("/update/{token}/{quantity}")
    ResponseEntity<ResponseDto> updateCart( @PathVariable("token") String token,
                                            @PathVariable("quantity") int quantity) {
        int cartId = tokenUtil.decodeToken(token);
        CartData cart = cartService.updateQuantity(cartId,quantity);
        ResponseDto response = new ResponseDto("Update call success for item Quantity updated From Cart ", cart);
        return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
    }



}
