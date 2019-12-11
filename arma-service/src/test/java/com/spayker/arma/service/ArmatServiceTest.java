package com.spayker.arma.service;

import com.spayker.arma.client.AuthServiceClient;
import com.spayker.arma.domain.UnitConfig;
import com.spayker.arma.domain.User;
import com.spayker.arma.repository.ArmaRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ArmatServiceTest {

	@InjectMocks
	private ArmaServiceImpl unitConfigService;

	@Mock
	private AuthServiceClient authClient;

	@Mock
	private ArmaRepository repository;

	@Before
	public void setup() {
		initMocks(this);
	}

	@Test
	public void shouldFindByName() {

		final UnitConfig account = new UnitConfig();
		account.setName("test");

		when(unitConfigService.findByName(account.getName())).thenReturn(account);
		UnitConfig found = unitConfigService.findByName(account.getName());

		assertEquals(account, found);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailWhenNameIsEmpty() {
		unitConfigService.findByName("");
	}

	@Test
	public void shouldCreateUnitConfigWithGivenUser() {

		User user = new User();
		user.setUsername("test");

		UnitConfig unitConfig = unitConfigService.create(user);

		assertEquals(user.getUsername(), unitConfig.getName());
		assertNotNull(unitConfig.getLastSeen());

		verify(authClient, times(1)).createUser(user);
		verify(repository, times(1)).save(unitConfig);
	}

	@Test
	public void shouldSaveChangesWhenUpdatedUnitConfigGiven() {

		final UnitConfig update = new UnitConfig();
		update.setName("test");
		update.setNote("test note");

		final UnitConfig account = new UnitConfig();

		when(unitConfigService.findByName("test")).thenReturn(account);
		unitConfigService.saveChanges("test", update);

		assertEquals(update.getNote(), account.getNote());
		assertNotNull(account.getLastSeen());
		
		verify(repository, times(1)).save(account);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailWhenNoUnitConfigsExistedWithGivenName() {
		final UnitConfig update = new UnitConfig();

		when(unitConfigService.findByName("test")).thenReturn(null);
		unitConfigService.saveChanges("test", update);
	}
}
