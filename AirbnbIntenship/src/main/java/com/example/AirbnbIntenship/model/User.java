package com.example.AirbnbIntenship.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; @Column(nullable = false)

    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String fullName;

    private String phoneNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<Property> properties;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Review> reviews;
}