package com.learn.controller.request;

import com.learn.common.Gender;
import com.learn.common.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@ToString
public class UserCreationRequest implements Serializable { //request = payload
    @NotBlank(message = "firstName must be not blank")
    private String firstName;

    @NotBlank(message = "lastName must be not blank")
    private String lastName;

    private String username;
    private Gender gender;
    private Date birthday;
    @Email(message = "Email invalid")
    private String email;
    private String phone;
    private UserType userType;
    private List<AddressRequest> address;//home, office
}
