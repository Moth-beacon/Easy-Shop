package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.UserDao;
import org.yearup.models.User;
import org.yearup.models.authentication.RegisterUserDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController
{
    private final UserDao userDao;

    @Autowired
    public UserController(UserDao userDao)
    {
        this.userDao = userDao;
    }

    @GetMapping
    public List<User> getAll()
    {
        return userDao.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id)
    {
        return userDao.getUserById(id);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody RegisterUserDto dto)
    {
        try
        {
            // Check that passwords match
            if (!dto.getPassword().equals(dto.getConfirmPassword()))
            {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match.");
            }

            // Create and register new user
            User newUser = new User();
            newUser.setUsername(dto.getUsername());
            newUser.setPassword(dto.getPassword());
            newUser.addRole(dto.getRole());

            return userDao.create(newUser);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to register user.");
        }
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user)
    {
        try
        {
            User found = userDao.getByUserName(user.getUsername());

            if (found == null)
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password.");

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            boolean passwordMatches = encoder.matches(user.getPassword(), found.getPassword());

            if (!passwordMatches)
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password.");

            // Return a fake token for now
            Map<String, String> result = new HashMap<>();
            result.put("token", "fake-jwt-token");
            return result;
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Login failed.");
        }
    }
}
