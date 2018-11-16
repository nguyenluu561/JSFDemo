package com.axonactive.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.axonactive.bom.EmployeeBOM;
import com.axonactive.service.EmployeeService;

@Stateless
@Path("employee")
public class EmployeeResources {
	
	@EJB EmployeeService employeeService;
	
	@POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML})
    public Response save(EmployeeBOM employee) {
        employeeService.save(employeeService.toEntity(employee));
        return Response.status(Response.Status.CREATED).build();
    }
	
	@GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML})
	public List<EmployeeBOM> readAll() {
		return employeeService.toBoms(employeeService.readAll());
	}
	
	@GET
    @Path("{code}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML})
    public EmployeeBOM read(@PathParam("code")Integer id) {
        return employeeService.toBom(employeeService.findById(id));
    }
    
    @DELETE
    @Path("{code}")
    public Response deleteByCode(@PathParam("code")Integer id) {
        this.employeeService.remove(id);
        return Response.ok().build();
    }

}
