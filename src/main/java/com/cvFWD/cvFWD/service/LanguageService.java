package com.cvFWD.cvFWD.service;

import com.cvFWD.cvFWD.domain.Account;
import com.cvFWD.cvFWD.domain.Language;
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

    public List<Language> update(LanguageModel languageModel, String email) {
        if (languageModel == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No language model provided");
        if (StringUtils.isBlank(languageModel.getLanguage()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No language provided");
        if (StringUtils.isBlank(languageModel.getLevel()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No language level provided");

        Account account = this.accountRepo.getByEmail(email);
        if (account == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect account provided");

        List<Language> languages = this.languageRepo.getByAccount(account);
        Language language = new Language();

        if (languages.isEmpty() || languageModel.getId() == -1) {
            language = createLanguage(language, account, languageModel);
            languages.add(language);
        } else {
            int indexProject = 0;
            boolean notFound = false;
            for (Language languageFromList : languages
            ) {
                if (languageFromList.getId() == languageModel.getId()) {

                    language = languageFromList;
                    notFound = true;
                    break;
                }
                indexProject++;

            }
            if (notFound) {
                language = createLanguage(language, account, languageModel);
                languages.set(indexProject, language);
            }

        }

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
}
