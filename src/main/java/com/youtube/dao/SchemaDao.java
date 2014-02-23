package com.youtube.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.util.ToJSON;

public class SchemaDao extends OracleDao {

	public JSONArray queryReturnBrandParts(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = getConnection();
			query = conn
					.prepareStatement("select PC_PARTS_PK, title, code, maker, avail, description "
									  + " from PC_PARTS" + " where UPPER(MAKER) = ? ");
			
			query.setString(1, brand.toUpperCase());
			
			ResultSet rs = query.executeQuery();
			
			json = converter.toJSONArray(rs);
			query.close();

		} catch (SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return json;
		}finally {
			if (conn != null)
				conn.close();
		}

		return json;
	}
	
	public JSONArray queryReturnedBrandItemNumber(String brand, int item_number) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = getConnection();
			query = conn
					.prepareStatement("select PC_PARTS_PK, title, code, maker, avail, description "
									  + " from PC_PARTS" + " where UPPER(MAKER) = ? and code = ?");
			
			query.setString(1, brand.toUpperCase());
			query.setInt(2, item_number);
			
			ResultSet rs = query.executeQuery();
			
			json = converter.toJSONArray(rs);
			query.close();

		} catch (SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return json;
		}finally {
			if (conn != null)
				conn.close();
		}

		return json;
	}
	
	public int insertIntoPCParts(String TITLE, String CODE, String MAKER, String AVAIL, String DESCRIPTION) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		try {
			/*
			 * If this was a real application, you should do data validation here
			 * before starting to insert data into the database.
			 * 
			 * Important: The primary key on PC_PARTS table will auto increment.
			 * 		That means the PC_PARTS_PK column does not need to be apart of the 
			 * 		SQL insert query below.
			 */
			conn = getConnection();
			query = conn.prepareStatement("insert into PC_PARTS " +
					"(TITLE, CODE, MAKER, AVAIL, DESCRIPTION) " +
					"VALUES ( ?, ?, ?, ?, ? ) ");

			query.setString(1, TITLE);
			query.setString(2, CODE);
			query.setString(3, MAKER);

			//PC_PARTS_AVAIL is a number column, so we need to convert the String into a integer
			int avilInt = Integer.parseInt(AVAIL);
			query.setInt(4, avilInt);

			query.setString(5, DESCRIPTION);
			query.executeUpdate(); //note the new command for insert statement

		} catch(Exception e) {
			e.printStackTrace();
			return 500; //if a error occurs, return a 500
		}
		finally {
			if (conn != null) conn.close();
		}

		return 200;
		
		
		
	}

}