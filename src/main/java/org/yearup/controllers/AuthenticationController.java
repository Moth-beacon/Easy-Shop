package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.UserDao;
import org.yearup.models.User;
import org.yearup.models.authentication.RegisterUserDto;

@RestController
@RequestMapping("/")
@CrossOrigin
public class AuthenticationController
{
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(UserDao userDao, PasswordEncoder passwordEncoder)
    {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody RegisterUserDto dto)
    {
        try
        {
            if (!dto.getPassword().equals(dto.getConfirmPassword()))
            {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
            }

            if (userDao.exists(dto.getUsername()))
            {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
            }

            String hashedPassword = passwordEncoder.encode(dto.getPassword());
            User newUser = new User();
            newUser.setUsername(dto.getUsername());
            newUser.setPassword(hashedPassword);
            newUser.addRole(dto.getRole());

            userDao.create(newUser);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Registration failed");
        }
    }
}
