package com.cvFWD.cvFWD.controller;

import com.cvFWD.cvFWD.model.CvModel;
import com.cvFWD.cvFWD.service.CvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jona on 23-11-2018.
 */
@RestController
@RequestMapping("/upload")
public class CvController {

    private final CvService cvService;

    @Autowired
    public CvController(CvService cvService) {
        this.cvService = cvService;
    }


}
