var pk_id;
var templist;
var glbUser;
$(document).ready(function() {

	$('#checkDatatable').dataTable({
		//		"paging" : false,
	//	"searching": false,
		"info": false,
		"ordering": false,
		"fixedHeader": true,
	});


	$.fn.dataTable.ext.errMode = 'none';
	//viewUser(null,null,null,null);



});


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

		if (data.length == 0 || data.length == undefined) {

			return;
		}

		$.each(data, function(index, value) {
			$('<option>').val(value.ZONE_CODE).text(value.DESCRIPTION).appendTo(
				select);

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
			$('<option>').val(value.DISTRICT_CODE).text(value.DESCRIPTION).appendTo(
				select);

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


			return;
		}

		$.each(data, function(index, value) {
			$('<option>').val(value.OFFICE_CODE).text(value.DESCRIPTION).appendTo(
				select);

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


			return;
		}

		$.each(data, function(index, value) {
			$('<option>').val(value.OLT_CODE).text(value.DESCRIPTION + "-" + value.OLT_CODE).appendTo(
				select);

		});


	});

}
function fetchView() {


	//	var FDC_CODE=$("#FDC_CODE").val();	
	var REGION_CODE = $("#REGION_CODE").val();
	var ZONE_CODE = $("#ZONE_CODE").val();
	var DISTRICT_CODE = $("#DISTRICT_CODE").val();
	var OFFICE_CODE = $("#OFFICE_CODE").val();
	var OLT_CODE = $("#OLT_CODE").val();


	viewFDC(REGION_CODE, ZONE_CODE, DISTRICT_CODE, OFFICE_CODE, OLT_CODE);




}



function viewFDC(REGION_CODE, ZONE_CODE, DISTRICT_CODE, OFFICE_CODE, OLT_CODE) {


	$.get("../getOLTContacts", {
		Region: REGION_CODE,
		zone: ZONE_CODE,
		district: DISTRICT_CODE,
		OFFICE_CODE: OFFICE_CODE,

	}, function(data) {

		templist = data;

		var t = $('#checkDatatable').DataTable();
		t.clear().draw();

		if (data.length == 0 || data.length == undefined) {

			return;
		}

		$.each(data, function(index, value) {

			var edit = '<button type="button" title="Edit" class="btn bg-purple" data-toggle="modal" data-target="#editModal" onclick=editOLTContact(' + value.PK_ID + ')> <i class="fa fa-edit"></i>Edit</button>';

			var deletes = '<button type="button" title="Delete" class="btn btn-danger" data-toggle="modal" data-target="#deleteModal" onclick=deleteOLTContact(' + value.PK_ID + ')> <i class="fa fa-edit"></i>Delete</button>';




			$("#checkDatatable")
				.dataTable()
				.fnAddData(
					[	value.REGION,
					value.DISTRICT,
					value.OFFICE,
						value.OLT,
						value.CONTACT_NO,
						value.CONTACT_NAME,
						edit,
						deletes

					]);










		});


	});
}

function saveOLTCONTACT() {
	if ($("#OLT_CODE").val().length <= 0) {
		alert('Please select OLT from drop down');
		return false;
	}



	$.post("../savecontactolt", {
		OLTCODE: $("#OLT_CODE").val(),
		ContactNo: $("#CONTACT_NO").val(),
		ContactName: $("#CONTACT_NAME").val()
	}, function(data) {
		alert(data);
		fetchView();
		// getAllMenu();

	});

}

function editOLTContact(PKID) {
	pk_id=PKID;
	$.each(templist, function(index, value) {
		if (value.PK_ID == PKID) {
			$('#eolttitlelable').html(value.OLT);
			$('#eCONTACT_NO').val(value.CONTACT_NO);
			$('#eCONTACT_NAME').val(value.CONTACT_NAME);

		}
	});


}


function updateOLTCONTACT() {

	$.post("../updateoltcontact", {
		OLTCODE: pk_id,
		ContactNo: $("#eCONTACT_NO").val(),
		ContactName: $("#eCONTACT_NAME").val()
	}, function(data) {
		alert(data);
		// getAllMenu();
		fetchView();

	});

}

function deleteOLTContact(OLT){
	pk_id=OLT;
}


function del() {

	$.post("../deleteoltcontact", {
		OLTCODE: pk_id
		
	}, function(data) {
		alert(data);
		// getAllMenu();
		fetchView();

	});

}

deleteOLTContact



function loadLevelWise(level) {
	if (level == '1') {
		return;
	}
	else if (level == '2') {
		getZone();
		return;
	}
	else if (level == '3') {
		getDistrict();
		return;

	}
	else if (level == '4') {
		getOffice();
		return;
	}
	else {
		getOLT();
		return;
	}

}