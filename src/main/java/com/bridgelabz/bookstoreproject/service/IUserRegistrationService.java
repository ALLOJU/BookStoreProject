package com.bridgelabz.bookstoreproject.service;

import com.bridgelabz.bookstoreproject.dto.UserRegistrationDto;
import com.bridgelabz.bookstoreproject.model.LoginDto;
import com.bridgelabz.bookstoreproject.model.UserRegistrationData;

import java.util.List;
import java.util.Optional;

public interface IUserRegistrationService {
    UserRegistrationData userRegistration(UserRegistrationDto userDTO);

    List<UserRegistrationData> getAllUsersData();

    UserRegistrationData getUserById(int userId);

    UserRegistrationData updateUser(int userId, UserRegistrationDto userDTO);

    UserRegistrationData getUserByEmailId(String emailId);

    Optional<UserRegistrationData> UserLogin(LoginDto logindto);
}
