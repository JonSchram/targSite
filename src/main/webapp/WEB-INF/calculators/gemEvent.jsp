<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<title>Gem Event Reward Calculator</title>
</head>
<body>
	<!-- JQuery and Bootstrap scripts -->
	<%@include file="../includes/jqueryBtst.html"%>
	<!-- Main site header -->
	<%@include file="../includes/NavHeader.jsp"%>
	<%-- Header showing links to other calculators --%>
	<%@include file="../includes/calcHeader.html"%>


	<script type="text/javascript">
        
    <%-- Convert reward data to an object and store as javascript variable rewards --%>
        var rewards =
    <%@include file="gemRewardArray.jsp"%>
        function calculate() {

            var currentGems = [
                    parseInt(document.getElementById("gemslvl1").value, 10),
                    parseInt(document.getElementById("gemslvl2").value, 10),
                    parseInt(document.getElementById("gemslvl3").value, 10),
                    parseInt(document.getElementById("gemslvl4").value, 10),
                    parseInt(document.getElementById("gemslvl5").value, 10),
                    parseInt(document.getElementById("gemslvl6").value, 10),
                    parseInt(document.getElementById("gemslvl7").value, 10),
                    parseInt(document.getElementById("gemslvl8").value, 10),
                    parseInt(document.getElementById("gemslvl9").value, 10),
                    parseInt(document.getElementById("gemslvl10").value, 10),
                    parseInt(document.getElementById("gemslvl11").value, 10),
                    parseInt(document.getElementById("gemslvl12").value, 10) ];

            var maxRewardLevel = 1;
            //Ensure that all gem numbers are positive and not NaN and at the same time see what the highest synthesis reward level is
            for (i = 0; i < currentGems.length; i++) {
                if (isNaN(currentGems[i]) || currentGems[i] < 0) {
                    currentGems[i] = 0;
                    document.getElementById("gemslvl" + (i + 1).toString()).value = "0";
                }
                if ((i + 1) in rewards) {
                    maxRewardLevel = i + 1;
                }
            }

            //lvl 2-7 require 4 gems to craft
            //lvl 8-10 require 3 gems to craft
            //lvl 11-12 require 2 gems to craft
            //higher level costs from Elia Tan's comment on
            //http://cosmos-wartune.blogspot.com/2014/08/level-12-gem-transposer-from-necropolis.html
            var redemptions = [];
            var cost = 4;
            //only synthesize gems up to max reward level
            for (i = 1; i < maxRewardLevel; i++) {
                //array starts at index 0, so level 8 gems are in index 7
                if (i === 7) {
                    cost = 3;
                }
                if (i === 10) {
                    cost = 2
                }
                var remainder = currentGems[i - 1] % cost;
                var synthesized = Math.floor(currentGems[i - 1] / cost);
                currentGems[i] += synthesized;
                currentGems[i - 1] = remainder;
                if ((i + 1) in rewards) {
                    /* Reward object supports rewards of gems that aren't the same level as the one synthesized,
                     * but this scenario has never happened and accounting for it will require more processing,
                     * so until this kind of event happens the simpler alorithm is the most fit for the job
                     */
                    redemptions.push(synthesized);
                    currentGems[i] += synthesized * rewards[i + 1]["bonus"];
                } else {
                    redemptions.push(0);
                }
            }
            //put leftover gems in table
            for (i = 1; i <= 12; i++) {
                document.getElementById("leftover" + i).innerHTML = currentGems[i - 1];
            }

            //put in other rewards
            //get rid of any old rewards
            deleteTableRows();

            //tally rewards
            var rewardsGiven = {};
            //redemptions array index 0 corresponds to synthesizing a lvl 2 gem
            for (i = 2; i <= 12; i++) {
                if (i in rewards) {
                    if (isNaN(redemptions[i - 2])) {
                        redemptions[i - 2] = 0
                    }
                    for ( var itemName in rewards[i].additional) {
                        //before adding a reward check that the item has a corresponding entry
                        if (!(itemName in rewardsGiven)) {
                            rewardsGiven[itemName] = 0;
                        }
                        //multiply number of times reward is given by reward amount
                        rewardsGiven[itemName] += redemptions[i - 2]
                                * rewards[i]["additional"][itemName];
                    }
                }
            }
            //rewardsGiven now is an object mapping reward names to amounts

            //how to do this is adapted from Mozilla Developer Network example:
            //https://developer.mozilla.org/en-US/docs/Web/API/HTMLTableElement.insertRow
            var table = document.getElementById("totalRewards");
            for ( var itemName in rewardsGiven) {
                var newRow = table.insertRow(-1);
                newRow.className = "row"
                var newCellItemName = newRow.insertCell(0);

                var itemNameText = document.createTextNode(itemName);
                newCellItemName.appendChild(itemNameText);

                var newCellAmount = newRow.insertCell(-1);
                var amountText = document
                        .createTextNode(rewardsGiven[itemName]);
                newCellAmount.appendChild(amountText);
            }

            return false;
        }

        function deleteTableRows() {
            var table = document.getElementById("totalRewards");
            var tableLength = table.rows.length;
            for (i = 1; i < tableLength; i++) {
                table.deleteRow(-1);
            }
        }
    </script>

	<div class="container-fluid">
		<div class="panel panel-default">
			<div class="panel-heading">Gem event rewards:</div>
			<table class="table table-hover">
				<c:forEach items="${rewards.map}" var="level" varStatus="levelLoop">
					<tr class="row">
						<th>Level ${level.key}</th>
						<td>Level ${level.value.level} gem x ${level.value.bonusGems}</td>
						<td><c:forEach items="${level.value.map}" var="additional"
								varStatus="loop">
								<td>${additional.key}${" x "}${additional.value}</td>
							</c:forEach>
					</tr>
				</c:forEach>
			</table>
		</div>

		<%--Useful feature would be to specify highest level transposer owned, for those who don't have them all --%>
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-4">
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="container-fluid">
								<form class="form-horizontal" action="#" role="form"
									method="post" onsubmit="return calculate()">
									<c:forEach begin="1" end="12" var="level">
										<div class="form-group">
											<label class="control-label col-sm-5 col-md-3"
												for="gemslvl${level}">Level ${level} gems:</label>
											<div class="col-sm-7 col-md-9">
												<input class="form-control" type="number" value="0" min="0"
													id="gemslvl${level}">
											</div>
										</div>
									</c:forEach>
									<div class="form-group">
										<div class="col-sm-offset-5 col-sm-7 col-md-offset-3 col-md-9">
											<button type="button" class="btn btn-default"
												onclick="calculate()">Calculate</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>

				<div class="col-sm-4">
					<div class="panel panel-default">
						<div class="panel-heading">Total Rewards:</div>
						<table class="table table-hover table-bordered text-center"
							id="totalRewards">
							<thead>
								<tr class="row">
									<th class="text-center">Item name</th>
									<th class="text-center">Quantity</th>
								</tr>
							</thead>
							<%--javascript inserts more rows here --%>
						</table>
					</div>
				</div>

				<div class="col-sm-4">
					<div class="panel panel-default">
						<div class="panel-heading">Leftover gems:</div>
						<table class="table table-striped table-bordered text-center">
							<thead>
								<tr class="row">
									<th class="text-center">Level</th>
									<th class="text-center">Amount</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach begin="1" end="12" var="i">
									<tr class="row">
										<td>${i}</td>
										<td id="leftover${i}">0</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>