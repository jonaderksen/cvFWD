package com.cvFWD.cvFWD.repository;

import com.cvFWD.cvFWD.domain.Account;
import com.cvFWD.cvFWD.domain.Language;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LanguageRepo extends CrudRepository<Language, Long> {
    List<Language> getByAccount(Account account);
}
