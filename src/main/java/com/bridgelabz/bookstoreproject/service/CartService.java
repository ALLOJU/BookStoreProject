package com.bridgelabz.bookstoreproject.service;

import com.bridgelabz.bookstoreproject.dto.Cartdto;
import com.bridgelabz.bookstoreproject.dto.ResponseDto;
import com.bridgelabz.bookstoreproject.model.BookDetails;
import com.bridgelabz.bookstoreproject.model.CartData;
import com.bridgelabz.bookstoreproject.model.Email;
import com.bridgelabz.bookstoreproject.model.UserRegistrationData;
import com.bridgelabz.bookstoreproject.repository.CartRepository;
import com.bridgelabz.bookstoreproject.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService
{
    @Autowired
    CartRepository cartRepo;
    @Autowired
    IUserRegistrationService userService;

    @Autowired
    BookDetailsService bookService;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    IEmailService emailService;

    /**
     * method to add data to cart
     * @param cartDTO -it consists of cart items
     * @return
     */
    @Override
    public ResponseEntity<ResponseDto> addToCart(Cartdto cartDTO) {
        Optional<UserRegistrationData> user = Optional.ofNullable(userService.getUserById(cartDTO.userId));
        if (user.isPresent()) {
            BookDetails book = bookService.getBookById(cartDTO.bookId);
            CartData cart = new CartData(user.get(), book, cartDTO.quantity);
            CartData cartData= cartRepo.save(cart);
            String token = tokenUtil.createToken(cartData.getCartId());
            System.out.println("Cart id is :"+cartData.getCartId());

            ResponseDto responseDto = new ResponseDto("Item Added to Cart", user.get().getUserId(),token);

            return new ResponseEntity<>(responseDto, HttpStatus.OK);

        }
        return null;
    }

    /**
     * showAllItems - get all items from the cart using findAll()  method from the repository layer
     * @return - it returns list of cart items
     */
    @Override
    public List<CartData> showAllItems() {
        List<CartData> allItems = (List<CartData>) cartRepo.findAll();
        System.out.println("AllBook" + allItems);
        return allItems;
    }

    /**
     * findAllInCart - returns cart details for the given cart id using repository layer from named queries
     * @param cartId - input parameter
     * @return - returns cart details
     */
    @Override
    public List<CartData> findAllInCart(int cartId) {
       List<CartData> cartItems = cartRepo.findAllCartsByCartId(cartId);
        System.out.println(cartItems);
        return cartItems;
    }

    /**
     * deleteFromCart - it remove cart details for the given cart id
     * @param cartId -input parameter
     */
    @Override
    public void deleteFromCart(int cartId) {
        cartRepo.deleteById(cartId);
    }

    /**
     * deleteAll - remove all the details from the cart
     */
    @Override
    public void deleteAll() {
        cartRepo.deleteAll();
    }

    /**
     * updateQuantity - update all details from the cart.
     * for updating cart we have to find cart details of particular cart id.
     * @param cartId - input parameter as cartId.
     * @param quantity
     * @return
     */
    @Override
    public CartData updateQuantity(int cartId, int quantity) {
        CartData cart = cartRepo.getById(cartId);

        cart.setQuantity(quantity);

        return cartRepo.save(cart);
    }


}
