<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<title>Error</title>
</head>
<body>

	<%@include file="../includes/jqueryBtst.html"%>
	<%@include file="../includes/NavHeader.jsp"%>

	<div class="container-fluid">
		<h1>Error:</h1>
		<h2>${message}</h2>
	</div>
</body>
</html>