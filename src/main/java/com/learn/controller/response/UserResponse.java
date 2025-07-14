package com.learn.controller.response;

import lombok.Setter;
import java.io.Serializable;
import java.util.Date;


@Setter
public class UserResponse implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String gender;
    private Date birthday;
    private String email;
    private String phone;
    //more
}
