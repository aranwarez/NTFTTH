var CODE;

$(document).ready(function () {
    //$(".js-example-basic-single").select2();
    $('.nepali-calendar').nepaliDatePicker();
    $.fn.dataTable.ext.errMode = 'none';
    $("#QSERVICE_CODE").select2({
        placeholder: "Select a Service  Code",
        allowClear: true
    });
    $("#QPACKAGE_TYPE").select2({
        placeholder: "Select Package",
        allowClear: true
    });
    $("#SERVICE_CODE").select2({
        dropdownParent: $("#myModal"),
        placeholder: "Select Service Code"
                // allowClear: false
    });

    $("#PACKAGE_TYPE").select2({
        dropdownParent: $("#myModal"),
        placeholder: "Select Package"
    });
    getWapapptariffFilterList();

});

function saveWapapptariff() {
    // debugger;
    if ($('#SERVICE_CODE') == null || $('#SERVICE_CODE') == ""
            || $('#SERVICE_CODE').val().length > 8) {
        alert('Invalid SERVICE!!!');
        return;
    }
    //var CODE = $("#TRANS_ID").val();
    var SERVICE_CODE = $("#SERVICE_CODE").val();
    var PACKAGE_TYPE = $("#PACKAGE_TYPE").val();
    var RATE = $("#RATE").val();
    var EFFECTIVE_DT = $("#EFFECTIVE_DT").val();
    var RANGE_FROM = $("#RANGE_FROM").val();
    var RANGE_TO = $("#RANGE_TO").val();
    var MO_MT_RATIO = $("#MO_MT_RATIO").val();

    // alert("nabin"+SHORT_CODE);
    $.post('../wapapptariff/saveJS', {
        SERVICE_CODE: SERVICE_CODE,
        PACKAGE_TYPE: PACKAGE_TYPE,
        RATE: RATE,
        EFFECTIVE_DT: EFFECTIVE_DT,
        RANGE_FROM: RANGE_FROM,
        RANGE_TO: RANGE_TO,
        MO_MT_RATIO: MO_MT_RATIO
    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            //location.reload();
            getWapapptariffFilterList();
            $('.modal').modal('hide');
        }
    });
}

function editWapapptariff(code) {
    CODE = code;

    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == code) {
            jQuery.ajaxSetup({async: false});

            //getSPServiceList(row[i][1]);

            $("#EDITTRANS_ID").val(row[i][0]);
            $("#EDITSERVICE_CODE").val(row[i][1]);
            $("#EDITPACKAGE_TYPE").val(row[i][2]);
            $("#EDITRATE").val(row[i][3]);
            $("#EDITEFFECTIVE_DT").val(row[i][4]);
            $("#EDITRANGE_FROM").val(row[i][5]);
            $("#EDITRANGE_TO").val(row[i][6]);
            $("#EDITMO_MT_RATIO").val(row[i][7]);
            jQuery.ajaxSetup({async: true});

        }
    }
}

function updateWapapptariff() {
    var TRANS_ID = $("#EDITTRANS_ID").val();
    var SERVICE_CODE = $("#EDITSERVICE_CODE").val();
    var PACKAGE_TYPE = $("#EDITPACKAGE_TYPE").val();
    var RATE = $("#EDITRATE").val();
    var EFFECTIVE_DT = $("#EDITEFFECTIVE_DT").val();
    var RANGE_FROM = $("#EDITRANGE_FROM").val();
    var RANGE_TO = $("#EDITRANGE_TO").val();
    var MO_MT_RATIO = $("#EDITMO_MT_RATIO").val();
    $.post('../wapapptariff/update', {
        TRANS_ID: TRANS_ID,
        SERVICE_CODE: SERVICE_CODE,
        PACKAGE_TYPE: PACKAGE_TYPE,
        RATE: RATE,
        EFFECTIVE_DT: EFFECTIVE_DT,
        RANGE_FROM: RANGE_FROM,
        RANGE_TO: RANGE_TO,
        MO_MT_RATIO: MO_MT_RATIO
    }, function (data) {
        // location.reload();
        alert(data);
        // temp = $('#example1').DataTable();
        // temp.clear().draw();
        if (data.substring(0, 6) === "Succes") {
            //location.reload();
            getWapapptariffFilterList();
            $('.modal').modal('hide');
        }

    });
}

function deleteWapapptariff(code) {
    CODE = code;
}

function del() {

    $.post('../wapapptariff/delete', {
        TRANS_ID: CODE
    }, function (data) {
        //location.reload();
        getWapapptariffFilterList();
        $('.modal').modal('hide');
        alert(data);
    });
}


function getWapapptariffFilterList() {
    jQuery.ajaxSetup({async: false});
    var service_code = $("#QSERVICE_CODE").val();
    var package_type = $("#QPACKAGE_TYPE").val();
    reponse = null;
    $.get('../wapapptariff/getWapapptarifflist', {SERVICE_CODE: service_code, PACKAGE_TYPE: package_type
    }, function (response) {
        //  alert(JSON.stringify(response));
        if (response !== null) {
            temp = $('#example1').DataTable();
            temp
                    .clear()
                    .draw();
            $.each(response, function (key, value) {
                $("#example1").dataTable().fnAddData([
                    value.TRANS_ID,
                    value.SERVICE_CODE,
                    value.PACKAGE_TYPE,
                    value.RATE,
                    value.NEP_EFFECTIVE_DT,
                    value.RANGE_FROM,
                    value.RANGE_TO,
                    value.MO_MT_RATIO,
                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#editModal" onclick="return editWapapptariff(\'' + value.TRANS_ID + '\')"> <i class="fa fa-edit"></i> Edit </a>',
                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#deleteModal" onclick="return deleteWapapptariff(\'' + value.TRANS_ID + '\')"> <i class="fa fa-trash"></i> Delete </a>'
                ]);
            }
            );
        }
    });

}