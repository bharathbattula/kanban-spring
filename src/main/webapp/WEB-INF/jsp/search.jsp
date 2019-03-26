<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%> 
<div class="row">
	<div class="search-input">
		<div class="col-md-6 input-group">
			<spring:message code="ask.askcontainer.label.addproducts" var="addProductslabel"></spring:message>
			<input type="text" id="searchBoxId" name="askSelectProduct" class="search-textfield" placeholder="${addProductslabel}" autocomplete="off" />
			<button type="button" onclick="getProductDetails()"><i class="fa fa-search"></i></button>
	    </div>
	 </div>	
</div>
 	