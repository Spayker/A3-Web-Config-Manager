package com.a3wcm.service;

import com.a3wcm.domain.Config;
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
