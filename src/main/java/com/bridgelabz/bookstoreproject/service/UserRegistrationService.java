package com.bridgelabz.bookstoreproject.service;

import com.bridgelabz.bookstoreproject.dto.UserRegistrationDto;
import com.bridgelabz.bookstoreproject.exceptions.UserRegistrationException;
import com.bridgelabz.bookstoreproject.model.LoginDto;
import com.bridgelabz.bookstoreproject.model.UserRegistrationData;
import com.bridgelabz.bookstoreproject.repository.UserRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class UserRegistrationService implements  IUserRegistrationService{
    @Autowired
    UserRegistrationRepository userRepo;
    @Override
    public UserRegistrationData userRegistration(UserRegistrationDto userDTO) {
        UserRegistrationData userData = new UserRegistrationData(userDTO);
        return  userRepo.save(userData);
    }
    @Override
    public List<UserRegistrationData> getAllUsersData() {
        List<UserRegistrationData> usersList = userRepo.findAll();
        return usersList;
    }

    @Override
    public UserRegistrationData getUserById(int userId) {
        System.out.println("Userid:" +userId);
        return userRepo.findById(userId)
                .orElseThrow(() -> new UserRegistrationException("User  with id " + userId + " does not exist in database..!"));

    }

    @Override
    public UserRegistrationData updateUser(int userId, UserRegistrationDto userDTO) {
        UserRegistrationData userData = this.getUserById(userId);
        userData.updateUser(userDTO);
        return userRepo.save(userData);
    }

    @Override
    public UserRegistrationData getUserByEmailId(String emailId) {
        return userRepo.findUserListByEmail(emailId);
    }

    @Override
    public Optional<UserRegistrationData> UserLogin(LoginDto logindto) {
        Optional<UserRegistrationData> userLogin = userRepo.findByEmailIdAndPassword(logindto.emailId, logindto.password);

        if (userLogin.isPresent()) {
            log.info("LOGIN SUCCESSFUL");
            return userLogin;
        } else {
            log.error("User not Found Exception:");

            return null;
        }
    }


}
