package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.UserDao;
import org.yearup.models.User;

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

    @PostMapping
    public User addUser(@RequestBody User user)
    {
        return userDao.create(user);
    }

    @GetMapping("/{userName}")
    public User getByUserName(@PathVariable String userName)
    {
        return userDao.getByUserName(userName);
    }
}
