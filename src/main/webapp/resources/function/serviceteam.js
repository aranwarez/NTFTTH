$(document).ready(function() {
	jQuery.ajaxSetup({
		async : false
	});
	
	 
	
	    
	$('#checkDatatable').dataTable({
		"paging" : false,
		"searching" : false,
		"info" : false,
		"ordering" : false,
		"fixedHeader": true,
	});
	
	$("#ROLE_CODE").val('');

	$.fn.dataTable.ext.errMode = 'none';
	
	
	
});





function getEditMode() {

	var SERVICE_TYPE_ID = $("#SERVICE_TYPE_ID").val();

	$.get("../getServiceAccess", {
		SERVICE_TYPE_ID : SERVICE_TYPE_ID
	}, function(data) {

//		console.log(data.length);
		
		if (data.length == 0 ||data.length == undefined) {
			
			clearDataTable();
			return;
		}	
		


	
//		for (i = 1; i <= table.fnGetData().length; i++) {
			
			
			$.each(data, function(index, value) {
				// Get the total rows
				
//				alert(value.sub_TEAM_CODE);
				
				console.log(value.sub_TEAM_CODE);
				
				checkDropDown(value.sub_TEAM_CODE);

//				if ($('.mastersetup' + i).val() == value.sub_TEAM_CODE) {
//					
//					$(".list" + i).val('Y');
//					
//					console.log(value.sub_TEAM_CODE);
//					console.log('YES= '+i);
//					
//				}
							
				

			});
					
			
//	}
		
		
		
		// console.log(JSON.stringify(data));

	});
}

function saveServiceTeam() {

	var table = $('#checkDatatable').dataTable();
	
//	alert(table.fnGetData().length);
	
	
	// Get the total rows
	var inserteditdeletepost = [];
	for (i = 1; i <= table.fnGetData().length; i++) {
		var myobj = new Object();		
		if ($('#SERVICE_TYPE_ID').val() != null) {			
//			console.log($('.list' + i).val());
			
			if($('.list' + i).val()=='Y'){

			myobj.SERVICE_TYPE_ID = $("#SERVICE_TYPE_ID").val();
			myobj.SUB_TEAM_CODE = $('.mastersetup' + i).val();
			
		
			myobj.LIST_FLAG = $('.list' + i).val();
			
			inserteditdeletepost.push(myobj);
		
			}
		}
	}

//		console.log(JSON.stringify(inserteditdeletepost));

	
	$.post("../saveServiceTeam", {
		SERVICE_TYPE_ID : $("#SERVICE_TYPE_ID").val(),
		menu_mode : JSON.stringify(inserteditdeletepost)
	}, function(data) {
		alert(data);
		// getAllMenu();

	});

}
function clearDataTable() {
	var table = $('#checkDatatable').dataTable();
	
	for (i = 1; i <= table.fnGetData().length; i++) {

		if ($('#SERVICE_TYPE_ID').val() != null) {

			$('.list' + i).val('N');


		}
	}
}
function checkDropDown(subteamcode){
	
//	mastersetup1
	
var table = $('#checkDatatable').dataTable();
	
	for (i = 1; i <= table.fnGetData().length; i++) {
		
		
		if($('.mastersetup' + i).val()===subteamcode){
			
			$('.list' + i).val('Y');
			
		}
			
		
		
		
	}
	
	
}