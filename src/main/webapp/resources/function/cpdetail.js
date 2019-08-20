var CODE;

$(document).ready(function () {
    //$(".js-example-basic-single").select2();
    $('.nepali-calendar').nepaliDatePicker();
    $.fn.dataTable.ext.errMode = 'none';
    $("#QSP_CODE").select2({
        placeholder: "Select a Service Provider Code",
        allowClear: true
    });
    $("#QSERVICE_CODE").select2({
        placeholder: "Select a Service  Code",
        allowClear: true
    });
    

});

function getCpdetailFilterList() {
    jQuery.ajaxSetup({async: false});
    var sp_code = $("#QSP_CODE").val();
    var service = $("#QSERVICE_CODE").val();
    reponse = null;
    $.get('../cpdetail/getCpDetaillist', {SP_CODE: sp_code, SERVICE_CODE: service
    }, function (response) {
        //  alert(JSON.stringify(response));
        if (response !== null) {
            temp = $('#example1').DataTable();
            temp
                    .clear()
                    .draw();
            $.each(response, function (key, value) {
                $("#example1").dataTable().fnAddData([
                    value.CP_CODE,
                    value.SP_CODE,
                    value.SP_NAME,
                    value.SERVICE_CODE,
                    value.CP_NAME,
                    value.ESME_CODE,
                    value.RATE,
                    value.NEP_EFFECTIVE_DT,
                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#myModal" onclick="return addCp(\'' + value.CP_CODE + '\')"> <i class="fa fa-plus"></i> Add </a>',
                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#editModal" onclick="return editCp(\'' + value.CP_CODE + '\')"> <i class="fa fa-edit"></i> Edit </a>',
                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#deleteModal" onclick="return deleteCp(\'' + value.CP_CODE + '\')"> <i class="fa fa-trash"></i> Delete </a>'
                ]);
            }
            );
        }
    });
}

function addCp(code) {
    CODE = code;

    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == code) {
            jQuery.ajaxSetup({async: false});
            $("#CP_CODE").val(row[i][0]);
            $("#SP_CODE").val(row[i][1]);
            $("#SP_NAME").val(row[i][2]);
            $("#SERVICE_CODE").val(row[i][3]);
            $("#CP_NAME").val(row[i][4]);
            $("#ESME_CODE").val(row[i][5]);
            $("#RATE").val(row[i][6]);
            $("#EFFECTIVE_DT").val(row[i][7]);
            jQuery.ajaxSetup({async: true});
        }
    }
}

function saveCpdetail() {
    // debugger;
    if ($('#RATE') == null || $('#RATE') == ""
            || $('#RATE').val().length > 8) {
        alert('Invalid RATE!!!');
        return;
    }
    var CODE = $("#CP_CODE").val();
    var RATE = $("#RATE").val();
    var EFFECTIVE_DT = $("#EFFECTIVE_DT").val();
    
     //alert(RATE+"nabin"+CODE);
    $.post('../cpdetail/saveJS', {
        CP_CODE: CODE,
        RATE: RATE,
        EFFECTIVE_DT: EFFECTIVE_DT        
    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            //location.reload();
            getCpdetailFilterList();
            $('.modal').modal('hide');
        }
    });
}