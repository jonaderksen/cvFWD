package com.cvFWD.cvFWD.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PersonalProjectModel {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String name;
    private String description;
    private String skills;
}
