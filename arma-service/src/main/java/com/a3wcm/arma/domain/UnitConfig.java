package com.a3wcm.arma.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  Unit config model entity that contains major information about unit properties.
 *  ToDo: update docs once model will be updated
 **/
@Entity
@Table(name = "unitConfig")
public class UnitConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	private String name;

	@Length(min = 0, max = 20_000)
	private String note;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
