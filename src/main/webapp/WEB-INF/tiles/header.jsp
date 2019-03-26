<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<nav class="navbar navbar-default">
	<div class="container">
		<div class="row">
			<div class="col-md-3"> 
		    	<a class="navbar-brand" href="${contextPath}/welcome"><img class="askHeaderUsermenuImg" src="${contextPath}/img/ansell-logo.png"></a>
		    </div>		    	
			 <div class="header-right" id="topnav">
			 	<ul class="headermenu">
					<li><img class="flag-image" src="${contextPath}/img/countryFlag_${sessionScope.askSessionUser.languageId}.png"/> ${languageName}</li>
					<li class="userprofile1">
						<img class="img-circle" src="${contextPath}/img/default-avatar-00.png"/>
						<span class="badge badge-inverse"><img class="askHeaderUsermenuImg" src="${contextPath}/img/region_s_${sessionScope.askSessionUser.regionId}.png"/></span>
						<span class="userprofile">${sessionScope.askSessionUser.firstName} ${sessionScope.askSessionUser.lastName}</span>
						<span class="caret"></span>
					</li>
					<li><a href="#">Contact Us</a></li>
				</ul>
			</div>						
		</div>
	</div>
</nav>