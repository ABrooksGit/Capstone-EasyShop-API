package org.yearup.data;


import org.springframework.http.ResponseEntity;
import org.yearup.models.Profile;
import org.yearup.models.User;

public interface ProfileDao
{
    Profile create(Profile profile);

    Profile editProfile(int userId);


    Profile getProfile(int userId);
}
