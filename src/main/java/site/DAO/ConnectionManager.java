package site.DAO;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class ConnectionManager {

	public static Connection getConnection() {
		InitialContext ic;
		Connection conn = null;
		try {
			ic = new InitialContext();

			Context co = (Context) ic.lookup("java:comp/env");
			DataSource datasource = (BasicDataSource) co.lookup("jdbc/MySQLDS");

			try {
				// can also validate that the connection is valid
				return datasource.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}

		return conn;
	}

}
