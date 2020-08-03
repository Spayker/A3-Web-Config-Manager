package com.a3wcm.account.repository;

import com.a3wcm.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *  DAO layer for account model. Serves to exchange data between micro-service and related to it, db
 **/
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	Account findByName(String name);

}
