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
    @Override
    public ResponseEntity<ResponseDto> userRegistration(UserRegistrationDto userDto) {

        UserRegistrationData user= userRepo.save(new UserRegistrationData(userDto));

        String token = tokenUtil.createToken(user.getUserId());

        Email email = new Email(user.getEmailId()," user is registered",user.getFirstName() + "=>" + emailService.getLink(token));
        emailService.sendMail(email);
        ResponseDto responseDto = new ResponseDto("User is created", user,token);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

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
