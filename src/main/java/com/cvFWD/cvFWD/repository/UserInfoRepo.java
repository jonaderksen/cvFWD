package com.cvFWD.cvFWD.repository;

import com.cvFWD.cvFWD.domain.Account;
import com.cvFWD.cvFWD.domain.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoRepo extends CrudRepository<UserInfo, Long> {
    UserInfo getByAccount(Account account);
}
