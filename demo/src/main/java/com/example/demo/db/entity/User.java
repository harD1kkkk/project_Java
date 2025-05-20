package com.example.demo.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "User")
@Table(name="Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 50, columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 50, columnDefinition = "VARCHAR(255)")
    private String email;

    @ManyToOne
    @JoinColumn(name="role_id", nullable = false)
    private UserRole role;
}
