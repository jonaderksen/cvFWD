package com.cvFWD.cvFWD.service;

import com.cvFWD.cvFWD.domain.Account;
import com.cvFWD.cvFWD.domain.Education;
import com.cvFWD.cvFWD.model.EducationModel;
import com.cvFWD.cvFWD.repository.AccountRepo;
import com.cvFWD.cvFWD.repository.EducationRepo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public List<Education> update(EducationModel educationModel, String email) {
        if (educationModel == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No education model provided");
        if (StringUtils.isBlank(educationModel.getStartDate()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No education start date provided");
        if (StringUtils.isBlank(educationModel.getEndDate()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No education end date provided");
        if (StringUtils.isBlank(educationModel.getCity()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No education city provided");
        if (StringUtils.isBlank(educationModel.getTitle()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No eduction title provided");
        if (StringUtils.isBlank(educationModel.getInstituion()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No education institution provided");

        Account account = this.accountRepo.getByEmail(email);
        if (account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");

        List<Education> educations = this.educationRepo.getByAccount(account);
        Education education = new Education();

        if (educations.isEmpty() || educationModel.getId() == -1) {
            education = createEducation(education, account, educationModel);
            educations.add(education);
        } else {
            int indexProject = 0;
            boolean notFound = false;
            for (Education educationFromList : educations
            ) {
                if (educationFromList.getId() == educationModel.getId()) {

                    education = educationFromList;
                    notFound = true;
                    break;
                }
                indexProject++;
            }
            if (notFound) {
                education = createEducation(education, account, educationModel);
                educations.set(indexProject, education);
            }
        }

        return educations;
    }

    private Education createEducation(Education education, Account account, EducationModel educationModel) {
        education.setStartDate(educationModel.getStartDate());
        education.setEndDate(educationModel.getEndDate());
        education.setCity(educationModel.getCity());
        education.setTitle(educationModel.getTitle());
        education.setInstituion(educationModel.getInstituion());
        education.setAccount(account);
        this.educationRepo.save(education);
        return education;
    }

    public List<Education> get(String email) {
        Account account = this.accountRepo.getByEmail(email);
        if (account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");
        return this.educationRepo.getByAccount(account);
    }
}
