$(document).ready(function() {
	
	 // -- till here
	 //disable double click 
	 	    $(".submitbutton").on("click", function() {
		        $(this).attr("disabled", "disabled");
		        //auto enable button after delay
		        setTimeout('$(".submitbutton").removeAttr("disabled")', 5000);
		    });
	// -till here disable double click
	
	
	// $(".js-example-basic-single").select2();
	$('.nepali-calendar').nepaliDatePicker();
	$.fn.dataTable.ext.errMode = 'none';

	$("#FAP").select2({
		dropdownParent : $("#myModal"),
		placeholder : "Select FAP"
	// allowClear: false
	});
	$("#fdc").select2({
		dropdownParent : $("#myModal"),
		placeholder : "Select FDC"
	// allowClear: false
	});
	$("#OLTCARD").select2({
		dropdownParent : $("#myModal"),
		placeholder : "Select OLT CARD"
	// allowClear: false
	});
	$("#OLTPORT").select2({
		dropdownParent : $("#myModal"),
		placeholder : "Select OLT PORT"
	// allowClear: false
	});
	$("#ODF").select2({
		dropdownParent : $("#myModal"),
		placeholder : "Select ODF"
	// allowClear: false
	});
	$("#OLT").select2({
		dropdownParent : $("#myModal"),
		placeholder : "Select OLT"
	// allowClear: false
	});

	elementtype();

});

function elementtype() {
	var type = $('#type').val();
	if (type === '1') {
		// type FAP
		$('.divhidden').hide();
		$('#DIVFAP').show();
		$('#DIVFDC').show();
	}
	if (type === '2') {
		// type FDC
		$('.divhidden').hide();
		$('#DIVFDC').show();
	}
	if (type === '3') {
		// type ODF
		$('.divhidden').hide();
		$('#DIVOLT').show();
		$('#DIVODF').show();
	}
	if (type === '6') {
		// type OLT POR
		$('.divhidden').hide();
		$('#DIVOLT').show();
		$('#DIVOLTCARD').show();
		$('#DIVOLTPORT').show();
	}
	if (type === '5') {
		// type OLTCARD
		$('.divhidden').hide();
		$('#DIVOLT').show();
	
		$('#DIVOLTCARD').show();
	}
	if (type === '4') {
		// type OLT
		$('.divhidden').hide();
		$('#DIVOLT').show();
	}

}

function getCardfromOLT(OLT) {
	$.get('../workorder/getCardfromOLT', {
		OLT : OLT

	}, function(data) {
		// console.log(data);
		var select = $('#OLTCARD,#EDITOLTCARD');
		select.find('option').remove();
		$.each(data, function(index, value) {
			$('<option>').val(value).text(value).appendTo(select);

		});
	});

}

function getPortFromCard(OLTCARD) {

	$.get('../workorder/getPortFromCard', {
		OLTCARD : OLTCARD

	}, function(data) {
		// console.log(data);
		var select = $('#OLTPORT,#EDITOLTPORT');
		select.find('option').remove();
		$.each(data, function(index, value) {
			$('<option>').val(value).text(value).appendTo(select);

		});
	});

}

function getODFFromOLT(OLT) {
	$.get('../workorder/getODFFromOLT', {
		OLT : OLT

	}, function(data) {
		// console.log(data);
		var select = $('#ODF,#EDITODF');
		select.find('option').remove();
		$.each(data, function(index, value) {
			$('<option>').val(value).text(value).appendTo(select);

		});
	});

}

function getFAPfromFDC(FDC) {
	$.get('../workorder/getFAPfromFDC', {
		FDC : FDC

	}, function(data) {
		// console.log(data);
		var select = $('#FAP,#EDITFAP');
		select.find('option').remove();
		$.each(data, function(index, value) {
			$('<option>').val(value).text(value).appendTo(select);

		});
	});

}

function saveWorkOrder(){
	var type=$('#type').val();
	var element_value=null;
	var fdc=null;
	var olt=null;
	if(type==='1'){
		element_value=$('#FAP').val();
		fdc=$('#fdc').val();
	}
	if(type==='2'){
		element_value=$('#fdc').val();
		fdc=$('#fdc').val();
	}
	if(type==='3'){
		element_value=$('#ODF').val();
		olt=$('#OLT').val();
	}
	if(type==='4'){
		element_value=$('#OLT').val();
		olt=$('#OLT').val();
	}
	if(type==='5'){
		element_value=$('#OLTCARD').val();
		olt=$('#OLT').val();
	}
	if(type==='6'){
		element_value=$('#OLTPORT').val();
		olt=$('#OLT').val();
	}
	var active_flag='N';
	 if($('#active_flag').prop("checked") == true) {
         active_flag='Y';
       }
	$.post('../workorder/saveworkorder', {
		type : type,
		value : element_value,
		remarks: $('#remarks').val(),
		starttime:$('#starttime').val(),
		active_flag:active_flag,
		fdc: fdc,
		olt: olt
		
		
	}, function(data) {
		alert(data);
		if (data.substring(0, 6) === "Succes") {
			location.reload();
		}

	});

	
}

function saveTeam() {
	// debugger;
	if ($('#TEAM_CODE') == null || $('#TEAM_CODE') == ""
			|| $('#TEAM_CODE').val().length > 8) {
		alert('Invalid TEAM_CODE!!!');
		return;
	}
	var CODE = $("#TEAM_CODE").val();
	var DESCRIPTION = $("#DESCRIPTION").val();

	$.post('../team/saveJS', {
		TEAM_CODE : CODE,
		DESCRIPTION : DESCRIPTION
	}, function(data) {
		alert(data);
		if (data.substring(0, 6) === "Succes") {
			location.reload();
		}

	});

}

var CODE;
function editTeam(code) {
	CODE = code;
	var row = $("#example1").dataTable().fnGetData();
	var l = row.length;
	for (var i = 0; i < l; i++) {
		if (row[i][0] == code) {

			$("#EDITTEAM_CODE").val(row[i][0]);
			$("#EDITDESCRIPTION").val(row[i][1]);

		}
	}
}

function updateTeam() {

	var DESCRIPTION = $("#EDITDESCRIPTION").val();

	$.post('../team/update', {
		TEAM_CODE : CODE,
		DESCRIPTION : DESCRIPTION
	}, function(data) {
		// location.reload();
		alert(data);
		// temp = $('#example1').DataTable();
		// temp.clear().draw();
		if (data.substring(0, 6) === "Succes") {
			location.reload();
		}

	});
}

var CODE;

function deleteTeam(code) {
	CODE = code;
}
function del() {

	$.post('../workorder/complete', {
		ID : CODE,
		endtime : $('#endtime').val(),
		remarks : $('#endremarks').val()
	}, function(data) {
		alert(data);
		location.reload();
		
	});
}


//function for query parameter components

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
	}

}

//  till here function for query p c




function fetchView() {

	var REGION_CODE = $("#REGION_CODE").val();
	var ZONE_CODE = $("#ZONE_CODE").val();
	var DISTRICT_CODE = $("#DISTRICT_CODE").val();
	var OFFICE_CODE = $("#OFFICE_CODE").val();

	var qtype = $('#qtype').val();
	var FRM_DT = $('#QFROM_DT').val();
	var TO_DT = $('#QTO_DT').val();
	var Statusflag = $('#ACTIVE_FLAG').val();

	$
			.get(
					"../workorder/getJSListWorkOrder",
					{
						REGION_CODE : REGION_CODE,
						ZONE_CODE : ZONE_CODE,
						DISTRICT_CODE : DISTRICT_CODE,
						OFFICE_CODE : OFFICE_CODE,
						qtype : qtype,
						QFROM_DT : FRM_DT,
						QTO_DT : TO_DT,
						ACTIVE_FLAG : Statusflag
						
					},
					function(data) {

						

						console.log(data);
						// $('#example1').DataTable();

						var table = $('#example1').DataTable({
							"bDestroy" : true
						});

						table.clear().draw();

						$
								.each(
										data,
										function(key, value) {
											if(Statusflag==='' && value.ACTIVE_FLAG==='C' ){
												//console.log(value);
												return;
												
											}
											
											var STARTTIME = new Date(value.STARTTIME);
													
											
											var ENDTIME = new Date(value.ENDTIME);
											
									
											var solveflag = 'Disabled';
											var edit = '<button type="button" title="Edit" class="btn bg-purple" data-toggle="modal" data-target="#editModal" onclick="editTeam=(\''
												+ value.ID
												+ '\')"> <i class="fa fa-edit"></i>Edit</button>';
											if (value.ACTIVE_FLAG == 'Y') {
												solveflag = '<button type="button" class="btn bg-red" data-toggle="modal" data-target="#deleteModal" onclick="deleteTeam(\''
														+ value.ID
														+ '\')"> <i class="fa fa-trash"></i> Complete</button>';
												
											} else if (value.ACTIVE_FLAG == 'C') {
												solveflag = 'Completed';
												edit='';
											}

											$("#example1")
													.dataTable()
													.fnAddData(
															[
																	value.DESCRIPTION,
																	value.ELEMENT_VALUE,
																	value.REMARKS,
																	STARTTIME,
																	ENDTIME,
																	value.ACTIVE_FLAG,
																	edit,
																	solveflag

															]);
										});

					});

}

