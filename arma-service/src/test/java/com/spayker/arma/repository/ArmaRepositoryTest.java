package com.spayker.arma.repository;

import com.spayker.arma.domain.UnitConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class ArmaRepositoryTest {

	@Autowired
	private ArmaRepository repository;

	@Test
	public void shouldFindUnitConfigByName() {

		UnitConfig stub = getStubUnitConfig();
		repository.save(stub);

		UnitConfig found = repository.findByName(stub.getName());
		assertEquals(stub.getNote(), found.getNote());
	}

	private UnitConfig getStubUnitConfig() {

		UnitConfig unitConfig = new UnitConfig();
		unitConfig.setName("test");
		unitConfig.setNote("test note");

		return unitConfig;
	}
}
