package com.a3wcm.service;

import com.a3wcm.domain.Config;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *  Service layer interface to provided API for work with Config entity.
 **/
public interface ConfigService {

	/**
	 *  Looks for configs by provided config name String
	 *  @param id - Long value to be used for search
	 *  @return found instance of Config
	 **/
	Optional<Config> findConfigById(Long id);

	/**
	 *  Looks for configs by provided config name String
	 *  @param configName - String value to be used for search
	 *  @return list of found Config entities
	 **/
	List<Config> findConfigByName(String configName);

	/**
	 *  Looks for configs by provided type String
	 *  @param type - String value to be used for search
	 *  @return list of found Config entities
	 **/
	List<Config> findConfigByType(String type);

	/**
	 *  Looks for configs by provided note String
	 *  @param note - String value to be used for seacrh
	 *  @return list of found Config entities
	 **/
	List<Config> findConfigByNote(String note);

	/**
	 *  Looks for config by provided created date instance
	 *  @param createdDate - instance of util.Date that represents date and time when config was created
	 *  @return instance of found config
	 **/
	Config findConfigByCreatedDate(Date createdDate);

	/**
	 *  Looks for config by provided modified date
	 *  @param modifiedDate - instance of util.Date that represents date and time when config was modified last time
	 *  @return list of found Config instances
	 **/
	List<Config> findByModifiedDate(Date modifiedDate);

	/**
	 *  Creates new Account and returns it by provided User instance.
	 *  @param config - instance of Config to be persisted
	 *  @return created Account
	 **/
	Config create(Config config);

	/**
	 *  Updates a stored account and returns its updated variant.
	 *  @param update - an updated variation of Account that must be persisted
	 **/
	Config saveChanges(Config update);

}
