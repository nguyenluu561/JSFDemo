package com.axonactive.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.axonactive.bom.DepartmentBOM;
import com.axonactive.bom.EmployeeBOM;
import com.axonactive.service.DepartmentService;
import com.axonactive.service.EmployeeService;

@SuppressWarnings("deprecation")
@ManagedBean(name = "bean")
@ViewScoped
public class WebHandler {
	final static Logger LOGGER = Logger.getLogger(WebHandler.class);
	
	private EmployeeBOM employee = new EmployeeBOM();
	private DepartmentBOM department = new DepartmentBOM();

	@Inject
	private EmployeeService employeeService;

	@Inject
	private DepartmentService departmentService;

	private List<EmployeeBOM> employeeList = new ArrayList<>();
	private List<DepartmentBOM> departmentList = new ArrayList<>();

	@PostConstruct
	public void init() {
		
		try {
			
			employeeList = employeeService.toBoms(employeeService.readAll());
			System.out.println(employeeList.size());
			departmentList = departmentService.toBoms(departmentService.readAll());

			if (departmentList.size() > 0) {
				department = departmentList.get(0);
			}
			
		} catch (Throwable e) {
			System.out.println("Cause: " + e.getCause());
			System.out.println("Message: " + e.getMessage());
			System.out.println("Class: " + e.getClass());
			System.out.println("StackTrace: " + e.getStackTrace());
			LOGGER.error("Error at" + e);
			throw e;
		}
		LOGGER.info("Initial successfully");
	}

	public String addNewEmployee() {
		employee.setDepartment(departmentService.toEntity(department));
		employeeService.save(employeeService.toEntity(employee));
		employeeList = employeeService.toBoms(employeeService.readAll());
		return "index.xhtml?faces-redirect=true&includeViewParams=true";
	}

	public String deleteEmployee(EmployeeBOM employeeBOM) {
		employeeService.removeEntity(employeeService.toEntity(employeeBOM));
		employeeList = employeeService.toBoms(employeeService.readAll());
		return "index.xhtml?faces-redirect=true&includeViewParams=true";
	}

	public void editEmployee(EmployeeBOM employeeBOM) {
		employee = employeeBOM;
		department = departmentService.toBom(employeeBOM.getDepartment());
	}

	public String updateEmployee() {
		employee.setDepartment(departmentService.toEntity(department));
		employeeService.update(employeeService.toEntity(employee));
		employeeList = employeeService.toBoms(employeeService.readAll());
		return "index.xhtml?faces-redirect=true&includeViewParams=true";
	}

	public Boolean isEmployeeExisting() {
		boolean disable = false;
		for (EmployeeBOM emp : employeeList) {
			if (emp.getId() == employee.getId()) {
				disable = true;
			}
		}
		return disable;
	}
	
	
//	public void seleteFrench() {
//		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
//		viewRoot.setLocale(new Locale("fr-FR"));
//		setLocale("fr-FR");
//		setFrench(true);
//		setEnglish(false);
//	}
//
//	public void seleteEnglish() {
//		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
//		viewRoot.setLocale(new Locale("en-US"));
//		setLocale("en-US");
//		setFrench(false);
//		setEnglish(true);
//	}

	public void reverseEmployeeList() {
		Collections.reverse(employeeList);
	}

	public void changeDepartment(ValueChangeEvent dept) {
		department = departmentService
				.toBom(departmentService.findById(Integer.parseInt(dept.getNewValue().toString())));
	}

	public EmployeeBOM getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeBOM employee) {
		this.employee = employee;
	}

	public List<EmployeeBOM> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<EmployeeBOM> employeeList) {
		this.employeeList = employeeList;
	}

	public DepartmentBOM getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentBOM department) {
		this.department = department;
	}

	public List<DepartmentBOM> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<DepartmentBOM> departmentList) {
		this.departmentList = departmentList;
	}
//	
//	public boolean isEnglish() {
//		return english;
//	}
//
//	public void setEnglish(boolean english) {
//		this.english = english;
//	}
//
//	public boolean isFrench() {
//		return french;
//	}
//
//	public void setFrench(boolean french) {
//		this.french = french;
//	}
//
//	public String getLocale() {
//		return locale;
//	}
//
//	public void setLocale(String locale) {
//		this.locale = locale;
//	}

}
