package com.spayker.arma.service;

import com.spayker.arma.domain.Account;
import com.spayker.arma.domain.User;

public interface AccountService {

	Account findByName(String accountName);

	Account create(User user);

	void saveChanges(String name, Account update);
}
