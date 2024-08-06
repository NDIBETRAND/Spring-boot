package com.example.AirbnbIntenship.Repository;

import com.example.AirbnbIntenship.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByEmail(String email);
}
