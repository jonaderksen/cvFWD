package com.cvFWD.cvFWD.service;

import com.cvFWD.cvFWD.exceptions.AccountNotFoundException;
import com.cvFWD.cvFWD.domain.Account;
import com.cvFWD.cvFWD.domain.Project;
import com.cvFWD.cvFWD.model.ProjectModel;
import com.cvFWD.cvFWD.repository.AccountRepo;
import com.cvFWD.cvFWD.repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final AccountRepo accountRepo;
    private final ProjectRepo projectRepo;

    @Autowired
    public ProjectService(AccountRepo accountRepo, ProjectRepo projectRepo) {
        this.accountRepo = accountRepo;
        this.projectRepo = projectRepo;
    }


    public List<Project> update(ProjectModel projectModel, String email) {
        if (projectModel == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No project model provided");
        if (projectModel.getStartDate().equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No project start date provided");
        if (projectModel.getEndDate().equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No project end date provided");
        if (projectModel.getCity().equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No project city provided");
        if (projectModel.getCompany().equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No project company provided");
        if (projectModel.getJobTitle().equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No project job title provided");
        if (projectModel.getDescription().equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No project description provided");
        if (projectModel.getSkills().equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No project skills provided");
        if (projectModel.getImage().equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No project images provided");

        Account account = this.accountRepo.getByEmail(email);
        if (account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");

        List<Project> projects = this.projectRepo.getByAccount(account);
        Project project = new Project();

        if (projects.isEmpty() || projectModel.getId() == -1) {
            project = createProejct(project, account, projectModel);
            projects.add(project);
        } else {
            int indexProject = 0;
            boolean notFound = false;
            for (Project projectFromList : projects
            ) {
                if (projectFromList.getId() == projectModel.getId()) {

                    project = projectFromList;
                    notFound = true;
                    break;
                }
                indexProject++;

            }
            if (notFound) {
                project = createProejct(project, account, projectModel);
                projects.set(indexProject, project);
            }

        }
        return projects;
    }

    private Project createProejct(Project project, Account account, ProjectModel projectModel) {
        project.setStartDate(projectModel.getStartDate());
        project.setEndDate(projectModel.getEndDate());
        project.setCity(projectModel.getCity());
        project.setCompany(projectModel.getCompany());
        project.setJobTitle(projectModel.getJobTitle());
        project.setDescription(projectModel.getDescription());
        project.setSkills(projectModel.getSkills());
        project.setImage(projectModel.getImage());
        project.setAccount(account);
        this.projectRepo.save(project);
        return project;
    }

    public List<Project> get(String email) {
        Account account = this.accountRepo.getByEmail(email);
        Optional<Account> optionalAccount = Optional.ofNullable(account);
        if (!optionalAccount.isPresent()) {
            throw new AccountNotFoundException("No account available");
//            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "No account available");
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No account available");
        }

        return this.projectRepo.getByAccount(account);
    }
}
