$(document).ready(function() {

	$('.nepali-calendar').nepaliDatePicker();
	
    

});
function saveSp() {
    //debugger;
    if ($('#SP_CODE') == null || $('#SP_CODE') == "" || $('#SP_CODE').val().length > 8) {
        alert('Invalid SP Type!!!');
        return;
    }
    var CODE = $("#SP_CODE").val();
    var SP_NAME = $("#SP_NAME").val();
    var SHORT_CODE = $("#SHORT_CODE").val();
    var ADDRESS = $("#ADDRESS").val();
    var CONTACT_PERSON = $("#CONTACT_PERSON").val();
    var TEL_NO = $("#TEL_NO").val();
    var MOBILE_NO = $("#MOBILE_NO").val();
    var EMAIL = $("#EMAIL").val();
    var SLDG_CODE = $("#SLDG_CODE").val();
    var PAN_NO = $("#PAN_NO").val();
    var CONTRACT_DT = $("#CONTRACT_DT").val();
    var CONTRACT_TER_DT = $("#CONTRACT_TER_DT").val();
	    $.post('../sp/saveJS', {
		SP_CODE : CODE,
		SP_NAME : SP_NAME,
		SHORT_CODE : SHORT_CODE,
		ADDRESS : ADDRESS,
		CONTACT_PERSON : CONTACT_PERSON,
		TEL_NO : TEL_NO,
		MOBILE_NO : MOBILE_NO,
		EMAIL : EMAIL,
		SLDG_CODE : SLDG_CODE,
		PAN_NO : PAN_NO,
		CONTRACT_DT : CONTRACT_DT,
		CONTRACT_TER_DT : CONTRACT_TER_DT
	}, function(data) {
		alert(data);
		if (data.substring(0, 6) === "Succes") {
			location.reload();
		}
	});
}

var CODE;
function editSp(code) {
    CODE = code;
    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == code) {

            $("#EDITSP_CODE").val(row[i][0]);
            $("#EDITSP_NAME").val(row[i][1]);
            $("#EDITSHORT_CODE").val(row[i][2]);
            $("#EDITADDRESS").val(row[i][3]);
            $("#EDITCONTACT_PERSON").val(row[i][4]);
            $("#EDITTEL_NO").val(row[i][5]);
            $("#EDITMOBILE_NO").val(row[i][6]);
            $("#EDITEMAIL").val(row[i][7]);
            $("#EDITPAN_NO").val(row[i][8]);
            $("#EDITCONTRACT_DT").val(row[i][9]);
            $("#EDITCONTRACT_TER_DT").val(row[i][10]);
            $("#EDITSLDG_CODE").val(row[i][11]);
        }
    }
}

function updateSp() {

    var SP_NAME = $("#EDITSP_NAME").val();
    var SHORT_CODE = $("#EDITSHORT_CODE").val();
    var ADDRESS = $("#EDITADDRESS").val();
    var CONTACT_PERSON = $("#EDITCONTACT_PERSON").val();
    var TEL_NO = $("#EDITTEL_NO").val();
    var MOBILE_NO = $("#EDITMOBILE_NO").val();
    var EMAIL = $("#EDITEMAIL").val();
    var SLDG_CODE = $("#EDITSLDG_CODE").val();
    var PAN_NO = $("#EDITPAN_NO").val();
    var CONTRACT_DT = $("#EDITCONTRACT_DT").val();
    var CONTRACT_TER_DT = $("#EDITCONTRACT_TER_DT").val();
    $.post('../sp/update', {
        SP_CODE: CODE,
        SP_NAME: SP_NAME,
        SHORT_CODE: SHORT_CODE,
        ADDRESS: ADDRESS,
        CONTACT_PERSON: CONTACT_PERSON,
        TEL_NO: TEL_NO,
        MOBILE_NO: MOBILE_NO,
        EMAIL: EMAIL,
        SLDG_CODE: SLDG_CODE,
        PAN_NO: PAN_NO,
        CONTRACT_DT: CONTRACT_DT,
        CONTRACT_TER_DT: CONTRACT_TER_DT
    }, function (data) {
        // location.reload();
        alert(data);
//        temp = $('#example1').DataTable();
//        temp.clear().draw();
        if (data.substring(0, 6) === "Succes") {
            location.reload();
        }


    });
}

var CODE;

function deleteSp(code) {
    CODE = code;
}
function del() {

    $.post('../sp/delete', {
        SP_CODE: CODE
    }, function (data) {
        location.reload();
        alert(data);
    });
}

