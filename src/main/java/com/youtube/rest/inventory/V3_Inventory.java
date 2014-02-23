package com.youtube.rest.inventory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.youtube.dao.SchemaDao;

@Path("/v3/inventory")
public class V3_Inventory {

	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPcParts2(String incomingData) {
		String returnString = null;
		JSONArray  jsonArray =  new JSONArray();
		JSONObject jsonObject = new JSONObject();
		SchemaDao dao = new SchemaDao();

		try {

			JSONObject partsData = new JSONObject(incomingData);
			System.out.println(" jsonData: " + partsData.toString());
			
			int httpCode = dao.insertIntoPCParts(partsData.optString("TITLE"), partsData.optString("CODE"), partsData.optString("MAKER"), partsData.optString("AVAIL"), 
					partsData.optString("DESCRIPTION"));
			
			if (httpCode == 200) {
					jsonObject.put("HTTP_CODE", httpCode);
					jsonObject.put("MSG", "Item has been inserted successfully, version 3");
					returnString = jsonArray.put(jsonObject).toString();
			} else {
				return Response.status(Status.BAD_REQUEST).entity("Unable to enter item").build();
			} 
			System.out.println(" returnString: " + returnString);			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
				
		return Response.ok(returnString).build();
	}
}
