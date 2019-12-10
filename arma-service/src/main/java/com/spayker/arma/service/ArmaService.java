package com.spayker.arma.service;

import com.spayker.arma.domain.UnitConfig;
import com.spayker.arma.domain.User;

public interface ArmaService {

	UnitConfig findByName(String accountName);

	UnitConfig create(User user);

	void saveChanges(String name, UnitConfig update);
}
