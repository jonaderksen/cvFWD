package com.cvFWD.cvFWD.controller;

import com.cvFWD.cvFWD.domain.Project;
import com.cvFWD.cvFWD.model.ProjectModel;
import com.cvFWD.cvFWD.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/project")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<List<Project>> update(ProjectModel projectModel, String email) {
        List<Project> result = this.projectService.update(projectModel, email);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{email}")
    public ResponseEntity<List<Project>> get(@PathVariable("email") String email) {
        if (StringUtils.isEmpty(email)) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        List<Project> result = this.projectService.get(email);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
