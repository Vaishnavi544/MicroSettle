package com.project.liquidchange.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users") // This tells Java: "I map to the 'users' table in SQL"
@Data // This Lombok annotation automatically writes Getters/Setters for us!
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String phone;

    private String role; // 'DRIVER' or 'RIDER'
}