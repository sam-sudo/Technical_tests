package com.ck.casfid_challenge.domain.model;

import java.io.Serializable;

public class User implements Serializable {
    public String fullName;
    public String email;
    public String phone;
    public UserPicture pictureUrl;
    public String address;
    public String gender;
    public String nat;
    public String cell;
    public Login login;

    public User(){}

    public User(String fullName, Login login, String cell, String nat, String gender, String address, UserPicture pictureUrl, String phone, String email) {
        this.fullName = fullName;
        this.login = login;
        this.cell = cell;
        this.nat = nat;
        this.gender = gender;
        this.address = address;
        this.pictureUrl = pictureUrl;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", nat='" + nat + '\'' +
                ", cell='" + cell + '\'' +
                ", login=" + login +
                '}';
    }
}

