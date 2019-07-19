var CODE;

function deleteRole(code) {
	CODE = code;
}
function del() {

	$.post('../role/delete', {
		ROLE_CODE : CODE
	}, function(data) {
		// location.reload();
		alert(data);
	});
}

var CODE;
function editRole(code) {
	CODE = code;

	$.post('../role/editrole', {
		ROLE_CODE : CODE
	}, function(data) {
		// location.reload();
		alert(JSON.stringify(data));
	});
	
}