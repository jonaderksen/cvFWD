package com.cvFWD.cvFWD.service;

import com.cvFWD.cvFWD.exceptions.AccountNotFoundException;
import com.cvFWD.cvFWD.domain.Account;
import com.cvFWD.cvFWD.domain.Project;
import com.cvFWD.cvFWD.model.DeleteModel;
import com.cvFWD.cvFWD.model.ProjectModel;
import com.cvFWD.cvFWD.repository.AccountRepo;
import com.cvFWD.cvFWD.repository.ProjectRepo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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


    public List<Project> create(ProjectModel projectModel, String email) {
        if (projectModel == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No project model provided");


        Account account = checkProjectInput(email, projectModel.getStartDate(), projectModel.getEndDate(), projectModel.getCity(), projectModel.getCompany(), projectModel.getJobTitle(), projectModel.getDescription(), projectModel.getSkills(), projectModel.getImage());
        List<Project> projects = this.projectRepo.getByAccount(account);
        Project project = new Project();
        project.setAccount(account);
        project.setStartDate(projectModel.getStartDate());
        project.setEndDate(projectModel.getEndDate());
        project.setCity(projectModel.getCity());
        project.setCompany(projectModel.getCompany());
        project.setJobTitle(projectModel.getJobTitle());
        project.setDescription(projectModel.getDescription());
        project.setSkills(projectModel.getSkills());
        project.setImage(projectModel.getImage());
        this.projectRepo.save(project);
        projects.add(project);
        return projects;
    }

    public List<Project> get(String email) {
        Account account = this.accountRepo.getByEmail(email);
        Optional<Account> optionalAccount = Optional.ofNullable(account);
        if (!optionalAccount.isPresent()) {
            throw new AccountNotFoundException("No account available");
        }

        return this.projectRepo.getByAccount(account);
    }

    public List<Project> update(Project project, String email) {
        if (project.getId() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invallided id provided");
        }
        Account account = checkProjectInput(email, project.getStartDate(), project.getEndDate(), project.getCity(), project.getCompany(), project.getJobTitle(), project.getDescription(), project.getSkills(), project.getImage());


        List<Project> skills = this.projectRepo.getByAccount(account);
        skills.stream().filter(currentProject -> currentProject.getId() == project.getId() &&
                currentProject.getAccount().getEmail().equals(account.getEmail()))
                .forEach(matchProject -> upddateMatchSkill(project));

        return this.projectRepo.getByAccount(account);
    }

    private void upddateMatchSkill(Project project) {
        Project currentProject = this.projectRepo.getById(project.getId());
        currentProject.setStartDate(project.getStartDate());
        currentProject.setEndDate(project.getEndDate());
        currentProject.setCity(project.getCity());
        currentProject.setCity(project.getCompany());
        currentProject.setJobTitle(project.getJobTitle());
        currentProject.setDescription(project.getDescription());
        currentProject.setSkills(project.getSkills());
        currentProject.setImage(project.getImage());
        this.projectRepo.save(currentProject);
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

        Project Project = this.projectRepo.getById(deleteModel.getId());
        if (!account.getEmail().equals(Project.getAccount().getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalled User with this skill");
        }

        this.projectRepo.delete(Project);
    }

    private Account checkProjectInput(String email, LocalDateTime startDate, LocalDateTime endDate, String city, String company, String jobtitle, String description, String skills, String image){
        if (startDate == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No project start date provided");
        if (endDate == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No project end date provided");
        if (StringUtils.isBlank(city))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No project city provided");
        if (StringUtils.isBlank(company))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No project company provided");
        if (StringUtils.isBlank(jobtitle))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No project job title provided");
        if (StringUtils.isBlank(description))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No project description provided");
        if (StringUtils.isBlank(skills))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No project skills provided");
        if (StringUtils.isBlank(image))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No project images provided");
        if (StringUtils.isBlank(email))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No email provided");

        Account account = this.accountRepo.getByEmail(email);
        if (account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");

        return account;
    }
}
