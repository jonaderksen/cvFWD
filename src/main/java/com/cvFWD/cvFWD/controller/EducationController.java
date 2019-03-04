package com.cvFWD.cvFWD.controller;

import com.cvFWD.cvFWD.domain.Education;
import com.cvFWD.cvFWD.model.EducationModel;
import com.cvFWD.cvFWD.service.EducationSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/education")
public class EducationController {
    private final EducationSerice educationSerice;

    @Autowired
    public EducationController(EducationSerice educationSerice) {
        this.educationSerice = educationSerice;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity update(EducationModel educationModel, String email) {
        List<Education> result = this.educationSerice.update(educationModel, email);
        if (result == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{email}")
    public ResponseEntity get(@PathVariable("email") String email) {
        List<Education> result = this.educationSerice.get(email);
        if (result == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
