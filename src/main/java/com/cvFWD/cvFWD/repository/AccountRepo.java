package com.cvFWD.cvFWD.repository;

import com.cvFWD.cvFWD.domain.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepo extends CrudRepository<Account, Long> {
    Account getByEmail(String email);
}
