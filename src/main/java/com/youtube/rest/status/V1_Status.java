package com.youtube.rest.status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.youtube.dao.OracleDao;


@Path("/v1/status/*")
public class V1_Status {
	
	private static final String API_VERSION = "00.1.00";
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnTitle() {
		return "<p>Java Web Service</p>";
	}

	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion() {
		return "<p>Version:</p>" + API_VERSION;
	}
	
	@Path("/database")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnDatabaseStatus() throws Exception {
		PreparedStatement query = null;
		String myString = null;
		String returnString = null;
		Connection conn = null;

		try {
			conn = OracleDao.OracleDaoConn().getConnection();
			query = conn.prepareStatement("select to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS') DATETIME from sys.dual");
			ResultSet rs = query.executeQuery();
			while (rs.next()) {
				myString = rs.getString("DATETIME");
			}
			query.close();
			returnString = "<p> Database Status </p>" +
							"<p> Database Date/Time return: " + myString + "</p>";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) conn.close();
		}
		
		return returnString;
	}
}
