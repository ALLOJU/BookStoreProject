package com.bridgelabz.bookstoreproject.service;

import com.bridgelabz.bookstoreproject.dto.OrderDto;
import com.bridgelabz.bookstoreproject.dto.ResponseDto;
import com.bridgelabz.bookstoreproject.exceptions.UserRegistrationException;
import com.bridgelabz.bookstoreproject.model.BookDetails;
import com.bridgelabz.bookstoreproject.model.Email;
import com.bridgelabz.bookstoreproject.model.OrderData;
import com.bridgelabz.bookstoreproject.model.UserRegistrationData;
import com.bridgelabz.bookstoreproject.repository.BookDetailsRepository;
import com.bridgelabz.bookstoreproject.repository.OrderRepository;
import com.bridgelabz.bookstoreproject.repository.UserRegistrationRepository;
import com.bridgelabz.bookstoreproject.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService{
    @Autowired
    UserRegistrationRepository userRepo;

    @Autowired
    OrderRepository orderRepo;
    @Autowired
    BookDetailsRepository bookRepo;

    @Autowired
    BookDetailsService bookService;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    IEmailService emailService;

    /**
     * placeOrder-method to insert data to database using repository layer.here  we use service layer to call
     * repository layer,in this case we insert data for specific user by giving userid and bookid
     * to insert data. we also calculated totalPrice by taking book price and quantity of the book
     * here -im also generated token using JWT using algorithm to encode data for the particular order id
     * i'm also taken JMS send email for the order details using token
     * @param orderDto - input param for the order
     * @return - it return response as inserted data with message.
     */
    @Override
    public ResponseEntity<ResponseDto> placeOrder(OrderDto orderDto) {
        Optional<UserRegistrationData> userData = userRepo.findById(orderDto.getUserId());
        Optional<BookDetails> bookData=bookRepo.findByBookId(orderDto.getBookId());
        System.out.println("user: "+userData);
        int totalPrice=bookData.get().getBookPrice() * bookData.get().getBookQuantity();
        if (userData.isPresent() && bookData.isPresent()) {
            OrderData order = new OrderData(userData.get(),bookData.get(),orderDto);
            order.setTotalPrice(totalPrice);

            OrderData orderData= orderRepo.save(order);
            System.out.println("order id is :"+order.getId());
            String token = tokenUtil.createToken(orderData.getId());

            Email email = new Email(userData.get().getEmailId(),"Order placed Successfully",userData.get().getFirstName() + "=>" + emailService.getOrder(token));
            emailService.sendMail(email);
            ResponseDto responseDto = new ResponseDto("Order Placed", userData.get().getUserId(),token);

            return new ResponseEntity<>(responseDto,HttpStatus.OK);
        }
        else{
            throw new UserRegistrationException("data not found");
        }

    }

    /**
     * allOrders - get all orders from repository using findAll().
     * @return - returns list of orders from the database.
     */

    @Override
    public List<OrderData> allOrders() {
       return  orderRepo.findAll();

    }

    /**
     * getOrderById - it returns orders of the particular id
     * @param orderId - input parameter for the specific order
     * @return - returns orders
     */
    @Override
    public OrderData getOrderById(int orderId) {

        return orderRepo.findById(orderId).orElseThrow( () -> new UserRegistrationException("order id not found"));

    }

    /**
     * cancelOrder - it cancels order,here it not removing data only updates the cancel from false to true
     * means data has updated.
     * @param orderId
     * @param userId
     * @return
     */
    @Override
    public ResponseEntity<ResponseDto> cancelOrder(int orderId,int userId) {


            Optional<OrderData> order = orderRepo.findById(orderId);
            Optional<UserRegistrationData> userData=userRepo.findById(userId);
            System.out.println("user Data"+userData);
            String token=tokenUtil.createToken(orderId);
            if (order.isPresent()) {
                order.get().setCancel(true);
                orderRepo.save(order.get());
                System.out.println("token"+token);
                Email email = new Email(userData.get().getEmailId(),"Order Canceled Successfully",userData.get().getFirstName() + "=>" +"Your Order has been Cancelled");
                emailService.sendMail(email);

                ResponseDto responseDto = new ResponseDto("Order Canceled", userData.get().getUserId(),token);

                return new ResponseEntity<>(responseDto,HttpStatus.OK);
            }
            else{
                throw new UserRegistrationException("data not found");
            }
}
    /**
     * verify - this method is used to verify data whether it false or true.
     * @param token - token for verification of user id.
     * @return - returns verification message with the token.
     */
    @Override
    public ResponseEntity<ResponseDto> verify(String token) {
        Optional<OrderData> order=orderRepo.findById(tokenUtil.decodeToken(token));
        if (order.isEmpty()) {
            ResponseDto responseDTO = new ResponseDto("ERROR: Invalid token", null, token);
            return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.UNAUTHORIZED);
        }
        order.get().setVerified(true);
        orderRepo.save(order.get());
        ResponseDto responseDTO = new ResponseDto(" The user has been verified ", order, token);
        return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
    }


}



