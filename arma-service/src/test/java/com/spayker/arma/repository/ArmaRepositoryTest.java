package com.spayker.arma.repository;

import com.spayker.arma.domain.UnitConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ArmaRepositoryTest {

	@Autowired
	private ArmaRepository repository;

	@Test
	public void shouldFindUnitConfigByName() {

		UnitConfig stub = getStubUnitConfig();
		repository.save(stub);

		UnitConfig found = repository.findByName(stub.getName());
		assertEquals(stub.getLastSeen(), found.getLastSeen());
		assertEquals(stub.getNote(), found.getNote());
	}

	private UnitConfig getStubUnitConfig() {

		UnitConfig unitConfig = new UnitConfig();
		unitConfig.setName("test");
		unitConfig.setNote("test note");
		unitConfig.setLastSeen(new Date());

		return unitConfig;
	}
}
