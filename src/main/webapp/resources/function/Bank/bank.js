$(document).ready(function() {
		$('.nepali-calendar').nepaliDatePicker();
});


function addbank() {
    //debugger;
    if ($('#BANK_CD') == null || $('#BANK_CD') == "" || $('#BANK_CD').val().length > 15) {
        alert('Invalid Bank Code!!!');
        return;
    }
    var CODE = $("#BANK_CD").val();
    var DESCRIPTION = $("#DESCRIPTION").val();
    
    $.post('../bank/save', {
    	BANK_CD: CODE,
    	BANK_NAME: DESCRIPTION,
    	BANK_ADDRESS: $('#ADDRESS').val(),
    	ACCT_NO :  $('#ACCTNO').val(),
    	ACCT_TYPE : $('#ACCTYPE').val(),
        ACT_FLAG :  $('#ACCTFLAG').val(),
        DEACTIVE_DT  : $('#DEACTIVE_DT').val()
        
    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            location.reload();
        }


    });



}


function editbank() {
    //debugger;

    $.post('../bank/update', {
    	BANK_CD: CODE,
    	BANK_NAME: $("#EDITDESCRIPTION").val(),
    	BANK_ADDRESS: $('#EDITADDRESS').val(),
    	ACCT_NO :  $('#EDITACCTNO').val(),
    	ACCT_TYPE : $('#EDITACCTYPE').val(),
        ACT_FLAG :  $('#EDITACCTFLAG').val(),
        DEACTIVE_DT  : $('#EDITDEACTIVE_DT').val()
        
    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            location.reload();
        }


    });



}

var CODE;
function editStream(code) {
    CODE = code;
    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == code) {
        	
        	$("#EDITBANK_CD").val(row[i][0]);
        	
        	$("#EDITDESCRIPTION").val(row[i][1]);
        	 $('#EDITADDRESS').val(row[i][2]),
        	 $('#EDITACCTNO').val(row[i][3]),
        	 $('#EDITACCTYPE').val(row[i][4]),
             $('#EDITACCTFLAG').val(row[i][5]),
             $('#EDITDEACTIVE_DT').val(row[i][6])
        	
        }
    }
}


var CODE;

function deleteStream(code) {
    CODE = code;
}
function del() {

    $.post('../bank/delete', {
    	bankcd: CODE
    }, function (data) {
        location.reload();
        alert(data);
    });
}

