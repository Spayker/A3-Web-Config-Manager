package com.a3wcm.account.repository;

import com.a3wcm.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 *  DAO layer for account model. Serves to exchange data between micro-service and related to it, db
 **/
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	List<Account> findByName(String name);

	Account findByEmail(String email);

	List<Account> findByCreatedDate(Date createDate);

	List<Account> findByModifiedDate(Date modifiedDate);

}
