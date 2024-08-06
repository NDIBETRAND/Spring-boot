package com.example.AirbnbIntenship.service;

import com.example.AirbnbIntenship.model.User;

import java.util.Optional;

public interface UserService {
    User findUserProfileByJwt(String jwt)throws Exception;

    User findUserByEmail(String email)throws Exception;


    Optional<User> findUserById(Long userId)throws  Exception;


}