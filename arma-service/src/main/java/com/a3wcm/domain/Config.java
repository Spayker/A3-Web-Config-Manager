package com.a3wcm.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 *  Config entity that contains major information about properties of:
 *  - unit
 *  - weapon
 *  - structure
 *  - ammo
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "CONFIG")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Config {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO, generator = "native" )
	@GenericGenerator( name = "native", strategy = "native" )
	private long id;

	private String name;

	@Length(min = 0, max = 20_000)
	private String note;

	private ConfigType type;

	private Date createdDate;

	private Date modifiedDate;

}
