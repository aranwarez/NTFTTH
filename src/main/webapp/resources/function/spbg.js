var CODE;

$(document).ready(function () {
    //$(".js-example-basic-single").select2();
    $('.nepali-calendar').nepaliDatePicker();
    $.fn.dataTable.ext.errMode = 'none';
    $("#QSP_CODE").select2({
        placeholder: "Select a Service Provider Code",
                 allowClear: true
    });
    $("#QTRANS_CD").select2({
        placeholder: "Select a Trans Code",
                 allowClear: true
    });
    
    $("#TRANS_CD").select2({
        dropdownParent: $("#myModal"),
        placeholder: "Select a Trans Code"
                // allowClear: false
    });
    
    $("#SP_CODE").select2({
        dropdownParent: $("#myModal"),
        placeholder: "Select a Service Provider Code"
                // allowClear: false
    });
    
    $("#BANK_CD").select2({
        dropdownParent: $("#myModal"),
        placeholder: "Select a Bank Code"
                // allowClear: false
    });
    
});

function getSpbgFilterList() {
    jQuery.ajaxSetup({async: false});
    var sp_code = $("#QSP_CODE").val();
    var from_dt = $("#QFROM_DT").val();
    var to_dt = $("#QTO_DT").val();
    var post_flag = $("#QPOST_FLAG").val();
    reponse = null;
    $.get('../spbg/getSpbglist', {SP_CODE: sp_code, FROM_DT: from_dt, TO_DT: to_dt, POST_FLAG: post_flag
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
                    value.TRANS_CD,
                    value.SP_CODE,
                    value.SP_NAME,
                    value.NEP_TRANS_DT,
                    value.BANK_CD,
                    value.NEP_BANK_GUARENTEE_DATE,
                    value.NEP_BANK_VALIDITY_DATE,
                    value.AMT,
                    value.REMARKS,
                    value.POST_FLAG,
                    value.POST_DT,
                    value.POST_BY,
                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#editModal" onclick="return editSpbg(\''+value.TRANS_ID+'\')"> <i class="fa fa-edit"></i> Edit </a>',
                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#deleteModal" onclick="return deleteSpbg(\''+value.TRANS_ID+'\')"> <i class="fa fa-trash"></i> Delete </a>'
                ]);
            }
            );
        }
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

function saveSpbg() {
    // debugger;
    if ($('#SP_CODE') == null || $('#SP_CODE') == ""
            || $('#SP_CODE').val().length > 8) {
        alert('Invalid SP Code!!!');
        return;
    }
    var CODE = $("#TRANS_ID").val();
    var TRANS_DT = $("#TRANS_DT").val();
    var TRANS_CD = $("#TRANS_CD").val();
    var SP_CODE = $("#SP_CODE").val();
    var BANK_CD = $("#BANK_CD").val();
    var AMT = $("#AMT").val();
    var BANK_GUARENTEE_DATE = $("#BANK_GUARENTEE_DATE").val();
    var BANK_VALIDITY_DATE = $("#BANK_VALIDITY_DATE").val();
    var REMARKS = $("#REMARKS").val();
    // alert("nabin"+SHORT_CODE);
    $.post('../spbg/saveJS', {
        TRANS_ID: CODE,
        TRANS_DT: TRANS_DT,
        TRANS_CD: TRANS_CD,
        SP_CODE: SP_CODE,
        BANK_CD: BANK_CD,
        AMT: AMT,
        BANK_GUARENTEE_DATE: BANK_GUARENTEE_DATE,
        BANK_VALIDITY_DATE: BANK_VALIDITY_DATE,
        REMARKS: REMARKS
    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            location.reload();
        }
    });
}

function editSpbg(code) {
    CODE = code;

    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == code) {
            jQuery.ajaxSetup({async: false});
            //getSPServiceList(row[i][1]);
            $("#EDITTRANS_ID").val(row[i][0]);
            $("#EDITTRANS_CD").val(row[i][1]);
            $("#EDITSP_CODE").val(row[i][2]);
            $("#EDITTRANS_DT").val(row[i][4]);
            $("#EDITBANK_CD").val(row[i][5]);
            $("#EDITBANK_GUARENTEE_DATE").val(row[i][6]);
            $("#EDITBANK_VALIDITY_DATE").val(row[i][7]);
            $("#EDITAMT").val(row[i][8]);
            $("#EDITREMARKS").val(row[i][9]);
            jQuery.ajaxSetup({async: true});
        }
    }
}

function updateSpbg() {
    
    
    var TRANS_DT = $("#EDITTRANS_DT").val();
    var TRANS_CD = $("#EDITTRANS_CD").val();
    var SP_CODE = $("#EDITSP_CODE").val();
    var BANK_CD = $("#EDITBANK_CD").val();
    var AMT = $("#EDITAMT").val();
    var BANK_GUARENTEE_DATE = $("#EDITBANK_GUARENTEE_DATE").val();
    var BANK_VALIDITY_DATE = $("#EDITBANK_VALIDITY_DATE").val();
    var REMARKS = $("#EDITREMARKS").val();
    $.post('../spbg/update', {
        TRANS_ID: CODE,
        TRANS_DT: TRANS_DT,
        TRANS_CD: TRANS_CD,
        SP_CODE: SP_CODE,
        BANK_CD: BANK_CD,
        AMT: AMT,
        BANK_GUARENTEE_DATE: BANK_GUARENTEE_DATE,
        BANK_VALIDITY_DATE: BANK_VALIDITY_DATE,
        REMARKS: REMARKS
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

function deleteSpbg(code) {
    CODE = code;
}

function del() {

    $.post('../spbg/delete', {
        TRANS_ID: CODE
    }, function (data) {
        location.reload();
        alert(data);
    });
}