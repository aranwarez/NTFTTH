var regionlist;

var USER_LEVEL = parseInt(0);
var REGION_CODE;
var ACC_CEN_CODE;
var CC_CODE;

$(document).ready(function() {
	jQuery.ajaxSetup({
		async : false
	});
	$("#example1").dataTable();
	getRegionData();
});
function RoleControl(level, region, acc, cc) {

	USER_LEVEL = parseInt(level);
	REGION_CODE = region;
	ACC_CEN_CODE = acc;
	CC_CODE = cc;

	if (USER_LEVEL == "1") {
		$("#REGION_CODE").val(REGION_CODE);
		getAccountCenter();
		document.getElementById('REGION_CODE').disabled = false;

	} else if (USER_LEVEL == "2") {
		document.getElementById('REGION_CODE').disabled = true;
		$("#REGION_CODE").val(REGION_CODE);
		getAccountCenter();

	}

}

function getRegionData() {
	jQuery.ajaxSetup({
		async : false
	});

	/*
	 * ---------------------------------------------------------------- level 1
	 * ---------------------------------------------------------------
	 */

	$.get('../region/getlist', {
		getRegionList : "getlist"
	}, function(response) {
		regionlist = response;
		var select = $('#REGION_CODE,#DOREGION_CODE,#EDITREGION_CODE');
		select.find('option').remove();
		$('<option>').val("").text("Select").appendTo(select);
		$.each(response, function(index, value) {
			$('<option>').val(value.region_CODE).text(
					value.description + " (" + value.region_CODE + ")")
					.appendTo(select);
		});
	});

	/*
	 * ----------------------------------------------------------------
	 * 
	 * ---------------------------------------------------------------
	 */

}

function getAccountCenter() {

	var REGION_CODE = $("#REGION_CODE,#DOREGION_CODE,#EDITREGION_CODE").val();

	jQuery.ajaxSetup({
		async : false
	});
	$
			.get(
					'../response/acccenter',
					{
						REGION_CODE : REGION_CODE
					},
					function(responseJson) {
						// alert(JSON.stringify(responseJson));
						if (responseJson !== null) {
							temp = $('#example1').DataTable();
							temp.clear().draw();

							$
									.each(
											responseJson,
											function(key, value) {
												$("#example1")
														.dataTable()
														.fnAddData(
																[
																		value.region_CODE,
																		value.acc_CEN_CODE,
																		value.description,
																		value.erp_ACC_CEN_CD,
																		"<a href = \"#\" data-toggle=\"modal\" data-target=\"#editModal\" title=\"Edit\" onclick=\"return editAccCenter('"
																				+ value.acc_CEN_CODE
																				+ "')\"<span class=\"fa fa-pencil-square-o\"></span></a>",
																		"<a href=\"#\" data-toggle=\"modal\" data-target=\"#deleteModal\"  title=\"Delete\" onclick=\"return delAccCenter('"
																				+ value.acc_CEN_CODE
																				+ "')\"<span class=\"fa fa-trash\"></span></a>" ]); // closing
												// fnAddData()
											}// closing function(key,value)
									); // closing each()
						}// closing if()
					}); // closing function(responseJson)
}

function saveAccCenter() {
	var REGION_CODE = $("#DOREGION_CODE").val();
	if (REGION_CODE) {

		$.post('../acccenter/save', {
			save : "saveAccCenter",
			REGION_CODE : REGION_CODE,
			ACC_CEN_CODE : $('#ACC_CEN_CODE').val(),
			DESCRIPTION : $("#DESCRIPTION").val(),
			ERP_ACC_CEN_CD : $("#ERP_ACC_CEN_CD").val()

		}, function(data) {
			alert(data);

			location.reload();

		});
	}
}
var region_code;
var acc_cen_code;

function editAccCenter(code) {
	acc_cen_code = code;
	alert(acc_cen_code);

	var row = $("#example1").dataTable().fnGetData();
	var l = row.length;
	for (var i = 0; i < l; i++) {
		if (row[i][1] == code) {
			$("#EDITREGION_CODE").val(row[i][0].toString());

			$("#EDITRACC_CEN_CODE").val(row[i][1].toString());
			$("#EDITDESCRIPTION").val(row[i][2].toString());
			$("#EDITERP_ACC_CEN_CD").val(row[i][3].toString());
		}

	}
}
function updateAccCenter() {

	$.post('../acccenter/update', {
		update : "updateAccCenter",
		REGION_CODE : $("#EDITREGION_CODE").val(),
		ACC_CEN_CODE : $("#EDITRACC_CEN_CODE").val(),
		DESCRIPTION : $("#EDITDESCRIPTION").val(),
		ERP_ACC_CEN_CD : $("#EDITERP_ACC_CEN_CD").val()
	}, function(data) {
		alert(data);

		location.reload();
	});
}
function delAccCenter(code) {
	acc_cen_code = code;

}
function del() {

	$.post('../acccenter/delete', {
		ACC_CEN_CODE : acc_cen_code
	}, function(data) {

		alert(data);
		location.reload();

	});
}

function getOpen() {
	$("#DOREGION_CODE").val($("#REGION_CODE").val());

}
