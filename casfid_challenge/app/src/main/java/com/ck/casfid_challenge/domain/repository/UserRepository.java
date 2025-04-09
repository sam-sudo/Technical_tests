package com.ck.casfid_challenge.domain.repository;

import com.ck.casfid_challenge.domain.model.User;

import java.io.IOException;
import java.util.List;

public interface UserRepository {
    List<User> getUsers() throws IOException;
    List<User> refreshUsersFromAPI() throws IOException;
    List<User> getUsersPaged(int page, int pageSize) throws IOException;
}