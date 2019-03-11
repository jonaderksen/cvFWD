package com.cvFWD.cvFWD.repository;

import com.cvFWD.cvFWD.domain.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvRepo extends CrudRepository<UserInfo, Long> {

}
