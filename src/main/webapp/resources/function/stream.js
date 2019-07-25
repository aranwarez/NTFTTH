function saveStream() {
    //debugger;
    if ($('#STREAM_TYPE') == null || $('#STREAM_TYPE') == "" || $('#STREAM_TYPE').val().length > 8) {
        alert('Invalid Stream Type!!!');
        return;
    }
    var CODE = $("#STREAM_TYPE").val();
    var DESCRIPTION = $("#DESCRIPTION").val();
    
    $.post('../stream/saveJS', {
        STREAM_TYPE: CODE,
        DESCRIPTION: DESCRIPTION
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

