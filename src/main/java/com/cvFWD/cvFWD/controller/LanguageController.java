package com.cvFWD.cvFWD.controller;

import com.cvFWD.cvFWD.domain.Language;
import com.cvFWD.cvFWD.model.DeleteModel;
import com.cvFWD.cvFWD.model.LanguageModel;
import com.cvFWD.cvFWD.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/language")
public class LanguageController {
    private final LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody LanguageModel languageModel, String email) {
        List<Language> result = this.languageService.create(languageModel, email);
        if (result == null) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{email}")
    public ResponseEntity<List<Language>> get(@PathVariable("email") String email) {
        List<Language> result = this.languageService.get(email);
        if (result == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<List<Language>> update(@RequestBody Language language, String email){
        List<Language> result = this.languageService.update(language, email);
        if (result == null) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody DeleteModel deleteModel) {
        this.languageService.delete(deleteModel);
        return new ResponseEntity(HttpStatus.OK);
    }

}
