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

import com.axonactive.bom.DepartmentBOM;
import com.axonactive.service.DepartmentService;

@Stateless
@Path("department")
public class DepartmentResources {
	
	@EJB DepartmentService departmentService;
	
	@POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML})
    public Response save(DepartmentBOM department) {
        departmentService.save(departmentService.toEntity(department));
        return Response.status(Response.Status.CREATED).build();
    }
	
	@GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML})
	public List<DepartmentBOM> readAll() {
		return departmentService.toBoms(departmentService.readAll());
	}
	
	@GET
    @Path("{code}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML})
    public DepartmentBOM read(@PathParam("code")Integer id) {
        return departmentService.toBom(departmentService.findById(id));
    }
    
    @DELETE
    @Path("{code}")
    public Response deleteByCode(@PathParam("code")Integer id) {
        this.departmentService.remove(id);
        return Response.ok().build();
    }

}
