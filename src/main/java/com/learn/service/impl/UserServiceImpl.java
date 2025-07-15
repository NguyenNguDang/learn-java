package com.learn.service.impl;

import com.learn.common.UserStatus;
import com.learn.controller.request.UserCreationRequest;
import com.learn.controller.request.UserPasswordRequest;
import com.learn.controller.request.UserUpdateRequest;
import com.learn.controller.response.UserResponse;
import com.learn.model.AddressEntity;
import com.learn.model.UserEntity;
import com.learn.repository.AddressRepository;
import com.learn.repository.UserRepository;
import com.learn.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j(topic="USER-SERVICE")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    @Override
    public List<UserResponse> findAll() {
        return List.of();
    }

    @Override
    public UserResponse findById(int id) {
        return null;
    }

    @Override
    public UserResponse findByEmail(String email) {
        return null;
    }

    @Override
    public UserResponse findByUsername(String username) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)//if exception then roll back user
    public Long save(UserCreationRequest req) {
        log.info("Saving user {}", req);
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(req.getFirstName());
        userEntity.setLastName(req.getLastName());
        userEntity.setEmail(req.getEmail());
        userEntity.setUsername(req.getUsername());
        userEntity.setGender(req.getGender());
        userEntity.setBirthday(req.getBirthday());
        userEntity.setPhone(req.getPhone());
        userEntity.setType(req.getUserType());
        userEntity.setStatus(UserStatus.NONE);
        userRepository.save(userEntity);

        if(userEntity.getId() != null) {
            log.info("User id is {}", userEntity.getId());
            List<AddressEntity> address = new ArrayList<>();
            req.getAddress().forEach(addressEntity -> {
                AddressEntity addressEntity1 = new AddressEntity();
                addressEntity1.setApartmentNumber(addressEntity.getApartmentNumber());
                addressEntity1.setFloor(addressEntity.getFloor());
                addressEntity1.setBuilding(addressEntity.getBuilding());
                addressEntity1.setStreetNumber(addressEntity.getStreetNumber());
                addressEntity1.setStreet(addressEntity.getStreet());
                addressEntity1.setCity(addressEntity.getCity());
                addressEntity1.setCountry(addressEntity.getCountry());
                addressEntity1.setAddressType(addressEntity.getAddressType());
                addressEntity1.setUserId(userEntity.getId());
                address.add(addressEntity1);
            });
            addressRepository.saveAll(address);
            log.info("Saved address {}", address);
        }

        return userEntity.getId();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(UserUpdateRequest req) {

    }

    @Override
    public void changePassword(UserPasswordRequest req) {

    }
}
