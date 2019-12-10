package com.spayker.arma.repository;

import com.spayker.arma.domain.UnitConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArmaRepository extends CrudRepository<UnitConfig, String> {

	UnitConfig findByName(String name);

}
