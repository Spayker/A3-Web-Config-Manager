package com.a3wcm.repository;

import com.a3wcm.domain.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 *  DAO layer for unit config model. Serves to exchange data between micro-service and related to it, db
 **/
@Repository
public interface ConfigRepository extends JpaRepository<Config, Long> {

	/**
	 *  Looks for config by provided name.
	 *  @param name - string value, used during unit config search by name
	 **/
	List<Config> findByNameContains(String name);

	/**
	 *  Looks for config by provided type.
	 *  @param type - string value of type to used in search
	 **/
	List<Config> findByType(String type);

	/**
	 *  Looks for config by provided note.
	 *  @param note - string value of note to used in search
	 **/
	List<Config> findByNoteContains(String note);

	/**
	 *  Looks for config by provided created date.
	 *  @param createDate - instance of util.Date that represents date and time when config was created
	 **/
	Config findByCreatedDate(Date createDate);

	/**
	 *  Looks for config by provided note.
	 *  @param modifiedDate - instance of util.Date that represents date and time when config was modified last time
	 **/
	Config findByModifiedDate(Date modifiedDate);

}
