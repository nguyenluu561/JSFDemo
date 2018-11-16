package com.axonactive.bom;

public class DepartmentBOM {
	private Integer id;
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DepartmentBOM(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public DepartmentBOM(String name) {
		this.name = name;
	}

	public DepartmentBOM() {
		// TODO Auto-generated constructor stub
	}
}
