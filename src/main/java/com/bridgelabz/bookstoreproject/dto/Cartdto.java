package com.bridgelabz.bookstoreproject.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Cartdto {
    public int userId;
    public int bookId;
    public int quantity;


}
