package com.cvFWD.cvFWD.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String jobTitle;
    private String company;
    private String city;
    private String description;
    private String skills;
    private String image;

    @OneToOne
    private Account account;
}
