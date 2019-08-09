function getList() {

	jQuery.ajaxSetup({
		async : false
	});
	$.get('../EmployeeServlet', {
		getEmpList : "getlist"
	}, function(response) {

		var select = $('#EMPLOYEE_CODE,#EDITEMPLOYEE_CODE');
		select.find('option').remove();
		$('<option>').val("").text("ALL").appendTo(select);
		$.each(response, function(index, value) {

			$('<option>').val(value.EMPLOYEE_CODE).text(
					value.EMPLOYEE_CODE + " ( " + value.EMPLOYEE_NAME + " )")
					.appendTo(select);
		});
	});

	$.get('../RegionServlet', {
		getRegionList : "getlist"
	}, function(response) {
		var select = $('#REGION_CODE,#EDITREGION_CODE');
		select.find('option').remove();
		$('<option>').val("").text("ALL").appendTo(select);
		$.each(response, function(index, value) {
			$('<option>').val(value.REGION_CODE).text(
					value.REGION_CODE + " ( " + value.DESCRIPTION + " )")
					.appendTo(select);
		});
	});

	$.get('../RoleServlet', {
		getRoleList : "getlist"
	}, function(response) {
		var select = $('#ROLE_CODE,#EDITROLE_CODE');
		select.find('option').remove();
		$('<option>').val("").text("ALL").appendTo(select);
		$.each(response, function(index, value) {
			$('<option>').val(value.ROLE_CODE).text(
					value.ROLE_CODE + " ( " + value.DESCRIPTION + " )")
					.appendTo(select);
		});
	});

	$("#LOCATION_CODE").combobox();
	$("#EMPLOYEE_CODE").combobox();

}
function getHold(useid) {

}
function editUser(userid) {

}
function deleteUser(){
	
}