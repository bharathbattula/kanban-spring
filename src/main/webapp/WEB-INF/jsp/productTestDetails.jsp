<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="rnd" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="sourceName" value="${paramName}" />
<script type="text/javascript">

$(function(){	
	/* var dates = $("#testResultsNeededByDate").datepicker({
		showOn: "button",
		buttonImageOnly: false,
		buttonImage: "/img/calendar.gif",
		buttonText:"Calendar",			
		dateFormat:"dd-M-yy",
		changeMonth: true,
		changeYear: true,
		minDate: 0,
		onSelect: function( selectedDate ) {
			var option = this.id == "testResultsNeededByDate" ? "minDate" : "maxDate",
				instance = $( this ).data( "datepicker" ),
				date = $.datepicker.parseDate(
					instance.settings.dateFormat ||
					$.datepicker._defaults.dateFormat,
					selectedDate, instance.settings );
			dates.not( this ).datepicker( "option", option, date );
		}
		});	 */
		
	formDatePicker("testResultsNeededByDate",dateFormatJs);
});
</script>
<div class="col-md-6">
	<div class="form-group">
		<label class="control-label col-md-12 text-left"><spring:message code="label.isthisbenchmarktestingrequiredforcompetitivebid"/></label>
		<div class="col-md-12">	
			<label class="radio-inline" for="yes"><input type="radio" id="isBenchmarktestingRequired_yes" name="isBenchmarktestingRequired" value="1" checked="checked">Yes</label>
			<label class="radio-inline" for="no"><input type="radio" id="isBenchmarktestingRequired_no" name="isBenchmarktestingRequired" value="0">No</label>
		</div>
	</div>
</div>
<div class="col-md-6">
	<div class="form-group">
		<label class="control-label col-md-12 text-left"><spring:message code="label.supportsampleprocurement"/></label>
		<div class="col-md-12">
			<label class="radio-inline" for="yes"><input type="radio" id="supportsProcurement_yes" name="supportsProcurement" value="1" checked="checked">Yes</label>
			<label class="radio-inline" for="no"><input type="radio" id="supportsProcurement_no" name="supportsProcurement" value="0">No</label>
		</div>
	</div>
</div>
<div class="col-md-6">
	<div class="form-group">
		<label class="control-label col-md-12 text-left"><spring:message code="label.isthisbenchmarktestingrequiredforspecificcustomer"/></label>
		<div class="col-md-12">
			<input id="specificCustomerName" name="specificCustomerName" type="text" class="form-control" value="">
		</div>
	</div>
</div>

<div class="col-md-6">
	<div class="form-group">
		<label class="control-label col-md-12 text-left"><spring:message code="label.linktoguardianoppurtunity"/></label>
		<div class="col-md-12">
			<input id="linkToGuardianOppurtunity" name="linkToGuardianOppurtunity" type="text" class="form-control" value="">
		</div>
	</div>
</div>
<div class="clearfix"></div>
<div class="col-md-12">
	<hr/>
	<h4><spring:message code="ask.askContainer.label.timingandurgency"></spring:message>:</h4>
</div>
<div class="col-md-6">
	<div class="form-group">
		<label class="control-label col-md-12 text-left"><spring:message code="label.urgency"/><span class="red">*</span></label>	
		<div class="col-md-12">		          		
            <input type="hidden" class="form-control" id="hiddenurgencyLevelId" value="${record_id}"/>
    	 	 <input type="hidden" class="form-control" id="hiddenurgencyLevelName" value="${record_description}"/>
      	 	 <input type="text" id="urgencyLevel" class="form-control multiselectdropicon" autocomplete="off" value="${record_description}" readonly/>
      	</div>
	</div>
</div>
<div class="col-md-6">
	<div class="form-group">
		<label class="control-label col-md-12 text-left"><spring:message code="label.resultsNeededBy"/><span class="red">*</span></label>
		<div class="col-md-12">
			<input type="text" readonly="readonly" id="testResultsNeededByDate" name="testResultsNeededByDate" class="form-control" title="" aria-describedby="basic-addon1" />
		</div>
	</div>
</div>
<c:if test="${fn:length(sourceName) > 0}">
	<div class="col-md-6">
		<div class="form-group">
			<label class="control-label col-md-12 text-left"><spring:message code="label.testToPerform"/></label>
			<div class="col-md-12">
				<input type="text" id="testToPerform" name="testToPerform" class="form-control" value="" />
			</div>
		</div>
	</div>
</c:if>
<div class="col-md-6">
	<div class="form-group">
		<div class="col-md-12" style="margin-top: 20px;">
			<input type="hidden" name="copyOfResponse" id="copyOfResponse">
			<input type="checkbox" id="isCheckedCopyOfResponse" value="true" class="mar_auto" name="isCheckedCopyOfResponse"><label for="label.copyOfResponses"><spring:message code="label.copyOfResponses"/></label>
		</div>
	</div>
</div>
<div class="clearfix"></div>
<div class="col-md-12">
	<div class="form-group">
		<label class="control-label col-md-12 text-left"><spring:message code="label.additionalInfo"/></label>
		<div class="col-md-12">				
			<textarea id="additionalInfo" name="additionalInfo" title="<spring:message code="label.enterAdditionalInfo"/>" maxlength="500" style="height:116px !important;" class="form-control" rows="2"></textarea>
		</div>
	</div>
</div>