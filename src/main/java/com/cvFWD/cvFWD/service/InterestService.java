package com.cvFWD.cvFWD.service;

import com.cvFWD.cvFWD.domain.Account;
import com.cvFWD.cvFWD.domain.Interest;
import com.cvFWD.cvFWD.model.InterestModel;
import com.cvFWD.cvFWD.repository.AccountRepo;
import com.cvFWD.cvFWD.repository.InterestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InterestService {
    private final AccountRepo accountRepo;
    private final InterestRepo interestRepo;

    @Autowired
    public  InterestService(AccountRepo accountRepo, InterestRepo interestRepo){
        this.accountRepo= accountRepo;
        this.interestRepo = interestRepo;
    }

    public Interest update(InterestModel interestModel, String email) {
        if (interestModel == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No interest model provided");
        if (interestModel.getInterest().equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No interests provided");

        Account account = this.accountRepo.getByEmail(email);

        if (account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect acocunt provided");

        Interest interest = this.interestRepo.getByAccount(account);

        if (interest == null)
            interest = new Interest();

        interest.setInterest(interestModel.getInterest());
        interest.setAccount(account);
        return this.interestRepo.save(interest);
    }

    public Interest get(String email) {
        Account account = this.accountRepo.getByEmail(email);
        if(account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");

        return this.interestRepo.getByAccount(account);
    }
}
