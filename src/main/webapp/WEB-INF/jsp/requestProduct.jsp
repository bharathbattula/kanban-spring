<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script>
var urgencyArr = [];
$(document).ready(function(){	
	//initializeAutocompleteForAllFileds(eval('${jsonRegionList}'),'regionScopeSelected','selectedHiddenRegionId',1);
	initializeAutocompleteForAllFileds(eval('${jsonProdCategoryList}'),'productCategoryId','selectedHiddenProductCategoryId',2);
	initializeAutocompleteForAllFileds(eval('${listProdManufacturer}'),'productManufacturerId','selectedHiddenProductManufacturerId',3);
	initializeAutocompleteForAllFileds(eval('${lstProductBrand}'),'productBrandId','selectedHiddenProductBrandId',4);	
	initializeAutocompleteForAllFileds(eval('${urgencyLevelsList}'),'urgencyLevel','hiddenurgencyLevelId',5);	
});
</script>
<script type="text/javascript">
$(function(){  
	 $('#regionScopeSelected').on('focus',function(){
		if($("#selectedHiddenRegionId").val() != ""){
			enableNotFilter=true;
			counter = 0;
		}
		var e = jQuery.Event("keyup", { keyCode: 20 });
		$("#regionScopeSelected").get(0).setSelectionRange(0,0);
		$("#regionScopeSelected").trigger( e );
		});
	
	$('#productCategoryId').on('focus',function(){
		if($("#selectedHiddenProductCategoryId").val() != ""){
			enableNotFilter=true;
			counter = 0;
		}
		var e = jQuery.Event("keyup", { keyCode: 20 });
		$("#productCategoryId").get(0).setSelectionRange(0,0);
		$("#productCategoryId").trigger( e );
	});
	$('#productManufacturerId').on('focus',function(){
		if($("#selectedHiddenProductManufacturerId").val() != ""){
			enableNotFilter=true;
			counter = 0;
		}
		
		var e = jQuery.Event("keyup", { keyCode: 20 });
		$("#productManufacturerId").get(0).setSelectionRange(0,0);
		$("#productManufacturerId").trigger( e );
	});
	
	$('#productBrandId').on('focus',function(){
		if($("#selectedHiddenProductBrandId").val() != ""){
			enableNotFilter=true;
			counter = 0;
		}
		var e = jQuery.Event("keyup", { keyCode: 20 });
		$("#productBrandId").get(0).setSelectionRange(0,0);
		$("#productBrandId").trigger( e );
		});
	
	   $('#urgencyLevel').on('focus',function(){
		if($("#hiddenurgencyLevelId").val() != ""){
			enableNotFilter=true;
			counter = 0;
		}
		var e = jQuery.Event("keyup", { keyCode: 20 });
		$("#urgencyLevel").get(0).setSelectionRange(0,0);
		$("#urgencyLevel").trigger( e );
		});
	
	   $('input[name=isBenchmarktestingRequired]').on('blur', function () {
		  if($('input[name=isBenchmarktestingRequired]:checked').val()=='0'){ $("#showHideCustNameDiv").show();}else{$("#showHideCustNameDiv").hide();}
	   });
	   
	   /* $('#specificCustomerName , #otherProductStyleName').on('focus',function(){		  
		   	if($("#otherProductName").val().trim() !==''){
		   		checkRequestedProductAlreadyExists();
		   	}	
	   }); */
	  
	   
});
</script>
<div class="askOtherProductForm">
	<div id="customMessage"></div>
	<div class="col-md-12">
		<div class="row">
			<form class="form-horizontal" id="askOtherProductForm">
			 	<div class="col-md-6">
			  		<div class="form-group">
						<label class="control-label col-sm-12 text-left"><span class="red" id="regionManId">*</span><spring:message code="label.region"/></label>
						<div class="col-sm-12">
							<input type="hidden" class="form-control" id="selectedHiddenRegionId" value="${selectedRegionId}"/>
					     	<input type="hidden" class="form-control" id="selectedHiddenRegionIdName" value="${selectedRegionName}"/>
				        	<input type="text" id="regionScopeSelected" class="form-control" autocomplete="off" value="${selectedRegionName}" readonly/>	
						</div>
					</div>
				</div>
				<div class="col-md-6">
			  		<div class="form-group">
						<label class="control-label col-sm-12 text-left"><span class="red">*</span><spring:message code="label.productCategory"/></label>
						<div class="col-sm-12">
							<input type="hidden" class="form-control" id="selectedHiddenProductCategoryId" value="${selectedCategoryId}"/>
				     	 	<input type="hidden" class="form-control" id="selectedHiddenProductCategoryIdName" value="${selectedCategoryName}"/>
			        	 	<input type="text" id="productCategoryId" class="form-control multiselectdropicon" autocomplete="off" value="${selectedCategoryName}" />	
						</div>
					</div>
				</div>
			    <div class="col-md-6">
			  		<div class="form-group">
						<label class="control-label col-sm-12 text-left"><spring:message code="label.productManufacturer"/></label>
						<div class="col-sm-12">
							<input type="hidden" class="form-control multiselectdropicon" id="selectedHiddenProductManufacturerId" value="${selectedManufacturerId}"/>
				     	 	<input type="hidden" class="form-control" id="selectedHiddenProductManufacturerIdName" value="${selectedManufacturerIdName}"/>
			        	 	<input type="text" id="productManufacturerId" style="display:inline;" class="form-control multiselectdropicon" autocomplete="off" value="${selectedManufacturerIdName}"/>
			        	 	<input id="othProdManufacturer" name="othProdManufacturer" style="margin-top:10px;display:inline;" type="text" class="form-control" maxlength="100" />	
						</div>
					</div>
				</div>
				<div class="col-md-6">
			  		<div class="form-group">
						<label class="control-label col-sm-12 text-left"><spring:message code="label.productBrand" /></label>
						<div class="col-sm-12">
							<input type="hidden" class="form-control" id="selectedHiddenProductBrandId" value="${selectedBrandId}"/>
				     	 	<input type="hidden" class="form-control" id="selectedHiddenProductBrandIdName" value="${selectedBrandName}"/>
			        	 	<input type="text" id="productBrandId" class="form-control multiselectdropicon" autocomplete="off" value="${selectedBrandName}" />
			        	 	<input id="othProdBrand" name="othProdBrand" style="margin-top:10px;" type="text" class="form-control" value="" maxlength="50"/>	
						</div>
					</div>
				</div>	
				<div class="col-md-6">
			  		<div class="form-group">
						<label class="control-label col-sm-12 text-left"><span class="red">*</span><spring:message code="label.productName"/></label>
						<div class="col-sm-12">
							<input id="otherProductName" name="otherProductName" type="text" class="otherProductName form-control" value="" maxlength="50" />	
						</div>
					</div>
				</div>
			   <div class="col-md-6">
			  		<div class="form-group">
						<label class="control-label col-sm-12 text-left"><spring:message code="label.productStyle"/></label>
						<div class="col-sm-12">
							<input id="otherProductStyleName" class="otherProductName form-control" name="otherProductStyleName" type="text" value="" maxlength="255" />
						</div>
					</div>
				</div>
				<jsp:include page="/WEB-INF/jsp/productTestDetails.jsp"></jsp:include>
			</form>
		</div>
	</div>
</div>
<div class="col-md-12 text-c" style="padding-top:20px;">
	<button type="button" class="btn btn-md btn-default btnBlueB btnmargin btnspacing" onclick="closeDialog('productRequestFormDialog');" title="<spring:message code="label.cancel"/>" value="<spring:message code="label.cancel"/>">
	<i class="fa fa-times-circle-o btnIcon"></i><spring:message code="label.cancel"/></button>
	<button type="button" class="btn btn-md btn-default btnBactiveB btnmargin btnspacing" onclick="newProductRequest();" title="<spring:message code="label.save"/>" value="<spring:message code="label.save"/>">
	<i class="fa fa-floppy-o btnIcon"></i><spring:message code="label.save"/></button>   				
</div>

