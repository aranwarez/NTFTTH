var CODE;

$(document).ready(function () {
    //$(".js-example-basic-single").select2();
    $('.nepali-calendar').nepaliDatePicker();
    $.fn.dataTable.ext.errMode = 'none';
    $("#QSERVICE_CODE").select2({
        placeholder: "Select a Service  Code",
                 allowClear: true
    });
    $("#QITEM_CODE").select2({
        placeholder: "Select Item",
                 allowClear: true
    });
    $("#SERVICE_CODE").select2({
        dropdownParent: $("#myModal"),
        placeholder: "Select Service Code"
                // allowClear: false
    });

    $("#ITEM_CODE").select2({
        dropdownParent: $("#myModal"),
        placeholder: "Select Item"
    });
    getItemtariffFilterList();

});

function getItemtariffFilterList() {
    jQuery.ajaxSetup({async: false});
    var service_code = $("#QSERVICE_CODE").val();
    var item_code = $("#QITEM_CODE").val();
    reponse = null;
    $.get('../itemtariff/getItemtarifflist', {SERVICE_CODE: service_code, ITEM_CODE: item_code
    }, function (response) {
        //  alert(JSON.stringify(response));
        if (response !== null) {
            temp = $('#example1').DataTable();
            temp
                    .clear()
                    .draw();
            $.each(response, function (key, value) {
                $("#example1").dataTable().fnAddData([
                    value.ID_NO,
                    value.ITEM_CODE,
                    value.ITEM,
                    value.CATEGORY,
                    value.SERVICE_CODE,
                    value.AMOUNT,
                    value.NEP_EFFECTIVE_DT,
                    value.ACTIVE_FLAG,
                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#editModal" onclick="return editItemtariff(\''+value.ID_NO+'\')"> <i class="fa fa-edit"></i> Edit </a>',
                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#deleteModal" onclick="return deleteItemtariff(\''+value.ID_NO+'\')"> <i class="fa fa-trash"></i> Delete </a>'
                ]);
            }
            );
        }
    });
}

function saveItemtariff() {
    // debugger;
    if ($('#ITEM_CODE') == null || $('#ITEM_CODE') == ""
            || $('#ITEM_CODE').val().length > 8) {
        alert('Invalid ITEM!!!');
        return;
    }
    //var CODE = $("#TRANS_ID").val();
    var ITEM_CODE = $("#ITEM_CODE").val();
    var SERVICE_CODE = $("#SERVICE_CODE").val();
    var AMOUNT = $("#AMOUNT").val();
    var EFFECTIVE_DT = $("#EFFECTIVE_DT").val();
    var ACTIVE_FLAG = $("#ACTIVE_FLAG").val();
    
    // alert("nabin"+SHORT_CODE);
    $.post('../itemtariff/saveJS', {
        ITEM_CODE: ITEM_CODE,
        SERVICE_CODE: SERVICE_CODE,
        AMOUNT: AMOUNT,
        EFFECTIVE_DT: EFFECTIVE_DT,
        ACTIVE_FLAG: ACTIVE_FLAG
    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            getItemtariffFilterList();
            $('.modal').modal('hide');
            //location.reload();
        }
    });
}

function editItemtariff(code) {
    CODE = code;

    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == code) {
            jQuery.ajaxSetup({async: false});

            //getSPServiceList(row[i][1]);

            $("#EDITID_NO").val(row[i][0]);
            $("#EDITITEM_CODE").val(row[i][1]);
            $("#EDITSERVICE_CODE").val(row[i][4]);
            $("#EDITAMOUNT").val(row[i][5]);
            $("#EDITEFFECTIVE_DT").val(row[i][6]);
            $("#EDITACTIVE_FLAG").val(row[i][7]);
            jQuery.ajaxSetup({async: true});

        }
    }
}

function updateItemtariff() {
    if ($('#EDITITEM_CODE') == null || $('#EDITITEM_CODE') == ""
            || $('#EDITITEM_CODE').val().length > 8) {
        alert('Invalid ITEM!!!');
        return;
    }
    var ID_NO = $("#EDITID_NO").val();
    var ITEM_CODE = $("#EDITITEM_CODE").val();
    var SERVICE_CODE = $("#EDITSERVICE_CODE").val();
    var AMOUNT = $("#EDITAMOUNT").val();
    var EFFECTIVE_DT = $("#EDITEFFECTIVE_DT").val();
    var ACTIVE_FLAG = $("#EDITACTIVE_FLAG").val();
    $.post('../itemtariff/update', {
        ID_NO: ID_NO,
        ITEM_CODE: ITEM_CODE,
        SERVICE_CODE: SERVICE_CODE,
        AMOUNT: AMOUNT,
        EFFECTIVE_DT: EFFECTIVE_DT,
        ACTIVE_FLAG: ACTIVE_FLAG
    }, function (data) {
        // location.reload();
        alert(data);
        // temp = $('#example1').DataTable();
        // temp.clear().draw();
        if (data.substring(0, 6) === "Succes") {
            //location.reload();
            getItemtariffFilterList();
            $('.modal').modal('hide');
        }

    });
}

function deleteItemtariff(code) {
    CODE = code;
}

function del() {

    $.post('../itemtariff/delete', {
        ID_NO: CODE
    }, function (data) {
        //location.reload();
        getItemtariffFilterList();
        $('.modal').modal('hide');
        alert(data);
    });
}