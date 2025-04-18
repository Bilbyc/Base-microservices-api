package com.base.users.model;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String user;

    @Column(unique = true)
    private String email;

    private String password;

//    private String role;
}
