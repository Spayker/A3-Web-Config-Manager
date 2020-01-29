package com.spayker.arma.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spayker.arma.domain.UnitConfig;
import com.spayker.arma.domain.User;
import com.spayker.arma.service.ArmaService;
import com.sun.security.auth.UserPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArmaControllerTest {

	private static final ObjectMapper mapper = new ObjectMapper();

	@InjectMocks
	private ArmaController armaController;

	@Mock
	private ArmaService armaService;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(armaController).build();
	}

	@Test
	public void shouldGetUnitConfigByName() throws Exception {

		final UnitConfig unitConfig = new UnitConfig();
		unitConfig.setName("test");

		when(armaService.findByName(unitConfig.getName())).thenReturn(unitConfig);

		mockMvc.perform(get("/" + unitConfig.getName()))
				.andExpect(jsonPath("$.name").value(unitConfig.getName()))
				.andExpect(status().isOk());
	}

	@Test
	public void shouldGetCurrentUnitConfig() throws Exception {

		final UnitConfig unitConfig = new UnitConfig();
		unitConfig.setName("test");

		when(armaService.findByName(unitConfig.getName())).thenReturn(unitConfig);

		mockMvc.perform(get("/current").principal(new UserPrincipal(unitConfig.getName())))
				.andExpect(jsonPath("$.name").value(unitConfig.getName()))
				.andExpect(status().isOk());
	}

	@Test
	public void shouldSaveCurrentUnitConfig() throws Exception {

		final UnitConfig unitConfig = new UnitConfig();
		unitConfig.setName("test");
		unitConfig.setNote("test note");

		String json = mapper.writeValueAsString(unitConfig);

		mockMvc.perform(put("/current").principal(new UserPrincipal(unitConfig.getName())).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk());
	}

	@Test
	public void shouldRegisterNewUnitConfig() throws Exception {

		final User user = new User();
		user.setUsername("test");
		user.setPassword("password");

		String json = mapper.writeValueAsString(user);
		System.out.println(json);
		mockMvc.perform(post("/").principal(new UserPrincipal("test")).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk());
	}

	@Test
	public void shouldFailOnValidationTryingToRegisterNewUnitConfig() throws Exception {

		final User user = new User();
		user.setUsername("t");

		String json = mapper.writeValueAsString(user);

		mockMvc.perform(post("/").principal(new UserPrincipal("test")).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest());
	}
}
