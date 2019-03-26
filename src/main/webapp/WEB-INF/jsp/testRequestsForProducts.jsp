<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix ="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script>
$( document ).ready(function() {
    populateProductTestDiv();
});
</script>
<div class="col-md-12 askOtherProductForm">
	<form class="form-horizontal" id="testRequestProductForm">
		<!-- Start of Product -->
		<div id = "productTestsDiv">			
		</div>
		<!-- End of Product -->
		<div class="clearfix"></div>
		<hr />
		<div class="row"><jsp:include page="/WEB-INF/jsp/productTestDetails.jsp">
			<jsp:param name="paramName" value="testRequestForProducts"/>
		</jsp:include></div>
	</form>
</div>
<div class="col-md-12 text-c" style="padding-top:20px;">
	<button type="button" class="btn btn-md btn-default btnBlueB btnmargin btnspacing" onclick="closeDialog('productRequestFormDialog');" title="<spring:message code="label.cancel"/>" value="<spring:message code="label.cancel"/>">
	<i class="fa fa-times-circle-o btnIcon"></i><spring:message code="label.cancel"/></button>
	<button type="button" class="btn btn-md btn-default btnBactiveB btnmargin btnspacing" onclick="submitRequestForTestDataForProducts();" title="<spring:message code="label.save"/>" value="<spring:message code="label.save"/>">
	<i class="fa fa-floppy-o btnIcon"></i><spring:message code="label.save"/></button>   				
</div>


