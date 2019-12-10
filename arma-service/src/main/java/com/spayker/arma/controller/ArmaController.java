package com.spayker.arma.controller;

import com.spayker.arma.domain.UnitConfig;
import com.spayker.arma.domain.User;
import com.spayker.arma.service.ArmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class ArmaController {

	@Autowired
	private ArmaService armaService;

	@PreAuthorize("#oauth2.hasScope('server') or #name.equals('demo')")
	@RequestMapping(path = "/{name}", method = RequestMethod.GET)
	public UnitConfig getAccountByName(@PathVariable String name) {
		return armaService.findByName(name);
	}

	@RequestMapping(path = "/current", method = RequestMethod.GET)
	public UnitConfig getCurrentAccount(Principal principal) {
		return armaService.findByName(principal.getName());
	}

	@RequestMapping(path = "/current", method = RequestMethod.PUT)
	public void saveCurrentAccount(Principal principal, @Valid @RequestBody UnitConfig unitConfig) {
		armaService.saveChanges(principal.getName(), unitConfig);
	}

	@RequestMapping(path = "/", method = RequestMethod.POST)
	public UnitConfig createNewAccount(@Valid @RequestBody User user) {
		return armaService.create(user);
	}
}
