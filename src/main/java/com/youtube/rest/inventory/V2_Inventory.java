package com.youtube.rest.inventory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.dao.SchemaDao;


@Path("v2/inventory")
public class V2_Inventory {
/*
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrandParts(@QueryParam("brand") String brand)  throws Exception {
		
		String returnString = null;
		JSONArray json = new JSONArray();
		
		try {
			if (brand == null)
			{
				return Response.status(Status.BAD_REQUEST).entity("Error: please specify brand for this search").build();
			}
			SchemaDao dao = new SchemaDao();
			json = dao.queryReturnedBrandParts(brand);
			returnString = json.toString();
	
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Server is not able to process your request").build();
		} 
		
		return Response.ok(returnString).build();
	}
	*/
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnErrorOnBrand() {
		return Response.status(Status.BAD_REQUEST).entity("Error: please specify brand for this search").build();
	}
	
	@Path("/{brand}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrand(@PathParam("brand") String brand)  throws Exception {
		
		String returnString = null;
		JSONArray json = new JSONArray();
		
		try {
			SchemaDao dao = new SchemaDao();
			json = dao.queryReturnedBrandParts(brand);
			returnString = json.toString();
	
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Server is not able to process your request").build();
		} 
		
		return Response.ok(returnString).build();
	}
}
