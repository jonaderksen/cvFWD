package com.cvFWD.cvFWD.service;

import com.cvFWD.cvFWD.domain.Account;
import com.cvFWD.cvFWD.domain.Education;
import com.cvFWD.cvFWD.domain.Skill;
import com.cvFWD.cvFWD.model.DeleteModel;
import com.cvFWD.cvFWD.model.EducationModel;
import com.cvFWD.cvFWD.repository.AccountRepo;
import com.cvFWD.cvFWD.repository.EducationRepo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EducationSerice {

    private final AccountRepo accountRepo;
    private final EducationRepo educationRepo;

    @Autowired
    public EducationSerice(AccountRepo accountRepo, EducationRepo educationRepo) {
        this.accountRepo = accountRepo;
        this.educationRepo = educationRepo;
    }

    public List<Education> create(EducationModel educationModel, String email) {
        if (educationModel == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No education model provided");

        Account account = checkEducationInput(email, educationModel.getStartDate(), educationModel.getEndDate(), educationModel.getCity(), educationModel.getTitle(), educationModel.getInstitution());
        List<Education> educations = this.educationRepo.getByAccount(account);
        Education education = new Education();
        education.setAccount(account);
        education.setStartDate(educationModel.getStartDate());
        education.setEndDate(educationModel.getStartDate());
        education.setCity(educationModel.getCity());
        education.setTitle(educationModel.getTitle());
        education.setInstitution(educationModel.getInstitution());

        this.educationRepo.save(education);
        educations.add(education);
        return educations;
    }

    public List<Education> get(String email) {
        Account account = this.accountRepo.getByEmail(email);
        if (account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");
        return this.educationRepo.getByAccount(account);
    }

    private Account checkEducationInput(String email, LocalDateTime startDate, LocalDateTime endDate, String city, String title, String  institution){
        if (startDate == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No education start date provided");
        if (endDate == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No education end date provided");
        if (StringUtils.isBlank(city))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No education city provided");
        if (StringUtils.isBlank(title))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No eduction title provided");
        if (StringUtils.isBlank(institution))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No education institution provided");
        if (StringUtils.isBlank(email))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No email provided");

        Account account = this.accountRepo.getByEmail(email);
        if (account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");

        return account;
    }

    public List<Education> update(Education education, String email) {
        if (education.getId() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invallided id provided");
        }
        Account account = checkEducationInput(email, education.getStartDate(), education.getEndDate(), education.getCity(), education.getTitle(), education.getInstitution());

        List<Education> skieducationsls = this.educationRepo.getByAccount(account);
        skieducationsls.stream().filter(currentEducation -> currentEducation.getId() == education.getId() &&
                currentEducation.getAccount().getEmail().equals(account.getEmail()))
                .forEach(matchEducation -> updateMatchEducation(matchEducation));

        return this.educationRepo.getByAccount(account);
    }

    private void updateMatchEducation(Education matchEducation) {
        Education currentEducaction = this.educationRepo.getById(matchEducation.getId());
        currentEducaction.setStartDate(matchEducation.getStartDate());
        currentEducaction.setEndDate(matchEducation.getEndDate());
        currentEducaction.setCity(matchEducation.getCity());
        currentEducaction.setTitle(matchEducation.getTitle());
        currentEducaction.setInstitution(matchEducation.getInstitution());
        this.educationRepo.save(currentEducaction);
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

        Education education = this.educationRepo.getById(deleteModel.getId());
        if (!account.getEmail().equals(education.getAccount().getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalled User with this skill");
        }

        this.educationRepo.delete(education);
    }
}
