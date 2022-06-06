package com.bridgelabz.bookstoreproject.service;

import com.bridgelabz.bookstoreproject.dto.ResponseDto;
import com.bridgelabz.bookstoreproject.dto.UserRegistrationDto;
import com.bridgelabz.bookstoreproject.model.LoginDto;
import com.bridgelabz.bookstoreproject.model.UserRegistrationData;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IUserRegistrationService {
//    UserRegistrationData userRegistration(UserRegistrationDto userDTO);

    List<UserRegistrationData> getAllUsersData();

    UserRegistrationData getUserById(int userId);

    UserRegistrationData updateUser(String token,int userId, UserRegistrationDto userDTO);

    UserRegistrationData getUserByEmailId(String emailId);


    ResponseEntity<ResponseDto> loginUser(LoginDto logindto);

    ResponseEntity<ResponseDto> userRegistration(UserRegistrationDto userDto);

    ResponseEntity<ResponseDto> verify(String token);
}
