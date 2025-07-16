package com.learn.repository;

import com.learn.model.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Integer> {
    AddressEntity findByUserIdAndAddressType(Long userId, Integer addressType);
}
