package com.gaeng.springsecuritytest.repository;


import com.gaeng.springsecuritytest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByUsername(String userName);

    UserEntity findByUsername(String userName);
}
