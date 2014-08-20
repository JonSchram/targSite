<%@page import="org.apache.tomcat.dbcp.dbcp.BasicDataSource"%>
<%@page import="javax.naming.Context"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="org.apache.tomcat.jdbc.pool.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="" method="POST">
		<table>
			<tr>
				<td>User name:</td>
				<td><input type="text" name="username"></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="password"></td>
			</tr>
		</table>
		<input type="submit" value="Log in">
	</form>

	<%
		InitialContext ic = new InitialContext();
		Context co = (Context) ic.lookup("java:comp/env");
		BasicDataSource ds = (BasicDataSource) co.lookup("jdbc/MySQLDS");
		java.sql.Connection con = ds.getConnection();
		java.sql.PreparedStatement ps = con
				.prepareStatement("select * from site.foo");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(1));
			out.println(rs.getString(1));
		}
		con.close();
	%>
</body>
</html>