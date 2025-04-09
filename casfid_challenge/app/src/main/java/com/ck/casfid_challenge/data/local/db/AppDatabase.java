package com.ck.casfid_challenge.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ck.casfid_challenge.data.local.dao.UserDao;
import com.ck.casfid_challenge.data.local.entity.UserEntity;

@Database(entities = {UserEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
