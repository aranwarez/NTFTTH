function saveRegion() {

	$.post('../region/save', {
		REGION_CODE : $("#REGION_CODE").val(),
		DESCRIPTION : $("#DESCRIPTION").val()

	}, function(data) {
		alert(data);

		location.reload();

	});
}

var region_code;
function editRegion(code) {
	region_code = code;
	var row = $("#example1").dataTable().fnGetData();
	var l = row.length;
	for (var i = 0; i < l; i++) {
		if (row[i][1] == code) {
			$("#EDITREGION_CODE").val(row[i][1].toString());
			$("#EDITDESCRIPTION").val(row[i][2].toString());
		}

	}
}
function updateRegion() {
	$.post('../region/update', {
		REGION_CODE : $("#EDITREGION_CODE").val(),
		DESCRIPTION : $("#EDITDESCRIPTION").val()
	}, function(data) {
		alert(data);
		location.reload();

	});
}
function delRegion(code) {
	region_code = code;

}

function del() {
	$.post('../region/delete', {
		REGION_CODE : region_code
	}, function(data) {
		alert(data);
		location.reload();

	});
}