<%@page import="java.util.Iterator"%>
<%@page import="site.calculators.Reward"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>

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
			<%="'" + rewardNamesIterator.next() + "'"%><%if(i<r.getNumberOfRewards()-1){%>,<%}%>
		<%}%>
		];
		var additionalRewardAmounts = [
		<%for (int i = 0; i<r.getNumberOfRewards();i++) {%>
			<%=amountsIterator.next()%> <%if(i<r.getNumberOfRewards()-1){%>,<%}%>
		<%}%>
		];
		
// 		var button = document.getElementById("calculateButton");
// 		button.onclick=function(){calculate()};

		function calculate() {
			var totalUsed = parseInt(repeatUse(parseInt(document.getElementById("startAmount").value,10),
			<%=r.getUseRequired()%>,
			<%=r.getReturnAmount()%>
			));
			var rewardsGiven = Math.floor(totalUsed / <%=r.getUseRequired()%>);

			document.getElementById("totalUsed").innerHTML = totalUsed;
			<%for(int i = 0;i<r.getNumberOfRewards();i++){%>
				document.getElementById("add"+<%=i%>+"name").innerHTML = additionalRewardNames[<%=i%>];
				document.getElementById("add"+<%=i%>+"amount").innerHTML = rewardsGiven * additionalRewardAmounts[<%=i%>];
			<%}%>
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
	<form id="calcForm" method="post" action="javascript:calculate()">
		Amount of
		<%=eventName%>: <input type="text" name="startTextbox"
			id="startAmount"><br> <input type="button"
			id="calculateButton" value="Calculate" onclick="calculate()">
	</form>

	<p>
	<table cellpadding="2">
		<tr>
			<td>Total <%=eventName%> Used:
			</td>
			<td id="totalUsed">
		</tr>
		<tr>
			<td>Total Additional Rewards:</td>
		</tr>

		<%
		    for (int i = 0; i < r.getNumberOfRewards(); i++) {
		%>
		<tr>
			<td id="<%="add" + i + "name"%>" />
			<td id="<%="add" + i + "amount"%>" />
			<%
			    }
			%>
		</tr>
	</table>

</body>
</html>