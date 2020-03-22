$(document).ready(function() {

	$('.nepali-calendar').nepaliDatePicker();
	
    

});
function saveService() {
    //debugger;
//    if ($('#ITEM_CODE') == null || $('#ITEM_CODE') == "" || $('#ITEM_CODE').val().length > 10) {
//        alert('Invalid Item Type!!!');
//        return;
//    }
 
    var DESCRIPTION = $("#DESCRIPTION").val();
    var SHORT_CODE = $("#SHORT_CODE").val();
    
    var ACTIVE_DT = $("#ACTIVE_DT").val();
    var DEACTIVE_DT = $("#DEACTIVE_DT").val();
    
    var ACTIVE_STATUS = $("#ACTIVE_STATUS").val();
 
   
    $.post('../service/saveJS', {
    	DESCRIPTION: DESCRIPTION,
    	SHORT_CODE: SHORT_CODE,
    	ACTIVE_DT: ACTIVE_DT,
    	DEACTIVE_DT: DEACTIVE_DT,
    	ACTIVE_STATUS: ACTIVE_STATUS
        
    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            location.reload();
        }
    });
}

var CODE;
function editService(code) {
    CODE = code;
    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == code) {
            $("#EDITDESCRIPTION").val(row[i][1]);
            $("#EDITSHORT_CODE").val(row[i][2]);
            $("#EDITACTIVE_DT").val(row[i][3]);
            $("#EDITDEACTIVE_DT").val(row[i][4]);            
            $("#EDITACTIVE_STATUS").val(row[i][5]);
            
        }
    }
}

function updateService() {
    var DESCRIPTION = $("#EDITDESCRIPTION").val();
    var SHORT_CODE = $("#EDITSHORT_CODE").val();
    
    var ACTIVE_DT = $("#EDITACTIVE_DT").val();
    
    var DEACTIVE_DT = $("#EDITDEACTIVE_DT").val();
    var ACTIVE_STATUS = $("#EDITACTIVE_STATUS").val();
    
    $.post('../service/update', {
    	SERVICE_ID: CODE,
        DESCRIPTION: DESCRIPTION,
        SHORT_CODE: SHORT_CODE,
        ACTIVE_DT: ACTIVE_DT,
        DEACTIVE_DT: DEACTIVE_DT,
        ACTIVE_STATUS: ACTIVE_STATUS
       
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

function deleteService(code) {
    CODE = code;
}
function del() {

    $.post('../service/delete', {
    	SERVICE_ID: CODE
    }, function (data) {
        location.reload();
        alert(data);
    });
}

