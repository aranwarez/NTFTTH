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
		ContactNo: CONTACT_NO,
		OFFICE_NAME: $("#OFFICE_NAME").val(),
		AREA_NAME:  $("#AREA_NAME").val(),
		SECTION: $("#SECTION").val(),
		LANDLINE_NO: $("#LANDLINE_NO").val(),
		MOBILE_NO: $("#MOBILE_NO").val(),
		REMARKS1: $("#REMARKS1").val(),
		REMARKS2: $("#REMARKS2").val()
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

			$("#eOFFICE_NAME").val(row[i][1]);
			$("#eAREA_NAME").val(row[i][2]);
			$("#eSECTION").val(row[i][5]);
			$("#eLANDLINE_NO").val(row[i][6]);
			$("#eMOBILE_NO").val(row[i][7]);
			$("#eREMARKS1").val(row[i][8]);
			$("#eREMARKS2").val(row[i][9]);


			$("#eDEPARTMENT").val(row[i][3]);
			$("#eCONTACTNO").val(row[i][4]);

		}
	}
}

function updateTeam() {

	var DESCRIPTION = $("#eDEPARTMENT").val();

	var eCONTACTNO = $("#eCONTACTNO").val();

	$.post('../department/update', {
		ID: CODE,
		Department: DESCRIPTION,
		ContactNo: eCONTACTNO,
		OFFICE_NAME: $("#eOFFICE_NAME").val(),
		AREA_NAME: $("#eAREA_NAME").val(),
		SECTION: $("#eSECTION").val(),
		LANDLINE_NO: $("#eLANDLINE_NO").val(),
		MOBILE_NO: $("#eMOBILE_NO").val(),
		REMARKS1: $("#eREMARKS1").val(),
		REMARKS2: $("#eREMARKS2").val()
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

