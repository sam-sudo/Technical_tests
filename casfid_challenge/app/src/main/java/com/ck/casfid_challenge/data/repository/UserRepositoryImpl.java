package com.ck.casfid_challenge.data.repository;

import com.ck.casfid_challenge.data.local.LocalDatasource;
import com.ck.casfid_challenge.data.local.entity.UserEntity;
import com.ck.casfid_challenge.data.mapper.UserMapper;
import com.ck.casfid_challenge.data.model.UserDTO;
import com.ck.casfid_challenge.data.remote.RemoteUserDatasource;
import com.ck.casfid_challenge.domain.model.User;
import com.ck.casfid_challenge.domain.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private final RemoteUserDatasource remoteDataSource;
    private final LocalDatasource localDataSource;

    public UserRepositoryImpl(RemoteUserDatasource remoteDataSource, LocalDatasource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    @Override
    public List<User> getUsers() throws IOException {
        List<UserEntity> localUsers = localDataSource.getUsers();

        if (!localUsers.isEmpty()) {
            List<User> users = new ArrayList<>();
            for (UserEntity entity : localUsers) {
                users.add(UserMapper.entityToDomain(entity));
            }
            return users;
        }

        List<UserDTO> remoteUsers = remoteDataSource.fetchUsers(10);

        if (!remoteUsers.isEmpty()) {
            localDataSource.deleteAllUsers();

            List<User> domainUsers = new ArrayList<>();
            List<UserEntity> entities = new ArrayList<>();

            for (UserDTO dto : remoteUsers) {
                User user = UserMapper.dtoToDomain(dto);
                domainUsers.add(user);
                entities.add(UserMapper.domainToEntity(user));
            }

            localDataSource.insertUsers(entities);
            return domainUsers;
        } else {
            List<User> users = new ArrayList<>();
            for (UserEntity entity : localUsers) {
                users.add(UserMapper.entityToDomain(entity));
            }
            return users;
        }

    }

    @Override
    public List<User> refreshUsersFromAPI() throws IOException {
        List<UserDTO> remoteUsers = remoteDataSource.fetchUsers(10);

        if (!remoteUsers.isEmpty()) {
            localDataSource.deleteAllUsers();

            List<User> domainUsers = new ArrayList<>();
            List<UserEntity> entities = new ArrayList<>();

            for (UserDTO dto : remoteUsers) {
                User user = UserMapper.dtoToDomain(dto);
                domainUsers.add(user);
                entities.add(UserMapper.domainToEntity(user));
            }

            localDataSource.insertUsers(entities);
            return domainUsers;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<User> getUsersPaged(int page, int pageSize) throws IOException {
        List<UserDTO> dtos = remoteDataSource.getUsers(page, pageSize);
        List<User> users = new ArrayList<>();
        List<UserEntity> entities = new ArrayList<>();
        for (UserDTO dto : dtos) {
            users.add(UserMapper.dtoToDomain(dto));
            entities.add(UserMapper.domainToEntity(dto));
        }
        localDataSource.insertUsers(entities);
        return users;
    }
}

