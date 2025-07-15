package com.learn.controller.request;

import com.learn.common.UserType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserCreationRequest implements Serializable { //request = payload
    private String firstName;
    private String lastName;
    private String username;
    private String gender;
    private Date birthday;
    private String email;
    private String phone;
    private UserType userType;
    private List<AddressRequest> address;//home, office
}
