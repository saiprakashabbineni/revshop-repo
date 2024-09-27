package com.project.revshop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "userdetails")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String userName;
    private String userEmail;
    private String userPassword;
    private String userRole;
    private String userAddress;
    private Long userMobileNumber;

    @OneToOne(mappedBy = "usermodel", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private SellerModel sellermodel;

    // Constructors
    public UserModel() {
    }

    public UserModel(String userName, String userEmail, String userPassword, String userRole, String userAddress, Long userMobileNumber) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userRole = userRole;
        this.userAddress = userAddress;
        this.userMobileNumber = userMobileNumber;
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public Long getUserMobileNumber() {
        return userMobileNumber;
    }

    public void setUserMobileNumber(Long userMobileNumber) {
        this.userMobileNumber = userMobileNumber;
    }

    public SellerModel getSellermodel() {
        return sellermodel;
    }

    public void setSellermodel(SellerModel sellermodel) {
        this.sellermodel = sellermodel;
    }
}
