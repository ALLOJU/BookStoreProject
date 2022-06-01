package com.bridgelabz.bookstoreproject.controller;

import com.bridgelabz.bookstoreproject.dto.ResponseDto;
import com.bridgelabz.bookstoreproject.dto.UserRegistrationDto;
import com.bridgelabz.bookstoreproject.exceptions.UserRegistrationException;
import com.bridgelabz.bookstoreproject.model.LoginDto;
import com.bridgelabz.bookstoreproject.service.IUserRegistrationService;
import com.bridgelabz.bookstoreproject.model.UserRegistrationData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/userregistration")
public class UserRegistrationController {
    @Autowired
    private IUserRegistrationService service;



@PostMapping("/register")
public ResponseEntity<ResponseDto> createAccount(@RequestBody UserRegistrationDto userDto){
    return service.userRegistration(userDto);
}

    @GetMapping("/getAllUser")
    public ResponseEntity<ResponseDto> readdata() throws UserRegistrationException {
        List<UserRegistrationData> users = null;
        users = service.getAllUsersData();
        if (users.size() > 0) {
            ResponseDto responseDTO = new ResponseDto("all user Fetched successfully", users,null);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            throw new UserRegistrationException("No Data Found");
        }
    }
    @GetMapping("/get/{userId}")
    public ResponseEntity<ResponseDto> getUserData(@PathVariable("userId") int userId){

        UserRegistrationData users = service.getUserById(userId);

        if (users!=null) {
            ResponseDto responseDTO = new ResponseDto("User Fetched successfully", users,null);
            return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
        } else {
            throw new UserRegistrationException("No Data Found");
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ResponseDto> updateContactData(@PathVariable("userId") int userId,
                                                         @Valid @RequestBody UserRegistrationDto userDTO) {
        UserRegistrationData userData = service.updateUser(userId, userDTO);
        ResponseDto response = new ResponseDto("Updated user data for", userData,null);
        return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

    }
    @GetMapping("/email/{emailId}")
    public ResponseEntity<ResponseDto> getUsersByEmail(@PathVariable String emailId) {
        UserRegistrationData userData = null;
        userData = service.getUserByEmailId(emailId);

        if (userData != null) {
            ResponseDto response = new ResponseDto("Get Call Users List By email is Successful", userData,null);
            return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
        } else {
            log.info("email id block");

            throw new UserRegistrationException("No Data Found");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> userLogin(@RequestBody LoginDto logindto) {
        return service.loginUser(logindto);
    }
    @GetMapping("/verify/{token}")
    public ResponseEntity<ResponseDto> verifyUser(@PathVariable String token) {
        return service.verify(token);
    }

}
