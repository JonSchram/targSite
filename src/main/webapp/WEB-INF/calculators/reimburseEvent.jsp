<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description"
	content="Calculate reward for Wartune events where consumption of an item gives back more of that same item.">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<title>${EventName}${" Reward Calculator"}</title>
</head>

<body>
	<!-- JQuery and Bootstrap scripts -->
	<%@include file="../includes/jqueryBtst.html"%>
	<!-- Main site header -->
	<%@include file="../includes/NavHeader.jsp"%>
	<%-- Header showing links to other calculators --%>
	<%@include file="../includes/calcHeader.html"%>

	<script type="text/javascript">
        var additionalRewardAmounts = [
    <%@include file="rewardArray.jsp"%>
        ];

        // 		var button = document.getElementById("calculateButton");
        // 		button.onclick = calculate;

        function calculate() {
            var useReq = '${RewardData.useRequired}'
            var returnAmount = '${RewardData.returnAmount}'
            var totalUsed = parseInt(repeatUse(parseInt(document
                    .getElementById("startAmount").value, 10), useReq,
                    returnAmount));
            var rewardsGiven = Math.floor(totalUsed / useReq);

            document.getElementById("totalUsed").innerHTML = totalUsed;
            for (i = 0; i < additionalRewardAmounts.length; i++) {
                document.getElementById("add" + i + "amount").innerHTML = rewardsGiven
                        * additionalRewardAmounts[i];
            }
            return false;
        }

        /**
         * Uses item and redeems reward until all items have been used (uses remaining
         * items even if using them gives no further rewards)
         */
        function repeatUse(startAmount, required, returnAmount) {
            if (startAmount <= 0 || isNaN(startAmount))
                return 0;
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

	<div class="container-fluid">
		<div class="panel panel-default">
			<div class="panel-heading">${EventName}${" event rewards"}:</div>
			<table class="table table-condensed">
				<tr class="row">
					<td>${EventName}${' needed for reward:'}</td>
					<td>${RewardData.useRequired}</td>
				</tr>
				<tr class="row">
					<td>${EventName}${' returned in reward:'}</td>
					<td>${RewardData.returnAmount}</td>
				</tr>
			</table>
		</div>

		<div class="panel panel-default">
			<div class="panel-heading">Additional rewards per redemption:</div>
			<table class="table table-hover table-condensed">
				<c:forEach items="${RewardData.map}" var="rew">
					<tr class="row">
						<td>${rew.key}</td>
						<td>${rew.value}</td>
					</tr>
				</c:forEach>
			</table>
		</div>

		<div class="panel panel-default">
			<div class="panel-heading">Your items</div>
			<div class="container-fluid">
				<form role="form" class="form-inline" id="calcForm" method="post"
					action="#" onsubmit="return calculate()">
					<div class="form-group">
						<label>Amount of ${EventName}:</label> <input type="number"
							class="form-control" name="startTextbox" id="startAmount" min="0">

						<button type="button" class="btn btn-default" id="calculateButton"
							onclick="calculate();">Calculate</button>
					</div>
				</form>
			</div>
		</div>

		<div class="panel panel-default">
			<div class="panel-heading">Rewards</div>
			<div class="panel-body">
				Total ${EventName} Used: <span id="totalUsed"></span>
			</div>
			<table class="table table-hover table-condensed">
				<thead>
					<tr class="row">
						<th>Total Additional Rewards:</th>
						<th></th>
					</tr>
				</thead>
				<c:forEach items="${RewardData.map}" var="item" varStatus="loop">
					<tr class="row">
						<td>${item.key}</td>
						<td id="add${loop.index}amount">0</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>

</body>
</html>