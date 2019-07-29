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
    $("#SP_CODE").select2({
        dropdownParent: $("#myModal"),
        placeholder: "Select a Service Provider Code"
                // allowClear: false
    });

    $("#SERVICE_CODE").select2({
        dropdownParent: $("#myModal"),
        placeholder: "Select Service Code"
    });

});

function saveCp() {
    // debugger;
    if ($('#CP_CODE') == null || $('#CP_CODE') == ""
            || $('#CP_CODE').val().length > 8) {
        alert('Invalid CP Type!!!');
        return;
    }
    var CODE = $("#CP_CODE").val();
    var SP_CODE = $("#SP_CODE").val();
    var SERVICE_CODE = $("#SERVICE_CODE").val();
    var CP_NAME = $("#CP_NAME").val();
    var ESME_CODE = $("#ESME_CODE").val();
    var SRV_FOR = $("#SRV_FOR").val();
    var PACKAGE_TYPE = $("#PACKAGE_TYPE").val();
    var STREAM_TYPE = $("#STREAM_TYPE").val();
    var START_DT = $("#START_DT").val();
    var END_DT = $("#END_DT").val();
    var SHARING_TYPE = $("#SHARING_TYPE").val();
    var SHARE_NT_PER = $("#SHARE_NT_PER").val();
    var AFS_FLAG = $("#AFS_FLAG").val();
    var MIN_QTY = $("#MIN_QTY").val();
    // alert("nabin"+SHORT_CODE);
    $.post('../cp/saveJS', {
        CP_CODE: CODE,
        SP_CODE: SP_CODE,
        SERVICE_CODE: SERVICE_CODE,
        CP_NAME: CP_NAME,
        ESME_CODE: ESME_CODE,
        SRV_FOR: SRV_FOR,
        PACKAGE_TYPE: PACKAGE_TYPE,
        STREAM_TYPE: STREAM_TYPE,
        START_DT: START_DT,
        END_DT: END_DT,
        SHARING_TYPE: SHARING_TYPE,
        SHARE_NT_PER: SHARE_NT_PER,
        AFS_FLAG: AFS_FLAG,
        MIN_QTY: MIN_QTY
    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            location.reload();
        }
    });
}

function editCp(code) {
    CODE = code;

    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == code) {
            jQuery.ajaxSetup({async: false});

            getSPServiceList(row[i][1]);

            $("#EDITCP_CODE").val(row[i][0]);
            $("#EDITSP_CODE").val(row[i][1]);
            $("#EDITSERVICE_CODE").val(row[i][3]);
            $("#EDITCP_NAME").val(row[i][4]);
            $("#EDITESME_CODE").val(row[i][5]);
            $("#EDITSRV_FOR").val(row[i][6]);
            $("#EDITPACKAGE_TYPE").val(row[i][7]);
            $("#EDITSTREAM_TYPE").val(row[i][8]);
            $("#EDITSTART_DT").val(row[i][9]);
            $("#EDITEND_DT").val(row[i][10]);
            $("#EDITSHARING_TYPE").val(row[i][11]);
            $("#EDITSHARE_NT_PER").val(row[i][12]);
            $("#EDITAFS_FLAG").val(row[i][13]);
            $("#EDITMIN_QTY").val(row[i][14]);
            jQuery.ajaxSetup({async: true});

        }
    }
}

function updateCp() {

    var SP_CODE = $("#EDITSP_CODE").val();
    var SERVICE_CODE = $("#EDITSERVICE_CODE").val();
    var CP_NAME = $("#EDITCP_NAME").val();
    var ESME_CODE = $("#EDITESME_CODE").val();
    var SRV_FOR = $("#EDITSRV_FOR").val();
    var PACKAGE_TYPE = $("#EDITPACKAGE_TYPE").val();
    var STREAM_TYPE = $("#EDITSTREAM_TYPE").val();
    var START_DT = $("#EDITSTART_DT").val();
    var END_DT = $("#EDITEND_DT").val();
    var SHARING_TYPE = $("#EDITSHARING_TYPE").val();
    var SHARE_NT_PER = $("#EDITSHARE_NT_PER").val();
    var AFS_FLAG = $("#EDITAFS_FLAG").val();
    var MIN_QTY = $("#EDITMIN_QTY").val();
    $.post('../cp/update', {
        CP_CODE: CODE,
        SP_CODE: SP_CODE,
        SERVICE_CODE: SERVICE_CODE,
        CP_NAME: CP_NAME,
        ESME_CODE: ESME_CODE,
        SRV_FOR: SRV_FOR,
        PACKAGE_TYPE: PACKAGE_TYPE,
        STREAM_TYPE: STREAM_TYPE,
        START_DT: START_DT,
        END_DT: END_DT,
        SHARING_TYPE: SHARING_TYPE,
        SHARE_NT_PER: SHARE_NT_PER,
        AFS_FLAG: AFS_FLAG,
        MIN_QTY: MIN_QTY
    }, function (data) {
        // location.reload();
        alert(data);
        // temp = $('#example1').DataTable();
        // temp.clear().draw();
        if (data.substring(0, 6) === "Succes") {
            location.reload();
        }

    });
}

function deleteCp(code) {
    CODE = code;
}

function del() {

    $.post('../cp/delete', {
        CP_CODE: CODE
    }, function (data) {
        location.reload();
        alert(data);
    });
}

function getSPServiceList(SP_CODE) {
    var sp_code
    //debugger;
    if (SP_CODE != null) {
        sp_code = SP_CODE;
    } else {
        sp_code = $('#SP_CODE').val();
    }
    $.get('../sp/getSPServiceList', {SP_CODE: sp_code
    }, function (response) {
        var select = $('#SERVICE_CODE,#EDITSERVICE_CODE');
        select.find('option').remove();
        $('<option>').val("").text("SELECT").appendTo(select);
        $.each(response, function (index, value) {
            $('<option>').val(value.SERVICE_CODE).text(value.SERVICE).appendTo(
                    select);

        });

    });

}
function getCpFilterList() {
    jQuery.ajaxSetup({async: false});
    var sp_code = $("#QSP_CODE").val();
    var service = $("#QSERVICE_CODE").val();
    reponse = null;
    $.get('../cp/getCplist', {SP_CODE: sp_code, SERVICE_CODE: service
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
                    value.SRV_FOR,
                    value.PACKAGE_TYPE,
                    value.STREAM_TYPE,
                    value.START_DT,
                    value.END_DT,
                    value.SHARING_TYPE,
                    value.SHARE_NT_PER,
                    value.AFS_FLAG,
                    value.MIN_QTY,
                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#editModal" onclick="return editCp(\''+value.CP_CODE+'\')"> <i class="fa fa-edit"></i> Edit </a>',
                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#deleteModal" onclick="return deleteCp(\''+value.CP_CODE+'\')"> <i class="fa fa-trash"></i> Delete </a>'
                ]);
            }
            );
        }
    });

}