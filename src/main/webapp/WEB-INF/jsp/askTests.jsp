<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="rnd" tagdir="/WEB-INF/tags"%>

<script type="text/javascript" src="${contextPath}/js/jquery/jquery-1.11.1.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery/css_browser_selector.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery/jquery-ui.js"></script> 

<script	type="text/javascript" src="${contextPath}/js/plugins/jqgrid/grid.locale-en.js" type="text/javascript"></script>
<script	type="text/javascript" src="${contextPath}/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>


<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap/bootstrap-grid.css">

<script>

var gridcolumnname = [ 
                       'TestCategory',
                       'TestName',
                       '',
                       '',
                       '',
                       '']

</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Tests</title>
</head>
<body>

<div class="container-fluid">
	<div id="search"><b></b></div>
		<div class="marb10">
			<div class="ansellGridContainer">
				<rnd:CustomFormGrid 
					gridId="testSearch" loadUrl="${contextPath}/loadGridData"   hidePagerInput="false"	
						sortOrder="desc" sortIndex="testCategoryName"						
						colModel="[		
								   {name:'testCategoryName',index:'testCategoryName',width:'40%'},						   
								   {name:'testName',index:'testName',width:'60%'},
					 	           {name:'testCategoryId',index:'testCategoryId',hidden:'true'},
					 	           {name:'testId',index:'testId',key:true,hidden:'true'},
					 	           {name:'testStandardId',index:'testStandardId',key:true,hidden:'true'},
					 	           {name:'standardId',index:'standardId',key:true,hidden:'true'}]"
						colNames="gridcolumnname"  customParam="gridId:'testSearch',cr_regionId:'int_${regionId}',
																cr_productCategoryId:'int_${productCategory}'"
						additonalGridParams=",
						onSelectRow:function(rowId){
						getTestResultsForSelectedProducts(rowId);
						},loadComplete: function(data) {		
						}," 
						filterToolbar="true" 						
						fn="" >
	  			 </rnd:CustomFormGrid>	   			
			</div>						
		</div>	
	</div>