package com.axonactive.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.axonactive.persistence.IEntity;

@Entity
@Table(name="Department")
public class DepartmentEntity implements IEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name ="name", nullable=false, unique = true)
	private String name;
	
	public DepartmentEntity() {
		super();
	}
	
	public DepartmentEntity(String name) {
		this.name = name;
	}
	
	public DepartmentEntity(int id,String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
