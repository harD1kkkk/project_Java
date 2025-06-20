package com.example.demo.repository;

import com.example.demo.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

}
