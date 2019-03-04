package com.cvFWD.cvFWD.service;

import com.cvFWD.cvFWD.domain.Account;
import com.cvFWD.cvFWD.domain.Profile;
import com.cvFWD.cvFWD.model.ProfileModel;
import com.cvFWD.cvFWD.repository.AccountRepo;
import com.cvFWD.cvFWD.repository.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProfileService {
    private final AccountRepo accountRepo;
    private final ProfileRepo profileRepo;

    @Autowired
    public ProfileService(AccountRepo accountRepo, ProfileRepo profileRepo) {
        this.accountRepo = accountRepo;
        this.profileRepo = profileRepo;
    }

    public Profile update(ProfileModel profileModel, String email) {
        if (profileModel == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No profile model provided");
        if (profileModel.getSummery().equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No profile summery provided");
        Account account = this.accountRepo.getByEmail(email);
        if (account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");

        Profile profile = this.profileRepo.getByAccount(account);

        if (profile == null)
            profile = new Profile();

        profile.setSummery(profileModel.getSummery());
        profile.setAccount(account);
        return this.profileRepo.save(profile);

    }

    public Profile get(String email) {
        Account account = this.accountRepo.getByEmail(email);
        if (account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");

        return this.profileRepo.getByAccount(account);
    }
}
