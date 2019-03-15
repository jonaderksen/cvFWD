package com.cvFWD.cvFWD.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EducationModel {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String institution;
    private String city;
    private String title;
}
