package com.cvFWD.cvFWD.repository;

import com.cvFWD.cvFWD.domain.Account;
import com.cvFWD.cvFWD.domain.Skill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SkillRepo extends CrudRepository<Skill, Long> {
    List<Skill> getByAccount(Account account);
    Skill getById(long id);
}
