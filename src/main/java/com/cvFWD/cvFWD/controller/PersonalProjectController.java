package com.cvFWD.cvFWD.controller;

import com.cvFWD.cvFWD.domain.PersonalProject;
import com.cvFWD.cvFWD.model.DeleteModel;
import com.cvFWD.cvFWD.model.PersonalProjectModel;
import com.cvFWD.cvFWD.service.PersonalProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/personalproject")
public class PersonalProjectController {
    private final PersonalProjectService personalProjectService;

    @Autowired
    public PersonalProjectController(PersonalProjectService personalProjectService) {
        this.personalProjectService = personalProjectService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody PersonalProjectModel personalProjectModel, String email) {
        List<PersonalProject> result = this.personalProjectService.create(personalProjectModel, email);
        if (result == null) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{email}")
    public ResponseEntity<List<PersonalProject>> get(@PathVariable("email") String email) {
        List<PersonalProject> result = this.personalProjectService.get(email);
        if (result == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<List<PersonalProject>> update(@RequestBody PersonalProject personalProject, String email){
        List<PersonalProject> result = this.personalProjectService.update(personalProject, email);
        if (result == null) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody DeleteModel deleteModel) {
        this.personalProjectService.delete(deleteModel);
        return new ResponseEntity(HttpStatus.OK);
    }
}
