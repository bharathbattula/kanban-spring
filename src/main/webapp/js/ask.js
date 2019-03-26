/**
 * 
 */ 
var pageNumber=1;
var pageSize=10;
var testValuesArray = [];
var enableNotFilter;
var counter = 0;
var testProductIdMap = new Object();
var testProductNameMap = new Object();
var requestParam = new Object();
var totalTestDataReqForProductCnt=0;
var productId1 = '8ff5e8d9-7648-11e8-9e9d-000d3a1a428c';
var productId2 = '8ff5e8d9-7648-11e8-9e9d-000d3a1a428c';
var selectedTest = "TESTS SELECTED - SUBMIT"
var productId;
var productName;
var comparisonChartCategoryId;
var tempCategoryId;
var dashboardtestIdArray = [];
var standardIds = [];

var testProductResultArray = [];
var map = new Object();	

$(document).ready(function(){
	
	var productName1 = 'Hyflex1000';
	var productName2 = 'Hyflex2000';
	var productName3 = 'Hyflex3000';
	var productName4 = 'Hyflex4000';

	var testNameList = [];
	testNameList.push('Test1');
	testNameList.push('Test2');
	testNameList.push('Test3');
	testNameList.push('Test4');
	testNameList.push('Test5');

	//testProductIdMap = {"country1": ["state1", "state2"], "country2": ["state1", "state2"]};

	
	$('#testId').on('focus',function(){ 
		if($("#hiddenTestId").val() != ""){
			enableNotFilter=true;
			counter = 0;
		}
		var e = jQuery.Event("keyup", { keyCode: 20 });
		jQuery("#testId").get(0).setSelectionRange(0,0);
		
		initializeTests(testValuesArray);
	});
	
	$('input[name=isBenchmarktestingRequired]').on('blur', function () {
		if($('input[name=isBenchmarktestingRequired]:checked').val()=='0'){ $("#showHideCustNameDiv").show();}else{$("#showHideCustNameDiv").hide();}
 	  });	
});

function loadAutoCompleteData(preferenceParamId,paramName){
	$('#'+preferenceParamId).richAutocomplete({
		loadPage: function  (searchTerm, pageNumber, pageSize){
			return loadServerPage(searchTerm, pageNumber, pageSize,paramName);
		},
	    paging: true,
	    pageSize: 10,
	    emptyRender: function(){	    	
	    		var strDiv = $('<div id="newProductRequestPopupId"><p><a href="#" >'+resourceBundle['ask.askContainer.js.label.no.productfound']+'</a></p> </div>'); 	    
	    	    return 	 strDiv;  	    	 
	    },
	    render: function(item){
	    	var x = this;	    	
	    	var renderStr ='';	    	
	    	if(item.emptyMsg == undefined || item.emptyMsg === ''){    		
	    		if(item.isExcluded == 'true')
	    			renderStr = '<div class="excludedProducts"><strong>'+resourceBundle['ask.askContainer.js.product.name']+': </strong>' + item.productName +"  | <strong>"+resourceBundle['ask.askContainer.js.product.category']+": </strong> "+ item.productCategoryName + " |  <strong>"+resourceBundle['ask.askContainer.js.product.brand']+": </strong>"+ item.productBrandName +" |  <strong>"+resourceBundle['ask.askContainer.js.product.manufacturer']+": </strong>"+ item.productManufacturer + '</div>';
	    		else
	    			renderStr = '<div><strong>'+resourceBundle['ask.askContainer.js.product.name']+': </strong>' + item.productName +"  | <strong>"+resourceBundle['ask.askContainer.js.product.category']+": </strong> "+ item.productCategoryName + " |  <strong>"+resourceBundle['ask.askContainer.js.product.brand']+": </strong>"+ item.productBrandName +" |  <strong>"+resourceBundle['ask.askContainer.js.product.manufacturer']+": </strong>"+ item.productManufacturer +  '</div>';    		 	
			}else{
	    		renderStr = item.emptyMsg;	
			}	    	
	        return renderStr;
	    },
	    extractText: function(item){	    	
	        return item.result;
	    },
	    select: function(item) {   	
	    		if(item.isExcluded == 'true'){
	    				openInformationWithOk('label.excludedProduct');
	    				return false;
	    		}else{
	    				if(productIdsArray !==undefined && productIdsArray.length >= 4){
	    					openInformationWithOk('label.maxFourProductsAllowedToCompare');
	    					return false;
	    				}
	    			
	    				var alreadyPresnt = false;
	    				$.each(productIdsArray, function( index, value ){    					
	    					 if(value==item.productId){
	    						 openInformationWithOk('label.productAlreadySelectedForComparision');
	    						 alreadyPresnt = true;
	    					 }
	    				});
	    				
	    				$('#'+preferenceParamId).val(item.productName);
	    		
	    				if (alreadyPresnt == false) {
							productId   = item.productId;
		    				productName = item.productName;
		    				tempCategoryId = item.productCategoryId;
		    				
		    				if(productIdsArray.length != 0){
		    					if(comparisonChartCategoryId != tempCategoryId){
		    						openConfirmationWithYesNo("label.categoryisdifferent",'confirmationClearDashBoardYes()','confirmationClearDashBoardNo()');
		    					}else{
		    						comparisonChartCategoryId  = item.productCategoryId;
		    						getProductDetails(productId, productName);
		    					}
		    				}else{
		    					comparisonChartCategoryId  = item.productCategoryId;
		    					getSpecificationsTests(comparisonChartCategoryId);
		    					getProductDetails(productId, productName);
		    					
		    				}
						}
	    		
	    				$('#searchBoxId').val("");
	    		}						    		
	    		if(item.emptyMsg != '<p>'+resourceBundle['ask.askContainer.js.please.type.text']+'</p>'){    			
		    	
	    	    }
	    },
	    selectEmpty:function(item) {  	
	    	  openProductRequestFormPopup();
	    },
	});
}

function confirmationClearDashBoardYes(){
	
	comparisonChartCategoryId = tempCategoryId;
	tempCategoryId = "";
	$(".leftDiv").html("");
	productIdsArray=[];
	productSpecsArray = new Array();
	testResultsArray = new Array();
	
	getSpecificationsTests(comparisonChartCategoryId);
	getProductDetails(productId, productName);
	productId="";
	productName="";
	okclose();
}

function confirmationClearDashBoardNo(){
	tempCategoryId = "";
	okclose();
}

function getSpecificationsTests(a_productCategoryId){
	$.ajax({
		url: contextPath+"/specificationTestResults",
		data:{
			"productCategoryId" : a_productCategoryId
		},
		async:false,
		success:function(data){
			$(".leftDiv").html(data);
		}
	});
}


function loadServerPage (searchTerm, pageNumber, pageSize,autoCompletedId){
	
	var autcompleteParams = {
			searchText:searchTerm,
			autocompleteId:autoCompletedId,
           	startIndex : pageNumber*pageSize,
           	pageSize : pageSize,
           	
	};
		autcompleteParams.ap_languageId = "int_"+1;
		autcompleteParams.ap_isDeleted = "int_"+0;
		autcompleteParams.ap_region_id = "int_"+regionId;
		 
	 	 var deferred = jQuery.Deferred();
	 		if(searchTerm != "")
	 		{
	 			jQuery.ajax({
	 			    type: "POST",
	 			    url: contextPath+"/getAutocompleteData",
	 			    data:autcompleteParams,
	 			    dataType: "json",
	 			    success: function (data) {
	 			    	deferred.resolve(data);
	 			    },
	 			    error: function (xhr, error) {
	 			        alert("readyState: " + xhr.readyState + "\nstatus: " + xhr.status);
	 			        alert("responseText: " + xhr.responseText);
	 			    }
	 			});
	 		}
	 		else if(searchTerm == "")
	 		{
	 			if(pageNumber < 1)
				{
					var emptyMsg = new Object();
					emptyMsg.emptyMsg = '<p><span class="glyphicon glyphicon-exclamation-sign" style="padding-right:5px;"></span>'+resourceBundle['ask.askContainer.js.please.select.product']+'</p>'
					var emptyMsgArray = new Array();
					emptyMsgArray.push(emptyMsg);
					deferred.resolve(emptyMsgArray);
				}
				else
				{
					var emptyMsgArray = new Array();
					deferred.resolve(emptyMsgArray);
				}		
	 		}
	 	    return deferred.promise();
}
 


function getProductDetails(a_prodId, a_prdName){
	openPageLoaderAP();
	productIdsArray.push(a_prodId);
	
	if(productSpecsArray.length == 0){
		productSpecsArray = new Array("0");
	}
	
	if(testResultsArray.length == 0){
		testResultsArray = new Array("0");
	}
	
	var standardsArray = new Array();
	
	$.each($("#standardsDiv").find("input[type='checkbox']:checked"), function(index, checkBoxElement){
		standardsArray.push(checkBoxElement.value);
	});
	
	$.ajax({
		url: contextPath+"/getProductDetails",
		type: 'GET',
		data:{
			"productId":a_prodId,
			"productName":a_prdName,
			"productSpecsIds": productSpecsArray,
			"testResultsIds" : testResultsArray,
			"standards"		 : standardsArray
		},
		success:function(data){
			setDataInComparisionDashBoard(data);
			closePageLoaderAP();
		},
		error: function(){
			closePageLoaderAP();
		}
	});
} 

function setDataInComparisionDashBoard(a_data){
	var prodId = a_data.productId;
	var prodImagePath = a_data.productImagePath;
	var prodName = a_data.productName;
	var productSpecs = a_data.productSpecificationVOs;
	var testCategory = a_data.testCategoryVOs;
	setTdInProductImageBlock(prodId, prodImagePath, prodName);
	setTDInProductSpecs(prodId, productSpecs);
	setTDInTestResults(prodId, testCategory,prodName);	
	
}

$('#addTestIconId').click(function(){	
	
	$(".testClass").each(function(){
		dashboardtestIdArray.push($(this).attr('id'));  
	});
	$(".standardCheckBox").each(function(){
		standardIds.push($($(this).attr('value')));
	});
	
	 $('#testDialogId').html("<center><div class='loading_gif'></div></center>").dialog({
		 	title:resourceBundle['ask.askContainer.js.search.available.tests'],
			width:'60%',
			resizable : false,
			height: '400',
			autoOpen:true,
			modal:true,
			draggable:true,
			close: function() {
				//$(this).dialog('destroy').html("");
			}
	}).load(contextPath+'/openTest?dashboardtestIdArray='+dashboardtestIdArray+'&comparisonChartCategoryId='+comparisonChartCategoryId); 
});

function setTdInProductImageBlock(a_prodId, a_prodImage, a_prodName){
	var td = "<th class='"+a_prodId+" draggableCol'>"
				+"<span onclick='removeClickedCol(this)'><i class='fa fa-minus'></i></span>"
				+"<center><img src='data:image/*;base64,"+a_prodImage+"' id='test"+a_prodId+"' width='150' /></center>"
				+"<center><label>"+a_prodName+"</label></center>"
			+"</th>";
	$(td).insertAfter($("#productImagesTR >th:first"));
	
	
	
	var colSpan = productIdsArray.length;
	
	if(colSpan == 1){
		var balnkTD = "<td class='productDetailsClass'><center>"+resourceBundle['ask.askContainer.js.product.details']+"</center></td>"
		$(balnkTD).insertAfter($(".productSpecs >td:first"));
	}else{
		$(".productDetailsClass").attr("colspan",colSpan);
	}
	
}

function setTDInProductSpecs(a_prodId, a_productSpecs){
	$.each(a_productSpecs,function(index, productSpec){
		var specId = productSpec.prodSpecificationId;
		var specValue = productSpec.prodSpecificationValue;
		
		var td = "<td class='"+a_prodId+"'>"
				+"<center><label>"+specValue+"</label></center>"
				+"</td>";
		$(td).insertAfter($("#"+specId+" >td:first"));		
	});
	var balnkTD = "<td class='"+a_prodId+"'></td>"
	$(balnkTD).insertAfter($(".testResults > td:first"));
}

function setTestResultsForProductsInTR(a_testId,a_testStandardId,a_testName, a_testCategory,a_testCategoryName,a_testResults){
	var testCategoryId = a_testCategory;
	
    if($("#"+testCategoryId).length == 0){
    	var tr = "<tr id='"+testCategoryId+"' class='testCategory'> "
		+"<td> <span class='collapseSpanSpec'> <label> "+a_testCategoryName+" </label>" 
		+"<label for='"+testCategoryId+"_testCat' class='arrow-toggling'><i class='fa fa-angle-down'></i></label>"
		+"<input type='checkbox' name='"+testCategoryId+"' id='"+testCategoryId+"_testCat' data-toggle='toggle-test'>"
		+" </span></td>";
    	$.each(a_testResults,function(index, testResult){
    		var productId=testResult.productId;
    		tr = tr + "<td class='"+productId+" testResult'> <center><label></label></center></td> ";
    	});
    	tr = tr + "</tr>";
		$(tr).insertAfter($(".testResults"));
    }
	var tr = "<tr id='"+a_testStandardId+"' class='"+a_testCategory+" testClass' >"
	+"<td> "  +a_testName + " <span class='remove-test-icon pull-right' onlcick='removeClickedTR(this)'> "
	+"<i class='fa fa-minus'></i></span> </td> ";
	
	$.each(a_testResults,function(index, testResult){	
		var testValue="";
		var productId=testResult.productId;
		if(testResult === "" || testResult === undefined || testResult === "undefined"){
			testValue = "<center>"+resourceBundle['ask.askContainer.js.request.data']+" <input type='checkbox' class = 'testRequestDataChkBox'  onchange='eneableTestReqProductDataBtn()'></center>"
		}else{
			testValue = testResult.testResult;			
			if(testValue === "" || testValue === undefined || testValue === "undefined"){
				testValue = "<center>"+resourceBundle['ask.askContainer.js.request.data']+" <input type='checkbox' class = 'testRequestDataChkBox' onchange='eneableTestReqProductDataBtn()' ></center>"
			}
		}
		var $table = $('#comparisionTable');
		var a_prodName = $table.find("th." + productId).text();
		
		tr = tr + "<td class='"+productId+" testResult'  name='"+a_prodName+"' > <center><label>"+ testValue + " </label></center></td> ";
		testProductResultArray.push(productId+"::@@::"+a_testStandardId);
	});
	tr = tr + " </tr>";
	$(tr).insertAfter($("#"+a_testCategory));   
	$(".ui-dialog-titlebar-close").trigger('click');
	var presentTdLength = $($("#"+a_testStandardId+" >td.testResult")).length;	
	if(presentTdLength > 1 ){
		highlightCellBasedOnTestResult(a_testId);	
	}	
}

function openProductRequestFormPopup(){
	$('#productRequestFormDialog').html("<center><div class='loading_gif'></div></center>").dialog({
		width: "50%",
        maxWidth:"50%",
		autoOpen:true,
		title : resourceBundle['ask.askContainer.js.request.product.form'],
		modal:true,
		draggable: true,
		resizable: true,
		close: function() {
			//$(this).dialog('destroy').html("");			
		}
	}).load(contextPath+'/requestProduct',{regionId:regionId});

}

function setTDInTestResults(a_prodId, a_testCategories,a_prodName){
	var testValue = "";
	var testHighlightLogic = "";
	var highestValue = "";
	var lowestValue = "";
	var presentTdLength="";
	$.each(a_testCategories,function(index, testCategory){
		var categoryId = testCategory.testCategoryId;		
		var balnkTD = "<td class='"+a_prodId+"'></td>"
		$(balnkTD).insertAfter($("#"+categoryId+" >td:first"));
		var testResults = testCategory.testResultsVOs;
		$.each(testResults, function(index, testResult){
			var testId = testResult.testId;
			testValue = testResult.testValue;
			testHighlightLogic = testResult.testHighlightLogic;		
			if(testValue === ""){
				testValue = "<center>"+resourceBundle['ask.askContainer.js.request.data']+" <input type='checkbox' class = 'testRequestDataChkBox' id='testid' onchange='eneableTestReqProductDataBtn()'></center>"
			}
			var td = "<td class='"+a_prodId+" testResult' name='"+a_prodName+"'>"
			+"<center><label>"+testValue+"</label></center>"
			+"</td>";		
			
			$(td).insertAfter($("#"+testId+" >td:first"));
			
			testProductResultArray.push(a_prodId+"::@@::"+testId);
			presentTdLength = $($("#"+testId+" >td.testResult")).length;	
			if(presentTdLength > 1 ){
				highlightCellBasedOnTestResult(testId,testHighlightLogic);	
			}
		});
	});
}

function highlightCellBasedOnTestResult(a_testStandardId,a_testHighlightLogic){
	var testValues=[];
    var maxValue = "";
    var minvalue = "";
	$($("#"+a_testStandardId+" >td.testResult")).each(function(){
		testValues.push($(this).text());
		$(this).removeAttr('bgcolor');
	});	
	maxValue = Math.max.apply(null, testValues); 
	minvalue = Math.min.apply(null, testValues);	
	$($("#"+a_testStandardId+" >td.testResult")).each(function(){
		if(a_testHighlightLogic == "HG"){
			if($(this).text() == maxValue){			
				$(this).attr('bgcolor','#60f360');
			}
			
			if($(this).text() == minvalue){			
				$(this).attr('bgcolor','#f64e4e');
			}
		}
		else{
			if($(this).text() == maxValue){			
				$(this).attr('bgcolor','#f64e4e');
			}
			
			if($(this).text() == minvalue){			
				$(this).attr('bgcolor','#60f360');
			}
		}
		
	});
	
}

function eneableTestReqProductDataBtn(){
	var buttonCaption = "";
	$("#requestForTestsForProductsId").prop('disabled', false);	
	totalTestDataReqForProductCnt = $('input[class=testRequestDataChkBox]:checked').length;
	if(totalTestDataReqForProductCnt > 0){
		buttonCaption = totalTestDataReqForProductCnt +' ' +selectedTest;		
	}
	else{
		$("#requestForTestsForProductsId").prop('disabled', true);	
		buttonCaption = "Request TestData";
	}
	
	$("#requestForTestsForProductsId").val(buttonCaption); 	
}
function exceptFirstCollapseAllTrs(){
	for(var index=1; index<$(".testCategory").length; index++){
		var tr = $(".testCategory")[index];
		var testCategoryId = $(tr).attr("id");
		
		var i = $("#"+testCategoryId).find('label.arrow-toggling>i');
		i.removeClass('fa-angle-down').addClass('fa-angle-up');
		$("."+testCategoryId).toggle();
	}
}

function removeClickedTR(a_obj){
	var testCategoryId = $(a_obj).parent().parent().attr('class');
	$(a_obj).parent().parent().remove();
	var length = $("."+testCategoryId).length;
	
	if(length == 0){
		$("#"+testCategoryId).remove();
	}	
}

function removeClickedTestStandard(a_obj){
	var parentULId = $(a_obj).parent().parent().parent().attr('id');	
	$(a_obj).parent().parent().remove();
	var length = $("."+parentULId).length;	
	if(length == 0){
		$("#"+parentULId).remove();
	}
}

function removeClickedCol(a_obj){
	var productId = $(a_obj).closest("th").attr('class').replace(" accept","");
	var indexOfProd = productIdsArray.indexOf(productId);
	productIdsArray.splice(indexOfProd,1);
	var myIndex = $(a_obj).closest("th").prevAll("th").length;
    $(a_obj).parents("table").find("tr").each(function(){
      $(this).find("td:eq("+myIndex+"), th:eq("+myIndex+")").fadeOut('slow', function() {
      	if(productIdsArray.length != 0){
      		if($(this).hasClass("productDetailsClass") == false){
          		$(this).remove();
          	}else{
          		$(this).show();
          	}
      	}else{
      		$(this).remove();
      	}
      });
    });
    
    var colSpan = productIdsArray.length;
	
	$(".productDetailsClass").attr("colspan",colSpan);
    
    if(productIdsArray.length == 0){
    	productSpecsArray = new Array();
    	testResultsArray = new Array();
    	$(".leftDiv").html("");
    }
}
function initializeAutocompleteForAllFileds(list,fieldId,hiddenId,autoCompleteFlag){	
	$("#"+fieldId).richAutocomplete({
		items:list,
		extractText: function(item) {
	    },
	    filter: function(items, searchTerm) {
	    	if(enableNotFilter === true && counter === 0){
	    		searchTerm = "";
	    		counter += 1;
	    	}
	        return items.filter(function(item) {
	            return item.label.toLowerCase().indexOf(searchTerm.toLowerCase()) !== -1;
	        });
	    },
	    emptyRender: function() {
	    		return '<p>'+resourceBundle['ask.askContainer.js.no.match.found']+'</p>';
	    },
	    extractText: function(item) {
	        return item.label;
	    },
	    render: function(item) {
	    	if(autoCompleteFlag == 1){
	    		return "<div style='float:left; vertical-align:middle; padding-right:5px;'><img src='"+contextPath+"/img/region_s_"+item.id+".png' class='negmart3'/></div>"  +  "   " + "<div style='float:left;'>" +item.label+ "</div>"  + "<div class='clearfix'></div>";
    		}
    		return "<div style='float:left; vertical-align:middle; padding-right:5px;'></div>"  +  "   " + "<div style='float:left;'>" +item.label+ "</div>"  + "<div class='clearfix'></div>";
	    },
	    select: function(item) {
	    	if(autoCompleteFlag == 3){
	    		var productManufacturerValue=$("#productManufacturerId").val();
	    		if(productManufacturerValue != "Other"){
	    			$("#othProdManufacturer").attr('disabled',true);
	    		}else{
		    		$("#othProdManufacturer").attr('disabled',false);
		    	}
	    	}
	    	if(autoCompleteFlag == 4){
	    		var productBrandValue=$("#productBrandId").val();
	    		if(productBrandValue != "OTHER"){
	    			$("#othProdBrand").attr('disabled',true);
	    		}else{
		    		$("#othProdBrand").attr('disabled',false);
		    	}
	    	}
	    	autoCompleteSelect(fieldId,hiddenId,item);	    	    	
	    }
	});
}

function autoCompleteSelect(fieldId,hiddenId,item){
	if(item != null){
			$('#'+fieldId).val(item.label);
			$("#"+hiddenId).val(item.id);
			$("#"+hiddenId+"Name").val(item.label);
			$('#'+fieldId).blur();
        }
}

function newProductRequest(){	
	//Validate form
		if($("#isCheckedCopyOfResponse").attr('checked')){
				$("#copyOfResponse").val(1); 
		}else{
				$("#copyOfResponse").val(0);
		}	
		//var isValidForm = $("#askOtherProductForm").validationEngine('validate');
		//if(isValidForm){
			//to do..			
		//}		
		checkRequestedProductAlreadyExists();
		
		var othProdBrand=$("#othProdBrand").val();		
		if($("#productBrandId").val().toLocaleLowerCase() == "other" && othProdBrand != undefined && othProdBrand.trim() ==""){
				openInformationWithOk('label.pleaseEnterOtherBrandName');		
				return false;
		}		   
		var othProdManufacturer=$("#othProdManufacturer").val();	
		if($("#productManufacturerId").val().toLocaleLowerCase() == "other" && othProdManufacturer != undefined && othProdManufacturer.trim() ==""){
				openInformationWithOk('label.pleaseEnterOtherManufacturerName');
				return false;
		}		
		
		var otherProductName=$("#otherProductName").val();
		if(otherProductName != undefined && otherProductName.trim() ==""){
			openInformationWithOk('label.pleaseEnterOtherProductName');
				return false;
		}
		
		var resultsNeededByDate = $("#testResultsNeededByDate").val();
		if(resultsNeededByDate != undefined && resultsNeededByDate.trim() ==""){
			openInformationWithOk('label.pleaseSelectDateForResults');
				return false;
		}
		
	//Submit form		
		$.ajax({
			type : 'POST',
			url : contextPath+"/submitNewProductRequest",
			data : {				
				regionScopeSelected: $("#selectedHiddenRegionId").val(),
				productCategoryId:	$("#selectedHiddenProductCategoryId").val(),			
				productManufacturerId:$("#selectedHiddenProductManufacturerId").val(),
				othProdManufacturer:$("#othProdManufacturer").val(),
				productBrandId:$("#selectedHiddenProductBrandId").val(),	
				othProdBrand:$("#othProdBrand").val(),					
				otherProductName:$("#otherProductName").val(),				
				otherProductStyleName:$("#otherProductStyleName").val(),				
				
				isBenchmarktestingRequired:$('input[name=isBenchmarktestingRequired]:checked').val(),				
				specificCustomerName:$("#specificCustomerName").val(),
				urgencyLevel:$("#hiddenurgencyLevelId").val(),
				supportsProcurement:$('input[name=supportsProcurement]:checked').val(),
				testResultsNeededByDate:$("#testResultsNeededByDate").val(),
				testToPerform:$("#testToPerform").val(),
				additionalInfo:$("#additionalInfo").val(),
				copyOfResponse:$("#copyOfResponse").val()				
			},
			success : function(data) {				
				$(".ui-dialog-titlebar-close").trigger('click');				
				callSuccessWithMessage(resourceBundle['ask.askContainer.js.informationsavesucessfully.requestId']+data);							
			},
			error : function(data) {				
				$(".ui-dialog-titlebar-close").trigger('click');				
				callSuccessWithMessage(data);							
			}
		});
}

function checkRequestedProductAlreadyExists(){	
	$.ajax({
			type : 'GET',
			url  : contextPath+"/checkRequestedProductAlreadyExists",
			data : {				
						productName: $("#otherProductName").val(),
						regionId:	$("#selectedHiddenRegionId").val(),
						prodManufacturer:$("#othProdManufacturer").val(),
						prodBrand:$("#othProdBrand").val()
			},
			success : function(data) {
					console.log('Success : '+data);
					if(undefined !== data && data !==''){
						//console.log('This product is already requested, Request Id is -'+data);						
						//alert('This product is already requested, Request Id is - '+data);
						openInformationWithOk('label.productAlreadyRequested','<strong>'+data+'</strong>')
						$("#otherProductName").val('');
						return false;
					}						
			},
			error : function(data) {
				console.log('Error : '+data);
			}
	});
}

function initializeTests(a_testValuesArray){	
	$("#testId").richAutocomplete(
		{		
		items:a_testValuesArray ,
		extractText: function(item) {
	    },
	    filter: function(items, searchTerm) {	    	 
	    	if(enableNotFilter === true && counter === 0){	    		
				if(searchFlag){
					searchTerm = "";
					searchFlag=false;	    				
				}	    			
	    	}	        
	    	return items.filter(function(item) {	        
	        	return item.testName.toLowerCase().indexOf(searchTerm.toLowerCase()) !== -1;	        			        		            
	        });
	    },
	    emptyRender: function() {
	    	return '<p style="font-size:12px; margin:0px;"><span class="glyphicon glyphicon-warning-sign" style="padding-right:5px;"></span>'+resourceBundle['ask.askContainer.js.no.match.found']+'</p>';
	    },
	    extractText: function(item) {
	        return item.testName;
	    },
	    render: function(item) {	    	
	    		return '<p><div width:2px;line-height:28px class="highlightSelectedDistributor">' + item.testName + '</p>';
	    },
	    select: function(item) {
	    	var testId = $("#hiddenTestId").val();
	    	searchFlag=true;
	    	if(testId != item.testId){	    		
	    		$("#hiddenTestId").val(item.testId);
	    		$("#hiddenTestName").val(item.testName);
	    		isDirty = true;
	    		$("#hiddenDirtyFlag").val(true);
	    		$('#testId').blur();
	    	}
    	}
	});
}

function getTestResultsForSelectedProducts(rowId){
	var testId = $('#testSearchgrid').jqGrid("getCell", rowId, 'testId');
	var testStandardId = $('#testSearchgrid').jqGrid("getCell", rowId, 'testStandardId');
	var testCategoryName = $('#testSearchgrid').jqGrid("getCell",rowId,'testCategoryName');
	var standardId = $('#testSearchgrid').jqGrid("getCell",rowId,'standardId');
	if($("#comparisionTable").has("#"+testStandardId).length > 0){
         alert("The test you are trying to add is already present");
    }else{
		var testName = $('#testSearchgrid').jqGrid("getCell", rowId, 'testName');
		var testCategoryId = $('#testSearchgrid').jqGrid("getCell", rowId, 'testCategoryId');	
		$.ajax({
			url: contextPath+"/getTestResultsForSelectedProducts",
			data:{
				"productIds":productIdsArray,		
				"testId" : testId,
				"testStandardId" : testStandardId
			},
			success:function(data){				
				//Check standardId CheckBox
				$(".standardCheckBox").each(function(){
					if(standardId == $(this).val()){
						$(this).prop('checked',true);
					}
				});
				testResultsArray.push(testStandardId+'::@::'+testCategoryId);				
				setTestResultsForProductsInComparisionDashBoard(testId,testName,testCategoryId,testCategoryName,testStandardId,data);			
			}
		});
    }
}

function setTestResultsForProductsInComparisionDashBoard(testId,a_testName,a_testCategoryId,a_testCategoryName,a_testStandardId,a_data){
	var testName = a_testName;
	var testCategoryId = a_testCategoryId;
	var testResults = a_data;
	var testCategoryName = a_testCategoryName;
	setTestResultsForProductsInTR(testId,a_testStandardId,testName, testCategoryId,testCategoryName,testResults);	
	
}

function closeDialog(id){
	$(".ui-dialog-titlebar-close").trigger('click');
	$('#'+id).dialog('close');
}

$("#requestForTestsForProductsId").click(function(){
	 
	 $('#requestTestsForProductDialogId').html("<center><div class='loading_gif'></div></center>");	 
	 $('#requestTestsForProductDialogId').dialog({
			width: "50%",
	        maxWidth:"50%",
			autoOpen:true,
			title : resourceBundle['ask.askContainer.js.request.test.data.product'],
			modal:true,
			draggable: true,
			resizable: true,
			close: function() {
				//$(this).dialog('destroy').html("");			
			}
	}).load(contextPath+'requestTestsForProduct'); 
});

function populateTestStandardProductRequestMap(){debugger;
	//var requestParam = new Object();
	var prodNameArray = [];
	var testIdList1Array = [];
	$("#productTestsDiv li").each(function(){
		var productName = $(this).attr('class');		
		prodNameArray.indexOf(productName) === -1 ? prodNameArray.push(productName) : "";
	});
	$.each(prodNameArray,function(index,productNm){
		testIdList1Array = [];
		$("#productTestsDiv li").each(function(){
			var productName = $(this).attr('class');
			var standardId = $(this).attr('id');	
			if(productNm == productName){
				testIdList1Array.push(standardId)
			}
		});
		testProductIdMap[productNm] =  testIdList1Array;
	});
}

function submitRequestForTestDataForProducts(){
	if($("#isCheckedCopyOfResponse").attr('checked')){
		$("#copyOfResponse").val(1); 
	}else{
		$("#copyOfResponse").val(0);
	}	
	/*var isValidForm = $("#testRequestProductForm").validationEngine('validate');
	if(isValidForm){
		//to do..			
	}	*/	
	populateTestStandardProductRequestMap();
	var map = new Object();	
	var requestParam = new Object();
	
	var testIdList1Array= ['9d284636-3fd3-11e9-8484-f48e38ab8d4e','9d2bda5f-3fd3-11e9-8484-f48e38ab8d4e'];
	var testIdList2Array = ['9d2bdc34-3fd3-11e9-8484-f48e38ab8d4e','9d2eb953-3fd3-11e9-8484-f48e38ab8d4e'];	
	
	map['074ad436-e7b8-48f9-9007-f312e0724f19'] =  testIdList1Array;
	map['076c3779-d4db-4e7b-9933-92f16237fe1c'] =  testIdList2Array;
		
	requestParam['isBenchmarktestingRequired']= $('input[name=isBenchmarktestingRequired]:checked').val(),
	requestParam['urgencyLevel']= $("#specificCustomerName").val(),
	requestParam['specificCustomerName']='Deepak';//$("#specificCustomerName").val(),
	requestParam['supportsProcurement']=$("#hiddenurgencyLevelId").val(),
	requestParam['testResultsNeededByDate']=$("#testResultsNeededByDate").val(),	
	requestParam['additionalInfo']=$("#additionalInfo").val(),
	requestParam['copyOfResponse']=$("#copyOfResponse").val()	
	requestParam['productRequestedTestMap']=testProductIdMap;
		
//Submit form		
$.ajax({
	type : "POST",
	url : contextPath+"/submitTestRequestsForProduct",
	contentType : "application/json",
	dataType : 'json',	
	data : JSON.stringify(requestParam),	
	success : function(data1) {
		alert("Other product saved Successfully.."+data1);
		$(".ui-dialog-titlebar-close").trigger('click');			
	},
	error : function(data) {
		console.log('Error : '+data);
	}
});
}
function populateProductTestDiv(){debugger;
	var prodNameArray = [];
	var newdiv =  "";	
	var productNm = "";
	var prodNameTestStandardNmArray = [];
	var divClass = "";
	$(".testRequestDataChkBox").each(function(){
		if($(this).prop('checked'))	{
			productNm = "";
			productNm = $(this).closest("td").attr('name');
			prodNameArray.indexOf(productNm) === -1 ? prodNameArray.push(productNm) : "";			
		}
	});
	$(".testRequestDataChkBox").each(function(){			
		if($(this).prop('checked')){
			var testStandardNm = "";
			var testStandardId = "";
			productNm = $(this).closest("td").attr('name');
			testStandardNm = $(this).closest("tr").find("td:first").text();
			testStandardId = $(this).closest("tr").attr('id');
			prodNameTestStandardNmArray.push(productNm +"@@"+testStandardNm+"@@"+testStandardId);			
		}
	});
	$.each(prodNameArray,function(index,prodNm){
		newdiv =  "";		
		switch(prodNameArray.length){
		case 1 :
			divClass = "col-md-12 request-product-details";
			break;
		case 2 :
			divClass = "col-md-6 request-product-details";
			break;
		case 3 :
			divClass = "col-md-4 request-product-details";
			break;
		case 4 :
			divClass = "col-md-3 request-product-details";
			break;
		}		
		newdiv = '<div class="'+divClass+'" id='+prodNm+'><h5>'+prodNm+'</h5><ul id="'+prodNm+'">' ;		
		$.each(prodNameTestStandardNmArray,function(index,productStandardNm){			
			var productName = productStandardNm.split("@@");			
			if(prodNm == productName[0]){
				var testStandardNm = "";
				var testStandardId = "";			
				testStandardNm = productName[1];
				testStandardId = productName[2]
				newdiv = newdiv + '<li class="'+prodNm+'" id='+testStandardId+'><a href = "#"><i class="fa fa-minus" onclick="removeClickedTestStandard(this)"></i></a>'+testStandardNm+'</li>' ;			
			}
		});
		newdiv = newdiv + '</ul>' ;
		$('#productTestsDiv ').prepend($(newdiv));
	});
	
}

function clearComparisionDashBoard(){
	openConfirmationWithYesNo("label.clearComparisionDashBoard",'clearComparisionDashBoardConfirmationYes()','clearComparisionDashBoardConfirmationNo()');	
}

function clearComparisionDashBoardConfirmationYes(){	
	productIdsArray=[];
	productSpecsArray = new Array();
	testResultsArray = new Array();
	$("#comparisionTable").remove();
	okclose();
}

function clearComparisionDashBoardConfirmationNo(){
	okclose();
}

function callSaveSuccess() {
	$('#notificationSave').fadeIn('slow');
	$('#notificationSave').fadeOut(1500);
}

function callSuccessWithMessage(message) {	
	var notificationDiv = $('#customMessage');	
	notificationDiv.html(message).fadeIn('slow').fadeOut(7000);	
}

function sortLabTests(){
	var categoryTRs = $('.testCategory');
	var n = categoryTRs.length;
	var flag = $('.testResults').parents('tbody').attr('class');
	
	if(flag == 'asc'){
		$('.testResults').parents('tbody').attr('class' , 'desc');
		$('.sortingSpan').html("");
		$('.sortingSpan').html("<i class='fa fa-sort-desc'></i>");
	}else if(flag == 'desc'){
		$('.testResults').parents('tbody').attr('class' , 'asc');
		$('.sortingSpan').html("");
		$('.sortingSpan').html("<i class='fa fa-sort-asc'></i>");
	}
	
	
	/*Sorting First Category*/
	for(var i=0; i< n-1; i++){
		for(var j=0; j<n-i-1; j++){
			var firstTR = categoryTRs[j];
			var secondTR = categoryTRs[j+1];
			
			flag = $('.testResults').parents('tbody').attr('class');
			
			var firstTrId = firstTR.id;
			var secondTrId = secondTR.id;
			if(flag == 'desc'){
				if(firstTR.textContent.trim().toLocaleLowerCase() < secondTR.textContent.trim().toLocaleLowerCase()){
					
					$("#"+secondTrId).insertBefore('#'+firstTrId);
					var tempTestId;
					$.each($('.'+secondTrId),function(index , trElement){
						if(!tempTestId){
							$(trElement).insertAfter($('#'+secondTrId));
						}else{
							$(trElement).insertAfter($('#'+tempTestId));
						}
						tempTestId = trElement.id;
					});
				}
			}else if(flag == 'asc'){
				if(firstTR.textContent.trim().toLocaleLowerCase() > secondTR.textContent.trim().toLocaleLowerCase()){
					$("#"+secondTrId).insertBefore('#'+firstTrId);
					
					var tempTestId;
					$.each($('.'+secondTrId),function(index , trElement){
						if(!tempTestId){
							$(trElement).insertAfter($('#'+secondTrId));
						}else{
							$(trElement).insertAfter($('#'+tempTestId));
						}
						tempTestId = trElement.id;
					});
				}
			}
		}
	}
	/*Sorting First Category*/
	
	/*Sorting Tests*/
	for(var i=0; i<n; i++){
		var testCategoryElement = categoryTRs[i];
		var testCatgeoryId = testCategoryElement.id;
		
		var testsUnderCategory = $('.'+testCatgeoryId);
		var lengthOfTests = testsUnderCategory.length;
		
		for(var j=0; j< lengthOfTests-1; j++){
			for(var k=0; k<lengthOfTests-j-1; k++){
				var firstTR = testsUnderCategory[k];
				var secondTR = testsUnderCategory[k+1];
				
				flag = $('.testResults').parents('tbody').attr('class');
				var firstTrId = firstTR.id;
				var secondTrId = secondTR.id;
				
				if(flag == 'desc'){
					if(firstTR.textContent.trim().toLocaleLowerCase() < secondTR.textContent.trim().toLocaleLowerCase()){
						$("#"+secondTrId).insertBefore('#'+firstTrId);
					}
				}else if(flag == 'asc'){
					if(firstTR.textContent.trim().toLocaleLowerCase() > secondTR.textContent.trim().toLocaleLowerCase()){
						$("#"+secondTrId).insertBefore('#'+firstTrId);
					}
				}
			}
		}
	}
	/*Sorting Tests*/
}

/*
/*function enableDraggableColumns(){
      $('#comparisionTable').sorttable({
    	  	items:'.draggableCol',
    	  	placeholder: 'placeholder',
    	    helperCells: null,
            start: function (e, ui) { 
            },
            stop: function (e, ui) { 
            }
      }).disableSelection();
      
}*/

function getTestsBasedOnStandard(a_obj){
	openPageLoaderAP();
	var standardId = a_obj.value;
	var checked = a_obj.checked;
	
	$.ajax({
		url: contextPath+"/getTestsBasedOnStandardId",
		type: "GET",
		data: {
			"standardId" : standardId,
			"productIds" : productIdsArray
		},
		success: function(dataList){
			addRemoveTestsInLabTests(dataList, checked);
			closePageLoaderAP();
		},
		error: function(){
			closePageLoaderAP();
		}
	});
}

function addRemoveTestsInLabTests(a_dataList, a_checked){
	if(a_checked){
		$.each(a_dataList, function(index, data){
			var prodId = data.productId;
			var testCategories = data.testCategoryVOs;
			
			$.each(testCategories, function(index, testCategory){
				var testCategoryId = testCategory.testCategoryId;
				var testResults = testCategory.testResultsVOs;
				var testCategroyName = testCategory.testCategroyName;
				
				if($("#"+testCategoryId).length == 0){
					var trForCategory = "<tr id='"+testCategoryId+"' class='testCategory'> "+
											"<td>" +
												"<span class='collapseSpanSpec'> " +
													"<label> "+testCategroyName+"</label> " +
													"<label for='"+testCategoryId+"_testCat' class='arrow-toggling'><i class='fa fa-angle-down'></i></label> " +
													"<input type='checkbox' name='"+testCategoryId+"' id='"+testCategoryId+"_testCat' data-toggle='toggle-test'> " +
												"</span> "+
											"</td> "+
										"</tr>";
					
					if($(".testCategories >tr:first").length == 0){
						$(".testCategories").append(trForCategory);
					}else{
						$(trForCategory).insertAfter($(".testCategories >tr:first"));
					}
					$.each(a_dataList, function(index, data){
						var prodId = data.productId;
						var blankTD = "<td class='"+prodId+"'></td>";
						$(blankTD).insertAfter($("#"+testCategoryId+" >td:first"));
					});
				}
				$.each(testResults, function(index, test){
					var testId = test.testId;
					if($("#"+testId).length == 0){
						var testValue = test.testValue;
						if(testValue === ""){
							testValue = "<center>"+resourceBundle['ask.askContainer.js.request.data']+" <input type='checkbox' class = 'testRequestDataChkBox' id='testid' onchange='eneableTestReqProductDataBtn()'></center>"
						}
						var td = "<td class='"+prodId+"'>"
						+"<center><label>"+testValue+"</label></center>"
						+"</td>";
						var trElement = "<tr id='"+testId+"' class='"+testCategoryId+"'> <td>"+test.testName+" <span class='remove-test-icon pull-right' onclick='removeClickedTR(this)'><i class='fa fa-minus'></i></span></td>"+td+"</tr>";
						if(testProductResultArray.indexOf(prodId+"::@@::"+testId) == -1){
							$(trElement).insertAfter("#"+testCategoryId);
							testProductResultArray.push(prodId+"::@@::"+testId);
							testResultsArray.push(testId+'::@::'+testCategoryId);
						}
					}else{
						var testValue = test.testValue;
						if(testValue === ""){
							testValue = "<center>"+resourceBundle['ask.askContainer.js.request.data']+" <input type='checkbox' class = 'testRequestDataChkBox' id='testid' onchange='eneableTestReqProductDataBtn()'></center>"
						}
						var td = "<td class='"+prodId+"'>"
						+"<center><label>"+testValue+"</label></center>"
						+"</td>";
						
						if(testProductResultArray.indexOf(prodId+"::@@::"+testId) == -1){
							$(td).insertAfter($('#'+testId).find('td:last'));
							testProductResultArray.push(prodId+"::@@::"+testId);
						}
					}
				});
			});
		});
	}else{
		$.each(a_dataList, function(index, data){
			var prodId = data.productId;
			var testCategories = data.testCategoryVOs;
			
			$.each(testCategories, function(index, testCategory){
				var testCategoryId = testCategory.testCategoryId;
				var testResults = testCategory.testResultsVOs;
				
				$.each(testResults, function(index, test){
					var testId = test.testId;
					
					var indexOfTest = testResultsArray.indexOf(testId+'::@::'+testCategoryId);
					testResultsArray.splice(indexOfTest, 1);
					
					
					var indexOfProdTest = testProductResultArray.indexOf(prodId+'::@@::'+testId);
					testProductResultArray.splice(indexOfProdTest, 1);
					$("#"+testId).remove();
					
					if($('.'+testCategoryId).length == 0){
						$('#'+testCategoryId).remove();
					}
				});	
			});
		});
	}
}


