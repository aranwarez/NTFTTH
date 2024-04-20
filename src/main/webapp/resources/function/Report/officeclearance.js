
$(document)
		.ready(
				function() {
					// $(".js-example-basic-single").select2();
					$('.nepali-calendar').nepaliDatePicker();

					

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


