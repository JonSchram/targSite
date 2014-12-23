<!-- Way to get the server root URL from stack overflow user hfmanson -->
<!-- http://stackoverflow.com/a/16690893 -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL"
	value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />

<nav class="navbar navbar-default" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="${baseURL}">Site-TargMPD </a>
		</div>
		<ul class="nav navbar-nav">
			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown" role="button" aria-expanded="false">Calculators<span
					class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="${baseURL}/RewardCalculator">Calculator
							Select</a></li>
					<li class="divider"></li>
					<li><a href="${baseURL}/RewardCalculator?event=SC">Soul
							Crystal</a></li>
					<li><a href="${baseURL}/RewardCalculator?event=MountWhip">Mount
							Training Whip</a></li>
					<li><a href="${baseURL}/RewardCalculator?event=Mahra">Mahra</a></li>
					<li><a href="${baseURL}/RewardCalculator?event=Sep">Sepulcrum</a></li>
					<li class="divider"></li>
					<li><a href="${baseURL}/GemEventCalculator">Gem Event
							Reward Calculator</a></li>
				</ul></li>
		</ul>
	</div>
</nav>

