$(document).ready(function() {

	$('.nepali-calendar').nepaliDatePicker();
	
	getServiceTypeList();

});

function getSubTeamList(status){
	
	if(status=='Y'){
		var TEAM_CODE=$("#TEAM_CODE").val();
		
		 $.get('../subteamByTeam/jsonlist', {TEAM_CODE: TEAM_CODE
		    }, function (response) {
		        var select = $('#SUB_TEAM_CODE');
		        select.find('option').remove();
		        $('<option>').val("").text("SELECT Sub Team ").appendTo(select);
		        $.each(response, function (index, value) {
		            $('<option>').val(value.SUB_TEAM_CODE).text(value.DESCRIPTION+'-'+value.SUB_TEAM_CODE).appendTo(
		                    select);

		        });

		    });
	}
	else{
		var TEAM_CODE=$("#EDITTEAM_CODE").val();
		
		 $.get('../subteamByTeam/jsonlist', {TEAM_CODE: TEAM_CODE
		    }, function (response) {
		        var select = $('#EDITSUB_TEAM_CODE');
		        select.find('option').remove();
		        $('<option>').val("").text("SELECT Sub Team ").appendTo(select);
		        $.each(response, function (index, value) {
		            $('<option>').val(value.SUB_TEAM_CODE).text(value.DESCRIPTION+'-'+value.SUB_TEAM_CODE).appendTo(
		                    select);

		        });

		    });
	}

	 
	 
	
}


function getServiceTypeList() {
   
    $.get('../serviceType/jsonlist', {getlist: "servicelist"
    }, function (response) {
        var select = $('#SERVICE_TYPE_ID,#EDITSERVICE_TYPE_ID');
        select.find('option').remove();
        $('<option>').val("").text("SELECT Service Type").appendTo(select);
        $.each(response, function (index, value) {
            $('<option>').val(value.SERVICE_TYPE_ID).text(value.DESCRIPTION).appendTo(
                    select);

        });

    });
}

function saveProblem() {
    //debugger;
//    if ($('#ITEM_CODE') == null || $('#ITEM_CODE') == "" || $('#ITEM_CODE').val().length > 10) {
//        alert('Invalid Item Type!!!');
//        return;
//    }
	
    var DESCRIPTION = $("#DESCRIPTION").val();    
	 var SERVICE_TYPE_ID = $("#SERVICE_TYPE_ID").val(); 
	 
    var ACTIVE_DT = $("#ACTIVE_DT").val();
    var DEACTIVE_DT = $("#DEACTIVE_DT").val();
    
    var ACTIVE_STATUS = $("#ACTIVE_STATUS").val();
 
    var ACTIVE_STATUS = $("#ACTIVE_STATUS").val();
    var SUB_TEAM_CODE = $("#SUB_TEAM_CODE").val();
    
    
    $.post('../problem/saveJS', {    	
    	DESCRIPTION: DESCRIPTION,
    	SERVICE_TYPE_ID: SERVICE_TYPE_ID,
    	ACTIVE_DT: ACTIVE_DT,
    	DEACTIVE_DT: DEACTIVE_DT,
    	ACTIVE_STATUS: ACTIVE_STATUS,
    	SUB_TEAM_CODE:SUB_TEAM_CODE
        
    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            location.reload();
        }
    });
}

var CODE;
function editProblem(code,SERVICE_TYPE_ID) {
    CODE = code;  
    $("#EDITTEAM_CODE").val('');
    
    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == code) {
        	
        	$("#EDITDESCRIPTION").val(row[i][1]);        	
        	$("#EDITSERVICE_TYPE_ID").val(SERVICE_TYPE_ID);   
        	
        	//        	 $("#EDITSUB_TEAM_CODE").val();
        	 
        	 
        	 var sbtm = $('#EDITSUB_TEAM_CODE');
        	 sbtm.find('option').remove();
             $('<option>').val(row[i][3]).text(row[i][3]).appendTo(sbtm);
            
             
        	$("#EDITACTIVE_DT").val(row[i][4]);
            $("#EDITDEACTIVE_DT").val(row[i][5]);            
            $("#EDITACTIVE_STATUS").val(row[i][6]);
            
            
            
        }
    }
}

function updateProblem() {
	
    var DESCRIPTION = $("#EDITDESCRIPTION").val();    
    var SERVICE_TYPE_ID = $("#EDITSERVICE_TYPE_ID").val(); 
	 
   var ACTIVE_DT = $("#EDITACTIVE_DT").val();
   var DEACTIVE_DT = $("#DEACTIVE_DT").val();
   
   var ACTIVE_STATUS = $("#EDITACTIVE_STATUS").val();
   var SUB_TEAM_CODE = $("#EDITSUB_TEAM_CODE").val();
    
    $.post('../problem/update', {
    	PROBLEM_ID:CODE,
    	DESCRIPTION:DESCRIPTION,
    	SERVICE_TYPE_ID: SERVICE_TYPE_ID,    	
    	ACTIVE_DT: ACTIVE_DT,
    	DEACTIVE_DT: DEACTIVE_DT,
        ACTIVE_DT: ACTIVE_DT,
        DEACTIVE_DT: DEACTIVE_DT,
        ACTIVE_STATUS: ACTIVE_STATUS,
        SUB_TEAM_CODE:SUB_TEAM_CODE
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

function deleteProblem(code) {
    CODE = code;
}
function del() {

    $.post('../problem/delete', {
    	PROBLEM_ID: CODE
    }, function (data) {
        location.reload();
        alert(data);
    });
}

