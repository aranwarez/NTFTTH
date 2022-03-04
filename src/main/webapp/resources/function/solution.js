var problemlist;
var CODE;

$(document).ready(function() {

	$('.nepali-calendar').nepaliDatePicker();

	getServiceTypeList();


	$.get('../problem/JSlist', {
	}, function(response) {
		problemlist = response;
	});

});



function getServiceTypeList() {

	$.get('../serviceType/jsonlist', {
		getlist: "servicelist"
	}, function(response) {
		var select = $('#SERVICE_TYPE_ID,#EDITSERVICE_TYPE_ID');
		select.find('option').remove();
		$('<option>').val("").text("SELECT Service Type").appendTo(select);
		$.each(response, function(index, value) {
			$('<option>').val(value.SERVICE_TYPE_ID).text(value.DESCRIPTION).appendTo(
				select);

		});

	});
}

function saveProblem() {
	
	var DESCRIPTION = $("#DESCRIPTION").val();
	var SERVICE_TYPE_ID = $("#SERVICE_TYPE_ID").val();
	var ACTIVE_STATUS = $("#ACTIVE_STATUS").val();



	$.post('../solution/saveJS', {
		DESCRIPTION: DESCRIPTION,
		SERVICE_TYPE_ID: SERVICE_TYPE_ID,
		ACTIVE_STATUS: ACTIVE_STATUS,
		PROBLEM_ID:$('#PROBLEM_CODE').val()
	}, function(data) {
		alert(data);
		if (data.substring(0, 6) === "Succes") {
			location.reload();
		}
	});
}

function editProblem(code, SERVICE_TYPE_ID,PROBLEM_ID) {
	CODE = code;
	
	var row = $("#example1").dataTable().fnGetData();
	var l = row.length;
	for (var i = 0; i < l; i++) {
		if (row[i][0] == code) {

			$("#EDITDESCRIPTION").val(row[i][1]);
			$("#EDITSERVICE_TYPE_ID").val(SERVICE_TYPE_ID);
			loadproblem(SERVICE_TYPE_ID);
			$('#ePROBLEM_CODE').val(PROBLEM_ID)

			//        	 $("#EDITSUB_TEAM_CODE").val();


			

			$("#EDITACTIVE_STATUS").val(row[i][4]);



		}
	}
}

function updateProblem() {

	var DESCRIPTION = $("#EDITDESCRIPTION").val();
	var SERVICE_TYPE_ID = $("#EDITSERVICE_TYPE_ID").val();

	
	var ACTIVE_STATUS = $("#EDITACTIVE_STATUS").val();
	
 
	$.post('../solution/update', {
		PROBLEM_ID: $("#ePROBLEM_CODE").val(),
		DESCRIPTION: DESCRIPTION,
		SERVICE_TYPE_ID: SERVICE_TYPE_ID,
		ACTIVE_STATUS: ACTIVE_STATUS,
		SOLUTION_ID:CODE
		
	}, function(data) {
		// location.reload();
		alert(data);
		//        temp = $('#example1').DataTable();
		//        temp.clear().draw();
		if (data.substring(0, 6) === "Succes") {
			location.reload();
		}


	});
}

function deleteProblem(code) {
	CODE = code;
}
function del() {

	$.post('../solution/delete', {
		PROBLEM_ID: CODE
	}, function(data) {
		location.reload();
		alert(data);
	});
}

function loadproblem(service) {
	var select = $('#PROBLEM_CODE,#ePROBLEM_CODE');
	select.find('option').remove();


	$.each(problemlist, function(index, value) {
	
		if (service == value.SERVICE_TYPE_ID)
			$('<option>').val(value.PROBLEM_ID).text(value.DESCRIPTION).appendTo(
				select);

	});

	
}



