package com.cvFWD.cvFWD.repository;

import com.cvFWD.cvFWD.domain.Account;
import com.cvFWD.cvFWD.domain.Interest;
import org.springframework.data.repository.CrudRepository;

public interface InterestRepo extends CrudRepository<Interest, Long> {
    Interest getByAccount(Account account);
}
