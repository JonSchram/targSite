<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach items="${RewardData.map}" var="item" varStatus="loop">
	${item.value} ${!loop.last?",":""}
</c:forEach>