var CODE;
var tempdetaillist;
var TRANS_ID;

$(document).ready(function () {
    // $(".js-example-basic-single").select2();
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

    if (getAllUrlParams().cp_code) {
        CODE = getAllUrlParams().cp_code;
        getCpdetailFilterList(CODE);
        detailCp(CODE);
        $('#myDetailModal').modal('show');

    }

});

function getCpdetailFilterList(cp_code) {
    var sp_code = $("#QSP_CODE").val();
    var service = $("#QSERVICE_CODE").val();
//	var cp_code = code;
    reponse = null;
    $
            .get(
                    '../cpdetail/getCpDetaillist',
                    {
                        SP_CODE: sp_code,
                        SERVICE_CODE: service,
                        CP_CODE: cp_code
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
                                                                    value.CP_CODE,
                                                                    value.SP_CODE,
                                                                    value.SP_NAME,
                                                                    value.SERVICE_CODE,
                                                                    value.CP_NAME,
                                                                    value.ESME_CODE,
                                                                    value.PACKAGE_TYPE,
                                                                    value.RATE,
                                                                    value.NEP_EFFECTIVE_DT,
                                                                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#myModal" onclick="return addCp(\''
                                                                            + value.CP_CODE
                                                                            + '\')"> <i class="fa fa-plus"></i> ADD </a>',
                                                                    '<a href="#" class="btn btn-info bg-green" data-toggle="modal" data-target="#myDetailModal" onclick="return detailCp(\''
                                                                            + value.CP_CODE
                                                                            + '\')"> <i class="fa fa-edit"></i> Detail </a>']);
                                            });
                        }
                    });
}

function addCp(code) {
    CODE = code;

    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == code) {
            $("#CP_CODE").val(row[i][0]);
            $("#SP_CODE").val(row[i][1]);
            $("#SP_NAME").val(row[i][2]);
            $("#SERVICE_CODE").val(row[i][3]);
            $("#CP_NAME").val(row[i][4]);
            $("#ESME_CODE").val(row[i][5]);
            $("#PACKAGE_TYPE").val(row[i][6]);
            // $("#RATE").val(row[i][7]);
            // $("#EFFECTIVE_DT").val(row[i][8]);
        }
    }
}

function saveCpdetail() {
    if ($('#RATE') == null || $('#RATE') == "" || $('#RATE').val().length > 8) {
        alert('Invalid RATE!!!');
        return;
    }
    var CODE = $("#CP_CODE").val();
    var RATE = $("#RATE").val();
    var EFFECTIVE_DT = $("#EFFECTIVE_DT").val();

    // alert(RATE+"nabin"+CODE);
    $.post('../cpdetail/saveJS', {
        CP_CODE: CODE,
        RATE: RATE,
        EFFECTIVE_DT: EFFECTIVE_DT
    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            // location.reload();
            getCpdetailFilterList(CODE);
            $('.modal').modal('hide');
        }
    });
}

function detailCp(cp_code) {
    CODE = cp_code;
    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == cp_code) {
            // $("#CP_CODE").val(row[i][0]);
            // $("#SP_CODE").val(row[i][1]);
            // $("#SP_NAME").val(row[i][2]);
            // $("#SERVICE_CODE").val(row[i][3]);
            $("#RatesLabel").html(row[i][4]);
            // $("#ESME_CODE").val(row[i][5]);
            // $("#PACKAGE_TYPE").val(row[i][6]);
            // $("#RATE").val(row[i][7]);
            // $("#EFFECTIVE_DT").val(row[i][8]);
        }
    }

    $
            .get(
                    '../cpdetail/getCpRatelist',
                    {
                        CP_CODE: cp_code
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
                                                $("#detailtable")
                                                        .dataTable()
                                                        .fnAddData(
                                                                [

                                                                    value.RATE,
                                                                    value.NEP_DT,
                                                                    value.CREATE_BY,
                                                                    '<a href="#" class="btn btn-info" data-toggle="modal" data-target="#editRate" onclick="return editCprate(\''
                                                                            + value.TRANS_ID
                                                                            + '\')"> <i class="fa fa-edit"></i> Edit </a>',
                                                                    '<a href="#" class="btn btn-info bg-red" data-toggle="modal" data-target="#deleteRate" onclick="return deleteCprate(\''
                                                                            + value.TRANS_ID
                                                                            + '\')"> <i class="fa fa-trash"></i> Delete </a>']);
                                            });
                        }
                    });

}

function editCprate(id) {
    TRANS_ID = id;
    $.each(tempdetaillist, function (key, value) {
        if (value.TRANS_ID == id) {
            $('#EDITEFFECTIVE_DT').val(value.NEP_DT);
            $('#EDITRATE').val(value.RATE);
        }
    });
}

function updaterate() {
    if ($('#RATE') == null || $('#RATE') == "" || $('#RATE').val().length > 8) {
        alert('Invalid RATE!!!');
        return;
    }
    var RATE = $("#EDITRATE").val();
    var EFFECTIVE_DT = $("#EDITEFFECTIVE_DT").val();
    $.post('../cpdetail/updateJS', {
        TRANS_ID: TRANS_ID,
        CP_CODE: CODE,
        RATE: RATE,
        EFFECTIVE_DT: EFFECTIVE_DT
    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            // location.reload();
            detailCp(CODE);
            getCpdetailFilterList();
            $('#editRate').modal('hide');

        }
    });

}

function deleteCprate(id) {
    if (confirm("Are you sure you want to delete!!")) {
        $.post('../cpdetail/deleteJS', {
            TRANS_ID: id,
        }, function (data) {
            alert(data);
            if (data.substring(0, 6) === "Succes") {
                // location.reload();
                detailCp(CODE);
                getCpdetailFilterList(CODE);
                // $('#editRate').modal('hide');

            }
        });

    }

}

function getAllUrlParams(url) {

    // get query string from url (optional) or window
    var queryString = url ? url.split('?')[1] : window.location.search.slice(1);

    // we'll store the parameters here
    var obj = {};

    // if query string exists
    if (queryString) {

        // stuff after # is not part of query string, so get rid of it
        queryString = queryString.split('#')[0];

        // split our query string into its component parts
        var arr = queryString.split('&');

        for (var i = 0; i < arr.length; i++) {
            // separate the keys and the values
            var a = arr[i].split('=');

            // set parameter name and value (use 'true' if empty)
            var paramName = a[0];
            var paramValue = typeof (a[1]) === 'undefined' ? true : a[1];

            // (optional) keep case consistent
            paramName = paramName.toLowerCase();
            if (typeof paramValue === 'string')
                paramValue = paramValue.toLowerCase();

            // if the paramName ends with square brackets, e.g. colors[] or
            // colors[2]
            if (paramName.match(/\[(\d+)?\]$/)) {

                // create key if it doesn't exist
                var key = paramName.replace(/\[(\d+)?\]/, '');
                if (!obj[key])
                    obj[key] = [];

                // if it's an indexed array e.g. colors[2]
                if (paramName.match(/\[\d+\]$/)) {
                    // get the index value and add the entry at the appropriate
                    // position
                    var index = /\[(\d+)\]/.exec(paramName)[1];
                    obj[key][index] = paramValue;
                } else {
                    // otherwise add the value to the end of the array
                    obj[key].push(paramValue);
                }
            } else {
                // we're dealing with a string
                if (!obj[paramName]) {
                    // if it doesn't exist, create property
                    obj[paramName] = paramValue;
                } else if (obj[paramName] && typeof obj[paramName] === 'string') {
                    // if property does exist and it's a string, convert it to
                    // an array
                    obj[paramName] = [obj[paramName]];
                    obj[paramName].push(paramValue);
                } else {
                    // otherwise add the property
                    obj[paramName].push(paramValue);
                }
            }
        }
    }

    return obj;
}
