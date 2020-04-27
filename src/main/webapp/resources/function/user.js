var CODE;
function saveUser() {

	var pass = $("#PASSWORD").val();
	var passmatch = $("#passmatch").val();
	if (pass === passmatch) {

		$(".matchpass").html('Password Matches');
	} else {
		$(".matchpass").html("Password doesn't match");
		return false;
	}

	$.post('../save/user', {
		USER_ID : $("#USER_ID").val(),
		PASSWORD: $("#PASSWORD").val(),
		FULL_NAME : $("#FULL_NAME").val(),		
		EMPLOYEE_CODE : $("#EMPLOYEE_CODE").val(),
		LOCK_FLAG : $('.LOCK_FLAG:checked').val(),
		SUPER_FLAG : $('.SUPER_FLAG:checked').val(),
		DISABLE_FLAG : $('.SUPER_FLAG:checked').val(),
		LOCATION_CODE : $("#LOCATION_CODE").val(),
		USER_LEVEL : $("#USER_LEVEL").val(),
		ROLE_CODE : $("#ROLE_CODE").val(),		
		OFFICE_CODE : $("#OFFICE_CODE").val(),	
		MOBILE_NO : $("#MOBILE_NO").val()
	}, function(data) {
		alert(data);

		location.reload();
	});
}
function getAccountCenter() {
	jQuery.ajaxSetup({
		async : false
	});
	var REGION_CODE = $('#REGION_CODE').val();

	$.get('../response/acccenter', {
		getaccCenList : "getlist",
		REGION_CODE : REGION_CODE
	}, function(response) {

		var select = $('#ACC_CEN_CODE');
		select.find('option').remove();
		$('<option>').val("").text("Select").appendTo(select);
		$.each(response, function(index, value) {
			$('<option>').val(value.acc_CEN_CODE).text(value.description)
					.appendTo(select);
		});

	}); // closing function(responseJson)

}

function getCC() {
	var ACC_CEN_CODE = $("#ACC_CEN_CODE").val();
	jQuery.ajaxSetup({
		async : false
	});
	// change getCCList to getCCListModule
	$.get('../cc/getlist', {
		getCCListModule : "getlist",
		ACC_CEN_CODE : ACC_CEN_CODE
	}, function(response) {

		var select = $('#CC_CODE');
		select.find('option').remove();
		$('<option>').val("").text("Select").appendTo(select);
		$.each(response, function(index, value) {
			$('<option>').val(value.cc_CODE).text(value.description).appendTo(
					select);
		});

	}); // closing function(responseJson)

}
function getHold(code) {

	CODE = code;
	$("#pcuser").html(code);
}

function changePassword() {

	var pass = $("#newpass").val();
	var passmatch = $("#newpassmatch").val();

	if (passmatch === pass) {
		$(".matchpass1").html('Password Matches');

	} else {
		$(".matchpass1").html("Password doesn't match");
		return false;

	}
	$.post('../adminPassChange', {
		adminchangepass : "adminchangepass",
		usercode : CODE,
		pass : pass
	}, function(response) {
		$(".matchpass1").html('');

		$('#updatepass').addClass('return').html(response).fadeIn('fast')
				.delay(6000).fadeOut('slow');

	});

}
function editUser(code) {
    CODE = code;
   
    jQuery.ajaxSetup({async: false});
    $.get('../user/edit', {code: code}, function (response) {
    	
        $("#EDITUSER_ID").val(CODE);
        $("#EDITFULL_NAME").val(response.full_NAME);
        $("#EDITEMPLOYEE_CODE").val(response.employee_CODE).trigger('change');
//    	alert(response.employee_CODE);
    	
        if (response.lock_FLAG == 'Y') {
            $('input:radio[name="EDITLOCK_FLAG"][value=Y]').attr('checked', true);
        } else if (response.lock_FLAG == 'N') {
            $('input:radio[name=EDITLOCK_FLAG][value=N]').attr('checked', true);
        }

        if (response.super_FLAG == 'Y') {
            $('input:radio[name="EDITSUPER_FLAG"][value=Y]').attr('checked', true);
        } else if (response.super_FLAG == 'N') {
            $('input:radio[name="EDITSUPER_FLAG"][value=N]').attr('checked', true);
        }

        if (response.disable_FLAG == 'Y') {
            $('input:radio[name="EDITDISABLE_FLAG"][value=Y]').attr('checked', true);
        } else if (response.disable_FLAG == 'N') {
            $('input:radio[name="EDITDISABLE_FLAG"][value=N]').attr('checked', true);
        }        
        $("#EDITLOCATION_CODE").val(response.location_CODE);
             

        $("#EDITUSER_LEVEL").val(response.user_LEVEL);
        
        
        $("#EDITROLE_CODE").val(response.role_CODE);
        $("#EDITOFFICE_CODE").val(response.office_CODE).trigger('change');
        $("#EDITMOBILE_NO").val(response.mobile_NO);
   

    });
}


function getEditAccountCenter() {
    jQuery.ajaxSetup({async: false});
    
    var REGION_CODE = $('#EDITREGION_CODE').val();
    
    $.get('../response/acccenter', {getaccCenList: "getlist", REGION_CODE: REGION_CODE}, function (response) {

        var select = $('#EDITACC_CEN_CODE');
        select.find('option').remove();
        $('<option>').val("").text("Select").appendTo(select);
        $.each(response, function (index, value) {
            $('<option>').val(value.acc_CEN_CODE).text(value.description+"("+value.acc_CEN_CODE+")").appendTo(select);
        });

    }); //closing function(responseJson)



}


function updateUser() {

	
    $.post('../update/user', {
    	USER_ID : CODE,		
		FULL_NAME : $("#EDITFULL_NAME").val(),		
		EMPLOYEE_CODE : $("#EDITEMPLOYEE_CODE").val(),
		LOCK_FLAG : $('.EDITLOCK_FLAG:checked').val(),
		SUPER_FLAG : $('.EDITSUPER_FLAG:checked').val(),
		DISABLE_FLAG : $('.EDITSUPER_FLAG:checked').val(),
		LOCATION_CODE : $("#EDITLOCATION_CODE").val(),
		USER_LEVEL : $("#EDITUSER_LEVEL").val(),
		ROLE_CODE : $("#EDITROLE_CODE").val(),		
		OFFICE_CODE : $("#EDITOFFICE_CODE").val(),	
		MOBILE_NO : $("#EDITMOBILE_NO").val()
		
    }, function (data) {
        	alert(data);
        	
//        location.reload();
    });
}


function getEditCC() {
    var ACC_CEN_CODE = $("#EDITACC_CEN_CODE").val();
    
    //change getCCList to getCCListModule
    $.get('../cc/getlist', {ACC_CEN_CODE: ACC_CEN_CODE}, function (response) {

        var select = $('#EDITCC_CODE');
        select.find('option').remove();
        $('<option>').val("").text("Select").appendTo(select);
        $.each(response, function (index, value) {
            $('<option>').val(value.cc_CODE).text(value.description).appendTo(select);
            $("#EDITACC_CEN_CODE").val(value.acc_CEN_CODE);

        });

    }); //closing function(responseJson)


}

function deleteUser(code) {
    CODE = code;
}

function del() {

    $.post('../user/delete', {delete: "deletuser", USER_ID: CODE}, function (data) {
    	
    	alert(data);
    	
        location.reload();
    });
}



