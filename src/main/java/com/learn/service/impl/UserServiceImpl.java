package com.learn.service.impl;

import com.learn.common.UserStatus;
import com.learn.controller.request.UserCreationRequest;
import com.learn.controller.request.UserPasswordRequest;
import com.learn.controller.request.UserUpdateRequest;
import com.learn.controller.response.UserPageResponse;
import com.learn.controller.response.UserResponse;
import com.learn.exception.ResourceNotFoundException;
import com.learn.model.AddressEntity;
import com.learn.model.UserEntity;
import com.learn.repository.AddressRepository;
import com.learn.repository.UserRepository;
import com.learn.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
      Get user detail, get list user, find user
     */
@Service
@Slf4j(topic = "USER-SERVICE")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserPageResponse findAll(String keyword, String sort, int page, int size) {

        //Regex - Sort
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
        if (StringUtils.hasLength(sort)) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");//name column:asc/desc
            Matcher matcher = pattern.matcher(sort);
            if (matcher.find()) {
                String columnName = matcher.group(1);
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    order = new Sort.Order(Sort.Direction.ASC, columnName);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, columnName);
                }
            }
        }

        //Xử lý trường hợp FE muốn bắt đầu với page number = 1
        int pageNo = 0;
        if (page > 0) {
            pageNo = page - 1;
        }


        //paging
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(order));

        //Check null and blank keyword
        Page<UserEntity> entityPage = null;
        if (StringUtils.hasLength(keyword)) {
            //call search method
            keyword = "%" + keyword.toLowerCase() + "%";
            entityPage = userRepository.searchByKeyword(keyword, pageable);
        } else {

            entityPage = userRepository.findAll(pageable);
        }

        return getUserPageResponse(page, size, entityPage);
    }

    private static UserPageResponse getUserPageResponse(int page, int size, Page<UserEntity> users) {
        List<UserResponse> userList = users.stream().map(
                entity -> UserResponse.builder()
                        .id(entity.getId())
                        .firstName(entity.getFirstName())
                        .lastName(entity.getLastName())
                        .gender(entity.getGender())
                        .birthday(entity.getBirthday())
                        .username(entity.getUsername())
                        .phone(entity.getPhone())
                        .email(entity.getEmail())
                        .build()
        ).toList();
        //Return: page no, page size, list
        UserPageResponse response = new UserPageResponse();
        response.setPageNumber(page);
        response.setPageSize(size);
        response.setTotalElements(users.getTotalElements());
        response.setTotalPages(users.getTotalPages());
        response.setUsers(userList);
        return response;
    }

    @Override
    public UserResponse findById(Long id) {
        log.info("Find User by id: {}", id);

        UserEntity user = getUserById(id);

        //convert UserEntity to UserResponse
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender())
                .username(user.getUsername())
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
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

        if (userEntity.getId() != null) {
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
            if (addressEntity1 == null) {
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
        if (req.getPassword().equals(req.getConfirmPassword())) {
//            user.setPassword(req.getPassword());//chưa mã hóa
            user.setPassword(passwordEncoder.encode(req.getPassword()));//mã hóa và set password
        }
        userRepository.save(user);
    }

    private UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
