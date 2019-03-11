package com.cvFWD.cvFWD.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String Summery;

    @OneToOne
    private Account account;
}
