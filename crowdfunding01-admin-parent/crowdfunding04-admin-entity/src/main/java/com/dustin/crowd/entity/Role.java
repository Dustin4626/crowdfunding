package com.dustin.crowd.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;


/**
 * The persistent class for the t_role database table.
 * 
 */
@Data
@Entity
@Table(name="t_role")
@NamedQuery(name="Role.findAll", query="SELECT t FROM Role t")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	

}