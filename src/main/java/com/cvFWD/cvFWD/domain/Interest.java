package com.cvFWD.cvFWD.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String interest;

    @OneToOne
    private Account account;
}
