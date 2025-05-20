package com.example.demo.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name="Roles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", nullable = false, length = 50, columnDefinition = "VARCHAR(255)")
    private String name;

    @OneToMany(mappedBy = "role")
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<User> users = new HashSet<>();
}
