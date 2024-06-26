var currentdate;
var glbUser;
var token_id;
var closeflag;
var closeticketarray;
var subteam_id;
var solutionlist;
$(document).ready(
	function() {
		$.fn.dataTable.ext.errMode = 'none';
		if (getURLParameter('token_id') != null
			&& getURLParameter('token_id').length > 0) {
			getTokenDetailbyID(getURLParameter('token_id'));

		}


		// -- till here
		// disable double click
		$(".submitbutton").on("click", function() {
			$(this).attr("disabled", "disabled");
			// auto enable button after delay
			setTimeout('$(".submitbutton").removeAttr("disabled")', 2000);
		});
		// -till here disable double click

		//load solution list
		$.get('../solution/JSlist', {
	}, function(response) {
		solutionlist = response;
	});


	});

function getTokenDetailbyID(token) {
	$
		.get(
			"../troubleticket/gettokendetails",
			{
				token_id: token
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
							var solveflag = '<button type="button" title="Resolve" class="btn bg-yellow submitbutton" data-toggle="modal" data-target="#deleteModal" onclick="token_id=(\''
								+ value.SUB_TOKEN_ID
								+ '\');Solutionflag(\''
								+ value.PROBLEM_ID
								+ '\');closeflag=(\''
								+ 'Y'
								+ '\');"> <i class="fa fa-trash"></i>Resolved</button>';
							var forwardflag = '<button type="button" title="Forward" class="btn bg-purple submitbutton" data-toggle="modal" data-target="#myModal" onclick="token_id=(\''
								+ value.SUB_TOKEN_ID
								+ '\');subteam_id=(\''
								+ value.SUB_TEAM_CODE
								+ '\');"> <i class="fa fa-mail-forward"></i>Fordward</button>';
							if (value.SOLVE_FLAG == 'Y') {
								solveflag = '<button type="button" class="btn bg-red submitbutton" data-toggle="modal" data-target="#deleteModal" onclick="token_id=(\''
									+ value.SUB_TOKEN_ID
									+ '\');closeflag=(\''
									+ 'C'
									+ '\');"> <i class="fa fa-trash"></i> Close</button>';


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
										+ '<button type="button" class="btn bg-blue submitbutton" data-toggle="modal" data-target="#viewModal" onclick="return viewdetail(\''
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
		+ '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1]
		.replace(/\+/g, '%20'))
		|| null;
}

function getZone() {

	var REGION_CODE = $("#REGION_CODE").val();

	$.get("../getZoneByRegion", {
		REGION_CODE: REGION_CODE
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
		ZONE_CODE: ZONE_CODE
	}, function(data) {

		var select = $('#DISTRICT_CODE');
		select.find('option').remove();
		$('<option>').val("").text("SELECT District").appendTo(select);

		$('#OFFICE_CODE').find('option:not(:first)').remove();
		$('#OLT_CODE').find('option:not(:first)').remove();

		if (data.length == 0 || data.length == undefined) {


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
		DISTRICT_CODE: DISTRICT_CODE
	}, function(data) {

		var select = $('#OFFICE_CODE');
		select.find('option').remove();
		$('<option>').val("").text("SELECT Office").appendTo(select);

		$('#OLT_CODE').find('option:not(:first)').remove();
		if (data.length == 0 || data.length == undefined) {

			//	clearDataTable();
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
		OFFICE_CODE: OFFICE_CODE
	}, function(data) {

		var select = $('#OLT_CODE');
		select.find('option').remove();
		$('<option>').val("").text("SELECT OLT").appendTo(select);

		$('#OLT_CODE').find('option:not(:first)').remove();
		if (data.length == 0 || data.length == undefined) {

			//			clearDataTable();
			return;
		}

		$.each(data, function(index, value) {
			$('<option>').val(value.OLT_CODE).text(
				value.DESCRIPTION + "-" + value.OLT_CODE).appendTo(select);

		});

	});

}

function getfdcteam() {

	var OFFICE_CODE = $("#OFFICE_CODE").val();

	$.get("../fdcteam/jsonlist", {

	}, function(data) {

		var select = $('#WEBTEAMCODE');
		select.find('option').remove();
		$('<option>').val("").text("SELECT FDC TEAM").appendTo(select);

		$('#WEBTEAMCODE').find('option:not(:first)').remove();
		if (data.length == 0 || data.length == undefined) {

			//	clearDataTable();
			return;
		}

		$.each(data, function(index, value) {
			if (value.OFFICE_CODE == OFFICE_CODE || OFFICE_CODE == "") {
				$('<option>').val(value.TEAM_ID).text(
					value.DESCRIPTION + "-" + value.TEAMNAME).appendTo(
						select);
			}
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
				REGION_CODE: REGION_CODE,
				ZONE_CODE: ZONE_CODE,
				DISTRICT_CODE: DISTRICT_CODE,
				OFFICE_CODE: OFFICE_CODE,
				OLT_CODE: OLT_CODE,
				SERVICE_TYPE: SERVICE_TYPE,
				SUB_TEAM: SUBTEAMCODE,
				FRM_DT: FRM_DT,
				TO_DT: TO_DT,
				Statusflag: Statusflag,
				WEBTEAMCODE: $('#WEBTEAMCODE').val()

			},
			function(data) {

				// geting close all ticket button and store tickets in
				// array
				closeticketarray = [];
				if ($('#Statusflag').val() == 'Y'
					&& $('#SUBTEAMCODE').val() == 'FLMTA') {
					$('#closebuttondiv').fadeIn();
					$.each(data, function(key, value) {
						closeticketarray.push(value.SUB_TOKEN_ID);
					});
				} else {
					$('#closebuttondiv').fadeOut();
				}

				//console.log(data);
				// $('#example1').DataTable();

				var table = $('#example1').DataTable({
					"bDestroy": true
				});

				table.clear().draw();

				$
					.each(
						data,
						function(key, value) {
							if (Statusflag === '' && value.SOLVE_FLAG === 'C') {
								//console.log(value);
								return;

							}

							var date = new Date(value.CREATE_DT)
								.toDateString("yyyy-MM-dd");
							;
							var solveflag = '<button type="button" title="Resolve" class="btn bg-yellow submitbutton" data-toggle="modal" data-target="#deleteModal" onclick="token_id=(\''
								+ value.SUB_TOKEN_ID
								+ '\');Solutionflag(\''
								+ value.PROBLEM_ID
								+ '\');closeflag=(\''
								+ 'Y'
								+ '\');"> <i class="fa fa-trash"></i>Resolved</button>';
							var forwardflag = '<button type="button" title="Forward" class="btn bg-purple submitbutton" data-toggle="modal" data-target="#myModal" onclick="token_id=(\''
								+ value.SUB_TOKEN_ID
								+ '\');subteam_id=(\''
								+ value.SUB_TEAM_CODE
								+ '\');"> <i class="fa fa-mail-forward"></i>Fordward</button>';
							if (value.SOLVE_FLAG == 'Y') {
								solveflag = '<button type="button" class="btn bg-red submitbutton" data-toggle="modal" data-target="#deleteModal" onclick="token_id=(\''
									+ value.SUB_TOKEN_ID
									+ '\');closeflag=(\''
									+ 'C'
									+ '\');"> <i class="fa fa-trash"></i> Close</button>';

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
										value.SERVICE_DESC + '-' + value.SERVICE_NO,
										javadate(value.CREATE_DT),
										value.SUB_TEAM_CODE,
										value.PROBLEM_DESC,
										value.FDC_DESC,
										value.COMPLAIN_NO,
										value.CUSTOMER_NAME,
										'<div class="btn=group">'
										+ forwardflag
										+ solveflag
										+ '<button type="button" class="btn bg-blue submitbutton" data-toggle="modal" data-target="#viewModal" onclick="return viewdetail(\''
										+ value.SUB_TOKEN_ID
										+ '\')"> <i class="fa fa-history"></i>History</button>',
										'<a target="_blank" href="../complain/list?CPE='
										+ value.SRV_NO
										+ '" class="btn bg-green"> <i class="fa fa-edit"></i> Detail </a></div>'

									]);
						});

			});

}

function getSubTeamList() {

	var TEAM_CODE = $("#TEAM_CODE").val();

	$.get('../subteamByTeam/jsonlist', {
		TEAM_CODE: TEAM_CODE
	}, function(response) {
		var select = $('#SUB_TEAM_CODE');
		select.find('option').remove();
		$('<option>').val("").text("SELECT Sub Team ").appendTo(select);
		$.each(response, function(index, value) {
			if (value.ACTIVE_FLAG === 'Y') {
				$('<option>').val(value.SUB_TEAM_CODE).text(
					value.DESCRIPTION + '-' + value.SUB_TEAM_CODE).appendTo(
						select);
			}
		});

	});
}

function ForwardTeam() {
	if ($('#remarks').val().length > 250 || $('#SUB_TEAM_CODE').val() === "") {
		alert('Warning Sub team is invalid or remark length exceeded!!!');
		return false;
	}
	if (subteam_id === $('#SUB_TEAM_CODE').val()) {
		alert('Warning: Cant forward to same team. Select Different Team');
		return false;
	}



	$.post('../troubleticket/Forward', {
		Remarks: $('#remarks').val(),
		token: token_id,
		toteam: $('#SUB_TEAM_CODE').val()
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
			Remarks: $('#closeremarks').val(),
			token: token_id,
			solution_id:$('#solution_CODE').val()
		}, function(response) {
			alert(response);
			$('#deleteModal').modal('hide');
			fetchView();

		});

	} else if (closeflag == 'C') {
		$.post('../troubleticket/Close', {
			Remarks: $('#closeremarks').val(),
			token: token_id
		}, function(response) {
			alert(response);
			$('#deleteModal').modal('hide');
			fetchView();

		});

	}

}

function viewdetail(subtokenid) {
	$.get("../troubleticket/gettokenhistory", {
		SUBTOKEN: subtokenid

	}, function(data) {
		//console.log(data);
		// $('#example1').DataTable();
		var table = $('#viewdetailtable').DataTable();

		table.clear().draw();

		$.each(data, function(key, value) {
			var date = new Date(value.CREATE_DT);
			$("#viewdetailtable").dataTable().fnAddData(
				[value.FROM_SUB_TEAM_CODE, value.TO_SUB_TEAM_CODE,
				value.SOLVE_FLAG+'('+value.SOLUTION+')', value.REMARKS, value.CREATE_BY,
					date]);
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
	$.post('../troubleticket/CloseArray', {
		Remarks: $('#closeallremarks').val(),
		tokenarray: JSON.stringify(closeticketarray)
	}, function(response) {
		alert(response);
		$('#deleteallModal').modal('hide');
		fetchView();

	});

}

function Solutionflag(problemid){
	
	var select = $('#solution_CODE');
		select.find('option').remove();
		$('<option>').val("-1").text("Others").appendTo(select);

		$.each(solutionlist, function(key, value) {
			if(problemid==value.PROBLEM_ID)
					$('<option>').val(value.SOLUTION_ID).text(value.DESCRIPTION)
				.appendTo(select);

		
		});
	
}