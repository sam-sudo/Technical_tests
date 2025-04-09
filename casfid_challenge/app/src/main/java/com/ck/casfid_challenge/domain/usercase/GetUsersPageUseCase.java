package com.ck.casfid_challenge.domain.usercase;

import com.ck.casfid_challenge.domain.model.User;
import com.ck.casfid_challenge.domain.repository.UserRepository;

import java.io.IOException;
import java.util.List;

public class GetUsersPageUseCase {
    private final UserRepository userRepository;

    public GetUsersPageUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> execute(int page, int pageSize) throws IOException {
        return userRepository.getUsersPaged(page, pageSize);
    }
}

