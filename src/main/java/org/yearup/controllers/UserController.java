package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.UserDao;
import org.yearup.models.User;

import java.util.List;

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
}
