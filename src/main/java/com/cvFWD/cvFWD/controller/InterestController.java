package com.cvFWD.cvFWD.controller;

import com.cvFWD.cvFWD.domain.Interest;
import com.cvFWD.cvFWD.model.InterestModel;
import com.cvFWD.cvFWD.service.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/interest")
public class InterestController {
    private final InterestService interestService;

    @Autowired
    public InterestController(InterestService interestService) {
        this.interestService = interestService;
    }

    @PostMapping
    public ResponseEntity<String> update(@RequestBody InterestModel interestModel, String email) {
        Interest result = this.interestService.update(interestModel, email);
        if (result == null) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{email}")
    public ResponseEntity<String> get(@PathVariable("email") String email) {
        Interest result = this.interestService.get(email);
        if (result == null) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
