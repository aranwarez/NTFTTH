$(document).ready(function() {
	jQuery.ajaxSetup({
		async : false
	});
	$('#checkDatatable').dataTable({
		"paging" : false
	});
	$("#ROLE_CODE").val('');

	$.fn.dataTable.ext.errMode = 'none';
});

function getEditMode() {

	var ROLE_CODE = $("#ROLE_CODE").val();

	$.get("../getEditMode", {
		ROLE_CODE : ROLE_CODE
	}, function(data) {
		if (data.length === 0) {
			alert('length zero ');
			var table = $('#checkDatatable').dataTable();
			for (i = 1; i <= table.fnGetData().length; i++) {
				$(".editing" + i).val('');
				$(".list" + i).val('');
				$(".deleting" + i).val('');
				$(".posting" + i).val('');
				$(".add" + i).val('');
				$(".cancel" + i).val('');

				$('input:checkbox').attr('checked', false);

				$('.mastersetup').attr('checked', false);

			}

			// reset all the checkbox
			$('.mastersetup' + i).attr('checked', false);
			return;

		}

	

		// alert(JSON.stringify(data));

		var table = $('#checkDatatable').dataTable();
		for (i = 1; i <= table.fnGetData().length; i++) {
			$.each(data, function(index, value) {
				// Get the total rows
				
				if ($('.mastersetup' + i).val() == value.menu_CODE) {
					
					$(".list" + i).val(value.list_FLAG);	
					
					$(".editing" + i).val(value.edit_FLAG);
					$(".deleting" + i).val(value.delete_FLAG);
					$(".posting" + i).val(value.post_FLAG);
					$(".add" + i).val(value.add_FLAG);
					$(".cancel" + i).val(value.cancel_FLAG);
				}

				if (value.menu_CODE != undefined) {
					

					$(':checkbox[Value="' + value.menu_CODE + '"]').prop(
							'checked', true);
				}

			});
		}
	});
}

function saveMenuAccess() {
    var val = [];
    $(':checkbox:checked').each(function (i) {
        val[i] = $(this).val();
    });
    
    var table = $('#checkDatatable').dataTable();
//Get the total rows
    var inserteditdeletepost = [];
    for (i = 1; i <= table.fnGetData().length; i++) {
        var myobj = new Object();
        if ($('#ROLE_CODE').val() != null) {
            myobj.ROLE_CODE = $("#ROLE_CODE").val();
            myobj.MENU_CODE = $('.mastersetup' + i).val();
            myobj.ADD_FLAG = $('.add' + i).val();
            myobj.LIST_FLAG=$('.list'+i).val();
            myobj.EDIT_FLAG = $('.editing' + i).val();
            myobj.DELETE_FLAG = $('.deleting' + i).val();
            myobj.POST_FLAG = $('.posting' + i).val();
            myobj.CANCEL_FLAG = $('.cancel' + i).val();
            inserteditdeletepost.push(myobj);
        }
    }
alert(JSON.stringify(inserteditdeletepost));

alert($("#ROLE_CODE").val());

//alert('save menu code '+JSON.stringify(val));

//$.post("../saveModeList", {ROLE_CODE: $("#ROLE_CODE").val(), list: JSON.stringify(inserteditdeletepost)}, function (data) {
//    alert(data);
////    getAllMenu();
//
//
//});

 
    $.post("../saveModeList", {ROLE_CODE: $("#ROLE_CODE").val(), menu_mode: JSON.stringify(inserteditdeletepost)}, function (data) {
        alert(data);
//        getAllMenu();


    });
    
    
//    $.ajax({
//    	url: "../saveModeList",
//    	type: 'POST',
//    	data: {_token: CSRF_TOKEN,vaccinelists:insertrow,treatmentlists:insertrow1},
//    	dataType: 'JSON',
//    	success: function (data) {	
//    		
//    		alert(data.comments);
//    		
//    	}
//
//    });
    
    
}