package com.cvFWD.cvFWD.controller;

import com.cvFWD.cvFWD.domain.Profile;
import com.cvFWD.cvFWD.model.ProfileModel;
import com.cvFWD.cvFWD.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/profile")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity update(@RequestBody ProfileModel profileModel, String email) {
        Profile result = this.profileService.update(profileModel, email);
        if (result == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{email}")
    public ResponseEntity get(@PathVariable("email") String email) {
        Profile result = this.profileService.get(email);
        if (result == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
