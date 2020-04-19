var currentdate;
var glbUser;
var token_id;

$(document).ready(function() {

});

function getZone() {

	var REGION_CODE = $("#REGION_CODE").val();

	$.get("../getZoneByRegion", {
		REGION_CODE : REGION_CODE
	}, function(data) {

		var select = $('#ZONE_CODE');
		select.find('option').remove();
		$('<option>').val("").text("SELECT Zone").appendTo(select);
		$('#DISTRICT_CODE').find('option:not(:first)').remove();
		$('#OFFICE_CODE').find('option:not(:first)').remove();
		$('#OLT_CODE').find('option:not(:first)').remove();

	
		$.each(data, function(index, value) {
			$('<option>').val(value.ZONE_CODE).text(value.DESCRIPTION)
					.appendTo(select);

		});

	});

}

function getDistrict() {

	var ZONE_CODE = $("#ZONE_CODE").val();

	$.get("../getDistrictByZone", {
		ZONE_CODE : ZONE_CODE
	}, function(data) {

		var select = $('#DISTRICT_CODE');
		select.find('option').remove();
		$('<option>').val("").text("SELECT District").appendTo(select);

		$('#OFFICE_CODE').find('option:not(:first)').remove();
		$('#OLT_CODE').find('option:not(:first)').remove();

		if (data.length == 0 || data.length == undefined) {

			clearDataTable();
			return;
		}

		$.each(data, function(index, value) {
			$('<option>').val(value.DISTRICT_CODE).text(value.DESCRIPTION)
					.appendTo(select);

		});

	});

}

function getOffice() {

	var DISTRICT_CODE = $("#DISTRICT_CODE").val();

	$.get("../getOfficeByDistrict", {
		DISTRICT_CODE : DISTRICT_CODE
	}, function(data) {

		var select = $('#OFFICE_CODE');
		select.find('option').remove();
		$('<option>').val("").text("SELECT Office").appendTo(select);

		$('#OLT_CODE').find('option:not(:first)').remove();
		if (data.length == 0 || data.length == undefined) {

			clearDataTable();
			return;
		}

		$.each(data, function(index, value) {
			$('<option>').val(value.OFFICE_CODE).text(value.DESCRIPTION)
					.appendTo(select);

		});

	});

}

function getOLT() {

	var OFFICE_CODE = $("#OFFICE_CODE").val();

	$.get("../getOLTByOffice", {
		OFFICE_CODE : OFFICE_CODE
	}, function(data) {

		var select = $('#OLT_CODE');
		select.find('option').remove();
		$('<option>').val("").text("SELECT OLT").appendTo(select);

		$('#OLT_CODE').find('option:not(:first)').remove();
		if (data.length == 0 || data.length == undefined) {

			clearDataTable();
			return;
		}

		$.each(data, function(index, value) {
			$('<option>').val(value.OLT_CODE).text(
					value.DESCRIPTION + "-" + value.OLT_CODE).appendTo(select);

		});

	});

}
function fetchView() {

	var REGION_CODE = $("#REGION_CODE").val();
	var ZONE_CODE = $("#ZONE_CODE").val();
	var DISTRICT_CODE = $("#DISTRICT_CODE").val();
	var OFFICE_CODE = $("#OFFICE_CODE").val();
	var OLT_CODE = $("#OLT_CODE").val();

	var SUBTEAMCODE = $('#SUBTEAMCODE').val();
	var SERVICE_TYPE = $('#SERVICE_TYPE').val();
	var FRM_DT = $('#QFROM_DT').val();
	var TO_DT = $('#QTO_DT').val();
	var Statusflag = $('#Statusflag').val();

	$
			.get(
					"../troubleticket/getfilterlist",
					{
						REGION_CODE : REGION_CODE,
						ZONE_CODE : ZONE_CODE,
						DISTRICT_CODE : DISTRICT_CODE,
						OFFICE_CODE : OFFICE_CODE,
						OLT_CODE : OLT_CODE,
						SERVICE_TYPE : SERVICE_TYPE,
						SUB_TEAM : SUBTEAMCODE,
						FRM_DT : FRM_DT,
						TO_DT : TO_DT,
						Statusflag:Statusflag

					},
					function(data) {
						console.log(data);
					//	$('#example1').DataTable();
						var table = $('#example1').DataTable();
						 
						table
						    .clear()
						    .draw();
						

						$
								.each(
										data,
										function(key, value) {
											$("#example1")
													.dataTable()
													.fnAddData(
															[
																	value.TOKEN_ID,
																	value.SERVICE_DESC,
																	value.SUB_TEAM_CODE,
																	value.PROBLEM_DESC,
																	value.FDC_DESC,
																	value.COMPLAIN_NO,
																	value.CONTACT_NAME,
																	'<a href="#" class="btn bg-purple" data-toggle="modal" data-target="#myModal" onclick="token_id=(\''
																			+ value.SUB_TOKEN_ID
																			+ '\')"> <i class="fa fa-mail-forward"></i> Forward </a>',
																	'<a href="#" class="btn bg-red" data-toggle="modal" data-target="#deleteModal" onclick="token_id=(\''
																			+ value.SUB_TOKEN_ID
																			+ '\')"> <i class="fa fa-trash"></i> Close</a>',
																	'<a href="#" class="btn bg-blue" data-toggle="modal" data-target="#viewModal" onclick="return viewdetail(\''
																			+ value.SUB_TOKEN_ID
																			+ '\')"> <i class="fa fa-edit"></i> History </a>',
																			'<a target="_blank" href="../complain/list?CPE='+value.SRV_NO+'" class="btn bg-green"> <i class="fa fa-edit"></i> Detail </a>'				
																			
																			
																			]);
										});

					});

}

function saveUserFDC() {

	var table = $('#checkDatatable').dataTable();

	// alert(table.fnGetData().length);

	if ($('#USER_ID').val() == '') {
		alert('Please select user ');
		return;
	}

	// Get the total rows
	var inserteditdeletepost = [];
	for (i = 1; i <= table.fnGetData().length; i++) {
		var myobj = new Object();
		if ($('#USER_ID').val() != null) {

			// console.log('user cha '+$('#USER_ID').val());

			if ($('.list' + i).val() == 'Y') {

				myobj.USER_ID = $("#USER_ID").val();
				myobj.FDC_CODE = $('.mastersetup' + i).val();
				myobj.ACTIVE_DT = $('#ACTIVE_DT' + i).val();
				myobj.DEACTIVE_DT = $('#DEACTIVE_DT' + i).val();

				myobj.LIST_FLAG = $('.list' + i).val();

				inserteditdeletepost.push(myobj);

			}
		}
	}

	// console.log(JSON.stringify(inserteditdeletepost));

	$.post("../saveuserfdc", {
		USER_ID : $("#USER_ID").val(),
		menu_mode : JSON.stringify(inserteditdeletepost)
	}, function(data) {
		alert(data);
		// getAllMenu();

	});

}


function getSubTeamList(){
	
		var TEAM_CODE=$("#TEAM_CODE").val();
		
		 $.get('../subteamByTeam/jsonlist', {TEAM_CODE: TEAM_CODE
		    }, function (response) {
		        var select = $('#SUB_TEAM_CODE');
		        select.find('option').remove();
		        $('<option>').val("").text("SELECT Sub Team ").appendTo(select);
		        $.each(response, function (index, value) {
		            $('<option>').val(value.SUB_TEAM_CODE).text(value.DESCRIPTION+'-'+value.SUB_TEAM_CODE).appendTo(
		                    select);

		        });

		    });
}

function ForwardTeam(){
	if($('#remarks').val().length>250 || $('#SUB_TEAM_CODE').val()===""){
		alert('Warning Sub team is invalid or remark length exceeded!!!');
		return false;
	}

	$.post('../troubleticket/Forward', {Remarks: $('#remarks').val(),
		 token:token_id,
		 toteam:$('#SUB_TEAM_CODE').val()
	    }, function (response) {
	    	alert(response);
	    	$('#myModal').modal('hide');
	    	fetchView();
	    	
	    	
	    });
}

function CloseTicket(){
	if($('#remarks').val().length>250){
		alert('Warning remark length exceeded!!!');
		return false;
	}

	$.post('../troubleticket/Close', {Remarks: $('#closeremarks').val(),
		 token:token_id
	    }, function (response) {
	    	alert(response);
	    	$('#deleteModal').modal('hide');
	    	fetchView();
	    	
	    	
	    });
}

function viewdetail(subtokenid){
	$
	.get(
			"../troubleticket/gettokenhistory",
			{
				SUBTOKEN:subtokenid

			},
			function(data) {
				console.log(data);
			//	$('#example1').DataTable();
				var table = $('#viewdetailtable').DataTable();
				 
				table
				    .clear()
				    .draw();
				

				$
						.each(
								data,
								function(key, value) {
									var date=new Date(value.CREATE_DT);
									$("#viewdetailtable")
											.dataTable()
											.fnAddData(
													[value.FROM_SUB_TEAM_CODE,
														value.TO_SUB_TEAM_CODE,
														value.SOLVE_FLAG,
														value.REMARKS,
														value.CREATE_BY,
														date
														 ]);
								});

			});


}


function loadLevelWise(level){
	if(level=='1'){
	return;
	}
	else if(level=='2'){
		getZone();
		return;
	}
	else if(level=='3'){
	getDistrict();
	return;
	
	}
	else if(level=='4'){
	getOffice();
	return;
}
else {
	getOLT();
	return;
}
	
}
function clearDataTable(){
	
}
