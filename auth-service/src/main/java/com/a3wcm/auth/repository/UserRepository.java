package com.a3wcm.auth.repository;

import com.a3wcm.auth.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 *  DAO layer for user model. Serves to exchange data between micro-service and related to it, db
 **/
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    @Query("UPDATE User u SET u.lastLogin=:lastLogin WHERE u.username = ?#{ principal?.username }")
    @Modifying
    @Transactional
    void updateLastLogin(@Param("lastLogin") Date lastLogin);

}
