var currentdate;
var glbUser;
var token_id;
var closeflag;
$(document).ready(
		function() {
			if (getURLParameter('token_id') != null
					&& getURLParameter('token_id').length > 0) {
				getTokenDetailbyID(getURLParameter('token_id'));

			}

		});

function getTokenDetailbyID(token) {
	$
			.get(
					"../troubleticket/gettokendetails",
					{
						token_id : token
					},
					function(data) {
						var table = $('#example1').DataTable();

						table.clear().draw();

						$
								.each(
										data,
										function(key, value) {
											var date = new Date(value.CREATE_DT)
													.toDateString("yyyy-MM-dd");
											;
											var solveflag = '<button type="button" title="Resolve" class="btn bg-yellow" data-toggle="modal" data-target="#deleteModal" onclick="token_id=(\''
													+ value.SUB_TOKEN_ID
													+ '\');closeflag=(\''
													+ 'Y'
													+ '\');"> <i class="fa fa-trash"></i>Resolved</button>';
											var forwardflag = '<button type="button" title="Forward" class="btn bg-purple" data-toggle="modal" data-target="#myModal" onclick="token_id=(\''
													+ value.SUB_TOKEN_ID
													+ '\')"> <i class="fa fa-mail-forward"></i>Fordward</button>';
											if (value.SOLVE_FLAG == 'Y') {
												solveflag = '<button type="button" class="btn bg-red" data-toggle="modal" data-target="#deleteModal" onclick="token_id=(\''
														+ value.SUB_TOKEN_ID
														+ '\');closeflag=(\''
														+ 'C'
														+ '\');"> <i class="fa fa-trash"></i> Close</button>';
												forwardflag = "";
											} else if (value.SOLVE_FLAG == 'C') {
												solveflag = 'Closed-';
												forwardflag = "";
											}

											$("#example1")
													.dataTable()
													.fnAddData(
															[
																	value.TOKEN_ID,
																	value.SRV_NO,
																	value.SERVICE_DESC,

																	javadate(value.CREATE_DT),
																	value.SUB_TEAM_CODE,
																	value.PROBLEM_DESC,
																	value.FDC_DESC,
																	value.COMPLAIN_NO,
																	value.CONTACT_NAME,
																	'<div class="btn=group">'
																			+ forwardflag
																			+ solveflag
																			+ '<button type="button" class="btn bg-blue" data-toggle="modal" data-target="#viewModal" onclick="return viewdetail(\''
																			+ value.SUB_TOKEN_ID
																			+ '\')"> <i class="fa fa-history"></i>History</button>',
																	'<a target="_blank" href="../complain/list?CPE='
																			+ value.SRV_NO
																			+ '" class="btn bg-green"> <i class="fa fa-edit"></i> Detail </a></div>'

															]);
										});

					});

}

function getURLParameter(name) {
	return decodeURIComponent((new RegExp('[?|&]' + name + '='
			+ '([^&;]+?)(&|#|;|$)').exec(location.search) || [ null, '' ])[1]
			.replace(/\+/g, '%20'))
			|| null;
}

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
						Statusflag : Statusflag,
						WEBTEAMCODE : $('#WEBTEAMCODE').val()

					},
					function(data) {
						console.log(data);
						// $('#example1').DataTable();

						var table = $('#example1').DataTable({
							responsive : true
						});

						table.clear().draw();

						$
								.each(
										data,
										function(key, value) {
											var date = new Date(value.CREATE_DT)
													.toDateString("yyyy-MM-dd");
											;
											var solveflag = '<button type="button" title="Resolve" class="btn bg-yellow" data-toggle="modal" data-target="#deleteModal" onclick="token_id=(\''
													+ value.SUB_TOKEN_ID
													+ '\');closeflag=(\''
													+ 'Y'
													+ '\');"> <i class="fa fa-trash"></i>Resolved</button>';
											var forwardflag = '<button type="button" title="Forward" class="btn bg-purple" data-toggle="modal" data-target="#myModal" onclick="token_id=(\''
													+ value.SUB_TOKEN_ID
													+ '\')"> <i class="fa fa-mail-forward"></i>Fordward</button>';
											if (value.SOLVE_FLAG == 'Y') {
												solveflag = '<button type="button" class="btn bg-red" data-toggle="modal" data-target="#deleteModal" onclick="token_id=(\''
														+ value.SUB_TOKEN_ID
														+ '\');closeflag=(\''
														+ 'C'
														+ '\');"> <i class="fa fa-trash"></i> Close</button>';
												forwardflag = "";
											} else if (value.SOLVE_FLAG == 'C') {
												solveflag = 'Closed-';
												forwardflag = "";
											}

											$("#example1")
													.dataTable()
													.fnAddData(
															[
																	value.TOKEN_ID,
																	value.SRV_NO,
																	value.SERVICE_DESC,

																	javadate(value.CREATE_DT),
																	value.SUB_TEAM_CODE,
																	value.PROBLEM_DESC,
																	value.FDC_DESC,
																	value.COMPLAIN_NO,
																	value.CONTACT_NAME,
																	'<div class="btn=group">'
																			+ forwardflag
																			+ solveflag
																			+ '<button type="button" class="btn bg-blue" data-toggle="modal" data-target="#viewModal" onclick="return viewdetail(\''
																			+ value.SUB_TOKEN_ID
																			+ '\')"> <i class="fa fa-history"></i>History</button>',
																	'<a target="_blank" href="../complain/list?CPE='
																			+ value.SRV_NO
																			+ '" class="btn bg-green"> <i class="fa fa-edit"></i> Detail </a></div>'

															]);
										});

					});

	// geting close all ticket button
	if ($('#Statusflag').val() == 'Y' && $('#SUBTEAMCODE').val() == 'FLMTA') {
		$('#closebuttondiv').fadeIn();
	} else {
		$('#closebuttondiv').fadeOut();
	}

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

function getSubTeamList() {

	var TEAM_CODE = $("#TEAM_CODE").val();

	$.get('../subteamByTeam/jsonlist', {
		TEAM_CODE : TEAM_CODE
	}, function(response) {
		var select = $('#SUB_TEAM_CODE');
		select.find('option').remove();
		$('<option>').val("").text("SELECT Sub Team ").appendTo(select);
		$.each(response, function(index, value) {
			$('<option>').val(value.SUB_TEAM_CODE).text(
					value.DESCRIPTION + '-' + value.SUB_TEAM_CODE).appendTo(
					select);

		});

	});
}

function ForwardTeam() {
	if ($('#remarks').val().length > 250 || $('#SUB_TEAM_CODE').val() === "") {
		alert('Warning Sub team is invalid or remark length exceeded!!!');
		return false;
	}

	$.post('../troubleticket/Forward', {
		Remarks : $('#remarks').val(),
		token : token_id,
		toteam : $('#SUB_TEAM_CODE').val()
	}, function(response) {
		alert(response);
		$('#myModal').modal('hide');
		fetchView();

	});
}

function CloseTicket() {

	if ($('#remarks').val().length > 250) {
		alert('Warning remark length exceeded!!!');
		return false;
	}

	if (closeflag == 'Y') {
		$.post('../troubleticket/Resolved', {
			Remarks : $('#closeremarks').val(),
			token : token_id
		}, function(response) {
			alert(response);
			$('#deleteModal').modal('hide');
			fetchView();

		});

	} else if (closeflag == 'C') {
		$.post('../troubleticket/Close', {
			Remarks : $('#closeremarks').val(),
			token : token_id
		}, function(response) {
			alert(response);
			$('#deleteModal').modal('hide');
			fetchView();

		});

	}

}

function viewdetail(subtokenid) {
	$.get("../troubleticket/gettokenhistory", {
		SUBTOKEN : subtokenid

	}, function(data) {
		console.log(data);
		// $('#example1').DataTable();
		var table = $('#viewdetailtable').DataTable();

		table.clear().draw();

		$.each(data, function(key, value) {
			var date = new Date(value.CREATE_DT);
			$("#viewdetailtable").dataTable().fnAddData(
					[ value.FROM_SUB_TEAM_CODE, value.TO_SUB_TEAM_CODE,
							value.SOLVE_FLAG, value.REMARKS, value.CREATE_BY,
							date ]);
		});

	});

}

function loadLevelWise(level) {
	if (level == '1') {
		return;
	} else if (level == '2') {
		getZone();
		return;
	} else if (level == '3') {
		getDistrict();
		return;

	} else if (level == '4') {
		getOffice();
		return;
	} else {
		getOLT();
		return;
	}

}
function clearDataTable() {

}

function javadate(date) {
	var d = new Date(date);
	var curr_date = d.getDate();
	var curr_month = d.getMonth() + 1; // Months are zero based
	var curr_year = d.getFullYear();
	var hour = d.getHours();
	var min = d.getMinutes();
	// return (curr_year + "-" + curr_month + "-" + curr_date+" "+
	// hour+":"+min);
	return (curr_year + "-" + curr_month + "-" + curr_date);

}

function Closealltickets() {
	alert('WIP in progress!!');

}
