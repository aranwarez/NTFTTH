$(document).ready(function() {
	   
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

	var ROLE_CODE = $("#ROLE_CODE").val();

	$.get("../getEditMode", {
		ROLE_CODE : ROLE_CODE
	}, function(data) {

		if (data.length == 0) {
			
			clearDataTable();
			
			return;
		}
		//console.log(JSON.stringify(data));
		i = 1;
		$.each(data, function(index, value) {
			// Get the total rows

			checkDropDown(value.menu_CODE,value.list_FLAG,value.add_FLAG,value.edit_FLAG,value.delete_FLAG,value.post_FLAG,value.cancel_FLAG)
			
			
		});

		// console.log(JSON.stringify(data));

	});
}

function saveMenuAccess() {

	var table = $('#checkDatatable').dataTable();
	
//	alert(table.fnGetData().length);
	
	
	// Get the total rows
	var inserteditdeletepost = [];
	for (i = 1; i <= table.fnGetData().length; i++) {
		var myobj = new Object();
		if ($('#ROLE_CODE').val() != null) {
			myobj.ROLE_CODE = $("#ROLE_CODE").val();
			myobj.MENU_CODE = $('.mastersetup' + i).val();
			myobj.ADD_FLAG = $('.add' + i).val();
			myobj.LIST_FLAG = $('.list' + i).val();
			myobj.EDIT_FLAG = $('.editing' + i).val();
			myobj.DELETE_FLAG = $('.deleting' + i).val();
			myobj.POST_FLAG = $('.posting' + i).val();
			myobj.CANCEL_FLAG = $('.cancel' + i).val();
			inserteditdeletepost.push(myobj);
		
			
		}
	}


	
	$.post("../saveModeList", {
		ROLE_CODE : $("#ROLE_CODE").val(),
		menu_mode : JSON.stringify(inserteditdeletepost)
	}, function(data) {
		alert(data);
		// getAllMenu();

	});

}


function checkDropDown(menu_CODE,list_FLAG,add_FLAG,edit_FLAG,delete_FLAG,post_FLAG,cancel_FLAG){
	
//	mastersetup1

var table = $('#checkDatatable').dataTable();
	
	for (i = 1; i <= table.fnGetData().length; i++) {
				
		if($('.mastersetup' + i).val()===menu_CODE){
			
			$('.list' + i).val(list_FLAG);
			$('.add' + i).val(add_FLAG);	
			
			$('.editing' + i).val(edit_FLAG);
			$('.deleting' + i).val(delete_FLAG);
			$('.posting' + i).val(post_FLAG);
			$('.cancel' + i).val(cancel_FLAG);
			
			
			
		}
			
		
	}
	
	
}

function clearDataTable() {
	var table = $('#checkDatatable').dataTable();
	setTimeout(function(){
	for (i = 1; i <= table.fnGetData().length; i++) {
		
			
			$('.add' + i).val('N');
			$('.list' + i).val('N');
			$('.editing' + i).val('N');
			$('.deleting' + i).val('N');
			$('.posting' + i).val('N');
			$('.cancel' + i).val('N');


	}
	
}, 2000);
}