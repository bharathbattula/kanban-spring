<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>

<script type="text/javascript">
	var contextPath = '${contextPath}';
</script>
<script type="text/javascript" src="${contextPath}/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery/jquery-ui.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery/css_browser_selector.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery/tether.min.js"></script>
<%-- <script type="text/javascript" src="${contextPath}/js/jquery/jquery.ui.datepicker.min.js"></script> --%>
<%-- <script type="text/javascript" src="${contextPath}/js/masterpagejs.js"></script> --%>
<script type="text/javascript" src="${contextPath}/js/rich-autocomplete/jquery.richAutocomplete.js"></script>
<script type="text/javascript" src="${contextPath}/js/js_resource_bundle.js"></script>
<script type="text/javascript" src="${contextPath}/js/ask.js"></script>
<script type="text/javascript" src="${contextPath}/js/commonDialogBox.js"></script>
<script type="text/javascript" src="${contextPath}/js/date.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery-sorttable/jquery.sorttable.js"></script>

<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/fontawesome/font-awesome.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/animate.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/common.css">
<link type="text/css" rel="stylesheet" href="${contextPath}/css/normalize.min.css"/>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/survey.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/js/rich-autocomplete/richAutocomplete.min.css">	
<link rel="stylesheet" type="text/css" href="${contextPath}/css/jQuery/jquery-ui.css">
<link type="text/css" rel="stylesheet" href="${contextPath}/css/plugins/jqgrid/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css">
<meta charset="UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
</head>
<body>
<div class="mainpanel">
	<tiles:insertAttribute name="header" />		
	<!-- page Content start -->
	<div id="wrapper" class="container-fluid" style="padding-top:20px;"> 
	<div id="notificationSave"><spring:message code="informationsavedsuccessfully"></spring:message></div>
		 <div id="customMessage"></div>
		 
		<div class="row"> 
			<div id="page-content-wrapper">
				<div class="container-fluid">
					<div class="row hideMenuCls">
						<div> <!--The content will be displayed here..<div class="col-lg-12"> commented--> 
						<!-- tab start -->
							<tiles:insertAttribute name="body" />
						<!-- tab end --> 
						</div>
					</div>
				</div>
				
			</div>
		</div>
		<div id="confirmationDialog" style="display: none;"></div>
	</div>
</div>
<div class="clearfix"></div>
<tiles:insertAttribute name="footer" />

<div id="pageLoaderAP" class="loaderTransparent" style="display: none;">
	<div>
		<div class="loading_gif"></div>
	</div>
</div>
</body>
</html>