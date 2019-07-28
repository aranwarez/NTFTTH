/**
 * 
 */
var currentSP;
$(document).ready(function() {

	$('.nepali-calendar').nepaliDatePicker();
	$("#sptarget").dataTable();
    

});

function editSp(SP_CODE) {
	currentSP = SP_CODE;
	$('#SP_CODE').val(SP_CODE);
	$('#eSP_CODE').val(SP_CODE);
	$('.modal-title').html('Service Target Entry for Service Code : '+SP_CODE);
}

function savesptarget() {
	debugger;
	$.post('../sp/savesptargetJS', {
		SP_CODE : currentSP,
		EFFECTIVE_DT : $('#EFFECTIVE_DT').val(),
		REVENUE_TARGET : $('#REVENUE_TARGET').val(),
		MINIMUM_GUARENTEE : $('#MINIMUM_GUARENTEE').val()

	}, function(data) {
		alert(data);
		if (data.substring(0, 6) === "Succes") {
			// location.reload();
			$('#addModal').modal('hide');
		}
	});
}

function getsptargetList(){
	
	 $.get('../sp/getSPTargetList', {SP_CODE: currentSP}, function (response) {
    if (response !== null) {
        temp = $('#sptarget').DataTable();
        temp
                .clear()
                .draw();

        $.each(response, function (key, value) {
            $("#sptarget").dataTable().fnAddData([
                value.EFFECTIVE_DT,
                value.REVENUE_TARGET,
                value.MINIMUM_GUARENTEE,
                value.UPDATE_BY,
                value.UPDATE_DT,
                "<a href = \"#\" data-toggle=\"modal\" data-target=\"#editModal\" title=\"Edit\" onclick=\"return editAccCenter('" + value.TRANS_ID + "')\"<span class=\"fa fa-pencil-square-o\"></span></a>" ,
                        "<a href=\"#\" data-toggle=\"modal\" data-target=\"#deleteModal\"  title=\"Delete\" onclick=\"return delAccCenter('" + value.TRANS_ID + "')\"<span class=\"fa fa-trash\"></span></a>"]); //closing fnAddData()
        }//closing function(key,value)
        ); //closing each()
    }//closing if()
}); //closing function(responseJson)	
	
}