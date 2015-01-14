<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description"
	content="Site managed by the Targaryens guild of Wartune s62 and s65 on Kabam. Calculate your rewards for several types of in-game events.">

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<title>Site-Targmpd: Targaryens guild on Wartune s62/s65</title>
</head>
<body>
	<!-- JQuery and Bootstrap scripts -->
	<%@include file="/WEB-INF/includes/jqueryBtst.html"%>
	<%@include file="/WEB-INF/includes/NavHeader.jsp"%>
	<div class="container-fluid">
		<p>This site is updated periodically to add new features. Current
			content:</p>
		<ul>
			<li><a href="RewardCalculator">Event Reward Calculator</a></li>
			<li><a href="GemEventCalculator">Gem Event Reward Calculator</a>
		</ul>
		<p>NOTE: Events are determined by Wartune devs, not us, and are
			subject to change. All event calculators are based on current
			knowledge of upcoming events.</p>
	</div>
</body>
</html>