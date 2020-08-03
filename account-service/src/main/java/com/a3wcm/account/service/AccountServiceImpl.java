package com.a3wcm.account.service;

import com.a3wcm.account.client.AuthServiceClient;
import com.a3wcm.account.domain.Account;
import com.a3wcm.account.domain.User;
import com.a3wcm.account.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

/**
 *  Service layer implementation to work with Account entities.
 **/
@Service
public class AccountServiceImpl implements AccountService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private AuthServiceClient authClient;

	@Autowired
	private AccountRepository repository;

	/**
	 *  Looks for stored account by its name.
	 *  @param accountName - string value for search
	 *  @return found Account
	 **/
	@Override
	public Account findByName(String accountName) {
		Assert.hasLength(accountName);
		return repository.findByName(accountName);
	}

	/**
	 *  Creates new Account and returns it by provided User instance.
	 *  @param user - instance of User with email and password
	 *  @return created Account
	 **/
	@Override
	public Account create(User user) {

		Account existing = repository.findByName(user.getUsername());
		Assert.isNull(existing, "account already exists: " + user.getUsername());

		authClient.createUser(user);
		Account account = new Account();
		account.setName(user.getUsername());
		account.setLastSeen(new Date());

		repository.save(account);

		log.info("new account has been created: " + account.getName());

		return account;
	}

	/**
	 *  Updates a stored account and returns its updated variant.
	 *  @param name - String value to search a target account for update
	 *  @param update - an updated variation of Account that must be persisted
	 **/
	@Override
	public void saveChanges(String name, Account update) {

		Account account = repository.findByName(name);
		Assert.notNull(account, "can't find account with name " + name);

		account.setNote(update.getNote());
		account.setLastSeen(new Date());
		repository.save(account);

		log.debug("account {} changes has been saved", name);
	}
}
