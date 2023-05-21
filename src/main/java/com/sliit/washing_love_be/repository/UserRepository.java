package com.sliit.washing_love_be.repository;

import com.sliit.washing_love_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);
}
