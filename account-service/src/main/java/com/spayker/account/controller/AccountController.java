package com.spayker.account.controller;

import com.spayker.account.domain.Account;
import com.spayker.account.domain.User;
import com.spayker.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 *  A controller layer with all needed (for now) methods.
 *  Last ones are called when request handling starts happening.
 *  Requests come on correspond url that linked by RequestMapping annotation with an appropriate declared method below.
 **/
@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

	/**
	 *  Returns an Account instance found by name.
	 *  @param name Strign value to make search by name possible
	 *  @return found Account entity
	 **/
	@PreAuthorize("#oauth2.hasScope('server') or #name.equals('demo')")
	@RequestMapping(path = "/{name}", method = RequestMethod.GET)
	public Account getAccountByName(@PathVariable String name) {
		return accountService.findByName(name);
	}

	/**
	 *  Creates account by provided User instance.
	 *  ToDo: change it once User instance will be replaced
	 *  @param
	 *  @return
	 **/
	@RequestMapping(path = "/", method = RequestMethod.POST)
	public Account createNewAccount(@Valid @RequestBody User user) {
		return accountService.create(user);
	}
}
