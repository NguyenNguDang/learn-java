package com.learn.controller;

import com.learn.controller.request.UserCreationRequest;
import com.learn.controller.request.UserPasswordRequest;
import com.learn.controller.request.UserUpdateRequest;
import com.learn.controller.response.UserPageResponse;
import com.learn.controller.response.UserResponse;
import com.learn.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller")
@RequiredArgsConstructor
@Slf4j(topic= "USER_CONTROLLER")
@Validated
public class UserController {
    @Autowired
    private final UserService userService;

    @Operation(summary = "Get user list", description = "API retrieve user from db ")
    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(required = false) String keyword,
                                      @RequestParam(required = false) String sort,
                                      @RequestParam(defaultValue = "0") int page,//number of page
                                      @RequestParam(defaultValue = "20") int size) { //Return how many record
        log.info("Get user list");

        UserPageResponse userList = userService.findAll(keyword, sort , page , size );

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());//200
        result.put("message", "user list");
        result.put("data",userList );
        return result;
    }

    @Operation(summary = "Get user detail", description = "API retrieve user detail by ID ")
    @GetMapping("/{userId}")
    public Map<String, Object> getUserDetail(@PathVariable @Min(value = 1, message = "userId must be equal or greater than 1") Long userId) {
        log.info("Get user detail by ID {}", userId);

        UserResponse userDetail = userService.findById(userId);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());//200
        result.put("message", "user detail");
        result.put("data", userDetail);
        return result;
    }

    @Operation(summary = "Creat User", description = "API add new user to db ")
    @PostMapping("/add")
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserCreationRequest request) {
        log.info("Create user {}", request);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());//201
        result.put("message", "User created successfully");
        result.put("data", userService.save(request));

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Operation(summary = "Update User", description = "API update an user ")
    @PutMapping("/upd")
    public Map<String, Object> updateUser(@RequestBody @Valid UserUpdateRequest request) {
        log.info("Updating user {}", request);

        userService.update(request);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.ACCEPTED.value());//202
        result.put("message", "User updated successfully");
        result.put("data", "");

        return result;
    }

    @Operation(summary = "Change Password", description = "API change password for user to database ")
    @PatchMapping("/change-pwd")
    public Map<String, Object> changePassword(@RequestBody @Valid UserPasswordRequest request) {
        log.info("Changing password for user {}", request);

        userService.changePassword(request);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.NO_CONTENT.value());//204
        result.put("message", "Password updated successfully");
        result.put("data", "");

        return result;
    }

    //Ko xóa thật được, csdl quan hệ
    @Operation(summary = "Inactivate User", description = "API inactivate user from database ")
    @DeleteMapping("/del/{userId}")
    public Map<String, Object> deleteUser(@PathVariable @Min(value = 1, message = "userId must be equal or greater than 1") Long userId) {
        log.info("Deleting user {}", userId);

        userService.delete(userId);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.RESET_CONTENT.value());//205
        result.put("message", "User deleted successfully");
        result.put("data", "");

        return result;
    }
}
