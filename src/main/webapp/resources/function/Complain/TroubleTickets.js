var currentdate;
var glbUser;
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
						TO_DT : TO_DT

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
																	'<a href="#" class="btn bg-purple" data-toggle="modal" data-target="#editModal" onclick="return editReceipt(\''
																			+ value.TOKEN_ID
																			+ '\')"> <i class="fa fa-mail-forward"></i> Forward </a>',
																	'<a href="#" class="btn bg-red" data-toggle="modal" data-target="#deleteModal" onclick="return deleteReceipt(\''
																			+ value.TOKEN_ID
																			+ '\')"> <i class="fa fa-trash"></i> Resolved </a>',
																	'<a href="#" class="btn bg-purple" data-toggle="modal" data-target="#editModal" onclick="return editReceipt(\''
																			+ value.TOKEN_ID
																			+ '\')"> <i class="fa fa-edit"></i> View Detail </a>' ]);
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
