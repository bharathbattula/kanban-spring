<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<footer class="container-fluid text-center">
<spring:message code="ap.fooer.ansellRightsReserved" />
 <c:if test="${not empty sessionScope.askAppVersion}">
 	<span class="pull-right"><spring:message code="ap.fooer.version" /> <c:out value="${sessionScope.askAppVersion}"></c:out></span>
 </c:if>
</footer> 