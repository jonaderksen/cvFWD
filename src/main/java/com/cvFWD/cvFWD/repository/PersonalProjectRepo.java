package com.cvFWD.cvFWD.repository;

import com.cvFWD.cvFWD.domain.Account;
import com.cvFWD.cvFWD.domain.PersonalProject;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonalProjectRepo extends CrudRepository<PersonalProject, Long> {
    List<PersonalProject> getByAccount(Account account);

    PersonalProject getById(Long id);
}
