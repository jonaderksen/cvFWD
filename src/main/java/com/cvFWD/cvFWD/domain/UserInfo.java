package com.cvFWD.cvFWD.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String functiontitle;
    private String name;
    private String city;
    private String birthday;
    private String nationality;
    private String driverLicences;
    private String keywords;
    private String image;

    @OneToOne
    private Account account;
}
