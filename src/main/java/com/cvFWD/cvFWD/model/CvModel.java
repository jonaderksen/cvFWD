package com.cvFWD.cvFWD.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CvModel {
    private String firstName;
    private String lastName;
    private String function;
    private String city;
    private LocalDate birthday;
    private String nationalty;
    private String drivinglicence;
    private String keywords;
    private String summery;
}
