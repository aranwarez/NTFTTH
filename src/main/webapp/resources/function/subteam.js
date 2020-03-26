$(document).ready(function() {

	$('.nepali-calendar').nepaliDatePicker();
	
	getTeamList();

});

function getTeamList() {
   
    $.get('../team/jsonlist', {getlist: "servicelist"
    }, function (response) {
        var select = $('#TEAM_CODE,#EDITTEAM_CODE');
        select.find('option').remove();
        $('<option>').val("").text("SELECT").appendTo(select);
        $.each(response, function (index, value) {
            $('<option>').val(value.TEAM_CODE).text(value.DESCRIPTION).appendTo(
                    select);

        });

    });
}


function saveSubTeam() {
    //debugger;
    if ($('#SUB_TEAM_CODE') == null || $('#SUB_TEAM_CODE') == "" || $('#SUB_TEAM_CODE').val().length > 10) {
        alert('Invalid SUB_TEAM_CODE Type!!!');
        return;
    }
    var SUB_TEAM_CODE = $("#SUB_TEAM_CODE").val();
    var DESCRIPTION = $("#DESCRIPTION").val();
    var TEAM_CODE = $("#TEAM_CODE").val();
    
        
    $.post('../subteam/saveJS', {
    	SUB_TEAM_CODE: SUB_TEAM_CODE,
        DESCRIPTION: DESCRIPTION,
        TEAM_CODE: TEAM_CODE
    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            location.reload();
        }
    });
}

var CODE;
function editSubTeam(code) {
    CODE = code;
    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == code) {
            $("#EDITSUB_TEAM_CODE").val(row[i][0]);
            $("#EDITDESCRIPTION").val(row[i][1]);
            
            $("#EDITTEAM_CODE").val(row[i][2]);
            
        }
    }
}

function updateSubTeam() {
	
    var SUB_TEAM_CODE = $("#EDITSUB_TEAM_CODE").val();
    var DESCRIPTION = $("#EDITDESCRIPTION").val();
    
    var TEAM_CODE = $("#EDITTEAM_CODE").val();
    
    
    $.post('../subteam/update', {
    	SUB_TEAM_CODE: CODE,
        DESCRIPTION: DESCRIPTION,
        TEAM_CODE: TEAM_CODE
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

function deleteSubTeam(code) {
    CODE = code;
}
function del() {

    $.post('../subteam/delete', {
    	SUB_TEAM_CODE: CODE
    }, function (data) {
        location.reload();
        alert(data);
    });
}

