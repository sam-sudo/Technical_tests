package com.ck.casfid_challenge.domain.usercase;

import com.ck.casfid_challenge.domain.model.User;
import com.ck.casfid_challenge.domain.repository.UserRepository;

import java.io.IOException;
import java.util.List;

public class GetUsersUseCase {
    private final UserRepository userRepository;

    public GetUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> execute() throws IOException {
        return userRepository.getUsers();
    }
}
