$(document).ready(function() {
	jQuery.ajaxSetup({
		async : false
	});
	
	 
	setTimeout(function(){
		
		firstTimeLoad();
		
		}, 0000);
	    
	$('#checkDatatable').dataTable({
		"paging" : false,
		"searching" : false,
		"info" : false,
		"ordering" : false,
		"fixedHeader": true,
	});
	
	
	$.fn.dataTable.ext.errMode = 'none';
	
	
	
});





function getEditMode() {

	var USER_ID = $("#USER_ID").val();

	$.get("../getuserteam", {
		USER_ID : USER_ID
	}, function(data) {

		if (data.length == 0 ||data.length == undefined) {
			
			clearDataTable();
			return;
		}	
			
			
			$.each(data, function(index, value) {
				// Get the total rows		
				checkDropDown(value.sub_TEAM_CODE,value.active_DT,value.deactive_DT);

			});
					

	});
}

function saveUserTeam () {

	var table = $('#checkDatatable').dataTable();

	
	// Get the total rows
	var inserteditdeletepost = [];
	for (i = 1; i <= table.fnGetData().length; i++) {
		var myobj = new Object();		
		if ($('#USER_ID').val() != null) {			
//			console.log($('.list' + i).val());
			
			if($('.list' + i).val()=='Y'){

			myobj.USER_ID = $("#USER_ID").val();
			myobj.SUB_TEAM_CODE = $('.mastersetup' + i).val();
			myobj.ACTIVE_DT = $('#ACTIVE_DT' + i).val();
			myobj.DEACTIVE_DT = $('#DEACTIVE_DT' + i).val();
		
			myobj.LIST_FLAG = $('.list' + i).val();
			
			inserteditdeletepost.push(myobj);
		
			}
		}
	}

//		console.log(JSON.stringify(inserteditdeletepost));

	
	$.post("../saveUserTeam", {
		USER_ID : $("#USER_ID").val(),
		menu_mode : JSON.stringify(inserteditdeletepost)
	}, function(data) {
		alert(data);
		// getAllMenu();

	});

}
function clearDataTable() {
	var table = $('#checkDatatable').dataTable();
	
	var currentdate='';
	$.get('../currentNepaliDate', {getRegionList: "getlist"}, function (response) {
	    
		currentdate= response;
		
	});
	
	
	for (i = 1; i <= table.fnGetData().length; i++) {

		if ($('#USER_ID').val() != null) {

			$('#ACTIVE_DT' + i).val(currentdate);
			$('#DEACTIVE_DT' + i).val(currentdate);
			$('.list' + i).val('N');


		}
	}
}
function checkDropDown(subteamcode,activedt,deactivedt){
	
//	mastersetup1
	
var table = $('#checkDatatable').dataTable();
	
	for (i = 1; i <= table.fnGetData().length; i++) {
		
		
		if($('.mastersetup' + i).val()===subteamcode){
			
			$('#ACTIVE_DT' + i).val(activedt);
			$('#DEACTIVE_DT' + i).val(deactivedt);
			
			$('.list' + i).val('Y');
			
		}	
		
		
		
	}
	
	
}

function firstTimeLoad(){
	
//	mastersetup1
	
	var currentdate='';
	$.get('../currentNepaliDate', {getRegionList: "getlist"}, function (response) {
	    
		currentdate= response;
		
	});
	
	var table = $('#checkDatatable').dataTable();
		
		for (i = 1; i <= table.fnGetData().length; i++) {				
		
			  $('#ACTIVE_DT'+i).nepaliDatePicker();
               $('#DEACTIVE_DT'+i).nepaliDatePicker();          
			
			$('#ACTIVE_DT'+i).val(currentdate.toString());
	       $('#DEACTIVE_DT'+i).val(currentdate.toString());
	       	
		}
	
}
function checkFlag(flag){
	
	var table = $('#checkDatatable').dataTable();
		
		for (i = 1; i <= table.fnGetData().length; i++) {
			$('.list' + i).val(flag);
				
		}
	}
