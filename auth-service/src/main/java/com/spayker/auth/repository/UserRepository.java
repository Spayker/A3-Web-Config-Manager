package com.spayker.auth.repository;

import com.spayker.auth.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *  DAO layer for user model. Serves to exchange data between micro-service and related to it, db
 **/
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
