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
	<%@include file="calcHeader.html"%>

	<%
	    Reward r = (Reward) (request.getAttribute("RewardData"));
	    Iterator<String> rewardNamesIterator = r.getRewardNames().iterator();
	    Iterator<Integer> amountsIterator = r.getRewardAmounts().iterator();
	%>

	<script type="text/javascript">
		var additionalRewardNames = [
		<%for (int i = 0; i<r.getNumberOfRewards();i++) {%>
			<%=rewardNamesIterator.next()%><%if(i<r.getNumberOfRewards()-1){%>,<%}%>
		<%}%>
		];
		var additionalRewardAmounts = [
		<%for (int i = 0; i<r.getNumberOfRewards();i++) {%>
			<%=amountsIterator.next()%><%if(i<r.getNumberOfRewards()-1){%>,<%}%>
		<%}%>
		];

		function calculate() {
			var totalUsed = repeatUse(document.getElementById("startAmount").getAttribute("value")
			<%=r.getUseRequired()%>
			,
			<%=r.getReturnAmount()%>
			);
			document.write(totalUsed)
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

	This is the
	<b><%=eventName%></b> event calculator page.

	<p>
		<%=eventName%>
		needed for reward:
		<%=r.getUseRequired()%>
		<br>
		<%=eventName%>
		returned in reward:
		<%=r.getReturnAmount()%>
	<p>
		Additional rewards per redemption: <br>
	<table border="1">
		<%
		    rewardNamesIterator = r.getRewardNames().iterator();
		    amountsIterator = r.getRewardAmounts().iterator();
		    while (rewardNamesIterator.hasNext()) {
				out.println("<tr><td>" + rewardNamesIterator.next()
					+ "</td><td>" + amountsIterator.next() + "</td></tr>");
		    }
		%>
	</table>
	<p>
		Amount of
		<%=eventName%>: <input type="text" name="startAmount"><br>
		<input type="button" value="Calculate" onclick="calculate()">
	<p>Total used:
	<div id="totalUsed"></div>

</body>
</html>