package com.spayker.account.service;

import com.spayker.account.domain.Account;
import com.spayker.account.domain.User;

/**
 *  Service layer interface to provided API for work with Account entity.
 **/
public interface AccountService {

	/**
	 *  Looks for stored account by its name.
	 *  @param accountName - string value for search
	 *  @return found Account
	 **/
	Account findByName(String accountName);

	/**
	 *  Creates new Account and returns it by provided User instance.
	 *  @param user - instance of User with email and password
	 *  @return created Account
	 **/
	Account create(User user);

	/**
	 *  Updates a stored account and returns its updated variant.
	 *  @param name - String value to search a target account for update
	 *  @param update - an updated variation of Account that must be persisted
	 **/
	void saveChanges(String name, Account update);
}
