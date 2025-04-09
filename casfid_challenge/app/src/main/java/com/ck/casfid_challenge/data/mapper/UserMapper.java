package com.ck.casfid_challenge.data.mapper;

import com.ck.casfid_challenge.data.local.entity.UserEntity;
import com.ck.casfid_challenge.data.model.UserDTO;
import com.ck.casfid_challenge.domain.model.Login;
import com.ck.casfid_challenge.domain.model.User;
import com.ck.casfid_challenge.domain.model.UserPicture;

public class UserMapper {
    public static User dtoToDomain(UserDTO dto) {
        User user = new User();
        Login login = new Login();

        if (dto.login != null) {
            login.uuid = dto.login.uuid;
            login.username = dto.login.username;
            login.password = dto.login.password;
        }

        UserPicture picture = new UserPicture();
        if (dto.picture != null) {
            picture.large = dto.picture.getLarge();
            picture.thumbnail = dto.picture.getThumbnail();
        }

        user.fullName = dto.name != null ? dto.name.first + " " + dto.name.last : "N/A";
        user.cell = dto.cell != null ? dto.cell : "N/A";
        user.nat = dto.nat != null ? dto.nat : "N/A";
        user.gender = dto.gender != null ? dto.gender : "N/A";
        user.login = login;
        user.email = dto.email != null ? dto.email : "N/A";
        user.phone = dto.phone != null ? dto.phone : "N/A";
        user.pictureUrl = picture;

        if (dto.location != null && dto.location.street != null) {
            user.address = dto.location.street.number + " " + dto.location.street.name + ", " +
                    dto.location.city + ", " + dto.location.state + ", " + dto.location.country;
        } else {
            user.address = "N/A";
        }

        return user;
    }

    public static UserEntity domainToEntity(UserDTO dto) {
        UserEntity userEntity = new UserEntity();
        Login login = new Login();

        if (dto.login != null) {
            login.uuid = dto.login.uuid;
            login.username = dto.login.username;
            login.password = dto.login.password;
        }

        UserPicture picture = new UserPicture();
        if (dto.picture != null) {
            picture.large = dto.picture.getLarge();
            picture.thumbnail = dto.picture.getThumbnail();
        }

        userEntity.fullName = dto.name != null ? dto.name.first + " " + dto.name.last : "N/A";
        userEntity.cell = dto.cell != null ? dto.cell : "N/A";
        userEntity.nat = dto.nat != null ? dto.nat : "N/A";
        userEntity.gender = dto.gender != null ? dto.gender : "N/A";
        userEntity.password = login.password;
        userEntity.username = login.username;
        userEntity.uuid = login.uuid;
        userEntity.email = dto.email != null ? dto.email : "N/A";
        userEntity.phone = dto.phone != null ? dto.phone : "N/A";
        userEntity.pictureThumbnail = picture.thumbnail;
        userEntity.pictureLarge = picture.large;

        if (dto.location != null && dto.location.street != null) {
            userEntity.address = dto.location.street.number + " " + dto.location.street.name + ", " +
                    dto.location.city + ", " + dto.location.state + ", " + dto.location.country;
        } else {
            userEntity.address = "N/A";
        }

        return userEntity;
    }

    public static UserEntity domainToEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.uuid = user.login.uuid;
        entity.fullName = user.fullName;
        entity.email = user.email;
        entity.username = user.login.username;
        entity.phone = user.phone;
        entity.cell = user.cell;
        entity.pictureLarge = user.pictureUrl != null ? user.pictureUrl.large : null;
        entity.pictureThumbnail = user.pictureUrl != null ? user.pictureUrl.thumbnail : null;
        entity.password = user.login.password;
        entity.gender = user.gender;
        entity.nat = user.nat;
        return entity;
    }

    public static User entityToDomain(UserEntity entity) {
        UserPicture picture = new UserPicture(entity.pictureLarge, entity.pictureThumbnail);

        Login login = new Login();
        login.uuid = entity.uuid;
        login.username = entity.username;
        login.password = entity.password;

        return new User(
                entity.fullName,
                login,
                entity.cell,
                entity.nat,
                entity.gender,
                entity.address,
                picture,
                entity.phone,
                entity.email
        );
    }

}