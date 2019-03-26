<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${contextPath}/js/ask.js"></script>	
<script type="text/javascript">
$(document).ready(function() {
	$('[data-toggle="toggle"]').change(function(){
		var i = $(this).parent().find('label>i');
		if(i.hasClass('fa-angle-down')){
			i.removeClass('fa-angle-down').addClass('fa-angle-up');
		}else{
			i.removeClass('fa-angle-up').addClass('fa-angle-down');
		}
		$(this).parents('tbody').next('.toggleTBody').toggle();
	});
	$('[data-toggle="toggle-test"]').change(function(){
		var testCategoryId = $(this).parents("tr").attr("id");
		var isHidden = $("."+testCategoryId).is(":hidden");
		if(isHidden){
			$("."+testCategoryId).toggle();
			for(var index=0; index<$(".testCategory").length; index++){
				var tr = $(".testCategory")[index];
				var testCategoryIds = $(tr).attr("id");
				
				var i = $("#"+testCategoryIds).find('label.arrow-toggling>i');
				var isHiddenTR = $("."+testCategoryIds).is(":hidden");
				if(!isHiddenTR){
					if(i.hasClass('fa-angle-down')){
						i.removeClass('fa-angle-down').addClass('fa-angle-up');
					}else{
						i.removeClass('fa-angle-up').addClass('fa-angle-down');
					}
					
					$("."+testCategoryIds).not($("."+testCategoryId)).toggle();
				}
			}
		}
		//$(this).closest("tbody").find("tr").not($(this).parents("tr")).toggle()
	});
	exceptFirstCollapseAllTrs();
});
</script>
<table id="comparisionTable" class="table table-bordered table-addproduct">
	<thead id="productImages">
		<tr id="productImagesTR">			
			<th class="text-center" valign="middle" style="position:relative;">
				<div id="standardsDiv">
					<c:forEach var="standardMap" items="${standardsList}">
						${standardMap['standardName']} <input type="checkbox" class="standardCheckBox" value="${standardMap['standardId']}"  <c:if test="${standardMap['isDefault']}">checked="checked"</c:if> onchange="getTestsBasedOnStandard(this);"> 
					</c:forEach>
				</div>
				<div class="buttons-table-head">
					<input type="button" class="btn btn-md btn-default btnBlueB btnmargin" onClick="clearComparisionDashBoard()" value="<spring:message code="ask.askcontainer.label.cleardashboard"/>" style="margin-bottom:10px;" /><br />
					<input type ="button" id="requestForTestsForProductsId" disabled="disabled"  value="Request TestData" class="btn btn-md btn-default btnBactiveB btnmargin btnspacing" />
				</div>
			</th>
		</tr>
	</thead>
	<tbody>
		<tr class="productSpecs">
			<td>
				<label><spring:message code="ask.askContainer.product.specs" /></label>
				<span class="collapseSpanSpec">
					<label for="collapseSpec" class="arrow-toggling productSpecsLabel"><i class="fa fa-angle-down"></i></label>
					<input type="checkbox" name="collapseSpec" id="collapseSpec" data-toggle="toggle">
				</span>
			</td>
		</tr>
	</tbody>
	<tbody class="toggleTBody">
		<c:forEach items="${productSpecs}" var="spec">
			<script type="text/javascript">
				productSpecsArray.push('${spec.prodSpecificationId}');
			</script>
			<tr id="${spec.prodSpecificationId }">
				<td>${spec.prodSpecificationName }</td>
			</tr>
		</c:forEach>
	</tbody>
	<tbody class='asc'>
		<tr class="testResults">
			<td>
				<span class="sortingSpan" onclick="sortLabTests();">
					<i class="fa fa-sort-asc"></i>
				</span>
				<label><spring:message code="ask.askConatiner.test.results" /></label>
				<span class="collapseSpanSpec">
					<label for="collapseTest" class="arrow-toggling labtestLabel"><i class="fa fa-angle-down"></i></label>
					<input type="checkbox" name="collapseTest" id="collapseTest" data-toggle="toggle">
				</span>
				<span class="addTestSpan">
					<i class="fa fa-plus-circle pull-right" id="addTestIconId"></i>
				</span>
			</td>
		</tr>
	</tbody>
	<tbody class="toggleTBody testCategories">
		<c:forEach items="${testResults}" var="testCategory">
			<tr id="${testCategory.testCategoryId}" class="testCategory">
				<td>
					<span class="collapseSpanSpec">
						<label> ${testCategory.testCategroyName}</label>
						<label for="${testCategory.testCategoryId}_testCat" class="arrow-toggling"><i class="fa fa-angle-down"></i></label>
						<input type="checkbox" name="${testCategory.testCategoryId}" id="${testCategory.testCategoryId}_testCat" data-toggle="toggle-test">
					</span>
				</td>
			</tr>
			<c:forEach items="${testCategory.testResultsVOs}" var="testResult">
				<script type="text/javascript">
					testResultsArray.push('${testResult.testId }::@::${testCategory.testCategoryId}');
				</script>
				<tr id="${testResult.testId }" class="${testCategory.testCategoryId} testClass">
					<td>${testResult.testName } <span class="remove-test-icon pull-right" onclick="removeClickedTR(this)"><i class="fa fa-minus"></i></span></td>
				</tr>
			</c:forEach>
		</c:forEach>
	</tbody>
</table>