package com.bridgelabz.bookstoreproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ResponseDto {
    private String message;
    private Object data;

    private String token;


    public ResponseDto(String message, Object data,String token) {
        this.message = message;
        this.data = data;
        this.token=token;
    }


}
