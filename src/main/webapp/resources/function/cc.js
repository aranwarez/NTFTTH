

var regionlist;
$(document).ready(function () {
    jQuery.ajaxSetup({async: false});
    $("#example1").dataTable();
    $.fn.dataTable.ext.errMode='none';
    getRegionData();
    getOfficeList();

});
function getRegionData() {
    jQuery.ajaxSetup({async: false});
    $.get('../region/getlist', {getRegionList: "getlist"}, function (response) {
        regionlist = response;
        var select = $('#REGION_CODE,#DOREGION_CODE,#EDITREGION_CODE');
        select.find('option').remove();
        $('<option>').val("").text("Select").appendTo(select);
        $.each(response, function (index, value) {
            $('<option>').val(value.region_CODE).text(value.description + " (" + value.region_CODE + ")").appendTo(select);
        });
    });

}

function getAccountCenter() {
    jQuery.ajaxSetup({async: false});
    var REGION_CODE = $('#REGION_CODE,#DOREGION_CODE').val();

    $.get('../acccenter/getlist', {getaccCenList: "getlist", REGION_CODE: REGION_CODE}, function (response) {

        var select = $('#ACC_CEN_CODE,#DOACC_CEN_CODE');
        select.find('option').remove();
        $('<option>').val("").text("Select").appendTo(select);
        $.each(response, function (index, value) {
            $('<option>').val(value.acc_CEN_CODE).text(value.description + " (" + value.acc_CEN_CODE + ")").appendTo(select);
        });

    }); // closing function(responseJson)
}
function getOfficeList() {
    jQuery.ajaxSetup({async: false});
    $.get('../PbimOfficeServlet', {getallList: "getlist"}, function (response) {
        regionlist = response;
        var select = $('#OFFICE_CODE');
        var editselect = $('#EDITOFFICE_CODE');
        select.find('option').remove();
        $('<option>').val("").text("Select").appendTo(select);
        $.each(response, function (index, value) {
            $('<option>').val(value.OFFICE_CODE).text(value.DESCRIPTION).appendTo(select);
        });
        // for edit select button
        editselect.find('option').remove();
        $('<option>').val("").text("Select").appendTo(editselect);
        $.each(response, function (index, value) {
            $('<option>').val(value.OFFICE_CODE).text(value.DESCRIPTION).appendTo(editselect);
        });

    });

}
function getDOAccountCenter() {

    jQuery.ajaxSetup({async: false});

    var REGION_CODE = $('#DOREGION_CODE').val();

    $.get('../AccCenterServlet', {getaccCenList: "getlist", REGION_CODE: REGION_CODE}, function (response) {

        var select = $('#DOACC_CEN_CODE');
        select.find('option').remove();
        $('<option>').val("").text("Select").appendTo(select);
        $.each(response, function (index, value) {
            $('<option>').val(value.ACC_CEN_CODE).text(value.DESCRIPTION + " (" + value.ACC_CEN_CODE + ")").appendTo(select);
        });

    }); // closing function(responseJson)
}
function getEDITAccountCenter() {

    jQuery.ajaxSetup({async: false});

    var REGION_CODE = $('#EDITREGION_CODE').val();

    $.get('../AccCenterServlet', {getaccCenList: "getlist", REGION_CODE: REGION_CODE}, function (response) {

        var select = $('#EDITACC_CEN_CODE');
        select.find('option').remove();
        $('<option>').val("").text("Select").appendTo(select);
        $.each(response, function (index, value) {
            $('<option>').val(value.ACC_CEN_CODE).text(value.DESCRIPTION + " (" + value.ACC_CEN_CODE + ")").appendTo(select);
        });

    }); // closing function(responseJson)
}


function getCC() {
    jQuery.ajaxSetup({async: true});

    var ACC_CEN_CODE = $("#ACC_CEN_CODE,#DOACC_CEN_CODE").val();

    jQuery.ajaxSetup({async: false});
    
    $.get('../cc/getlist', {getCCList: "getlist", ACC_CEN_CODE: ACC_CEN_CODE}, function (responseJson) {

        if (responseJson !== null) {
            temp = $('#example1').DataTable();
            temp
                    .clear()
                    .draw();

            $.each(responseJson, function (key, value) {
                $("#example1").dataTable().fnAddData([
                    value.cc_CODE,
                    value.description,
                    value.erp_CC_CD,
                    value.acc_CEN_CODE,
                    value.cc_TYPE,
                    value.office_CODE,
                    "<a href = \"#\" data-toggle=\"modal\" data-target=\"#editModal\" title=\"Edit\" onclick=\"return editCC('" + value.cc_CODE + "')\"<span class=\"fa fa-pencil-square-o\"></span></a>",
                    "<a href=\"#\" data-toggle=\"modal\" data-target=\"#deleteModal\"  title=\"Delete\" onclick=\"return delCC('" + value.cc_CODE + "')\"<span class=\"fa fa-trash\"></span></a>"]); // closing
																																																		// fnAddData()
            }// closing function(key,value)
            ); // closing each()
        }// closing if()
    }); // closing function(responseJson)
}



/*
 * dialog- open
 * 
 */
function getDOCC() {
    jQuery.ajaxSetup({async: true});

    var ACC_CEN_CODE = $("#DOACC_CEN_CODE").val();

    jQuery.ajaxSetup({async: false});
    $.get('../CCServlet', {getCCList: "getlist", ACC_CEN_CODE: ACC_CEN_CODE}, function (responseJson) {

        if (responseJson !== null) {
            temp = $('#example1').DataTable();
            temp
                    .clear()
                    .draw();

            $.each(responseJson, function (key, value) {
                $("#example1").dataTable().fnAddData([
                    value.CC_CODE,
                    value.DESCRIPTION,
                    value.ERP_CC_CD,
                    value.ACC_CEN_CODE,
                    value.CC_TYPE,
                    value.OFFICE_CODE,
                    "<a href = \"#\" data-toggle=\"modal\" data-target=\"#editModal\" title=\"Edit\" onclick=\"return editCC('" + value.CC_CODE + "')\"<span class=\"fa fa-pencil-square-o\"></span></a>" +
                            "<a href=\"#\" data-toggle=\"modal\" data-target=\"#deleteModal\"  title=\"Delete\" onclick=\"return delCC('" + value.CC_CODE + "')\"<span class=\"fa fa-trash\"></span></a>"]); // closing
																																																				// fnAddData()
            }// closing function(key,value)
            ); // closing each()
        }// closing if()
    }); // closing function(responseJson)
}

/*
 * end dialog- open box
 * 
 */
function saveCC() {
    if ($('#CC_CODE').val().length > 8) {
        alert('CC CODE Character max limit is 8.');
        return;
    }
    var ACC_CEN_CODE = $("#DOACC_CEN_CODE").val();
    if (ACC_CEN_CODE) {
        $.post('../CCServlet', {save: "saveCC", ACC_CEN_CODE: ACC_CEN_CODE, CC_CODE: $('#CC_CODE').val(),
            DESCRIPTION: $("#DESCRIPTION").val(), ERP_CC_CD: $("#ERP_CC_CD").val(), CC_TYPE: $("#CC_TYPE").val(), OFFICE_CODE: $("#OFFICE_CODE").val()

        }, function (data) {
            alert(data);

            getCC();


        });
    }
}
var CC_CODE;


function editCC(code) {
    CC_CODE = code;
    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == code) {

            $("#EDITCC_CODE").val(row[i][0]);
            $("#EDITDESCRIPTION").val(row[i][1]);
            $("#EDITERP_CC_CD").val(row[i][2]);
            $("#EDITACC_CEN_CODE").val(row[i][3]);
            $("#EDITCC_TYPE").val(row[i][4]);
            $("#EDITOFFICE_CODE").val(row[i][5]);

        }

    }
    $("#EDITREGION_CODE").val($("#REGION_CODE").val());

    var REGION_CODE = $('#EDITREGION_CODE').val();



    $.get('../AccCenterServlet', {getaccCenList: "getlist", REGION_CODE: REGION_CODE}, function (response) {

        var select = $('#EDITACC_CEN_CODE');
        select.find('option').remove();
        $('<option>').val("").text("Select").appendTo(select);
        $.each(response, function (index, value) {
            $('<option>').val(value.ACC_CEN_CODE).text(value.DESCRIPTION + " (" + value.ACC_CEN_CODE + ")").appendTo(select);
        });

    }); // closing function(responseJson)

    $("#EDITACC_CEN_CODE").val($("#ACC_CEN_CODE").val());
}
function updateCollectioncenter() {
    var ACC_CEN_CODE = $("#EDITACC_CEN_CODE").val();
    if (ACC_CEN_CODE) {


        $.post('../CCServlet', {update: "updateCC", ACC_CEN_CODE: ACC_CEN_CODE, CC_CODE: $("#EDITCC_CODE").val(),
            DESCRIPTION: $("#EDITDESCRIPTION").val(), ERP_CC_CD: $("#EDITERP_CC_CD").val(), CC_TYPE: $("#EDITCC_TYPE").val(), OFFICE_CODE: $("#EDITOFFICE_CODE").val()}, function (data) {
            temp = $('#example1').DataTable();
            temp
                    .clear()
                    .draw();
            alert(data);
            getCC();

        });
    }
}
function delCC(code) {
    CC_CODE = code;

}
function del() {

    $.post('../CCServlet', {delete: "deleteCC_CODE", CC_CODE: CC_CODE}, function (data) {
        temp = $('#example1').DataTable();
        temp
                .clear()
                .draw();
        alert(data);
        getCC();

    });
}



function Dialogopen() {

    $("#DOREGION_CODE").val($("#REGION_CODE").val());
    $("#DOACC_CEN_CODE").val($("#ACC_CEN_CODE").val());


}
