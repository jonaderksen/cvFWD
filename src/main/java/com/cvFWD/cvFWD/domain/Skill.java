package com.cvFWD.cvFWD.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Integer level;

    @OneToOne
    private Account account;
}
