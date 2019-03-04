package com.cvFWD.cvFWD.service;

import com.cvFWD.cvFWD.domain.Account;
import com.cvFWD.cvFWD.domain.Skill;
import com.cvFWD.cvFWD.model.SkillModel;
import com.cvFWD.cvFWD.repository.AccountRepo;
import com.cvFWD.cvFWD.repository.SkillRepo;
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

    public List<Skill> update(SkillModel skillModel, String email) {
        if (skillModel == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No skill model provided");
        if (skillModel.getName() == "")
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No sills provided");
        if (skillModel.getLevel() < 0 || skillModel.getLevel() > 5)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect skill level provided");

        Account account = this.accountRepo.getByEmail(email);
        if (account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");

        List<Skill> skills = this.skillRepo.getByAccount(account);
        Skill skill = new Skill();

        if (skills.isEmpty() || skillModel.getId() == -1) {
            skill = createSkill(skill, account, skillModel);
            skills.add(skill);
        } else {
            int indexProject = 0;
            boolean notFound = false;
            for (Skill skillFromList : skills
            ) {
                if (skillFromList.getId() == skillModel.getId()) {

                    skill = skillFromList;
                    notFound = true;
                    break;
                }
                indexProject++;
            }
            if (notFound) {
                skill = createSkill(skill, account, skillModel);
                skills.set(indexProject, skill);
            }
        }

        return skills;
    }

    private Skill createSkill(Skill skill, Account account, SkillModel skillModel) {
        skill.setName(skillModel.getName());
        skill.setLevel(skillModel.getLevel());
        skill.setAccount(account);
        this.skillRepo.save(skill);
        return skill;
    }

    public List<Skill> get(String email) {
        Account account = this.accountRepo.getByEmail(email);
        if (account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");
        return this.skillRepo.getByAccount(account);
    }
}
