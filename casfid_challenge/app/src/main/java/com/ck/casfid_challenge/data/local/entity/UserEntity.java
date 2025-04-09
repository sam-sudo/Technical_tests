package com.ck.casfid_challenge.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {

    @PrimaryKey
    @NonNull
    public String uuid;
    public String fullName;
    public String email;
    public String username;
    public String phone;
    public String cell;
    public String pictureLarge;
    public String pictureThumbnail;
    public String address;
    public String password;
    public String gender;
    public String nat;
}

