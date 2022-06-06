package com.bridgelabz.bookstoreproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Cart")
@Data
public class CartData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cartId;

    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserRegistrationData user;
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId")
    private BookDetails book;

    private int quantity;



    public CartData(UserRegistrationData user, BookDetails book, int quantity) {
        this.user = user;
        this.book = book;
        this.quantity = quantity;

    }


    public CartData() {

    }
}
