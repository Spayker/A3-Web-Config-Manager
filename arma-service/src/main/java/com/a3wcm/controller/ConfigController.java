package com.a3wcm.controller;

import com.a3wcm.domain.Config;
import com.a3wcm.dto.ConfigDto;
import com.a3wcm.dto.mapper.ConfigMapper;
import com.a3wcm.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.util.Optional;

/**
 *  A controller layer with all needed (for now) methods.
 *  Last ones are called when request handling starts happening.
 *  Requests come on correspond url that linked by RequestMapping annotation with an appropriate declared method below.
 **/
@RestController
@RequestMapping("/v1/configs")
public class ConfigController {

	@Autowired
	private ConfigService configService;

	@Autowired
	private ConfigMapper configMapper;

	@PreAuthorize("#oauth2.hasScope('server')")
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Config> getConfigById(@PathVariable Long id) {
		Optional<Config> foundConfig = configService.findConfigById(id);
		return foundConfig.map(config -> new ResponseEntity<>(config, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(null, HttpStatus.OK));
	}

	@RequestMapping(path = "/", method = RequestMethod.POST)
	public ResponseEntity<Config> createConfig(@Valid @RequestBody ConfigDto configDto) {
		Config config = configMapper.configDtoToConfig(configDto);
		return new ResponseEntity<>(configService.create(config), HttpStatus.CREATED);
	}

}
