$( document ).ready(function() {
    $('button.ui-datepicker-current').live('click', function() {
        $.datepicker._curInst.input.datepicker('setDate', new Date()).datepicker('hide');
    });
    
    $('.ui-datepicker-prev').live('click', function() {
    	var isDisabled = $('.ui-datepicker-prev').hasClass('ui-state-disabled');
    	/*if(!isDisabled){*/
    		findButtonsInDatePicker(this);
    	/*}*/
    });
    
    $('.ui-datepicker-next').live('click', function() {
    	var isDisabled = $('.ui-datepicker-next').hasClass('ui-state-disabled');
    	/*if(!isDisabled){*/
    		findButtonsInDatePicker(this);
    	/*}*/
    });
});

function formDatePicker(datePickerId,dateFormat){	
	var dates = $("#"+datePickerId).datepicker({		
		buttonText : '<span style="background:none; border:0px;" class="glyphicon glyphicon-calendar ansellgray"></span>',		
		dateFormat : dateFormat,
		changeMonth : true,
		changeYear : true,
		showOn: "both",
		showButtonPanel: true,
        closeText: 'Clear',
        currentText: 'Today',
        minDate:0,
        onClose: function (dateText, obj) {
            if ($(window.event.srcElement).hasClass('ui-datepicker-close')){
                $("#"+obj.id).val('');
            }
            
            if ($(window.event.srcElement).hasClass('fa-times')){
                $("#"+obj.id).val('');
            }
        },
        beforeShow : function(obj){
        	findButtonsInDatePicker(obj);
        },
		onSelect : function(selectedDate) {
			var option = (this.id.indexOf("from") != -1 ) ? "minDate"
					: "maxDate", instance = $(this).data(
					"datepicker"), date = $.datepicker
					.parseDate(
							instance.settings.dateFormat
							|| $.datepicker._defaults.dateFormat,
							selectedDate, instance.settings);
			dates.not(this).datepicker("option", option, date);
			//$("#"+datePickerId).datepicker( "option", "maxDate", new Date());
			$("#"+datePickerId).next().css("border", "0px");
			$("#"+datePickerId).next().css("background", "none");
			$("#"+datePickerId).datepicker({maxDate: '0'});
		}
	});
	//$("#"+datePickerId).datepicker( "option", "maxDate", new Date());
	$("#"+datePickerId).next().css("border", "0px");
	$("#"+datePickerId).next().css("background", "none");
}

function findButtonsInDatePicker(input){
	 setTimeout(function () {
	        var buttonPane = $('#ui-datepicker-div').find(".ui-datepicker-buttonpane");
	        var buttons = buttonPane.find('button').removeClass("ui-state-default ui-priority-secondary ui-priority-primary ui-corner-all ui-state-hover");
	        if(buttonPane.find('button .fa-times').length > 0){
	        	buttonPane.find('button .fa-times').remove();
	        }
	        
	        if(buttonPane.find('button .fa-calendar-check-o').length > 0){
	        	buttonPane.find('button .fa-calendar-check-o').remove();
	        }
	        buttonPane.find('button').last().prepend("<i class='fa fa-times namecls'></i>");
	        buttonPane.find('button').first().prepend("<i class='fa fa-calendar-check-o namecls'></i>");
	        buttonPane.find('button').addClass("btn btn-md btn-default btnBlueB btnmargin btnspacing");
	        var onChangeFunctionForMonth = $('.ui-datepicker-month').attr("onchange");
	        var onChangeFunctionForYear = $('.ui-datepicker-year').attr("onchange");
	        
	        if(onChangeFunctionForMonth){
	        	onChangeFunctionForMonth += " findButtonsInDatePicker(this);";
	        	onChangeFunctionForYear += " findButtonsInDatePicker(this);";
	        }else{
	        	onChangeFunctionForMonth = " findButtonsInDatePicker(this);";
	        	onChangeFunctionForYear = " findButtonsInDatePicker(this);";
	        }
	        $('.ui-datepicker-month').attr("onchange",onChangeFunctionForMonth);
	        $('.ui-datepicker-year').attr("onchange",onChangeFunctionForYear);
	    }, 1)
}