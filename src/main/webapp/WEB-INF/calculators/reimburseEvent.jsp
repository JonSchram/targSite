<%@page import="java.util.Iterator"%>
<%@page import="site.calculators.Reward"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
    String eventName = request.getAttribute("EventName").toString();
%>
<title><%=eventName%> Reward Calculator</title>
</head>
<body>

	<%
	    Reward r = (Reward) (request.getAttribute("RewardData"));
	    Iterator<String> rewardNamesIterator = r.getRewardNames()
													    .iterator();
	    Iterator<Integer> amountsIterator = r.getRewardAmounts().iterator();
	%>

	This is the calculator page for
	<b><%=eventName%></b>

	<p>
		Items needed for reward:
		<%=r.getUseRequired()%>
		<br> Items returned in reward:
		<%=r.getReturnAmount()%>
	<p>
		Additional rewards: <br>
	<table border="1px">
		<%
		    while (rewardNamesIterator.hasNext()) {
																								out.println("<tr><td>" + rewardNamesIterator.next()
																									+ "</td><td>" + amountsIterator.next() + "</td></tr>");
																						    }
		%>
	</table>
	<p>
		Amount of
		<%=eventName%>: <input type="text" name="startAmount"><br>
		<input type="button" value="Calculate" onclick="calculate">
	<p>Total used:
	<div id="totalUsed"></div>


	<script type="text/javascript">
		function calculate() {
			var totalUsed = repeatUse(document.getElementById("startAmount")
					.getAttribute("value"),
	<%=r.getUseRequired()%>
		,
	<%=r.getReturnAmount()%>
		);
			document.getElementById("totalUsed").set("value", totalUsed);
		}

		/**
		 * Uses item and redeems reward until all items have been used (uses remaining
		 * items even if using them gives no further rewards)
		 */
		function repeatUse(startAmount, required, returnAmount) {
			var used = 0;
			var current = startAmount;
			while (current >= required) {
				var rewardNum = Math.floor(current / required);
				var remaining = current % required;
				used += current - remaining;
				current = remaining + returnAmount * rewardNum;
			}
			used += current;
			return used;
		}
	</script>

</body>
</html>