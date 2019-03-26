<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<script type="text/javascript">

var productSpecsArray = new Array();
var testResultsArray = new Array();
var productIdsArray = new Array();
var regionId = '1';  
var dateFormatJs = '${dateFormatJS}';
$(document).ready(function(){
		loadAutoCompleteData('searchBoxId','askSelectProduct');					
					
	    $('#searchBoxId').on('focus',function(){						
	    	$(".excludedProducts").parent().css("background-color","gray");
		});   
	    	    
});

var regionId   = '${sessionScope.askSessionUser.regionId}';  
var languageId = '${sessionScope.askSessionUser.languageId}';
var userName   = '${sessionScope.askSessionUser.firstName} ${sessionScope.askSessionUser.lastName}';
var email      = '${sessionScope.askSessionUser.email}';

</script>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<h1 class="title-cls-name"><spring:message code="ask.label.mainpage.label.salesknowledge"></spring:message></h1>
			<p><spring:message code="ask.label.mainpage.label.searchbar.note"></spring:message></p>
		</div>			
		<div class="clearfix"></div>
		<div class="search-bar">
			<div class="container">
				<jsp:include page="/WEB-INF/jsp/search.jsp"></jsp:include>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
	<div class="leftDiv"></div>
</div>
<div id="productRequestFormDialog" class="productRequestFormDialog" style="{overflow-y: scroll;height: 400px;}"></div>
<div id="testDialogId"></div>		
<div id="requestTestsForProductDialogId"> </div>

		
