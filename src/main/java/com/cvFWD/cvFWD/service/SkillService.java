package com.cvFWD.cvFWD.service;

import com.cvFWD.cvFWD.domain.Account;
import com.cvFWD.cvFWD.domain.Skill;
import com.cvFWD.cvFWD.model.DeleteModel;
import com.cvFWD.cvFWD.model.SkillModel;
import com.cvFWD.cvFWD.repository.AccountRepo;
import com.cvFWD.cvFWD.repository.SkillRepo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SkillService {
    private final AccountRepo accountRepo;
    private final SkillRepo skillRepo;

    @Autowired
    public SkillService(AccountRepo accountRepo, SkillRepo skillRepo) {
        this.accountRepo = accountRepo;
        this.skillRepo = skillRepo;
    }

    public List<Skill> create(SkillModel skillModel, String email) {
        if (skillModel == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No skill model provided");
        Account account = checkSkillInput(skillModel.getName(), skillModel.getLevel(), email);

        List<Skill> skills = this.skillRepo.getByAccount(account);
        Skill skill = new Skill();
        skill.setAccount(account);
        skill.setLevel(skillModel.getLevel());
        skill.setName(skillModel.getName());

        this.skillRepo.save(skill);
        skills.add(skill);
        return skills;
    }

    public List<Skill> get(String email) {
        Account account = this.accountRepo.getByEmail(email);
        if (account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");
        return this.skillRepo.getByAccount(account);
    }

    public List<Skill> update(Skill skill, String email) {
        if (skill.getId() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invallided id provided");
        }
        Account account = checkSkillInput(skill.getName(), skill.getLevel(), email);

        List<Skill> skills = this.skillRepo.getByAccount(account);
        skills.stream().filter(currentskill -> currentskill.getId() == skill.getId() &&
                currentskill.getAccount().getEmail().equals(account.getEmail()))
                .forEach(matchSkill -> upddateMatchSkill(skill));

        return this.skillRepo.getByAccount(account);
    }

    private void upddateMatchSkill(Skill skill) {
        Skill currentSkill = this.skillRepo.getById(skill.getId());
        currentSkill.setName(skill.getName());
        currentSkill.setLevel(skill.getLevel());
        this.skillRepo.save(currentSkill);
    }

    public void delete(DeleteModel deleteModel) {
        if (StringUtils.isBlank(deleteModel.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No email provided");
        }
        if (deleteModel.getId() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invallided id provided");
        }
        Account account = this.accountRepo.getByEmail(deleteModel.getEmail());
        if (account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");

        Skill skill = this.skillRepo.getById(deleteModel.getId());
        if (!account.getEmail().equals(skill.getAccount().getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalled User with this skill");
        }

        this.skillRepo.delete(skill);
    }

    private Account checkSkillInput(String name, int level, String email){
        if (StringUtils.isBlank(name))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No sills provided");
        if (level < 0 || level > 5)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect skill level provided");
        if (StringUtils.isBlank(email))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No email provided");

        Account account = this.accountRepo.getByEmail(email);
        if (account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");

        return account;
    }
}
