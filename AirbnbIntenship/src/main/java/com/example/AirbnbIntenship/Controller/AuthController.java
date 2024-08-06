package com.example.AirbnbIntenship.Controller;
import com.example.AirbnbIntenship.Configuration.JwtProvider;
import com.example.AirbnbIntenship.Repository.UserRepository;
import com.example.AirbnbIntenship.Response.AuthResponse;
import com.example.AirbnbIntenship.model.User;
import com.example.AirbnbIntenship.request.LoginRequest;
import com.example.AirbnbIntenship.serviceImpl.CustomUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsImpl customUserDetails;

    @PostMapping("api/auth/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) {

        if (user.getPassword().length() <= 8){
            AuthResponse res= new AuthResponse();
            res.setMessage("Password must be greater than 8 characters");
            return  new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }



        User createdUser = new User();
        createdUser.setUsername(user.getUsername());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());
        createdUser.setPhoneNumber(user.getPhoneNumber());
        User savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthResponse res= new AuthResponse();
        res.setMessage("Sign up successful");
        res.setMessage(JwtProvider.generateToken(authentication));
        return  new ResponseEntity<>(res,HttpStatus.CREATED);

    }

    @PostMapping("api/auth/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        try {
            Authentication authentication = authenticate(username, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);


            AuthResponse res= new AuthResponse();
            res.setMessage("Login successful");
            res.setMessage(JwtProvider.generateToken(authentication));
            return  new ResponseEntity<>(res,HttpStatus.OK);


        } catch (BadCredentialsException e) {
            AuthResponse res= new AuthResponse();
            res.setMessage("Invalid credentials");

            return new ResponseEntity<>(res,HttpStatus.UNAUTHORIZED);
        }
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}