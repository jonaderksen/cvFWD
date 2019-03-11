package com.cvFWD.cvFWD.model;

import lombok.Data;

@Data
public class ProjectModel {
    private Long id;
    private String startDate;
    private String endDate;
    private String jobTitle;
    private String company;
    private String city;
    private String description;
    private String skills;
    private String image;
}
