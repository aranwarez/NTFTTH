function saveDepartment() {
	//debugger;
	if ($('#DEPARTMENT') == null || $('#DEPARTMENT') == "") {
		alert('Invalid Department Name!!!');
		return;
	}
	var CODE = $("#DEPARTMENT").val();
	var CONTACT_NO = $("#CONTACT_NO").val();


	$.post('../department/saveJS', {
		DEPARTMENT_CODE: CODE,
		ContactNo: CONTACT_NO
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

			$("#eDEPARTMENT").val(row[i][1]);
			$("#eCONTACTNO").val(row[i][2]);

		}
	}
}

function updateTeam() {

	var DESCRIPTION = $("#eDEPARTMENT").val();

	var eCONTACTNO = $("#eCONTACTNO").val();

	$.post('../department/update', {
		ID: CODE,
		Department: DESCRIPTION,
		ContactNo: eCONTACTNO
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

var CODE;

function deleteTeam(code) {
	CODE = code;
}
function del() {

	$.post('../department/delete', {
		ID: CODE
	}, function(data) {
		location.reload();
		alert(data);
	});
}

