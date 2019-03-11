package com.cvFWD.cvFWD.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String startDate;
    private String endDate;
    private String jobTitle;
    private String company;
    private String city;
    private String description;
    private String skills;
    private String image;

    @OneToOne
    private Account account;
}
