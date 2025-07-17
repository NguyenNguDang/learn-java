package com.learn.service;

import com.learn.controller.request.UserCreationRequest;
import com.learn.controller.request.UserPasswordRequest;
import com.learn.controller.request.UserUpdateRequest;
import com.learn.controller.response.UserPageResponse;
import com.learn.controller.response.UserResponse;

import java.util.List;

public interface UserService {
    UserPageResponse findAll(String keyword, String sort, int page, int size);
    UserResponse findById(Long id);
    UserResponse findByEmail(String email);
    UserResponse findByUsername(String username);
    Long save(UserCreationRequest req);
    void delete(Long id);
    void update(UserUpdateRequest req);
    void changePassword(UserPasswordRequest req);

}
