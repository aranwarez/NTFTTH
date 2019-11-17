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
    $("#EDITSP_CODE").select2({
        dropdownParent: $("#editModal"),
        placeholder: "Select a Service Provider Code"
                // allowClear: false
    });


    $("#SERVICE_CODE").select2({
        dropdownParent: $("#myModal"),
        placeholder: "Select Service Code"
    });

//    $("#EDITSERVICE_CODE").select2({
//        dropdownParent: $("#editModal"),
//        placeholder: "Select Service Code"
//    });
    $('#example1').DataTable().column(8).visible(false);
    $('#example1').DataTable().column(17).visible(false);
    $('#example1').DataTable().column(18).visible(false);
    $('#example1').DataTable().column(19).visible(false);

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
    var CATEGORY_MAP = $("#CATEGORY_MAP").val();
    var ESME_CODE_MAP = $("#ESME_CODE_MAP").val();
    var RENTAL_ITEM_CODE = $("#RENTAL_ITEM_CODE").val();
    var VPN_ITEM_CODE = $("#VPN_ITEM_CODE").val();
    var SPACE_ITEM_CODE = $("#SPACE_ITEM_CODE").val();
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
        MIN_QTY: MIN_QTY,
        CATEGORY_MAP: CATEGORY_MAP,
        ESME_CODE_MAP: ESME_CODE_MAP,
        RENTAL_ITEM_CODE: RENTAL_ITEM_CODE,
        VPN_ITEM_CODE: VPN_ITEM_CODE,
        SPACE_ITEM_CODE: SPACE_ITEM_CODE
    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            //location.reload();
            getCpFilterList();
            $('.modal').modal('hide');
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
            $("#EDITSP_CODE").trigger('change');
            getSPServiceList(row[i][1]);
            $("#EDITSERVICE_CODE").val(row[i][3]);
            $("#EDITCP_NAME").val(row[i][4]);
            $("#EDITESME_CODE").val(row[i][5]);
            $("#EDITCATEGORY_MAP").val(row[i][6]);
            $("#EDITESME_CODE_MAP").val(row[i][7]);
            $("#EDITSRV_FOR").val(row[i][8]);
            $("#EDITPACKAGE_TYPE").val(row[i][9]);
            $("#EDITSTREAM_TYPE").val(row[i][10]);
            $("#EDITSTART_DT").val(row[i][11]);
            $("#EDITEND_DT").val(row[i][12]);
            $("#EDITSHARING_TYPE").val(row[i][13]);
            $("#EDITSHARE_NT_PER").val(row[i][14]);
            $("#EDITAFS_FLAG").val(row[i][15]);
            $("#EDITMIN_QTY").val(row[i][16]);
            getItemTariffListRental(row[i][3], "RENTAL")
            $("#EDITRENTAL_ITEM_CODE").val(row[i][17]);
            getItemTariffListVpn(row[i][3], "VPN")
            $("#EDITVPN_ITEM_CODE").val(row[i][18]);
            getItemTariffListSpace(row[i][3], "SPACE")
            $("#EDITSPACE_ITEM_CODE").val(row[i][19]);

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
    var CATEGORY_MAP = $("#EDITCATEGORY_MAP").val();
    var ESME_CODE_MAP = $("#EDITESME_CODE_MAP").val();
    var RENTAL_ITEM_CODE = $("#EDITRENTAL_ITEM_CODE").val();
    var VPN_ITEM_CODE = $("#EDITVPN_ITEM_CODE").val();
    var SPACE_ITEM_CODE = $("#EDITSPACE_ITEM_CODE").val();
    //alert (ESME_CODE_MAP+ " nabin "+CATEGORY_MAP);
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
        MIN_QTY: MIN_QTY,
        CATEGORY_MAP: CATEGORY_MAP,
        ESME_CODE_MAP: ESME_CODE_MAP,
        RENTAL_ITEM_CODE: RENTAL_ITEM_CODE,
        VPN_ITEM_CODE: VPN_ITEM_CODE,
        SPACE_ITEM_CODE: SPACE_ITEM_CODE
    }, function (data) {
        // location.reload();
        alert(data);
        // temp = $('#example1').DataTable();
        // temp.clear().draw();
        if (data.substring(0, 6) === "Succes") {
            //location.reload();
            getCpFilterList();
            $('.modal').modal('hide');
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
        //location.reload();
        alert(data);
        getCpFilterList();
        $('.modal').modal('hide');
    });
}
function getItemTariffList() {
    getItemTariffListRental();
    getItemTariffListVpn();
    getItemTariffListSpace();

}
function getItemTariffListRental(SERVICE_CODE, ITEM_CODE) {

    var servicecode;
    var itemcode;
    if (SERVICE_CODE != null) {
        servicecode = SERVICE_CODE;
    } else {
        servicecode = $('#SERVICE_CODE').val();
    }
    if (ITEM_CODE != null) {
        itemcode = "RENTAL";
    } else {
        itemcode = "RENTAL";
    }
    //alert ("nabin"+servicecode+itemcode);
    $.get('../cp/getItemTariffList', {SERVICE_CODE: servicecode, ITEM_CODE: itemcode
    }, function (response) {
        var select = $('#RENTAL_ITEM_CODE,#EDITRENTAL_ITEM_CODE');
        select.find('option').remove();
        $('<option>').val("").text("SELECT").appendTo(select);
        $.each(response, function (index, value) {
            $('<option>').val(value.ITEM_CODE).text(value.ITEM_CODE).appendTo(
                    select);

        });
    });

}
function getItemTariffListVpn(SERVICE_CODE, ITEM_CODE) {

    var servicecode;
    var itemcode;
    if (SERVICE_CODE != null) {
        servicecode = SERVICE_CODE;
    } else {
        servicecode = $('#SERVICE_CODE').val();
    }
    if (ITEM_CODE != null) {
        itemcode = "VPN";
    } else {
        itemcode = "VPN";
    }
    //alert ("nabin"+servicecode+itemcode);
    $.get('../cp/getItemTariffList', {SERVICE_CODE: servicecode, ITEM_CODE: itemcode
    }, function (response) {
        var select = $('#VPN_ITEM_CODE,#EDITVPN_ITEM_CODE');
        select.find('option').remove();
        $('<option>').val("").text("SELECT").appendTo(select);
        $.each(response, function (index, value) {
            $('<option>').val(value.ITEM_CODE).text(value.ITEM_CODE).appendTo(
                    select);
        });
    });
}
function getItemTariffListSpace(SERVICE_CODE, ITEM_CODE) {

    var servicecode;
    var itemcode;
    if (SERVICE_CODE != null) {
        servicecode = SERVICE_CODE;
    } else {
        servicecode = $('#SERVICE_CODE').val();
    }
    if (ITEM_CODE != null) {
        itemcode = "SPACE";
    } else {
        itemcode = "SPACE";
    }
    //alert ("nabin"+servicecode+itemcode);
    $.get('../cp/getItemTariffList', {SERVICE_CODE: servicecode, ITEM_CODE: itemcode
    }, function (response) {
        var select = $('#SPACE_ITEM_CODE,#EDITSPACE_ITEM_CODE');
        select.find('option').remove();
        $('<option>').val("").text("SELECT").appendTo(select);
        $.each(response, function (index, value) {
            $('<option>').val(value.ITEM_CODE).text(value.ITEM_CODE).appendTo(
                    select);
        });
    });
}

function getSPServiceList(SP_CODE) {
    var sp_code;
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
                    value.CATEGORY_MAP,
                    value.ESME_CODE_MAP,
                    value.SRV_FOR,
                    value.PACKAGE_TYPE,
                    value.STREAM_TYPE,
                    value.START_DT,
                    value.END_DT,
                    value.SHARING_TYPE,
                    value.SHARE_NT_PER,
                    value.AFS_FLAG,
                    value.MIN_QTY,
                    value.RENTAL_ITEM_CODE,
                    value.VPN_ITEM_CODE,
                    value.SPACE_ITEM_CODE,
                    '<a href="../cpdetail/list?cp_code=' + value.CP_CODE + '" target="_blank" class="btn bg-purple"><i class="fa fa-rupee"></i>RATES</a>',
                    
                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#editModal" onclick="return editCp(\'' + value.CP_CODE + '\')"> <i class="fa fa-edit"></i> Edit </a>',
                    '<a href="#" class="btn bg-red" data-toggle="modal" data-target="#deleteModal" onclick="return deleteCp(\'' + value.CP_CODE + '\')"> <i class="fa fa-trash"></i> Delete </a>'
                ]);
            }
            );
        }
    });

}