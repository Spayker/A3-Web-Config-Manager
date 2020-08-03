package com.a3wcm.arma.controller;

import com.a3wcm.arma.domain.UnitConfig;
import com.a3wcm.arma.domain.User;
import com.a3wcm.arma.service.ArmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 *  A controller layer with all needed (for now) methods.
 *  Last ones are called when request handling starts happening.
 *  Requests come on correspond url that linked by RequestMapping annotation with an appropriate declared method below.
 **/
@RestController
public class ArmaController {

	@Autowired
	private ArmaService armaService;

	/**
	 *  Returns an UnitConfig instance found by name.
	 *  @param name Strign value to make search by name possible
	 *  @return found UnitConfig entity
	 **/
	@PreAuthorize("#oauth2.hasScope('server') or #name.equals('demo')")
	@RequestMapping(path = "/{name}", method = RequestMethod.GET)
	public UnitConfig getUnitConfigByName(@PathVariable String name) {
		return armaService.findByName(name);
	}

	/**
	 *  Creates unit config by provided User instance.
	 *  ToDo: change it once User instance will be replaced
	 *  @param
	 *  @return
	 **/
	@RequestMapping(path = "/", method = RequestMethod.POST)
	public UnitConfig createNewUnitConfig(@Valid @RequestBody User user) {
		return armaService.create(user);
	}
}
