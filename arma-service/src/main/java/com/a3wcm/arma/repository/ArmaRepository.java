package com.a3wcm.arma.repository;

import com.a3wcm.arma.domain.UnitConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *  DAO layer for unit config model. Serves to exchange data between micro-service and related to it, db
 **/
@Repository
public interface ArmaRepository extends CrudRepository<UnitConfig, Long> {

	/**
	 *  Looks for unit config by provided name.
	 *  @param name - string value, used during unit config search by name
	 **/
	UnitConfig findByName(String name);

}
