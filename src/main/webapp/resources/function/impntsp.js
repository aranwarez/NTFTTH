var CODE;
var oFileIn;
var JSONimport;
var JSONarrayList;
var srcFilename;
$(document).ready(function () {
    // $(".js-example-basic-single").select2();
    $('.nepali-calendar').nepaliDatePicker();
    $.fn.dataTable.ext.errMode = 'none';
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

    //$("#SERVICE_CODE").select2({
      //  dropdownParent: $("#myModal"),
       // placeholder: "Select Service Code"
    //});

    $(function () {
        oFileIn = document.getElementById('my_file_input');
        if (oFileIn.addEventListener) {
            oFileIn.addEventListener('change', filePicked, false);
        }
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

function importdata() {
    var param = {
        Excel_List: JSON.stringify(JSONimport),
        Filename: srcFilename,
        IMP_YEAR: $('#QIMP_YEAR').val(),
        Period: $('#QIMP_PERIOD').val(),
        SERVICE_CODE: $('#QSERVICE_CODE').val(),
        IMP_PERIOD: $('#QIMP_PERIOD').val(),
        IMP_MONTH: $('#QIMP_MONTH').val(),
        NT_SP: $('#QNT_SP').val()
    };
    var valid = validateform(param);
    if (valid !== 0) {
        alert("Invalid " + valid)
        return false;
    }

    JSONarrayList = [];
    var indexmap = [];
    $.each(JSONimport, function (indexR, valueR) {
        var arraydata = new Object();

        $.each(JSONimport[indexR], function (indexC, valueC) {
            if (indexR == 0) {
                // Skipping header
                indexmap[indexC] = valueC;
            } else if (indexC == 0 && valueC == "") {
                // Skipping value without catergories
                return false;
            } else {
                arraydata[indexmap[indexC]] = valueC.toString();
            }
        });
        if (indexmap[0] in arraydata) {
            // condition to push object if data exist otherwise it will push
            // empty data
            JSONarrayList.push(arraydata);
        }
    });
    // debugger;
    // alert(JSON.stringify(JSONarrayList));
    postJSONarray();
}

function postJSONarray() {
    $.post('../impntsp/postExcelData', {
        JSONarrayList: JSON.stringify(JSONarrayList),
        Filename: srcFilename,
        IMP_YEAR: $('#QIMP_YEAR').val(),
        Period: $('#QIMP_PERIOD').val(),
        Services: $('#QSERVICE_CODE').val(),
        SERVICE_CODE: $('#QSERVICE_CODE').val(),
        IMP_PERIOD: $('#QIMP_PERIOD').val(),
        IMP_MONTH: $('#QIMP_MONTH').val(),
        NT_SP: $('#QNT_SP').val()

    }, function (data) {
        alert(data);
        // temp = $('#example1').DataTable();
        // temp.clear().draw();
        if (data.substring(0, 6) === "Succes") {
            location.reload();
        }

    });
}

function filePicked(oEvent) {
    $("#my_file_output >thead tr").empty();
    $("#my_file_output >tbody tr").remove();
    $("#my_file_output tbody").empty();

    // Get The File From The Input
    var oFile = oEvent.target.files[0];
    var sFilename = oFile.name;
    // Create A File Reader HTML5
    var reader = new FileReader();

    // Ready The Event For When A File Gets Selected
    reader.onload = function (e) {
        srcFilename = sFilename;
        var data = e.target.result;
        var cfb = XLS.CFB.read(data, {
            type: 'binary'
        });
        var wb = XLS.parse_xlscfb(cfb);
        // Loop Over Each Sheet
        wb.SheetNames.forEach(function (sheetName) {
            // Obtain The Current Row As CSV
            var sCSV = XLS.utils.make_csv(wb.Sheets[sheetName]);
            var data = XLS.utils.sheet_to_json(wb.Sheets[sheetName], {
                header: 1,
                defval: ""
            });

            JSONimport = data;

            $.each(data, function (indexR, valueR) {
                var sRow = "<tr>";
                $.each(data[indexR], function (indexC, valueC) {

                    if (indexR == 0) {
                        $('#my_file_output > thead tr').append(
                                '<th> ' + valueC + '</th>');
                    } else {
                        if (typeof valueC === 'undefined') {
                            valueC = "";
                        }

                        sRow = sRow + "<td>" + valueC + "</td>";
                    }
                });
                sRow = sRow + "</tr>";
                $("#my_file_output").append(sRow);
            });
        });
    };

    // Tell JS To Start Reading The File.. You could delay this if desired
    reader.readAsBinaryString(oFile);
}

function getImpNtspFilterList() {
    // jQuery.ajaxSetup({async: false});
    var year = $("#QIMP_YEAR").val();
    var period = $("#QIMP_PERIOD").val();
    var month = $("#QIMP_MONTH").val();
    var service = $("#QSERVICE_CODE").val();
    var ntsp = $("#QNT_SP").val();
    var postflag = $("#QPOST_FLAG").val();
    reponse = null;
    $
            .get(
                    '../impntsp/getImpNtSplist',
                    {
                        IMP_YEAR: year,
                        IMP_PERIOD: period,
                        IMP_MONTH: month,
                        SERVICE_CODE: service,
                        NT_SP: ntsp,
                        POST_FLAG: postflag
                    },
                    function (response) {
                        // alert(JSON.stringify(response));
                        if (response !== null) {
                            temp = $('#example1').DataTable();
                            temp.clear().draw();
                            $
                                    .each(
                                            response,
                                            function (key, value) {
                                                $("#example1")
                                                        .dataTable()
                                                        .fnAddData(
                                                                [
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
                                                                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#editModal" onclick="return editSpbg(\''
                                                                            + value.TRANS_ID
                                                                            + '\')"> <i class="fa fa-edit"></i> Edit </a>',
                                                                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#deleteModal" onclick="return deleteSpbg(\''
                                                                            + value.TRANS_ID
                                                                            + '\')"> <i class="fa fa-trash"></i> Delete </a>']);
                                            });
                        }
                    });

}

function postExcelTransaction() {
    // validation before posting

    var param = {
        IMP_YEAR: $('#QIMP_YEAR').val(),
        Period: $('#QIMP_PERIOD').val(),
        SERVICE_CODE: $('#QSERVICE_CODE').val(),
        IMP_PERIOD: $('#QIMP_PERIOD').val(),
        IMP_MONTH: $('#QIMP_MONTH').val(),
        NT_SP: $('#QNT_SP').val()
    };
    var valid = validateform(param);
    if (valid !== 0) {
        alert("Invalid !!!!! " + valid)
        return false;
    }

    // --------------------

    $.post('../impntsp/postExcelDataTransaction', {
        IMP_YEAR: $('#QIMP_YEAR').val(),
        Period: $('#QIMP_PERIOD').val(),
        Services: $('#QSERVICE_CODE').val(),
        IMP_PERIOD: $('#QIMP_PERIOD').val(),
        IMP_MONTH: $('#QIMP_MONTH').val(),
        NT_SP: $('#QNT_SP').val()

    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            location.reload();
        }

    });

}

function saveImpntsp() {
    // debugger;
    if ($('#SP_CODE') == null || $('#SP_CODE') == ""
            || $('#SP_CODE').val().length > 20) {
        alert('Invalid sp code!!!');
        return;
    }
    //alert('Invalid sp code!!!');
    var CODE = $("#TRANS_NO").val();
    var IMP_YEAR = $("#IMP_YEAR").val();
    var IMP_PERIOD = $("#IMP_PERIOD").val();
    var IMP_MONTH = $("#IMP_MONTH").val();
    var SERVICE_CODE = $("#SERVICE_CODE").val();
    var NT_SP = $("#NT_SP").val();
    var CATEGORY = $("#CATEGORY").val();
    var CP_DESC = $("#CP_DESC").val();
    var S_NO = $("#S_NO").val();
    var ESME_CODE = $("#ESME_CODE").val();
    var MO_1ST = $("#MO_1ST").val();
    var MT_1ST = $("#MT_1ST").val();
    // alert("nabin"+SHORT_CODE);
    $.post('../impntsp/saveJS', {
        TRANS_NO: CODE,
        IMP_YEAR: IMP_YEAR,
        IMP_PERIOD: IMP_PERIOD,
        IMP_MONTH: IMP_MONTH,
        SERVICE_CODE: SERVICE_CODE,
        NT_SP: NT_SP,
        CATEGORY: CATEGORY,
        CP_DESC: CP_DESC,
        S_NO: S_NO,
        ESME_CODE: ESME_CODE,
        MO_1ST: MO_1ST,
        MT_1ST: MT_1ST
    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            location.reload();
        }
    });
}