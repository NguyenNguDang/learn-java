package com.learn.service;

import com.learn.controller.request.UserCreationRequest;
import com.learn.controller.request.UserPasswordRequest;
import com.learn.controller.request.UserUpdateRequest;
import com.learn.controller.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> findAll();
    UserResponse findById(int id);
    UserResponse findByEmail(String email);
    UserResponse findByUsername(String username);
    Long save(UserCreationRequest req);
    void delete(Long id);
    void update(UserUpdateRequest req);
    void changePassword(UserPasswordRequest req);

}
