package com.bridgelabz.bookstoreproject.service;

import com.bridgelabz.bookstoreproject.dto.ResponseDto;
import com.bridgelabz.bookstoreproject.model.Email;
import org.springframework.http.ResponseEntity;


public interface IEmailService {
    public ResponseEntity<ResponseDto> sendMail(Email email);

    public String getLink(String token);
}
