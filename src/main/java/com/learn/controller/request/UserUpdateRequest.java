package com.learn.controller.request;

import com.learn.common.Gender;
import com.learn.model.AddressEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class UserUpdateRequest implements Serializable {
    @NotNull(message = "Id must be not null")
    @Min(value = 1, message = "userId must be equal or greater than 1")
    private Long id;

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
    private List<AddressEntity> address;
}
