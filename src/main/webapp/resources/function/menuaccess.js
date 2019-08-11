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

	var ROLE_CODE = $("#ROLE_CODE").val();

	$.get("../getEditMode", {
		ROLE_CODE : ROLE_CODE
	}, function(data) {

		if (data.length == 0) {
			
			clearDataTable();
			
		}

		i = 1;
		$.each(data, function(index, value) {
			// Get the total rows

			console.log($('.mastersetup' + i).val());

			if (value.menu_CODE == $('.mastersetup' + i).val()) {
				$(".list" + i).val(value.list_FLAG);
				$(".editing" + i).val(value.edit_FLAG);
				$(".deleting" + i).val(value.delete_FLAG);
				$(".posting" + i).val(value.post_FLAG);
				$(".add" + i).val(value.add_FLAG);
				$(".cancel" + i).val(value.cancel_FLAG);
			}
			i = i + 1;

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
function clearDataTable() {
	var table = $('#checkDatatable').dataTable();
	
	for (i = 1; i <= table.fnGetData().length; i++) {
		if ($('#ROLE_CODE').val() != null) {
			
			$('.add' + i).val('N');
			$('.list' + i).val('N');
			$('.editing' + i).val('N');
			$('.deleting' + i).val('N');
			$('.posting' + i).val('N');
			$('.cancel' + i).val('N');

		}
	}
}