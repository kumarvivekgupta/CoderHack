package com.crio.coderhack.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crio.coderhack.entity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    List<UserEntity> findAll();

    Optional<UserEntity> findByUsername(String name);

    List<UserEntity> findAllByOrderByScoreAsc();
    Optional<UserEntity> findById(Long id);

    void deleteById(Long userId);

    
}
