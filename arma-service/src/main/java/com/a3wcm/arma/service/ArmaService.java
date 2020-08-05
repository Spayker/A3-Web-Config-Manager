package com.a3wcm.arma.service;

import com.a3wcm.arma.domain.UnitConfig;
import com.a3wcm.arma.domain.User;

/**
 *  Service layer interface to provided API for work with Arma entity.
 **/
public interface ArmaService {

	UnitConfig findByName(String accountName);

	UnitConfig create(User user);

	void saveChanges(String name, UnitConfig update);
}