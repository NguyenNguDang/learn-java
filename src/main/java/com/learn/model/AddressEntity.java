package com.learn.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "tbl_address")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name ="apartment_number", length = 50)
    private String apartmentNumber;

    @Column(name ="floor", length = 50)
    private String floor;

    @Column(name ="building", length = 50)
    private String building;

    @Column(name ="street_number", length = 50)
    private String streetNumber;

    @Column(name ="street", length = 50)
    private String street;

    @Column(name ="city", length = 50)
    private String city;

    @Column(name ="country", length = 50)
    private String country;

    @Column(name ="address_type", length = 50)
    private Integer addressType;

    @Column(name ="user_id", length = 50)
    private Long userId;

    @Column(name ="created_at", length = 50)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp//autofill date in database
    private Date createdAt;

    @Column(name ="updated_at", length = 50)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp//auto update date in database
    private Date updatedAt;
}
