package com.project.droneapi.model;

public class User {
    private String userName;
    private String password;
    private String companyName;
    private String firstName;
    private String lastname;
    private String email;
    private String teleNumber;

    public User(String userName ,String password,String firstName,String lastName,String companyName,String email,String teleNumber){
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastname = lastName;
        this.companyName =companyName;
        this.email = email;
        this.teleNumber = teleNumber;
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
        return teleNumber;
    }

    public void setTeleNumber(String teleNumber) {
        this.teleNumber = teleNumber;
    }
}
