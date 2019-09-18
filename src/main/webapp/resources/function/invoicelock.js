var CODE;
var SEQNO;
var oFileIn;
var JSONimport;
var JSONarrayList;
var srcFilename;
var SNO;
$(document).ready(function () {
    // $(".js-example-basic-single").select2();
    $('.nepali-calendar').nepaliDatePicker();
    $.fn.dataTable.ext.errMode = 'none';
    $("#QSERVICE_CODE").select2({
        placeholder: "Select a Service  Code",
        allowClear: true
    });
});

function validateform(Parameters) {
    var item = 0;
    $.each(Parameters, function (key, value) {
        if (isempty(value)) {
            item = key;
        }
        return (!isempty(value));
    });
    return item;
}
;

function isempty(object) {
    if (object == "" || object === "" || typeof (object) === "undefined") {
        return true;
    } else
        return false;
}

function getInvoiceLockList() {
    // jQuery.ajaxSetup({async: false});
    var year = $("#QIMP_YEAR").val();
    var period = $("#QIMP_PERIOD").val();
    var month = $("#QIMP_MONTH").val();
    var service = $("#QSERVICE_CODE").val();
    var postflag = $("#QPOST_FLAG").val();

    reponse = null;
    $
            .get('../invoicelock/getInvoiceLockList',
                    {IMP_YEAR: year, IMP_PERIOD: period, IMP_MONTH: month, SERVICE_CODE: service,
                        POST_FLAG: postflag},
                    function (response) {
                        // alert(JSON.stringify(response));

                        if (response !== null) {
                            temp = $('#example1').DataTable();
                            temp.clear().draw();
                            $
                                    .each(response,
                                            function (key, value) {
                                                var postflag = '';
                                                var postflagbox = value.POST_FLAG;
                                                var qdelete = '';
                                                var unpost = '';
                                                if (postflagbox == 'Y') {
                                                    postflag = '<input type="checkbox" checked onclick="return false" disabled />';
                                                 //   bd = "<a href = \"#\" data-toggle=\"modal\" data-target=\"#modalBankDeposit\" title=\"Bank Deposit\" onclick=\"return editBankDeposit('" + value.TRANS_NO + "')\"<span class=\"fa fa-pencil-square-o\"></span></a>";
                                                   // unpost = "<a href = \"#\" data-toggle=\"modal\" class=\"btn btn-info\" data-target=\"#UnpostRevenue\" onclick=\"return UnpostRevenueConfirm('" + value.OFFICE_CODE + "',\'" + value.TRANS_NO + "')\">UNPOST</a>";
                                                } else if (postflagbox == 'N') {
                                                    postflag = '<input type=\"checkbox\"  id=\"' + value.TRANS_NO + '\" class=\"postCancel\" value="' + value.TRANS_NO + '">';
                                                    //edit = "<a href=\"#\" data-toggle=\"modal\" class=\"btn btn-info\" onclick=\"return editDetails('" + value.OFFICE_CODE + "',\'" + value.TRANS_NO + "',\'" + value.NEP_DT + "')\">EDIT</a> ";
                                                    //qdelete = "<a href = \"#\" data-toggle=\"modal\" class=\"btn btn-info\" data-target=\"#deleteRevenue\" onclick=\"return deleteCreditCollConfirm('" + value.OFFICE_CODE + "',\'" + value.TRANS_NO + "',\'" + value.NEP_DT + "')\">Delete</a>";
                                                }
                                                $("#example1")
                                                        .dataTable()
                                                        .fnAddData(
                                                                [postflag,
                                                                    value.TRANS_NO,
                                                                    value.TRANS_DT,
                                                                    value.IMP_YEAR,
                                                                    value.IMP_PERIOD,
                                                                    value.IMP_MONTH,
                                                                    value.SERVICE_CODE,
                                                                    value.POST_FLAG,
                                                                    value.POST_BY,
                                                                    value.POST_DT,
                                                                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#myDetailModal" onclick="return detailBill(\''
                                                                            + value.TRANS_NO
                                                                            + '\')"> <i class="fa fa-edit"></i> View </a>',
                                                                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#deleteModal" onclick="return deleteImpntsp(\''
                                                                            + value.TRANS_NO
                                                                            + '\')"> <i class="fa fa-trash"></i> Delete </a>']);
                                            });
                        }
                    });

}

function detailBill(trans_no) {
    CODE = trans_no;
    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == trans_no) {
            // $("#CP_CODE").val(row[i][0]);
            // $("#SP_CODE").val(row[i][1]);
            // $("#SP_NAME").val(row[i][2]);
            // $("#SERVICE_CODE").val(row[i][3]);
            $("#RatesLabel").html(row[i][3] + row[i][5] + '(' + row[i][6] + ')');
            // $("#ESME_CODE").val(row[i][5]);
            // $("#PACKAGE_TYPE").val(row[i][6]);
            // $("#RATE").val(row[i][7]);
            // $("#EFFECTIVE_DT").val(row[i][8]);
        }
    }

    $
            .get(
                    '../invoicelock/getInvoiceDetailList',
                    {
                        TRANS_NO: trans_no
                    },
                    function (response) {
                        // alert(JSON.stringify(response));
                        if (response !== null) {

                            tempdetaillist = response;
                            temp = $('#detailtable').DataTable();
                            temp.clear().draw();
                            $
                                    .each(
                                            response,
                                            function (key, value) {
                                                var postflag = '';
                                                var postflagbox = value.POST_FLAG;
                                                $("#detailtable")
                                                        .dataTable()
                                                        .fnAddData(
                                                                [

                                                                    value.TRANS_ID,
                                                                    value.S_NO,
                                                                    value.ITEM_CODE,
                                                                    value.SHARING_TYPE,
                                                                    value.DR_CR_FLAG,
                                                                    value.AMT,
                                                                    value.ROYALTY_AMT,
                                                                    value.TSC_AMT,
                                                                    value.VAT_AMT
                                                                ]);
                                            });
                        }
                    });

}

function select_check(no) {
    if (no == true) {
        $('.postCancel').prop('checked', true);
        $("#selectcheck").hide();
        $("#selectcheckf").show();
    } else {
        $('.postCancel').prop('checked', false);
        $("#selectcheck").show();
        $("#selectcheckf").hide();
    }
}
