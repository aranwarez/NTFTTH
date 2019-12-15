function savePackage() {
    //debugger;
    if ($('#PACKAGE_TYPE') == null || $('#PACKAGE_TYPE') == "" || $('#PACKAGE_TYPE').val().length > 10) {
        alert('Invalid Package Type!!!');
        return;
    }
    var CODE = $("#PACKAGE_TYPE").val();
    var DESCRIPTION = $("#DESCRIPTION").val();
    
    $.post('../package/saveJS', {
        PACKAGE_TYPE: CODE,
        DESCRIPTION: DESCRIPTION,
        SERVICE_CODE: $('#SERVICE_CODE').val()
    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            location.reload();
        }


    });



}

var CODE;
function editPackage(code) {
    CODE = code;
    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == code) {

            $("#EDITPACKAGE_TYPE").val(row[i][0]);
            $("#EDITDESCRIPTION").val(row[i][1]);
            $('#EDITSERVICE_CODE').val(row[i][2]);
        }
    }
}

function updatePackage() {

    var DESCRIPTION = $("#EDITDESCRIPTION").val();
    
    $.post('../package/update', {
        PACKAGE_TYPE: CODE,
        DESCRIPTION: DESCRIPTION,
        SERVICE_CODE: $('#EDITSERVICE_CODE').val()
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

function deletePackage(code) {
    CODE = code;
}
function del() {

    $.post('../package/delete', {
        PACKAGE_TYPE: CODE
    }, function (data) {
        location.reload();
        alert(data);
    });
}

