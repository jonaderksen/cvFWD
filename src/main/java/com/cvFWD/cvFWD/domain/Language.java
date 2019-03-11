package com.cvFWD.cvFWD.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String language;
    private String level;

    @OneToOne
    private Account account;
}
