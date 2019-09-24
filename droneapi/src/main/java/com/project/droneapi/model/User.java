package com.project.droneapi.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class User {
    @Id
    @GeneratedValue
    private String userID;
    @NotNull
    private String userName;
    @NotNull
    private String password;
    @NotNull
    private String companyName;
    @NotNull
    private String firstName;
    @NotNull
    private String lastname;
    @Email
    private String email;
    @NotNull
    private String phoneNumber;

    public User(String userID,String userName ,String password,String firstName,String lastName,String companyName,String email,String phoneNumber){
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastname = lastName;
        this.companyName =companyName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeleNumber() {
        return phoneNumber;
    }

    public void setTeleNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
