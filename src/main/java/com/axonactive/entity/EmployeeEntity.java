package com.axonactive.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import com.axonactive.persistence.IEntity;

@NamedQueries({ @NamedQuery(name = "getEmployeeListWithNamedQuery", query = "select e from EmployeeEntity e "
		+ "where e.gender like :gender or e.name like :name " + "order by e.name desc") })

@NamedStoredProcedureQuery(name = "fn_getEmployeesOfOneDepartment", procedureName = "fn_getEmployeesOfOneDepartment", resultClasses = EmployeeEntity.class, parameters = {
		@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = void.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "departmentName") })

@Entity
@Table(name = "Employee")
public class EmployeeEntity implements IEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "gender", nullable = true)
	private String gender;

	@Column(name = "age", nullable = true)
	private int age;

	@ManyToOne
	@JoinColumn(name = "Department", nullable = false)
	private DepartmentEntity department;

	public EmployeeEntity() {
		super();
	}

	public EmployeeEntity(int id, String name, String email, String gender, int age, DepartmentEntity department) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.age = age;
		this.gender = gender;
		this.department = department;
	}

	public EmployeeEntity(String name, String email, String gender, int age, DepartmentEntity department) {
		super();
		this.name = name;
		this.email = email;
		this.age = age;
		this.gender = gender;
		this.department = department;
	}

	public int getId() {
		return id;
	}

	public DepartmentEntity getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentEntity department) {
		this.department = department;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
