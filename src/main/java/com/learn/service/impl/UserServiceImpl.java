package com.learn.service.impl;

import com.learn.common.UserStatus;
import com.learn.controller.request.UserCreationRequest;
import com.learn.controller.request.UserPasswordRequest;
import com.learn.controller.request.UserUpdateRequest;
import com.learn.controller.response.UserResponse;
import com.learn.exception.ResourceNotFoundException;
import com.learn.model.AddressEntity;
import com.learn.model.UserEntity;
import com.learn.repository.AddressRepository;
import com.learn.repository.UserRepository;
import com.learn.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder;

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

    //Xóa mềm
    @Override
    public void delete(Long id) {
        log.info("Deleting user {}", id);

        //Get user by id
        UserEntity user = getUserById(id);
        user.setStatus(UserStatus.INACTIVE);

        userRepository.save(user);
        log.info("Deleted user {}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserUpdateRequest req) {
        //Get user by id
        log.info("Updating user {}", req);
        UserEntity user = getUserById(req.getId());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setEmail(req.getEmail());
        user.setUsername(req.getUsername());
        user.setGender(req.getGender());
        user.setBirthday(req.getBirthday());
        user.setPhone(req.getPhone());

        userRepository.save(user);
        log.info("Updated user {}", user);
        //save address
        List<AddressEntity> addresses = new ArrayList<>();

        req.getAddress().forEach(addressEntity -> {
            AddressEntity addressEntity1 = addressRepository.findByUserIdAndAddressType(req.getId(), addressEntity.getAddressType());
            if(addressEntity1 == null) {
                addressEntity1 = new AddressEntity();
            }
            addressEntity1.setApartmentNumber(addressEntity.getApartmentNumber());
            addressEntity1.setFloor(addressEntity.getFloor());
            addressEntity1.setBuilding(addressEntity.getBuilding());
            addressEntity1.setStreetNumber(addressEntity.getStreetNumber());
            addressEntity1.setStreet(addressEntity.getStreet());
            addressEntity1.setCity(addressEntity.getCity());
            addressEntity1.setCountry(addressEntity.getCountry());
            addressEntity1.setAddressType(addressEntity.getAddressType());
            addressEntity1.setUserId(user.getId());

            addresses.add(addressEntity1);
        });

        // set data to database
        addressRepository.saveAll(addresses);
        log.info("Updated address {}", addresses);
    }

    @Override
    public void changePassword(UserPasswordRequest req) {
        log.info("Changing password for user {}", req);

        //Get user by Id
        UserEntity user = getUserById(req.getId());
        if(req.getPassword().equals(req.getConfirmPassword())) {
//            user.setPassword(req.getPassword());//chưa mã hóa
            user.setPassword(passwordEncoder.encode(req.getPassword()));//mã hóa và set password
        }
        userRepository.save(user);
    }
    /*
        Api update user, change password, delete user
     */
    private UserEntity getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
    }
}
