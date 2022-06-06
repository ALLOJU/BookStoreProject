package com.bridgelabz.bookstoreproject.model;

import com.bridgelabz.bookstoreproject.dto.OrderDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Orders")
public class OrderData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private LocalDate orderDate;
    private int totalPrice;
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserRegistrationData user_id;
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId")
    private BookDetails book;

    private String address;

    private boolean cancel;

    public boolean verified;

    public OrderData(UserRegistrationData userId, BookDetails bookId, OrderDto orderDTO) {
        this.user_id = userId;
        this.book = bookId;
        orderData(orderDTO);
    }

    public void orderData(OrderDto orderDTO) {

        this.orderDate = orderDTO.orderDate;
        this.totalPrice = orderDTO.totalPrice;
        this.address=orderDTO.address;
    }
    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean getVerified() {
        return verified;
    }
}
