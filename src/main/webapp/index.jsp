<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<title>Welcome</title>
</head>
<body>
	<!-- JQuery and Bootstrap scripts -->
	<%@include file="/WEB-INF/includes/jqueryBtst.html"%>
	<%@include file="/WEB-INF/includes/NavHeader.jsp"%>
	<div class="container-fluid">
		This site is currently under development. Current site content:
		<ul>
			<li><a href="RewardCalculator">Event Reward Calculator</a>
				(NOTE: Events are determined by Wartune devs and are subject to
				change)
		</ul>
	</div>
</body>
</html>