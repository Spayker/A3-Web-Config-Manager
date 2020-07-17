package com.spayker.auth.controller;

import com.spayker.auth.domain.User;
import com.spayker.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

/**
 *  A rest controller to handle auth requests. Was created to handle requests come from Account service
 *  regarding new account creation.
 **/
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 *  Creates a new record in user auth db once a new account must be created in system.
	 *  @param user - container with name (email) and password of user that must be created
	 **/
	@PreAuthorize("#oauth2.hasScope('server')")
	@RequestMapping(method = RequestMethod.POST)
	public void createUser(@Valid @RequestBody User user) {
		userService.create(user);
	}
}
