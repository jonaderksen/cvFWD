package com.cvFWD.cvFWD.controller;

import com.cvFWD.cvFWD.domain.PersonalProject;
import com.cvFWD.cvFWD.model.PersonalProjectModel;
import com.cvFWD.cvFWD.service.PersonalProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/personalproject")
public class PersonalProjectController {
    private final PersonalProjectService personalProjectService;

    @Autowired
    public PersonalProjectController(PersonalProjectService personalProjectService) {
        this.personalProjectService = personalProjectService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity upload(PersonalProjectModel personalProjectModel, String email) {
        List<PersonalProject> result = this.personalProjectService.update(personalProjectModel, email);
        if (result == null) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{email}")
    public ResponseEntity<List<PersonalProject>> get(@PathVariable("email") String email) {
        List<PersonalProject> result = this.personalProjectService.get(email);
        if (result == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
