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

}
