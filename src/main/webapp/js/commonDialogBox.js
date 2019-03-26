function openInformationWithOk(messageKey,specificData){	
	$("#confirmationDialog").html('<div class="loading_gif"></div>').dialog({
		title : 'Information',
		autoOpen:true,
		width : '400',
		height : '130',
		modal:true,
		resizable:false
	}).load(contextPath+'/loaddialogbox', {ok : 'okclose();', messageKey : messageKey, specificData : specificData});
}


function openConfirmationWithYesNo(messageKey,yes,no,specificData){	
	$("#confirmationDialog").html('<div class="loading_gif"></div>').dialog({
		title : 'Confirmation',
		autoOpen:true,
		width : '400',
		height : '130',
		modal:true,
		resizable:false
	}).load(contextPath+'/loaddialogbox', {yes : yes,no:no , messageKey : messageKey, specificData : specificData});
}

function okclose() {	
	var dialogDiv = jQuery("#confirmationDialog");
	dialogDiv.dialog('close');
}

function ok() {
	var dialogDiv = jQuery("#confirmationDialog");
	dialogDiv.dialog('close');
}


function openPageLoaderAP(){
	$("#pageLoaderAP").dialog({
		autoOpen:true,
		closeOnEscape:false,
		draggable:false,
		modal:true,
		resizable: false,
		dialogClass:"loaderTransparent",
		open:function(){
			$("#pageLoaderAP").parent().css("top","35%");
			$(".ui-dialog-titlebar").hide();
		},
		close : function(event) {
			$("#pageLoaderAP").dialog("destroy");
		}
	});
}

function closePageLoaderAP(){
	$("#pageLoaderAP").dialog("close");
}

/************************************************************ Jquery Dialog Extends *******************************************************************/

/*$.extend($.ui.dialog.prototype.options,{ 
	hide : {
		effect: "fade",
        duration: 300,
	},
	dialogClass:'custom_popup bounceInDown animated',
});*/

$.extend($.ui.dialog.prototype.options, {
    dialogClass:'custom_popup bounceInDown animated',
    hide : {
		effect: "fade",
        duration: 300,
	},
});

$.extend(jQuery.ui.dialog.prototype.options, {
	 position: {
			my: "top",
			at: "center",
			of: window,
			collision: "none"
			},
			open: function(event, ui){
				event.preventDefault(); 
			},
			 create: function (event, ui) {
				$(event.target).parent().css('position', 'fixed');
			}
}); 

/************************************************************ Jquery Dialog Extends *******************************************************************/