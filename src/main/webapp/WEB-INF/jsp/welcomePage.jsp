<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
<head>
<title><spring:message code="ask.welcome.label.welocmepage.title"></spring:message></title>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/fontawesome/font-awesome.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/ask.css">
<style>
html, body {
	height: 100%;
	width: 100%;
	margin: 0;
}

</style>
<script>
function openWin() {
  window.open("${contextPath}/askContainer","_parent");
}
</script>
</head>
<body>
<div id="outer">
	<tiles:insertAttribute name="header" />
	<div id="welcome-image">
		<a class="btn btn-lg btn-default btnBlueB enter-btn" onclick="openWin()"><spring:message code="ask.welocme.label.enter"></spring:message> <i class="fa fa-angle-right"></i></a>
	</div>
</div>
<tiles:insertAttribute name="footer" />
</body>
</html>