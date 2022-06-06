package com.bridgelabz.bookstoreproject.service;

import com.bridgelabz.bookstoreproject.dto.ResponseDto;
import com.bridgelabz.bookstoreproject.dto.UserRegistrationDto;
import com.bridgelabz.bookstoreproject.exceptions.UserRegistrationException;
import com.bridgelabz.bookstoreproject.model.Email;
import com.bridgelabz.bookstoreproject.model.LoginDto;
import com.bridgelabz.bookstoreproject.model.UserRegistrationData;
import com.bridgelabz.bookstoreproject.repository.UserRegistrationRepository;
import com.bridgelabz.bookstoreproject.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class UserRegistrationService implements  IUserRegistrationService{
    @Autowired
    UserRegistrationRepository userRepo;
    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    IEmailService emailService;

    /**
     * userRegistration - this method is used to save data into database using save method in
     * user repository
     * it also used to generate token for given userid for provide security  using JWT.
     * and also send mail to user with the verification message using JMS.
     * @param userDto  - input parameter to pass data.
     * @return - it returns inserted data with user details.
     */
    @Override
    public ResponseEntity<ResponseDto> userRegistration(UserRegistrationDto userDto) {

        UserRegistrationData user= userRepo.save(new UserRegistrationData(userDto));

        String token = tokenUtil.createToken(user.getUserId());

        Email email = new Email(user.getEmailId()," user is registered",user.getFirstName() + "=>" + emailService.getLink(token));
        emailService.sendMail(email);
        ResponseDto responseDto = new ResponseDto("User is created", user,token);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    /**
     * verify - this method is used to verify data whether it false or true.
     * @param token - token for verification of user id.
     * @return - returns verification message with the token.
     */
    @Override
    public ResponseEntity<ResponseDto> verify(String token) {
        Optional<UserRegistrationData> user=userRepo.findById(tokenUtil.decodeToken(token));
        if (user.isEmpty()) {
            ResponseDto responseDTO = new ResponseDto("ERROR: Invalid token", null, token);
            return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.UNAUTHORIZED);
        }
        user.get().setVerified(true);
        userRepo.save(user.get());
        ResponseDto responseDTO = new ResponseDto(" The user has been verified ", user, token);
        return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
    }

    /**
     * getAllUsersData - this method is used get all details of the users using findAll()
     * method of the JPA repository.
     * @return - lists out all the users details.
     */
    @Override
    public List<UserRegistrationData> getAllUsersData() {
        List<UserRegistrationData> usersList = userRepo.findAll();
        return usersList;
    }

    /**
     * getUserById - get details of the specific userid using findById() of the
     * JPA repository. if the userid is not found in the database then it throws Exception as
     * given userid not found using exception handler method.
     * @param userId - user id of the given particular user.
     * @return -returns user data for the specific user
     */
    @Override
    public UserRegistrationData getUserById(int userId) {
        System.out.println("Userid:" +userId);
        return userRepo.findById(userId)
                .orElseThrow(() -> new UserRegistrationException("User  with id " + userId + " does not exist in database..!"));

    }

    /**
     * updateUser - it updates the user details which are taken from dto class
     * @param token - input token
     * @param userId
     * @param userDTO
     * @return
     */
    @Override
    public UserRegistrationData updateUser(String token,int userId, UserRegistrationDto userDTO) {
        UserRegistrationData userData = this.getUserById(userId);
        userData.updateUser(userDTO);
        return userRepo.save(userData);
    }

    /**
     * getUserByEmailId - get the user details for the particular user id with  findUserListByEmail
     * using query provided in repo.
     * @param emailId
     * @return
     */
    @Override
    public UserRegistrationData getUserByEmailId(String emailId) {
        return userRepo.findUserListByEmail(emailId);
    }

    /**
     * loginUser - it checks the email id and password of the user.if the details are correct
     * then login successfully other given message with login failed.
     * @param logindto
     * @return
     */
    @Override
    public ResponseEntity<ResponseDto> loginUser(LoginDto logindto) {
        Optional<UserRegistrationData> user=userRepo.findUserRegistrationDataByEmailId(logindto.getEmailId());
        boolean password=user.get().getPassword().equals(logindto.getPassword());
        if(password=false){
            ResponseDto responseDto=new ResponseDto("login failed",null,null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED);
        }
        else{
            ResponseDto responseDto=new ResponseDto(" Login Sucessfully",user,null);
            return new ResponseEntity<>(responseDto,HttpStatus.OK);
        }
    }


}
