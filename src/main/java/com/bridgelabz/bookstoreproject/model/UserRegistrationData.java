package com.bridgelabz.bookstoreproject.model;

import com.bridgelabz.bookstoreproject.dto.UserRegistrationDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "userregistration_table")
public @Data class UserRegistrationData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "address")
    private String address;
    @Column
    private String password;

    public boolean verified;

    public UserRegistrationData() {
    }

    public UserRegistrationData(UserRegistrationDto userDTO) {
        this.updateUser(userDTO);
    }

    public void updateUser(UserRegistrationDto userDTO) {
        this.firstName = userDTO.firstName;
        this.lastName = userDTO.lastName;
        this.emailId = userDTO.emailId;
        this.address=userDTO.address;
        this.password = userDTO.password;

    }
    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean getVerified() {
        return verified;
    }

}
