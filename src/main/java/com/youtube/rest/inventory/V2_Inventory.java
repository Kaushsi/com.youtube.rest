package com.youtube.rest.inventory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.map.ObjectMapper;
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
	public Response returnBrand(
				@PathParam("brand") String brand) 
				throws Exception {

		String returnString = null;

		JSONArray json = new JSONArray();

		try {

			SchemaDao dao = new SchemaDao();

			json = dao.queryReturnBrandParts(brand);
			returnString = json.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}

		return Response.ok(returnString).build();
	}
	
	@Path("/{brand}/{item_number}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnSpecificBrandItem(
				@PathParam("brand") String brand,
				@PathParam("item_number") int item_number) 
				throws Exception {

		String returnString = null;

		JSONArray json = new JSONArray();

		try {

			SchemaDao dao = new SchemaDao();

			json = dao.queryReturnedBrandItemNumber(brand, item_number);
			returnString = json.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}

		return Response.ok(returnString).build();
	}
	
	
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPcParts(String incomingData) {
		
		String returnString = null;
		JSONArray json = new JSONArray();
		SchemaDao dao = new SchemaDao();
		
		try {
			System.out.println("Incoming Data: " + incomingData);
			
			ObjectMapper mapper = new ObjectMapper();
			ItemEntry itemEntry = mapper.readValue(incomingData, ItemEntry.class);
			
			int http_code = dao.insertIntoPCParts(itemEntry.TITLE, itemEntry.CODE, itemEntry.MAKER, 
												 itemEntry.AVAIL, itemEntry.DESCRIPTION);
			if (http_code == 200) {
//				returnString = json.toString();
				returnString = "item inserted";
			} else {
				return Response.status(http_code).entity("Unable to process data").build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		return Response.ok(returnString).build();
		
	}
}


class ItemEntry {
	
	public String TITLE;
	public String CODE;
	public String MAKER;
	public String AVAIL;
	public String DESCRIPTION;
	
}