package com.learn.controller.response;

import com.learn.common.Gender;
import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private Gender gender;
    private Date birthday;
    private String email;
    private String phone;
    //more
}
