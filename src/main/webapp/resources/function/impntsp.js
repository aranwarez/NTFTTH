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
    $("#S_NO").select2({
        dropdownParent: $("#myModal"),
        placeholder: "Select a Service Provider Code"
                // allowClear: false
    });
     $("#EDITS_NO").select2({
     dropdownParent: $("#editModal"),
     placeholder: "Select a Service Provider Code",
     //allowClear: false
     });

    // $("#SERVICE_CODE").select2({
    // dropdownParent: $("#myModal"),
    // placeholder: "Select Service Code"
    // });

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
                //indexmap[indexC] = valueC;  --remove comment if u need column header
            	indexmap[indexC] = indexC.toString();
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
    $('#my_file_output').show();

}
// function use for change the level as per service code
function momtlablechange(addedit) {
//    alert(addedit);
    //debugger;
    if (addedit == "a") {
        var service = $("#SERVICE_CODE").val();
        if (service == 'APP') {
            $("#addmo").html("Sub.Count");
            $("#addmt").html("PPV Count");
        } else if (service == 'IVR') {
            $("#addmo").html("Min.Count");
            $("#addmt").html("Min.Count");

        } else if (service == 'USSD') {
            $("#addmo").html("Sub.Count");
            $("#addmt").html("Sub.Count");

        } else {
            $("#addmo").html("MO");
            $("#addmt").html("MT");

        }
    } else {
        var service = $("#EDITSERVICE_CODE").val();
        if (service == 'APP') {
            $("#editmo").html("Sub.Count");
            $("#editmt").html("PPV Count");
        } else if (service == 'IVR') {
            $("#editmo").html("Min.Count");
            $("#editmt").html("Min.Count");
        } else if (service == 'USSD') {
            $("#editmo").html("Sub.Count");
            $("#editmt").html("Sub.Count");
        } else {
            $("#editmo").html("MO");
            $("#editmt").html("MT");
        }
    }
    //var service = $("#SERVICE_CODE").val();

}
function getImpNtspFilterList() {
    $('#my_file_output').hide();
    // jQuery.ajaxSetup({async: false});
    var year = $("#QIMP_YEAR").val();
    var period = $("#QIMP_PERIOD").val();
    var month = $("#QIMP_MONTH").val();
    var service = $("#QSERVICE_CODE").val();
    var ntsp = $("#QNT_SP").val();
    var postflag = $("#QPOST_FLAG").val();
    if (service == 'APP') {
        $("#thmo").html("Sub.Count");
        $("#thmt").html("PPV Count");
    } else if (service == 'IVR') {
        $("#thmo").html("Min.Count");
        $("#thmt").html("Min.Count");
    } else if (service == 'USSD') {
        $("#thmo").html("Sub.Count");
        $("#thmt").html("Sub.Count");
    } else {
        $("#thmo").html("MO");
        $("#thmt").html("MT");
    }
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
                                                                    value.MO_1ST,
                                                                    value.MT_1ST,
                                                                    value.RATE,
                                                                    value.START_DT,
                                                                    value.REMARKS,
                                                                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#editModal" onclick="return editImpntsp(\''
                                                                            + value.TRANS_NO
                                                                            + "','"
                                                                            + value.SEQ_NO
                                                                            + '\')"> <i class="fa fa-edit"></i> Edit </a>',
                                                                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#deleteModal" onclick="return deleteImpntsp(\''
                                                                            + value.TRANS_NO
                                                                            + "','"
                                                                            + value.SEQ_NO
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
     debugger;
    
    if ($('#S_NO') == null || $('#S_NO') == ""
            || $('#S_NO').val().length > 20) {
        alert('Invalid sp code!!!');
        return;
    }
    // alert('Invalid sp code!!!');
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
    //alert("nabin1"); 
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
            //location.reload();
            getImpNtspFilterList();
            $('.modal').modal('hide');
        }
    });
}

function editImpntsp(code, seqno) {
    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == code && row[i][1] == seqno) {
            // jQuery.ajaxSetup({async: false});

            // alert(row[i][9]);

            // getSPServiceList(row[i][1]);

            $("#EDITTRANS_NO").val(row[i][0]);
            $("#EDITSEQ_NO").val(row[i][1]);
            $("#EDITIMP_YEAR").val(row[i][2]);
            $("#EDITIMP_PERIOD").val(row[i][3]);
            $("#EDITIMP_MONTH").val(row[i][4]);
            $("#EDITSERVICE_CODE").val(row[i][5]);
            $("#EDITNT_SP").val(row[i][6]);
            $("#EDITCATEGORY").val(row[i][7]);
            $("#EDITCP_DESC").val(row[i][8]);
            $("#EDITS_NO").val(row[i][9]);
             $("#EDITS_NO").trigger('change');
            
            $("#EDITESME_CODE").val(row[i][10]);
            $("#EDITMO_1ST").val(row[i][13]);
            $("#EDITMT_1ST").val(row[i][14]);
            // jQuery.ajaxSetup({async: true});
        }
    }
}
function updateImpntsp() {

    var CODE = $("#EDITTRANS_NO").val();
    var SEQNO = $("#EDITSEQ_NO").val();
    var IMP_YEAR = $("#EDITIMP_YEAR").val();
    var IMP_PERIOD = $("#EDITIMP_PERIOD").val();
    var IMP_MONTH = $("#EDITIMP_MONTH").val();
    var SERVICE_CODE = $("#EDITSERVICE_CODE").val();
    var NT_SP = $("#EDITNT_SP").val();
    var CATEGORY = $("#EDITCATEGORY").val();
    var CP_DESC = $("#EDITCP_DESC").val();
    var S_NO = $("#EDITS_NO").val();
    var ESME_CODE = $("#EDITESME_CODE").val();
    var MO_1ST = $("#EDITMO_1ST").val();
    var MT_1ST = $("#EDITMT_1ST").val();
    // alert ("nab "+S_NO);
    $.post('../impntsp/update', {
        TRANS_NO: CODE,
        SEQ_NO: SEQNO,
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
        // location.reload();
        alert(data);
        // temp = $('#example1').DataTable();
        // temp.clear().draw();
        if (data.substring(0, 6) === "Succes") {
            getImpNtspFilterList();
            $('.modal').modal('hide');
        }

    });
}

function deleteImpntsp(code, seqno) {
    CODE = code;
    SEQNO = seqno;
}

function del() {

    $.post('../impntsp/delete', {
        TRANS_NO: CODE,
        SEQ_NO: SEQNO
    }, function (data) {
        //location.reload();
        alert(data);
        getImpNtspFilterList();
          $('.modal').modal('hide');
    });
}

function maindeletedata() {
    var IMP_YEAR = $("#QIMP_YEAR").val();
    var IMP_PERIOD = $("#QIMP_PERIOD").val();
    var IMP_MONTH = $("#QIMP_MONTH").val();
    var SERVICE_CODE = $("#QSERVICE_CODE").val();
    var NT_SP = $("#QNT_SP").val();
    var POST_FLAG = $("#QPOST_FLAG").val();
    $.post('../impntsp/deleteall', {
        IMP_YEAR: IMP_YEAR,
        IMP_PERIOD: IMP_PERIOD,
        IMP_MONTH: IMP_MONTH,
        SERVICE_CODE: SERVICE_CODE,
        NT_SP: NT_SP,
        POST_FLAG: POST_FLAG
    }, function (data) {
        location.reload();
        alert(data);
    });
}
