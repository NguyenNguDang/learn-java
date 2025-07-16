package com.learn.controller.request;

import com.learn.common.Gender;
import com.learn.model.AddressEntity;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class UserUpdateRequest implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private Gender gender;
    private Date birthday;
    private String email;
    private String phone;
    private List<AddressEntity> address;
}
