package com.ck.casfid_challenge.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ck.casfid_challenge.data.local.entity.UserEntity;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users ORDER BY rowid DESC LIMIT 100")
    List<UserEntity> getUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(List<UserEntity> users);

    @Query("DELETE FROM users")
    void deleteAllUsers();
}

