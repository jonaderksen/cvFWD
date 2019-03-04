package com.cvFWD.cvFWD.repository;

import com.cvFWD.cvFWD.domain.Account;
import com.cvFWD.cvFWD.domain.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepo extends CrudRepository<Project, Long> {
    List<Project> getByAccount(Account account);
}
