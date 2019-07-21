var CODE;

function deleteRole(code) {
	CODE = code;
}
function del() {

	$.post('../role/delete', {
		ROLE_CODE : CODE
	}, function(data) {
		 location.reload();
		alert(data);
	});
}

var CODE;
function editRole(code) {
	CODE = code;
	var row = $("#example1").dataTable().fnGetData();
	var l = row.length;
	for (var i = 0; i < l; i++) {
		if (row[i][0] == code) {

			$("#EDITROLE_CODE").val(row[i][0]);
			$("#EDITDESCRIPTION").val(row[i][1]);

		}

	}

}
function updateRole() {

	var DESCRIPTION = $("#EDITDESCRIPTION").val();

	$.post('../role/update', {
		ROLE_CODE : CODE,
		DESCRIPTION : DESCRIPTION
	}, function(data) {
		// location.reload();
		alert(data);
		temp = $('#example1').DataTable();
		temp.clear().draw();
	
		location.reload();
	});
}