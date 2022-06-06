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
    /**
     * here injecting dependency from UserRegistration service to UserRegistration Controller
     * using @Autowired annotation
     */
    private IUserRegistrationService service;


    /**
     * This method will call the service layer to insert a new record into the db. It will return an error message if the
     *  address record to be inserted has any invalid fields.
     *  here it takes values from userDto and by using servicelayer insert records ro repository layer
     * @param userDto - input parameter for insert data
     * @return -it returns json data with message and data.
     */
    @PostMapping("/register")
public ResponseEntity<ResponseDto> createAccount(@RequestBody UserRegistrationDto userDto){
    return service.userRegistration(userDto);
}

    /**
     * getAllUser - get all details of the user from the database using service layer
     * @return - it returns list of users
     * @throws UserRegistrationException - throws exception when users list is empty
     */
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

    /**
     * getUserData - this method used to get the userdetails based on the given user id given by
     * path.this method used getMapping to get data from database.
     * @param userId- here we passing userid from the uri using @Pathvariable annotation.
     * @return - returns specific userdata based on userid.
     */
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

    /**
     * updateContactData- this method is used to update user data which is taken from userDto class.
     * @param userId -userid of specific user for updating its details.
     * @param token - instead of userid we can also pass token for updating data.
     * @param userDTO - dto class for giving data
     * @return -returns updated data for entered userid.
     */
    @PutMapping("/update/{userId}/{token}")
    public ResponseEntity<ResponseDto> updateContactData(@PathVariable("userId") int userId,@PathVariable("token") String token,
                                                         @Valid @RequestBody UserRegistrationDto userDTO) {
        UserRegistrationData userData = service.updateUser(token,userId, userDTO);
        ResponseDto response = new ResponseDto("Updated user data for", userData,token);
        return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

    }

    /**
     * getUsersByEmail - list out the user details for a particular email id
     * @param emailId - input for getting user details of particular email.
     * @return - returns user data.
     */
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

    /**
     * userLogin - controller for user login,here we passing login data
     * @param logindto - as input parameter
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> userLogin(@RequestBody LoginDto logindto) {
        return service.loginUser(logindto);
    }

    /**
     * verifyUser- method to verify specific user
     * @param token - input for the user to verify
     * @return -it returns whether user has verified or not.
     */
    @GetMapping("/verify/{token}")
    public ResponseEntity<ResponseDto> verifyUser(@PathVariable String token) {
        return service.verify(token);
    }

}
