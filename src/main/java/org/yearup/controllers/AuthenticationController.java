package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.UserDao;
import org.yearup.models.User;
import org.yearup.models.authentication.LoginDto;
import org.yearup.models.authentication.RegisterUserDto;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {

    private final UserDao userDao;

    @Autowired
    public AuthenticationController(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterUserDto dto) {
        User newUser = new User();
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(dto.getPassword()); // Password should be hashed in real scenarios
        newUser.setAuthorities(dto.getRole());

        userDao.create(newUser);
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginDto dto) {
        User user = userDao.getByUserName(dto.getUsername());
        if (user != null && user.getPassword().equals(dto.getPassword())) {
            return user;
        }
        throw new RuntimeException("Invalid login credentials");
    }
}
