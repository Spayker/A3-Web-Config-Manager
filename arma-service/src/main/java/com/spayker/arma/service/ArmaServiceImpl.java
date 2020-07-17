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

/**
 *  Service layer implementation to work with unit config entities.
 **/
@Service
public class ArmaServiceImpl implements ArmaService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private AuthServiceClient authClient;

	@Autowired
	private ArmaRepository repository;

	/**
	 *  Looks for stored unit config by its name.
	 *  @param unitConfig - string value for search
	 *  @return found Account
	 **/
	@Override
	public UnitConfig findByName(String unitConfig) {
		Assert.hasLength(unitConfig);
		return repository.findByName(unitConfig);
	}

	/**
	 *  Creates new unit config and returns it by provided User instance.
	 *  @param user - instance of User with email and password
	 *  @return created unit config
	 **/
	@Override
	public UnitConfig create(User user) {

		UnitConfig existing = repository.findByName(user.getUsername());
		Assert.isNull(existing, "unit config already exists: " + user.getUsername());

		authClient.createUser(user);
		UnitConfig unitConfig = new UnitConfig();
		unitConfig.setName(user.getUsername());

		repository.save(unitConfig);

		log.info("new unit config has been created: " + unitConfig.getName());

		return unitConfig;
	}

	/**
	 *  Updates a stored unit config and returns its updated variant.
	 *  @param name - String value to search a target unit config for update
	 *  @param update - an updated variation of unit config that must be persisted
	 **/
	@Override
	public void saveChanges(String name, UnitConfig update) {

		UnitConfig unitConfig = repository.findByName(name);
		Assert.notNull(unitConfig, "can't find unit config with name " + name);

		unitConfig.setNote(update.getNote());
		repository.save(unitConfig);

		log.debug("unit config {} changes has been saved", name);
	}
}
