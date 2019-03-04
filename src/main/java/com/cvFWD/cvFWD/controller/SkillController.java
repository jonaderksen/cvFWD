package com.cvFWD.cvFWD.controller;

import com.cvFWD.cvFWD.domain.Skill;
import com.cvFWD.cvFWD.model.SkillModel;
import com.cvFWD.cvFWD.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/skill")
public class SkillController {
    private final SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity upload(SkillModel skillModel, String email) {
        List<Skill> result = this.skillService.update(skillModel, email);
        if (result == null) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{email}")
    public ResponseEntity<List<Skill>> get(@PathVariable("email") String email) {
        List<Skill> result = this.skillService.get(email);
        if (result == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }
}