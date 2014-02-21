package com.youtube.dao;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class OracleDao {

	private static DataSource OracleDatasource = null;
	private static Context context = null;
	
	public static DataSource OracleDaoConn() throws Exception {
		
		if (OracleDatasource != null) {
			return OracleDatasource;
		}
		try {
			if (context == null) {
				context = new InitialContext();
			}
			OracleDatasource = (DataSource) context.lookup("308tubeOracle");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return OracleDatasource;
	}
	
	protected static Connection getConnection()
	{
		Connection conn = null;
		try {
			conn = OracleDaoConn().getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
