package com.spayker.auth.service;

import com.spayker.auth.domain.User;

/**
 *  Service layer interface to provided API for work with User entity.
 **/
public interface UserService {

	void create(User user);

}
