package com.cvFWD.cvFWD.model;

import lombok.Data;

@Data
public class PersonalProjectModel {
    private long id;
    private String startDate;
    private String endDate;
    private String name;
    private String description;
    private String skills;
}
