package com.example.demo.repository;

import com.example.demo.db.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<UserRole, Integer> {}
