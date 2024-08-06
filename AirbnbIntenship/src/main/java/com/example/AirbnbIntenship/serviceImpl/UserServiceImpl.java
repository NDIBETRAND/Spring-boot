package com.example.AirbnbIntenship.serviceImpl;

import com.example.AirbnbIntenship.Configuration.JwtProvider;
import com.example.AirbnbIntenship.Repository.UserRepository;
import com.example.AirbnbIntenship.model.User;
import com.example.AirbnbIntenship.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private  UserRepository userRepository;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email= JwtProvider.getEmailFromToken(jwt);
        User user=userRepository.findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Optional<User> findUserById(Long userId) throws Exception {
        return userRepository.findById(userId);
    }
}
