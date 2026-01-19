package com.epicman.ecommerce_api.controller;

import com.epicman.ecommerce_api.dto.UserLoginRequestDto;
import com.epicman.ecommerce_api.dto.UserRegisterRequestDto;
import com.epicman.ecommerce_api.exception.NotFoundException;
import com.epicman.ecommerce_api.mapper.UserMapper;
import com.epicman.ecommerce_api.model.UserModel;
import com.epicman.ecommerce_api.service.UserService;
import com.epicman.ecommerce_api.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequestDto userRequestDto) throws Exception {
        UserModel createdUser = userService.registerUser(userRequestDto.getUsername(), userRequestDto.getPassword(), userRequestDto.getRole());

        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toResponse(createdUser));
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) throws Exception {
        UserModel user = userService.findByUsername(userLoginRequestDto.getUsername());

        if (user == null) {
            throw new NotFoundException("User could not be found");
        }

        return jwtUtil.generateToken(user.getUsername(), user.getRole());
    }
}
