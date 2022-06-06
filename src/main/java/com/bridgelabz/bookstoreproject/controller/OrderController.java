package com.bridgelabz.bookstoreproject.controller;

import com.bridgelabz.bookstoreproject.dto.OrderDto;
import com.bridgelabz.bookstoreproject.dto.ResponseDto;
import com.bridgelabz.bookstoreproject.model.OrderData;
import com.bridgelabz.bookstoreproject.service.IOrderService;
import com.bridgelabz.bookstoreproject.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    IOrderService orderService;
    @Autowired
    TokenUtil tokenUtil;

    /**
     * placeOrder -  inserting data from orderDto using service layer.
     * @param orderDto - input parameter as orderDto.
     * @return - returns response as order and message.
     */
    @PostMapping("/placeOrder")
    public ResponseEntity<ResponseDto> placeOrder(@RequestBody OrderDto orderDto) {

        //ResponseDto response= new ResponseDto("Order Placed Successfully","Order Details Order ID:" +  order.getId());
        return  orderService.placeOrder(orderDto);
    }

    /**
     * allOrders - get all orders from service layers
     * @return - it returns list of orders
     */
    @GetMapping("/allOrders")
    public ResponseEntity<ResponseDto> getAllOrders(){
        List<OrderData> orders = orderService.allOrders();
        ResponseDto response = new ResponseDto("Orders of user", orders);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * getUserOrders - get all orders by token using service layer.
     * @param token - it takes token as input parameter.
     * @return -returns orders from specific id.
     */
    @GetMapping("/userOrders/{token}")
    public ResponseEntity<ResponseDto> getUserOrders(@PathVariable("token") String token){
        int orderId = tokenUtil.decodeToken(token);
        OrderData userOrders = orderService.getOrderById(orderId);
        ResponseDto response = new ResponseDto("Orders of user", userOrders);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * cancelOrder - cancel orders for the given token,here we update the cancel from false to true
     * @param token - passing input param as token
     * @param userId
     * @return
     */
    @PutMapping("/cancelOrder/{token}/{userId}")
    public ResponseEntity<ResponseDto> cancelOrder(@PathVariable("token") String token,@PathVariable("userId") int userId){
        int orderId = tokenUtil.decodeToken(token);
        return orderService.cancelOrder(orderId,userId);

//        ResponseDto response=new ResponseDto("Cancel Order Succesfully",orderId,token);
//        return  new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * verifyUser- method to verify specific order
     * @param token - input for the order to verify
     * @return -it returns whether order has verified or not.
     */
    @GetMapping("/verify/{token}")
    public ResponseEntity<ResponseDto> verifyUser(@PathVariable String token) {
        return orderService.verify(token);
    }



}
