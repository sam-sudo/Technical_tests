package com.ck.casfid_challenge.data.remote;

import com.ck.casfid_challenge.data.mapper.UserMapper;
import com.ck.casfid_challenge.data.model.UserDTO;
import com.ck.casfid_challenge.data.model.UserResponseDTO;
import com.ck.casfid_challenge.data.remote.api.ApiService;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class RemoteUserDatasource {
    private final ApiService apiService;

    public RemoteUserDatasource(ApiService apiService) {
        this.apiService = apiService;
    }

    public List<UserDTO> fetchUsers(int count) throws IOException {
        Response<UserResponseDTO> response = apiService.getUsers(count).execute();
        if (response.isSuccessful() && response.body() != null) {
            return response.body().getResults();
        }
        return null;
    }

    public List<UserDTO> getUsers(int page, int pageSize) throws IOException {
        Response<UserResponseDTO> response = apiService.getUsersPaged(page, pageSize).execute();
        if (response.isSuccessful() && response.body() != null) {
            return response.body().getResults();
        } else {
            throw new IOException("Failed to fetch users");
        }
    }

}
