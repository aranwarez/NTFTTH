function addbank() {
    //debugger;
    if ($('#BANK_CD') == null || $('#BANK_CD') == "" || $('#BANK_CD').val().length > 15) {
        alert('Invalid Bank Code!!!');
        return;
    }
    var CODE = $("#BANK_CD").val();
    var DESCRIPTION = $("#DESCRIPTION").val();
    
    $.post('../stream/saveJS', {
    	BANK_CD: CODE,
    	BANK_NAME: DESCRIPTION,
        ADDRESS: $('#ADDRESS').val(),
        ACCTNO :  $('#ACCTNO').val(),
        ACCT_TYPE : $('#ACCTYPE').val(),
        ACT_FLAG :  $('#ACT_FLAG').val(),
        DEACTIVE_DT  : $('#DEACTIVE_DT').val()
        
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

            $("#EDITSTREAM_TYPE").val(row[i][0]);
            $("#EDITDESCRIPTION").val(row[i][1]);
        }
    }
}

function updateStream() {

    var DESCRIPTION = $("#EDITDESCRIPTION").val();
    
    $.post('../stream/update', {
        STREAM_TYPE: CODE,
        DESCRIPTION: DESCRIPTION
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

function deleteStream(code) {
    CODE = code;
}
function del() {

    $.post('../stream/delete', {
        STREAM_TYPE: CODE
    }, function (data) {
        location.reload();
        alert(data);
    });
}

