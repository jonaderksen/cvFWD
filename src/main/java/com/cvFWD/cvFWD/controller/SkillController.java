package com.cvFWD.cvFWD.controller;

import com.cvFWD.cvFWD.domain.Skill;
import com.cvFWD.cvFWD.model.DeleteModel;
import com.cvFWD.cvFWD.model.SkillModel;
import com.cvFWD.cvFWD.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/skill")
public class SkillController {
    private final SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    public ResponseEntity<List<Skill>> create(@RequestBody SkillModel skillModel, String email) {
        List<Skill> result = this.skillService.create(skillModel, email);
        if (result == null) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{email}")
    public ResponseEntity<List<Skill>> get(@PathVariable("email") String email) {
        List<Skill> result = this.skillService.get(email);
        if (result == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<List<Skill>> update(@RequestBody Skill skill, String email){
        List<Skill> result = this.skillService.update(skill, email);
        if (result == null) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody DeleteModel deleteModel) {
        this.skillService.delete(deleteModel);
        return new ResponseEntity(HttpStatus.OK);
    }
}
