package com.ck.casfid_challenge.data.local;

import com.ck.casfid_challenge.data.local.dao.UserDao;
import com.ck.casfid_challenge.data.local.db.AppDatabase;
import com.ck.casfid_challenge.data.local.entity.UserEntity;
import com.ck.casfid_challenge.data.mapper.UserMapper;
import com.ck.casfid_challenge.domain.model.User;

import java.util.ArrayList;
import java.util.List;

public class LocalDatasource {
    private final UserDao userDao;

    public LocalDatasource(AppDatabase db) {
        this.userDao = db.userDao();
    }

    public List<UserEntity> getUsers() {
        return userDao.getUsers();
    }

    public void deleteAllUsers(){
        userDao.deleteAllUsers();
    }

    public void insertUsers(List<UserEntity> users) {
        userDao.insertUsers(users);
    }
}

