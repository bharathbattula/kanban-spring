<%@ tag isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="gridId" required="true" rtexprvalue="true"%>
<%@ attribute name="colModel" required="true" rtexprvalue="true"%>
<%@ attribute name="colNames" required="true" rtexprvalue="true"%>
<%@ attribute name="tableCaption" required="false"%>
<%@ attribute name="sortIndex" required="false"%>
<%@ attribute name="sortOrder" required="false"%>
<%@ attribute name="loadUrl" required="true" rtexprvalue="true"%>
<%@ attribute name="loadUrlType" required="false" rtexprvalue="true"%>
<%@ attribute name="additonalGridParams" rtexprvalue="true"%>
<%@ attribute name="customFormId" required="false"%>
<%@ attribute name="customParam" rtexprvalue="true"%>
<%@ attribute name="fn" rtexprvalue="true" %>
<%@ attribute name="filterToolbar" %>
<%@ attribute name="loadOnce" %>
<%@ attribute name="shrinkToFit" %>
<%@ attribute name="ignoreCase" %>
<%@ attribute name="afterLoad" rtexprvalue="true"%>
<%@ attribute name="disableAutoWidth" rtexprvalue="true" required="false"%>
<%@ attribute name="hidePager" required="false"%>
<%@ attribute name="loadComplete" rtexprvalue="true"%>
<%@ attribute name="columnChooser" required="false"%>
<%@ attribute name="rowNum" required="false"%>
<%@ attribute name="isCaptionVisible" required="false"%>
<%@ attribute name="isHiddenGridCaption" required="false"%>
<%@ attribute name="hideDlg" required="false" rtexprvalue="false"%>
<%@ attribute name="hidePagerInput" rtexprvalue="true" required="false"%>
<%@ attribute name="subGrid" required="false"%>
<%@ attribute name="subGridFunction" required="false"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<script language="javascript" type="text/javascript">

var previousPgNo;
var rowNumber = 10;
<c:set var="isHiddenGridCaption" value="${(empty isHiddenGridCaption) ? true : isHiddenGridCaption}" />

<c:if test="${not empty rowNum}">
	rowNumber="${rowNum}";
</c:if>

$(function()
{ 	
	jQuery("#${gridId}grid").jqGrid({ 
		<c:if test="${not empty loadUrlType}">
				url:${loadUrl}, 
		</c:if>
		<c:if test="${empty loadUrlType or loadUrlType eq null}">
				url:'${loadUrl}', 
		</c:if>
				datatype: "json",				
				ajaxGridOptions: {  contentType: "application/json; charset=utf-8" }, 
				mtype: 'GET', 
				colNames:${colNames}, 
				colModel:${colModel},
				<c:choose>
				 	<c:when test="${hidePagerInput}">
				 		pginput: false,
					</c:when>
				 	<c:otherwise>
				 		pginput: true,
				 	</c:otherwise>
			 	</c:choose>
				<c:if test="${!hidePager}">
					pager: '#${gridId}pager', 
				</c:if>
				<c:if test="${not empty tableCaption and not empty isCaptionVisible and isCaptionVisible eq true}">
					caption: '${tableCaption}', 
					hiddengrid: ${isHiddenGridCaption},
				</c:if>	
					
				rowNum:rowNumber,
				height:'100%',
				rowList:[rowNumber * 1, rowNumber * 2, rowNumber * 3],
				viewrecords: true, 
				sortname: "${sortIndex}", 
				sortorder: "${sortOrder}",	
				loadonce:"${loadOnce}",
				<c:if test="${not empty shrinkToFit}">
					shrinkToFit: "${shrinkToFit}",
				</c:if>
				<c:if test="${not empty subGrid}">
					subGrid: ${subGrid}, 
				</c:if>
					<c:if test="${not empty shrinkToFit}">
					shrinkToFit: "${shrinkToFit}",
				</c:if>
				multiSubGrids: false,
				subGridOptions: {
			        "plusicon"  : "ui-icon-triangle-1-e",
			        "minusicon" : "ui-icon-triangle-1-s",
			        "openicon"  : "ui-icon-arrowreturn-1-e",			        
					"selectOnExpand" : false
				},
				subGridRowExpanded: function(subgrid_id, row_id) {
					return subGridFunction(subgrid_id, row_id,'${gridId}'+'grid');
					
				},
				serializeRowData : function(rowData){
					return replaceGridRow_HtmlEntites(rowData);
				},
				/* loadBeforeSend:function(){
					jQuery('#${gridId}Container').block({ message: null });
				}, */
				/* gridComplete:function () {jQuery('#${gridId}Container').unblock();}, */
				onPaging: function (pgButton) {
				    var p = this.p;
				   // alert( p.page + " "+  p.lastpage );
				  //  alert(  eval(p.page) > eval(p.lastpage) );
				    
				    if (pgButton === "user" && parseInt(p.page)  > parseInt(p.lastpage)) {
				        alert (jQuery.i18n.prop('i18n.youCantChooseThePage') +" "+ $(p.pager + " input.ui-pg-input").val());
				        p.page = p.currentPage; // restore the value of page parameter
				        $("#${gridId}pager").find(".ui-pg-input").val(previousPgNo);
				        $("#${gridId}grid").trigger("reloadGrid");
				        return 'stop';
				    }else{
				    	previousPgNo=p.page;
				    }
				},
				loadComplete: function() {
					${afterLoad}
			},		
				postData: { 
					${customParam}
			},
			<c:choose>
				 <c:when test="${disableAutoWidth}">
				autowidth: false,
				</c:when>
				 <c:otherwise>
				 autowidth: true,
				 </c:otherwise>
			 </c:choose>
		    jsonReader : {
		        repeatitems: false
		    },
		   ignoreCase: "${ignoreCase}",
		    onSelectRow: function (rowId, status) {
		    	${fn}(rowId,'${gridId}grid', status);
		    }	
		    ${additonalGridParams}
		}); 
	 <c:if test="${filterToolbar}">
		 jQuery("#${gridId}grid").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false,searchOperators : true});
		 //$(".ui-search-toolbar th:first").find("div:first").html("<input class='view_buttonFilterToolbar' type='button'/>");
	 </c:if>
	 
	 
	
	//${afterLoad} // commented to resolve DEF 597 (By dharmendra)
});

<c:if test="${not empty columnChooser and columnChooser ne false}">
jQuery( document ).ready(function() {
	jQuery("#${gridId}grid").jqGrid('navGrid','#${gridId}pager',{add:false,edit:false,del:false,search:false,refresh:false});
	jQuery("#${gridId}grid").jqGrid('navButtonAdd','#${gridId}pager',{
	    caption: jQuery.i18n.prop('i18n.selectColumns'),
	    title: jQuery.i18n.prop('i18n.selectColumns'),
	    hidedlg:"${hideDlg}",
	    jqModal : true,
	    onClickButton : function (){
		        var colModel = jQuery(this).jqGrid("getGridParam", "colModel");
		        var colNames = jQuery(this).jqGrid("getGridParam", "colNames");
		        //console.log(colModel);
		        $("#${gridId}_columns").html(prepareColumnModel${gridId}(colModel, colNames));
	        $("#${gridId}_columns").dialog({
	        	modal:true,
	        	autoOpen: true,
	        	position: { 
	        				my: "center", 
	        				at: "center", 
	        				of: window 
	        			   },
	        	width: 600,
	        	resizable: false,
	        	title: jQuery.i18n.prop('i18n.selectColumns'),
	    		open:function(){
	    			jQuery(this).parent().find(".ui-dialog-buttonset button").removeClass("ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
	    			jQuery(this).parent().find(".ui-dialog-buttonset").removeClass("ui-dialog-buttonset").addClass("pull-right");
	    		 	$('.cancelTag').html("<i class='fa fa-times namecls' ></i>"+jQuery.i18n.prop('i18n.cancel'));
	    			$('.okTag').html("<i class='fa fa-check-circle namecls' ></i>"+jQuery.i18n.prop('i18n.ok')); 
	    			jQuery(this).parent().find(".pull-right").parent().css("margin-top","32px");
	    		},
				buttons: [
				{
				  	text: jQuery.i18n.prop('i18n.cancel'),
				  	title: jQuery.i18n.prop('i18n.cancel'),
					"class": 'btn btnmargin btn-md btn-default btnBlueB cancelTag',
					//"class": 'btn_cancelSecondary',
				    click: function() {
				    $(this).dialog("close");
					}
				},
				{
	    			text: jQuery.i18n.prop('i18n.ok'),
	    			title:  jQuery.i18n.prop('i18n.ok'),
	    			"class": 'btn btnmargin btn-md btn-default btnBactiveB okTag',
	    			//"class": 'btn_okPrimary',
	    			click: function() {
	  	  				if($("#${gridId}_columns input:checkbox:checked").size() < 1){
							alert(jQuery.i18n.prop('i18n.pleaseSelectAtleastOneColumn'));
							return;
							}
						showHideColumns${gridId}("${gridId}");
						saveGridSettings${gridId}('${gridId}');
						$(this).dialog("close");
	    			}
				}		          
			],
			close: function() {
				$("#${gridId}_columns").html("");
			}
		});
		console.log("onclick trapped");
	    }
	});
	$("#${gridId}pager_left").css("float", "left")
});

function showHideColumns${gridId}(a_gridId){
	$("#" + a_gridId + "_columns input:checkbox").each(function(){
		if($(this).is(":checked")){
			$("#" + a_gridId + "grid").showCol($(this).val());
		}else{
			$("#" + a_gridId + "grid").hideCol($(this).val());
		}
	});
}

function prepareColumnModel${gridId}(colModel, colNames){
	var returnHtml = "<ul>";
	var colPermutation = "[";
	var colIndex = 0;
	$.each(colModel, function(i) {
		if(colIndex > 0){
			colPermutation += ",";
		}
		colPermutation += colIndex;	
		colIndex++;
		
		//console.log(colIndex)

        var isAllowed = true;		
        if(colNames[i] == jQuery.i18n.prop('i18n.action')){
        	isAllowed = false;
        }else if(colNames[i].indexOf("<input") == 0){
        	isAllowed = false;
        }else if($.trim(colNames[i]) <= 0){
        	isAllowed = false;
        }
        
        if(isAllowed == false || colModel[i].hidedlg == true){
        	return;
        }
        returnHtml += '<li class="ui-widget-content jqgrow ui-row-ltr"><label><input type="checkbox" value="' + colModel[i].name + '" ';
        returnHtml += this.hidden ? "" : "checked";
        returnHtml += '/>';
        returnHtml += colNames[i];
        returnHtml += '</label></li>';
    });
	returnHtml += "</ul>";
	returnHtml += "<input type='hidden' name='indexes' value='"+colPermutation+"]'>";
	return returnHtml;
}

function saveGridSettings${gridId}(){
	var requestBody = 'requestdata={';
	requestBody += 'gridID:"${gridId}",';
	requestBody += "configData:'[";
	$("#${gridId}_columns input:checkbox").each(function(){
		requestBody += '{';
		requestBody += '"columnName":"' + $(this).val() + '",';
		requestBody += '"isVisible":' + $(this).is(":checked");
		requestBody += '},';
	});
	requestBody += "]'}";
	console.log(requestBody);
	$.ajax({
		url: contextPath + '/commonGrid/saveGridSettings',
		type : 'POST',
		data : requestBody,
		success : function(data) {
			//console.log("grid config saved : " + data);
			resizeAllVisibleGrids();
		}
	});	
}

function loadGridSettings${gridId}(){
	var requestBody = "gridId=${gridId}";
	$.ajax({
		url: contextPath + '/commonGrid/loadGridSettings',
		type : 'GET',
		data : requestBody,
		success : function(data) {
			if($.trim(data).length <= 1){
				return;
			}
			//console.log("grid config found " + latestID);
			var tempObj = eval(data);
			$.each(tempObj, function(index, value){
				if($.trim(value.columnName).length <= 1){
					return;
				}
				//console.log(value.columnName + " : " + value.isVisible)
				if(value.isVisible == false){
					//console.log("hiding : " + value.columnName);
					$("#${gridId}grid").hideCol(value.columnName);
				}
			});
			resizeAllVisibleGrids();
		}
	});
}

$( document ).ready(function(){
	loadGridSettings${gridId}()	
});
</c:if>
</script>

<div id="${gridId}Container" class="ansellGridContainer">

<table id="${gridId}grid" class="ansellGrid"></table>
<div id="${gridId}pager" class="scroll" style="text-align:center;"></div>
</div>

<div id="${gridId}_columns" style=""></div>

<div id="${gridId}dialog" title="<spring:message code="featureNotSupported"/>"
	style="display:none">
<p>
<spring:message code="thatFeatureIsNotSupported"/>
</p>
</div>

<div id="${gridId}dialogSelectRow" title="<spring:message code="warning"/>" style="display:none">
<p>
<spring:message code="pleaseSelectRow"/>
</p>
</div>
