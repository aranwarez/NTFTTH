/**
 * This function contents code for Service And Target
 */

var currentSP;
var currentTrans_id;
$(document).ready(function () {

    $('.nepali-calendar').nepaliDatePicker();
    $("#sptarget").dataTable();

});

/*-----------------------------------------------------------------------
 * Start of Target functional Script
 * --------------------------------
 * */

function editSp(SP_CODE) {
    currentSP = SP_CODE;
    $('#SP_CODE').val(SP_CODE);
    $('#eSP_CODE').val(SP_CODE);
    $('.modal-title')
            .html('Service Target Entry for Service Code : ' + SP_CODE);
}

function savesptarget() {
    $.post('../sp/savesptargetJS', {
        SP_CODE: currentSP,
        SERVICE_CODE: $('#SERVICE_CODE').val(),
        EFFECTIVE_DT: $('#EFFECTIVE_DT').val(),
        REVENUE_TARGET: $('#REVENUE_TARGET').val(),
        MINIMUM_GUARENTEE: $('#MINIMUM_GUARENTEE').val()


    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            // location.reload();
            $('#addModal').modal('hide');
        }
    });
}

function getsptargetList() {
    $
            .get(
                    '../sp/getSPTargetList',
                    {
                        SP_CODE: currentSP
                    },
                    function (response) {
                        if (response !== null) {
                            temp = $('#sptarget').DataTable();
                            temp.clear().draw();

                            $
                                    .each(
                                            response,
                                            function (key, value) {
                                                $("#sptarget")
                                                        .dataTable()
                                                        .fnAddData(
                                                                [  value.SERVICE_CODE,
                                                                    value.EFFECTIVE_DT,
                                                                    value.REVENUE_TARGET,
                                                                    value.MINIMUM_GUARENTEE,
                                                                    value.UPDATE_BY,
                                                                    value.UPDATE_DT,
                                                                    "<a href = \"#\" data-toggle=\"modal\" data-target=\"#editSPTargetModal\" title=\"Edit\" onclick=\"return editSPTarget('"
                                                                            + value.TRANS_ID
                                                                            + "')\"<span class=\"fa fa-pencil-square-o\"></span></a>",
                                                                    "<a href=\"#\" data-toggle=\"modal\" data-target=\"#deleteModal\"  title=\"Delete\" onclick=\"return delSPTarget('"
                                                                            + value.TRANS_ID
                                                                            + "')\"<span class=\"fa fa-trash\"></span></a>"]); // closing
                                                // fnAddData()
                                            }// closing function(key,value)
                                    ); // closing each()
                        }// closing if()
                    }); // closing function(responseJson)

}

function editSPTarget(Trans_id) {
    currentTrans_id = Trans_id;
    $.get('../sp/getSPTargetList', {
        SP_CODE: currentSP
    }, function (response) {
        $.each(response, function (key, value) {
            if (value.TRANS_ID == Trans_id) {
                $('#eSP_CODE').val(currentSP);
                $('#eSERVICE_CODE').val(value.SERVICE_CODE);
                $('#eREV_TARGET').val(value.REVENUE_TARGET);
                $('#eMINIMUM_GUARENTEE').val(value.MINIMUM_GUARENTEE);
                $('#eEFFECTIVE_DT').val(value.NEP_EFFECTIVE_DT);
            }
        });

    });

}

function updateSPTarget() {
    $.post('../sp/updatesptargetJS', {
        TRANS_ID: currentTrans_id,
        SP_CODE: currentSP,
        SERVICE_CODE: $('#eSERVICE_CODE').val(),
        EFFECTIVE_DT: $('#eEFFECTIVE_DT').val(),
        REVENUE_TARGET: $('#eREV_TARGET').val(),
        MINIMUM_GUARENTEE: $('#eMINIMUM_GUARENTEE').val()

    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            // location.reload();
            $('#editSPTargetModal').modal('hide');
            getsptargetList();
        }
    });

}
function delSPTarget(TRANS_ID) {
    if (confirm("Are you sure to delete!")) {
        $.post('../sp/deletesptargetJS', {
            TRANS_ID: TRANS_ID

        }, function (data) {
            alert(data);
            if (data.substring(0, 6) === "Record") {
                // location.reload();
                $('#editSPTargetModal').modal('hide');
                getsptargetList();
            }
        });

    } else {
        // action
    }

}

/*---------------------------------------------------------
 *  End of Target functional Scripts
 * **----------------------------------*/

/*---------------------------------------------------------
 *  Start of SP Service functional Scripts
 * **----------------------------------*/

// Pops dialog for SP Service
function ServiceSpAction(SP_CODE) {
    currentSP = SP_CODE;
    $('#sSP_CODE').val(SP_CODE);
    $('#esSP_CODE').val(SP_CODE);
    $('.modal-title')
            .html('SP Service Entry for Service Provider : ' + SP_CODE);
}

// Saves Service for SP_CODE
function savespservice() {
    debugger;
    $.post('../sp/savespserviceJS', {
        SP_CODE: currentSP,
        SERVICE_CODE: $('#sSERVICE_CODE').val(),
        ACTIVE_FLAG: $('#sACTIVE_FLAG').val(),
        ACTIVE_DT: $('#sACTIVE_DT').val(),
        DEACTIVATE_DT: $('#sDEACTIVATE_DT').val()

    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            // location.reload();
            $('#addServiceModal').modal('hide');
        }
    });

}

function getSPServiceList() {
    $
            .get(
                    '../sp/getSPserviceList',
                    {
                        SP_CODE: currentSP
                    },
                    function (response) {
                        //debugger;
                        if (response !== null) {
                            temp = $('#spservice').DataTable();
                            temp.clear().draw();

                            $
                                    .each(
                                            response,
                                            function (key, value) {
                                                $("#spservice")
                                                        .dataTable()
                                                        .fnAddData(
                                                                [
                                                                    value.SERVICE_CODE,
                                                                    value.ACTIVE_FLAG,
                                                                    value.NEP_ACTIVE_DT,
                                                                    value.NEP_DEACTIVE_DT,
                                                                    value.UPDATE_DT,
                                                                    "<a href = \"#\" data-toggle=\"modal\" data-target=\"#editSPServiceModal\" title=\"Edit\" onclick=\"return editSPService('"
                                                                            + value.TRANS_ID
                                                                            + "')\"<span class=\"fa fa-pencil-square-o\"></span></a>",
                                                                    "<a href=\"#\" data-toggle=\"modal\" data-target=\"#deleteModal\"  title=\"Delete\" onclick=\"return delSPService('"
                                                                            + value.TRANS_ID
                                                                            + "')\"<span class=\"fa fa-trash\"></span></a>"]); // closing
                                                // fnAddData()
                                            }// closing function(key,value)
                                    ); // closing each()
                        }// closing if()
                    }); // closing function(responseJson)

}

function editSPService(Trans_id) {
    currentTrans_id = Trans_id;
    $.get('../sp/getSPserviceList', {
        SP_CODE: currentSP
    }, function (response) {
        $.each(response, function (key, value) {
            if (value.TRANS_ID == Trans_id) {
                $('#esSP_CODE').val(currentSP);
                $('#esSERVICE_CODE').val(value.SERVICE_CODE);
                $('#esACTIVE_FLAG').val(value.ACTIVE_FLAG);
                $('#esACTIVE_DT').val(value.NEP_ACTIVE_DT);
                $('#esDEACTIVATE_DT').val(value.NEP_DEACTIVE_DT);
            }
        });

    });
}

function updateSPService() {
    $.post('../sp/updatespserviceJS', {
        TRANS_ID: currentTrans_id,
        SP_CODE: currentSP,
        SERVICE_CODE: $('#esSERVICE_CODE').val(),
        ACTIVE_FLAG: $('#esACTIVE_FLAG').val(),
        ACTIVE_DT: $('#esACTIVE_DT').val(),
        DEACTIVATE_DT: $('#esDEACTIVATE_DT').val()
    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            // location.reload();
            $('#editSPServiceModal').modal('hide');
            getSPServiceList();
        }
    });

}
