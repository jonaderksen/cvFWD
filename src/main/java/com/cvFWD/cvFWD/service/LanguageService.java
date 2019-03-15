package com.cvFWD.cvFWD.service;

import com.cvFWD.cvFWD.domain.Account;
import com.cvFWD.cvFWD.domain.Education;
import com.cvFWD.cvFWD.domain.Language;
import com.cvFWD.cvFWD.model.DeleteModel;
import com.cvFWD.cvFWD.model.LanguageModel;
import com.cvFWD.cvFWD.repository.AccountRepo;
import com.cvFWD.cvFWD.repository.LanguageRepo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LanguageService {
    private final AccountRepo accountRepo;
    private final LanguageRepo languageRepo;

    @Autowired
    public LanguageService(AccountRepo accountRepo, LanguageRepo languageRepo) {
        this.accountRepo = accountRepo;
        this.languageRepo = languageRepo;
    }

    public List<Language> create(LanguageModel languageModel, String email) {
        if (languageModel == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No language model provided");

        Account account = checkLanguageInput(email, languageModel.getLanguage(), languageModel.getLevel());
        List<Language> languages = this.languageRepo.getByAccount(account);
        Language language = new Language();
        language.setAccount(account);
        language.setLanguage(languageModel.getLanguage());
        language.setLevel(languageModel.getLevel());
        this.languageRepo.save(language);
        languages.add(language);

        return languages;
    }

    private Language createLanguage(Language language, Account account, LanguageModel languageModel) {

        language.setLanguage(languageModel.getLanguage());
        language.setLevel(languageModel.getLevel());
        language.setAccount(account);
        this.languageRepo.save(language);
        return language;
    }

    public List<Language> get(String email) {
        Account account = this.accountRepo.getByEmail(email);

        if (account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");

        return this.languageRepo.getByAccount(account);

    }

    public List<Language> update(Language language, String email) {
        if (language.getId() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invallided id provided");
        }
        Account account = checkLanguageInput(email, language.getLanguage(), language.getLevel());

        List<Language> languages = this.languageRepo.getByAccount(account);
        languages.stream().filter(currentLanguage -> currentLanguage.getId() == language.getId() &&
                currentLanguage.getAccount().getEmail().equals(account.getEmail()))
                .forEach(matchLanguage -> upddateMatchSkill(language));

        return this.languageRepo.getByAccount(account);
    }

    private void upddateMatchSkill(Language language) {
        Language currentLanguage = this.languageRepo.getById(language.getId());
        currentLanguage.setLanguage(language.getLanguage());
        currentLanguage.setLevel(language.getLevel());
        this.languageRepo.save(currentLanguage);
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

        Language language = this.languageRepo.getById(deleteModel.getId());
        if (!account.getEmail().equals(language.getAccount().getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalled User with this skill");
        }

        this.languageRepo.delete(language);
    }

    private Account checkLanguageInput(String email, String language, String level){
        if (StringUtils.isBlank(language))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No language provided");
        if (StringUtils.isBlank(level))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No level provided");
        if (StringUtils.isBlank(email))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No email provided");

        Account account = this.accountRepo.getByEmail(email);
        if (account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");

        return account;
    }
}
