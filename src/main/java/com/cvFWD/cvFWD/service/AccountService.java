package com.cvFWD.cvFWD.service;

import com.cvFWD.cvFWD.domain.*;
import com.cvFWD.cvFWD.model.InitialCvModel;
import com.cvFWD.cvFWD.model.UserInfoModel;
import com.cvFWD.cvFWD.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountService {
    private final AccountRepo accountRepo;
    private final UserInfoRepo userInfoRepo;
    private final ProfileRepo profileRepo;
    private final SkillRepo skillRepo;
    private final ProjectRepo projectRepo;
    private final PersonalProjectRepo personalProjectRepo;
    private final EducationRepo educationRepo;
    private final LanguageRepo languageRepo;
    private final InterestRepo interestRepo;

    @Autowired
    public AccountService(AccountRepo accountRepo, UserInfoRepo userInfoRepo, ProfileRepo profileRepo, SkillRepo skillRepo, ProjectRepo projectRepo, PersonalProjectRepo personalProjectRepo, EducationRepo educationRepo, LanguageRepo languageRepo, InterestRepo interestRepo) {
        this.accountRepo = accountRepo;
        this.userInfoRepo = userInfoRepo;
        this.profileRepo = profileRepo;
        this.skillRepo = skillRepo;
        this.projectRepo = projectRepo;
        this.personalProjectRepo = personalProjectRepo;
        this.educationRepo = educationRepo;
        this.languageRepo = languageRepo;
        this.interestRepo = interestRepo;

    }

    public UserInfo updateUserinfo(UserInfoModel userInfoModel) {
        Account account = this.accountRepo.getByEmail(userInfoModel.getEmail());
        if (account == null) {
            InitialCvModel initialCvModel = new InitialCvModel();
            initialCvModel.setEmail(userInfoModel.getEmail());
            initialCvModel.setName(userInfoModel.getName());
            account = createInitalCv(initialCvModel);
        }
        UserInfo userInfo = this.userInfoRepo.getByAccount(account);

        userInfo.setFunctiontitle(userInfoModel.getFunctionTitle());
        userInfo.setName(userInfoModel.getName());
        userInfo.setCity(userInfoModel.getCity());
        userInfo.setBirthday(userInfoModel.getBirthday());
        userInfo.setNationality(userInfoModel.getNationality());
        userInfo.setDriverLicences(userInfoModel.getDrivingLicence());
        userInfo.setKeywords(userInfoModel.getKeywords());
        userInfo.setImage(userInfoModel.getImages());

        return this.userInfoRepo.save(userInfo);
    }

    public UserInfo getUserInfo(String email) {
        Account account = this.accountRepo.getByEmail(email);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");
        }
        return this.userInfoRepo.getByAccount(account);
    }

    public Account createInitalCv(InitialCvModel initialCvModel) {
        if (initialCvModel == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No initial cv model provided");
        }
        if (StringUtils.isBlank(initialCvModel.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No email provided");
        }
        if (StringUtils.isBlank(initialCvModel.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No name provided");
        }
        Account account = this.accountRepo.getByEmail(initialCvModel.getEmail());
        if (account == null) {
            account = new Account();
            account.setEmail(initialCvModel.getEmail());
            account.setName(initialCvModel.getName());
            this.accountRepo.save(account);

            initialiseUserInfo(account);
            initialiseProfile(account);

        }
        return account;
    }

    private void initialiseUserInfo(Account account) {
        UserInfo userInfo = new UserInfo();
        userInfo.setFunctiontitle("");
        userInfo.setName("");
        userInfo.setCity("");
        userInfo.setBirthday("");
        userInfo.setNationality("");
        userInfo.setDriverLicences("");
        userInfo.setKeywords("");
        userInfo.setImage("");
        userInfo.setAccount(account);
        this.userInfoRepo.save(userInfo);
    }

    private void initialiseProfile(Account account) {
        Profile profile = new Profile();
        profile.setSummery("");
        profile.setAccount(account);
        this.profileRepo.save(profile);
    }

}
