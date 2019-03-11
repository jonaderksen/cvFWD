package com.cvFWD.cvFWD.service;

import com.cvFWD.cvFWD.domain.Account;
import com.cvFWD.cvFWD.domain.PersonalProject;
import com.cvFWD.cvFWD.model.PersonalProjectModel;
import com.cvFWD.cvFWD.repository.AccountRepo;
import com.cvFWD.cvFWD.repository.PersonalProjectRepo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public List<PersonalProject> update(PersonalProjectModel personalProjectModel, String email) {
        if (personalProjectModel == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No personal project model provided");
        if (StringUtils.isBlank(personalProjectModel.getStartDate()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No personal project start date provided");
        if (StringUtils.isBlank(personalProjectModel.getEndDate()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No personal project end date provided");
        if (StringUtils.isBlank(personalProjectModel.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No personal project name provided");
        if (StringUtils.isBlank(personalProjectModel.getDescription()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No personal project description provided");
        if (StringUtils.isBlank(personalProjectModel.getSkills()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No personal project skills provided");

        Account account = this.accountRepo.getByEmail(email);
        if (account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");

        List<PersonalProject> personalProjects = this.personalProjectRepo.getByAccount(account);
        PersonalProject personalProject = new PersonalProject();

        if (personalProjects.isEmpty() || personalProjectModel.getId() == -1) {
            personalProject = createPersonalProject(personalProject, account, personalProjectModel);
            personalProjects.add(personalProject);
        } else {
            int indexProject = 0;
            boolean notFound = false;
            for (PersonalProject personalProjectFromList : personalProjects
            ) {
                if (personalProjectFromList.getId() == personalProjectModel.getId()) {

                    personalProject = personalProjectFromList;
                    notFound = true;
                    break;
                }
                indexProject++;
            }
            if (notFound) {
                personalProject = createPersonalProject(personalProject, account, personalProjectModel);
                personalProjects.set(indexProject, personalProject);
            }
        }

        return personalProjects;
    }

    private PersonalProject createPersonalProject(PersonalProject personalProject, Account account, PersonalProjectModel personalProjectModel) {
        personalProject.setStartDate(personalProjectModel.getStartDate());
        personalProject.setEndDate(personalProjectModel.getEndDate());
        personalProject.setName(personalProjectModel.getName());
        personalProject.setDescription(personalProjectModel.getDescription());
        personalProject.setSkills(personalProjectModel.getSkills());
        personalProject.setAccount(account);
        this.personalProjectRepo.save(personalProject);
        return personalProject;
    }

    public List<PersonalProject> get(String email) {
        Account account = this.accountRepo.getByEmail(email);
        if (account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");
        return this.personalProjectRepo.getByAccount(account);
    }
}
