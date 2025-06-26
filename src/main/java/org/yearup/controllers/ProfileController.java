package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;

import java.security.Principal;

@RestController
@CrossOrigin
@PreAuthorize("permitAll()")
public class ProfileController {

    private UserDao userDao;
    private ProfileDao profileDao;

    @Autowired
    public ProfileController(UserDao userDao, ProfileDao profileDao) {
        this.userDao = userDao;
        this.profileDao = profileDao;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ResponseEntity<Profile> getProfile(Principal principal) {

        //get the username
        String username = principal.getName();

        //get the id from the username
        int userId = userDao.getIdByUsername(username);

        return ResponseEntity.ok(profileDao.getProfile(userId));
    }

    @RequestMapping(value = "/profile", method = RequestMethod.PUT)
    public ResponseEntity<Profile> editProfile(@RequestBody Profile profile, Principal principal) {

        //get the username
        String username = principal.getName();

        //get the user Id from the Username
        int userId = userDao.getIdByUsername(username);

        //Get the profile attached to the userID
        profile.setUserId(userId);

        //Make the adjustments to the profile
        Profile adjustments = profileDao.editProfile(profile);

        return ResponseEntity.ok(profileDao.editProfile(adjustments));

    }



}
