package com.spayker.arma.service;

import com.spayker.arma.client.AuthServiceClient;
import com.spayker.arma.domain.UnitConfig;
import com.spayker.arma.domain.User;
import com.spayker.arma.repository.ArmaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

@Service
public class ArmaServiceImpl implements ArmaService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private AuthServiceClient authClient;

	@Autowired
	private ArmaRepository repository;

	@Override
	public UnitConfig findByName(String accountName) {
		Assert.hasLength(accountName);
		return repository.findByName(accountName);
	}

	@Override
	public UnitConfig create(User user) {

		UnitConfig existing = repository.findByName(user.getUsername());
		Assert.isNull(existing, "account already exists: " + user.getUsername());

		authClient.createUser(user);
		UnitConfig account = new UnitConfig();
		account.setName(user.getUsername());
		account.setLastSeen(new Date());

		repository.save(account);

		log.info("new account has been created: " + account.getName());

		return account;
	}

	@Override
	public void saveChanges(String name, UnitConfig update) {

		UnitConfig unitConfig = repository.findByName(name);
		Assert.notNull(unitConfig, "can't find account with name " + name);

		unitConfig.setNote(update.getNote());
		unitConfig.setLastSeen(new Date());
		repository.save(unitConfig);

		log.debug("device {} changes has been saved", name);
	}
}
