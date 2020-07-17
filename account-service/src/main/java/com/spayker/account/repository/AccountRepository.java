package com.spayker.account.repository;

import com.spayker.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *  DAO layer for account model. Serves to exchange data between micro-service and related to it, db
 **/
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	Account findByName(String name);

}
