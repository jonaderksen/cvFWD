package com.cvFWD.cvFWD.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectModel {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String jobTitle;
    private String company;
    private String city;
    private String description;
    private String skills;
    private String image;
}
