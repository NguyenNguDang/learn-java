package com.learn.controller;

import com.learn.controller.request.UserCreationRequest;
import com.learn.controller.request.UserPasswordRequest;
import com.learn.controller.request.UserUpdateRequest;
import com.learn.controller.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller")
public class UserController {

    @Operation(summary = "Get user list", description = "API retrieve user from db ")
    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(required = false) String keyword,
                                      @RequestParam(defaultValue = "0") int page,//number of page
                                      @RequestParam(defaultValue = "20") int size) { //Return how many record
        UserResponse userResponse1 = new UserResponse();
        userResponse1.setId(1L);
        userResponse1.setFirstName("John");
        userResponse1.setLastName("Doe");
        userResponse1.setGender("Male");
        userResponse1.setEmail("dddd00688@gmail.com");
        userResponse1.setBirthday(new Date());
        userResponse1.setUsername("admin");
        userResponse1.setPhone("123456789");

        UserResponse userResponse2 = new UserResponse();
        userResponse2.setId(2L);
        userResponse2.setFirstName("Leo");
        userResponse2.setLastName("Messi");
        userResponse2.setGender("Female");
        userResponse2.setEmail("m10@gmail.com");
        userResponse2.setBirthday(new Date());
        userResponse2.setUsername("user");
        userResponse2.setPhone("123456798");

        //List.of() to creat an immutable list
        List<UserResponse> userList = List.of(userResponse1, userResponse2);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());//200
        result.put("message", "user list");
        result.put("data", userList);


        return result;
    }

    @Operation(summary = "Get user detail", description = "API retrieve user detail by ID ")
    @GetMapping("/{userId}")
    public Map<String, Object> getUserDetail(@PathVariable Long userId) {
        UserResponse userDetail = new UserResponse();
        userDetail.setId(1L);
        userDetail.setFirstName("John");
        userDetail.setLastName("Doe");
        userDetail.setGender("Male");
        userDetail.setEmail("dddd00688@gmail.com");
        userDetail.setBirthday(new Date());
        userDetail.setUsername("admin");
        userDetail.setPhone("123456789");


        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());//200
        result.put("message", "user ");
        result.put("data", userDetail);

        return result;
    }

    @Operation(summary = "Creat User", description = "API add new user to db ")
    @PostMapping("/add")
    public Map<String, Object> createUser(UserCreationRequest request) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());//201
        result.put("message", "User created successfully");
        result.put("data", 3); //return an userId

        return result;
    }

    @Operation(summary = "Update User", description = "API update an user ")
    @PutMapping("/upd")
    public Map<String, Object> updateUser(UserUpdateRequest request) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.ACCEPTED.value());//202
        result.put("message", "User updated successfully");
        result.put("data", "");

        return result;
    }

    @Operation(summary = "Change Password", description = "API change password for user to database ")
    @PatchMapping("/change-pwd")
    public Map<String, Object> changePassword(UserPasswordRequest request) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.NO_CONTENT.value());//204
        result.put("message", "Password updated successfully");
        result.put("data", "");

        return result;
    }

    //Ko xóa thật được, csdl quan hệ
    @Operation(summary = "Inactivate User", description = "API inactivate user from database ")
    @DeleteMapping("/del/{userId}")
    public Map<String, Object> deleteUser(@PathVariable Long userId) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.RESET_CONTENT.value());//205
        result.put("message", "User deleted successfully");
        result.put("data", "");

        return result;
    }
}
