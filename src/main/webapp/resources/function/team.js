function saveTeam() {
    //debugger;
    if ($('#TEAM_CODE') == null || $('#TEAM_CODE') == "" || $('#TEAM_CODE').val().length > 8) {
        alert('Invalid TEAM_CODE!!!');
        return;
    }
    var CODE = $("#TEAM_CODE").val();
    var DESCRIPTION = $("#DESCRIPTION").val();
    

    $.post('../team/saveJS', {
    	TEAM_CODE: CODE,
        DESCRIPTION: DESCRIPTION
    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            location.reload();
        }


    });



}

var CODE;
function editTeam(code) {
    CODE = code;
    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == code) {

            $("#EDITTEAM_CODE").val(row[i][0]);
            $("#EDITDESCRIPTION").val(row[i][1]);
           
        }
    }
}

function updateTeam() {

    var DESCRIPTION = $("#EDITDESCRIPTION").val();
   

    $.post('../team/update', {
    	TEAM_CODE: CODE,
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

function deleteTeam(code) {
    CODE = code;
}
function del() {

    $.post('../team/delete', {
    	TEAM_CODE: CODE
    }, function (data) {
        location.reload();
        alert(data);
    });
}

