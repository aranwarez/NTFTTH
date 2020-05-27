function saveService() {
    //debugger;
    if ($('#SERVICE_CODE') == null || $('#SERVICE_CODE') == "" || $('#SERVICE_CODE').val().length > 8) {
        alert('Invalid Service Code!!!');
        return;
    }
    var CODE = $("#SERVICE_CODE").val();
    var DESCRIPTION = $("#DESCRIPTION").val();
    var SLDG_CODE = $("#SLDG_CODE").val();
    var DATA_IMPORT = $("#DATA_IMPORT").val();
    var ACTIVE_FLAG = $("#ACTIVE_FLAG").val();

    $.post('../service/saveJS', {
        SERVICE_CODE: CODE,
        DESCRIPTION: DESCRIPTION, SLDG_CODE: SLDG_CODE, DATA_IMPORT: DATA_IMPORT, ACTIVE_FLAG: ACTIVE_FLAG
    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            location.reload();
        }


    });



}

var CODE;
function editService(code) {
    CODE = code;
    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == code) {

            $("#EDITSERVICE_CODE").val(row[i][0]);
            $("#EDITDESCRIPTION").val(row[i][1]);
            $("#EDITSLDG_CODE").val(row[i][2]);
            $("#EDITDATA_IMPORT").val(row[i][3]);
            $("#EDITACTIVE_FLAG").val(row[i][4]);
        }
    }
}

function updateService() {

    var DESCRIPTION = $("#EDITDESCRIPTION").val();
    var SLDG_CODE = $("#EDITSLDG_CODE").val();
    var DATA_IMPORT = $("#EDITDATA_IMPORT").val();
    var ACTIVE_FLAG = $("#EDITACTIVE_FLAG").val();

    $.post('../service/update', {
        SERVICE_CODE: CODE,
        DESCRIPTION: DESCRIPTION, SLDG_CODE: SLDG_CODE, DATA_IMPORT: DATA_IMPORT, ACTIVE_FLAG: ACTIVE_FLAG
    }, function (data) {
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

function deleteService(code) {
    CODE = code;
}
function del() {

    $.post('../service/delete', {
        SERVICE_CODE: CODE
    }, function (data) {
    	alert(data);
        location.reload();
        
    });
}

