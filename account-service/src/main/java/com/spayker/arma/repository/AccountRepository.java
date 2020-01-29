package com.spayker.arma.repository;

import com.spayker.arma.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

	Account findByName(String name);

}
