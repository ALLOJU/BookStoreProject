package com.bridgelabz.bookstoreproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookStoredto {
    public String bookName;
    public String bookAuthor;
    public String bookDescription;
    public String bookLogo;
    public int bookPrice;
    public int bookQuantity;
}
