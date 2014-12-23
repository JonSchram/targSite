<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
{
<c:forEach items="${rewards.map}" var="level" varStatus="levelLoop">
		${level.key}:{
		"bonus":${level.value.bonusGems},
		"level":${level.value.level},
		"additional":{
			<c:forEach items="${level.value.map}" var="additional"
		varStatus="loop">
				"${additional.key}": ${additional.value}${loop.last ? "" : "," }
			</c:forEach>
			}
		}${levelLoop.last ? "" : ","}
</c:forEach>
}
