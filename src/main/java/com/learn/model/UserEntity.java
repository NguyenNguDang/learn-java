package com.learn.model;

import com.learn.common.Gender;
import com.learn.common.UserStatus;
import com.learn.common.UserType;
import com.learn.controller.request.AddressRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "tbl_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name ="first_name", length = 50)
    private String firstName;

    @Column(name ="last_name", length = 50)
    private String lastName;

    @Column(name="username", unique = true, nullable = false, length = 20)
    private String username;

    @Column(name ="password", length = 300)
    private String password;

    @Column(name ="gender", length = 50)
    private Gender gender;

    @Column(name ="date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(name ="email", length = 50)
    private String email;

    @Column(name ="phone", length = 15)
    private String phone;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name ="type", length = 50)
    private UserType type; // datatype = enum

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name ="status", length = 50)
    private UserStatus status;

    @Column(name ="created_at", length = 50)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp//autofill date in database
    private Date createdAt;

    @Column(name ="updated_at", length = 50)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp//auto update date in database
    private Date updatedAt;


}
