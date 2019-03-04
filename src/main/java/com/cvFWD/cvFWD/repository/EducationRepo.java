package com.cvFWD.cvFWD.repository;

import com.cvFWD.cvFWD.domain.Account;
import com.cvFWD.cvFWD.domain.Education;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EducationRepo extends CrudRepository<Education, Long> {
    List<Education> getByAccount(Account account);
}
