var CODE;

$(document).ready(function () {
    //$(".js-example-basic-single").select2();
    $('.nepali-calendar').nepaliDatePicker();
    $.fn.dataTable.ext.errMode = 'none';
    $("#QSERVICE_CODE").select2({
        placeholder: "Select a Service  Code",
                 allowClear: true
    });
    
    $("#SERVICE_CODE").select2({
        dropdownParent: $("#myModal"),
        placeholder: "Select Service Code"
    });
    
    
});

function getImpNtspFilterList() {
    //jQuery.ajaxSetup({async: false});
    var year = $("#QIMP_YEAR").val();
    var period = $("#QIMP_PERIOD").val();
    var month = $("#QIMP_MONTH").val();
    var service = $("#QSERVICE_CODE").val();
    var ntsp = $("#QNT_SP").val();
    var postflag = $("#QPOST_FLAG").val();
    reponse = null;
    $.get('../impntsp/getImpNtSplist', {IMP_YEAR: year, IMP_PERIOD: period, IMP_MONTH: month,SERVICE_CODE:service,NT_SP:ntsp, POST_FLAG: postflag
    }, function (response) {
        //  alert(JSON.stringify(response));
        if (response !== null) {
            temp = $('#example1').DataTable();
            temp
                    .clear()
                    .draw();
            $.each(response, function (key, value) {
                $("#example1").dataTable().fnAddData([
                    value.TRANS_NO,
                    value.SEQ_NO,
                    value.IMP_YEAR,
                    value.IMP_PERIOD,
                    value.IMP_MONTH,
                    value.SERVICE_CODE,
                    value.NT_SP,
                    value.CATEGORY,
                    value.CP_DESC,
                    value.S_NO,
                    value.ESME_CODE,
                    value.RATE,
                    value.START_DT,
                    value.MO_1ST,
                    value.MT_1ST,
                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#editModal" onclick="return editSpbg(\''+value.TRANS_ID+'\')"> <i class="fa fa-edit"></i> Edit </a>',
                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#deleteModal" onclick="return deleteSpbg(\''+value.TRANS_ID+'\')"> <i class="fa fa-trash"></i> Delete </a>'
                ]);
            }
            );
        }
    });

}

