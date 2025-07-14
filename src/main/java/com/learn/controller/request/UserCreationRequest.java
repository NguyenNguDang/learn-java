package com.learn.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class UserCreationRequest implements Serializable {
    private String firstName;
    private String lastName;
    private String username;
    private String gender;
    private Date birthday;
    private String email;
    private String phone;
}
