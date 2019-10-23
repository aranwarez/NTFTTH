var CODE;

$(document).ready(function () {
    //$(".js-example-basic-single").select2();
    $('.nepali-calendar').nepaliDatePicker();
    $.fn.dataTable.ext.errMode = 'none';
    $("#QSP_CODE").select2({
        placeholder: "Select a Service Provider Code",
                 allowClear: true
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
$('#example1').DataTable().column(4).visible(false);


});

function getPaymentFilterList() {
    jQuery.ajaxSetup({async: false});
    var sp_code = $("#QSP_CODE").val();
    var from_dt = $("#QFROM_DT").val();
    var to_dt = $("#QTO_DT").val();
    var post_flag = $("#QPOST_FLAG").val();
    var cc_code="CC010201";
    reponse = null;
    $.get('../payment/getPaymentlist', {CC_CODE: cc_code, S_NO: sp_code, FROM_DT: from_dt, TO_DT: to_dt, POST_FLAG: post_flag
    }, function (response) {
        //  alert(JSON.stringify(response));
        if (response !== null) {
            temp = $('#example1').DataTable();
            temp
                    .clear()
                    .draw();
            $.each(response, function (key, value) {
                $("#example1").dataTable().fnAddData([
                    value.PAYMENT_NO,
                    value.NEP_DT,
                    value.S_NO,
                    value.BANK_CD,
                    value.CHEQUE_NO,
                    value.PAID_AMT,
                    value.ROYALTY,
                    value.VAT,
                    value.TOTAL_AMT,
                    value.REMARKS,
                    value.CREATE_BY,
                    value.NEP_CREATE_DT,
                    value.CANCEL_BY,
                    value.CANCEL_DT,
                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#editModal" onclick="return editPayment(\''+value.PAYMENT_NO+'\')"> <i class="fa fa-edit"></i> View </a>',
                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#deleteModal" onclick="return deletePayment(\''+value.PAYMENT_NO+'\')"> <i class="fa fa-trash"></i> Cancel </a>'
                ]);
            }
            );
        }
    });

}

function getSpDue(SP_CODE, SERVICE_CODE) {
    var sp_code
    var service_code
    service_code=SERVICE_CODE;
    //debugger;
    if (SP_CODE != null) {
        sp_code = SP_CODE;
    } else {
        sp_code = $('#SP_CODE').val();
    }
    if (SERVICE_CODE != null) {
        service_code = SERVICE_CODE;
    } else {
        service_code = $('#SERVICE_CODE').val();
    }
    
    $.get('../payment/getSpdue', {SP_CODE: sp_code, SERVICE_CODE: service_code
    }, function (response) {
        if(Number(response.BAL_AMT)>=0){
            $('#BALAMT').css({'color':'black'});
            $('#BALAMTTAX').css({'color':'black'});
        }else{ $('#BALAMT').css({'color':'red'});
         $('#BALAMTTAX').css({'color':'red'});
    }
        $('#BALAMT').html(response.PAYABLE_BEFORE_TAX);
        $('#BALAMTTAX').html(response.BAL_AMT_WITH_TAX);
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



function savePayment() {
    // debugger;
    if ($('#SP_CODE') == null || $('#SP_CODE') == ""
            || $('#SP_CODE').val().length > 8) {
        alert('Invalid SP Code!!!');
        return;
    }
    var CC_CODE="CC010201";
    var CODE = $("#PAYMENT_NO").val();
    var PAYMENT_DT = $("#PAYMENT_DT").val();
    var SP_CODE = $("#SP_CODE").val();
    var BANK_CD = $("#BANK_CD").val();
    var CHEQUE_NO = $("#CHEQUE_NO").val();
    var AMT = $("#AMT").val();
    var REMARKS = $("#REMARKS").val();
    var SERVICE_CODE = $("#SERVICE_CODE").val();
    // alert("nabin"+SHORT_CODE);
    $.post('../payment/saveJS', {
        CC_CODE: CC_CODE,
        PAYMENT_NO: CODE,
        PAYMENT_DT: PAYMENT_DT,
        S_NO: SP_CODE,
        BANK_CD: BANK_CD,
        CHEQUE_NO: CHEQUE_NO,
        PAID_AMT: AMT,
        REMARKS: REMARKS,
        SERVICE_CODE: SERVICE_CODE
    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            location.reload();
        }
    });
}

function editPayment(code) {
    CODE = code;

    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == code) {
            jQuery.ajaxSetup({async: false});
            //getSPServiceList(row[i][1]);
            $("#EDITPAYMENT_NO").val(row[i][0]);
            $("#EDITPAYMENT_DT").val(row[i][1]);
            $("#EDITSP_CODE").val(row[i][2]);
            $("#EDITBANK_CD").val(row[i][3]);
            $("#EDITCHEQUE_NO").val(row[i][4]);
            $("#EDITAMT").val(row[i][5]);
            $("#EDITREMARKS").val(row[i][6]);
            $("#EDITCREATE_BY").val(row[i][7]);            
            jQuery.ajaxSetup({async: true});
        }
    }
}

function deletePayment(code) {
    CODE = code;
}

function del() {

    $.post('../payment/delete', {
        PAYMENT_NO: CODE
    }, function (data) {
        location.reload();
        alert(data);
    });
}