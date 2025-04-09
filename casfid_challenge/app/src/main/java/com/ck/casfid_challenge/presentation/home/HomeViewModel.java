package com.ck.casfid_challenge.presentation.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ck.casfid_challenge.domain.model.User;
import com.ck.casfid_challenge.domain.usercase.GetUsersPageUseCase;
import com.ck.casfid_challenge.domain.usercase.GetUsersUseCase;
import com.ck.casfid_challenge.domain.usercase.RefreshUsersFromAPIUseCase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private final GetUsersUseCase getUsersUseCase;
    private final RefreshUsersFromAPIUseCase refreshUsersFromAPIUseCase;
    private final GetUsersPageUseCase getUsersPaginationUseCase;
    private final MutableLiveData<List<User>> _users = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> _errorMessage = new MutableLiveData<>();

    private final List<User> currentUsers = new ArrayList<>();
    private int currentPage = 1;
    private final int pageSize = 10;

    public HomeViewModel(GetUsersUseCase getUsersUseCase, GetUsersPageUseCase getUsersPaginationUseCase, RefreshUsersFromAPIUseCase refreshUsersFromAPIUseCase) {
        this.getUsersUseCase = getUsersUseCase;
        this.getUsersPaginationUseCase = getUsersPaginationUseCase;
        this.refreshUsersFromAPIUseCase = refreshUsersFromAPIUseCase;
    }

    public LiveData<List<User>>  getUsers() {
        return _users;
    }

    public void updateUsersFromDB(){
        if (Boolean.TRUE.equals(_isLoading.getValue())) return;

        _isLoading.setValue(true);
        new Thread(() -> {
            try {
                List<User> result = getUsersUseCase.execute();
                if (currentUsers.isEmpty()) {
                    currentUsers.addAll(result);
                    _users.postValue(currentUsers);
                } else {
                    _users.postValue(currentUsers); // Solo actualiza LiveData
                }
            } catch (IOException e) {
                _errorMessage.postValue(e.getMessage());
            } finally {
                _isLoading.postValue(false);
            }
        }).start();
    }

    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return _errorMessage;
    }

    public void loadNextPage() {
        if (Boolean.TRUE.equals(_isLoading.getValue())) return;

        _isLoading.setValue(true);
        new Thread(() -> {
            try {
                List<User> result = getUsersPaginationUseCase.execute(currentPage, pageSize);
                currentUsers.addAll(result);
                _users.postValue(currentUsers);
                currentPage++;
            } catch (IOException e) {
                _errorMessage.postValue(e.getMessage());
            } finally {
                _isLoading.postValue(false);
            }
        }).start();
    }

    public void refresh() {
        if (Boolean.TRUE.equals(_isLoading.getValue())) return;

        _isLoading.setValue(true);

        new Thread(() -> {
            try {
                List<User> result = refreshUsersFromAPIUseCase.execute();
                if (!result.isEmpty()) {
                    currentPage = 1;
                    currentUsers.clear();
                    currentUsers.addAll(result);
                    _users.postValue(currentUsers);
                }

            } catch (IOException e) {
                _errorMessage.postValue(e.getMessage());
            } finally {
                _isLoading.postValue(false);
            }
        }).start();
    }

    public void clearError() {
        _errorMessage.setValue(null);
    }


}
