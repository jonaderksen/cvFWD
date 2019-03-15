package com.cvFWD.cvFWD.service;

import com.cvFWD.cvFWD.domain.Account;
import com.cvFWD.cvFWD.domain.PersonalProject;
import com.cvFWD.cvFWD.model.DeleteModel;
import com.cvFWD.cvFWD.model.PersonalProjectModel;
import com.cvFWD.cvFWD.repository.AccountRepo;
import com.cvFWD.cvFWD.repository.PersonalProjectRepo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PersonalProjectService {
    private final AccountRepo accountRepo;
    private final PersonalProjectRepo personalProjectRepo;

    @Autowired
    public PersonalProjectService(AccountRepo accountRepo, PersonalProjectRepo personalProjectRepo) {
        this.accountRepo = accountRepo;
        this.personalProjectRepo = personalProjectRepo;
    }

    public List<PersonalProject> create(PersonalProjectModel personalProjectModel, String email) {
        if (personalProjectModel == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No personal project model provided");


        Account account = checkPersonalProjectInput(email, personalProjectModel.getStartDate(), personalProjectModel.getEndDate(), personalProjectModel.getName(), personalProjectModel.getDescription(), personalProjectModel.getSkills());

        List<PersonalProject> personalProjects = this.personalProjectRepo.getByAccount(account);
        PersonalProject personalProject = new PersonalProject();
        personalProject.setAccount(account);
        personalProject.setStartDate(personalProjectModel.getStartDate());
        personalProject.setEndDate(personalProjectModel.getEndDate());
        personalProject.setName(personalProjectModel.getName());
        personalProject.setDescription(personalProjectModel.getDescription());
        personalProject.setSkills(personalProjectModel.getSkills());
        this.personalProjectRepo.save(personalProject);
        personalProjects.add(personalProject);

        return personalProjects;
    }

    public List<PersonalProject> get(String email) {
        Account account = this.accountRepo.getByEmail(email);
        if (account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");
        return this.personalProjectRepo.getByAccount(account);
    }

    public List<PersonalProject> update(PersonalProject personalProject, String email) {
        if (personalProject.getId() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invallided id provided");
        }
        Account account = checkPersonalProjectInput(email, personalProject.getStartDate(), personalProject.getEndDate(), personalProject.getName(), personalProject.getDescription(), personalProject.getSkills());

        List<PersonalProject> skieducationsls = this.personalProjectRepo.getByAccount(account);
        skieducationsls.stream().filter(currentPersonalProject -> currentPersonalProject.getId() == personalProject.getId() &&
                currentPersonalProject.getAccount().getEmail().equals(account.getEmail()))
                .forEach(matchPersonalProject -> updateMatchEducation(personalProject));

        return this.personalProjectRepo.getByAccount(account);
    }

    private void updateMatchEducation(PersonalProject personalProject) {
        PersonalProject currentPersonalProject = this.personalProjectRepo.getById(personalProject.getId());
        currentPersonalProject.setStartDate(personalProject.getStartDate());
        currentPersonalProject.setEndDate(personalProject.getEndDate());
        currentPersonalProject.setName(personalProject.getName());
        currentPersonalProject.setDescription(personalProject.getDescription());
        currentPersonalProject.setSkills(personalProject.getSkills());
        this.personalProjectRepo.save(currentPersonalProject);
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

        PersonalProject personalProject = this.personalProjectRepo.getById(deleteModel.getId());
        if (!account.getEmail().equals(personalProject.getAccount().getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalled User with this skill");
        }

        this.personalProjectRepo.delete(personalProject);
    }

    private Account checkPersonalProjectInput(String email, LocalDateTime startDate, LocalDateTime endDate, String name, String description, String skills){
        if (startDate == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No personal project start date provided");
        if (endDate == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No personal project end date provided");
        if (StringUtils.isBlank(name))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No personal project name provided");
        if (StringUtils.isBlank(description))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No personal project description provided");
        if (StringUtils.isBlank(skills))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No personal project skills provided");
        if (StringUtils.isBlank(email))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No email provided");

        Account account = this.accountRepo.getByEmail(email);
        if (account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");

        return account;
    }
}
