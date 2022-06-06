package com.bridgelabz.bookstoreproject.dto;

import com.bridgelabz.bookstoreproject.model.UserRegistrationData;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    public int totalPrice;
//    @JsonFormat(pattern = "dd-MM-yyyy")
    public LocalDate orderDate=LocalDate.now();
    public int userId;

    public int bookId;

    public String address;



}
