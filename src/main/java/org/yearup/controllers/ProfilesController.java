package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.models.Profile;

@RestController
@RequestMapping("/profiles")
@CrossOrigin
public class ProfilesController
{
    private final ProfileDao profileDao;

    @Autowired
    public ProfilesController(ProfileDao profileDao)
    {
        this.profileDao = profileDao;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Profile addProfile(@RequestBody Profile profile)
    {
        try
        {
            return profileDao.create(profile);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create profile.");
        }
    }
}
