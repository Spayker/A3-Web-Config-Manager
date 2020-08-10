package com.a3wcm.service;

import com.a3wcm.domain.Config;
import com.a3wcm.exception.ConfigException;
import com.a3wcm.repository.ConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *  Service layer implementation to work with unit config entities.
 **/
@Service
public class ConfigServiceImpl implements ConfigService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ConfigRepository repository;


	@Override
	public Optional<Config> findConfigById(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<Config> findConfigByName(String configName) {
		if(configName.isEmpty() || configName.isBlank()){
			throw new IllegalArgumentException("Provided config name can't be empty or blank");
		}
		return repository.findByNameContains(configName);
	}

	@Override
	public List<Config> findConfigByType(String type) {
		if(type.isEmpty() || type.isBlank()){
			throw new IllegalArgumentException("Provided config type can't be empty or blank");
		}
		return repository.findByType(type);
	}

	@Override
	public List<Config> findConfigByNote(String note) {
		if(note.isEmpty() || note.isBlank()){
			throw new IllegalArgumentException("Provided note can't be empty or blank");
		}
		return repository.findByNoteContains(note);
	}

	@Override
	public Config findConfigByCreatedDate(Date createdDate) {
		return repository.findByCreatedDate(createdDate);
	}

	@Override
	public List<Config> findByModifiedDate(Date modifiedDate) {
		return repository.findByModifiedDate(modifiedDate);
	}

	@Override
	public Config create(Config config) {
		config.setCreatedDate(new Date());
		Config savedConfig = repository.saveAndFlush(config);
		log.info("new training has been created: " + savedConfig.getId());
		return savedConfig;
	}

	@Override
	public Config saveChanges(Config update) {
		if(repository.existsById(update.getId())){
			log.debug("account {} changes have been saved", update.getId());
			return repository.saveAndFlush(update);
		} else {
			throw new ConfigException("Can't find config with id " + update.getId());
		}
	}

	
}
