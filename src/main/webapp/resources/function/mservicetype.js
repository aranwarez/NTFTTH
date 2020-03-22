$(document).ready(function() {

	$('.nepali-calendar').nepaliDatePicker();
	
	getServiceList();

});

function getServiceList() {
   
    $.get('../service/jsonlist', {getlist: "servicelist"
    }, function (response) {
        var select = $('#SERVICE_ID,#EDITSERVICE_ID');
        select.find('option').remove();
        $('<option>').val("").text("SELECT").appendTo(select);
        $.each(response, function (index, value) {
            $('<option>').val(value.SERVICE_ID).text(value.DESCRIPTION).appendTo(
                    select);

        });

    });
}

function saveServiceType() {
    //debugger;
//    if ($('#ITEM_CODE') == null || $('#ITEM_CODE') == "" || $('#ITEM_CODE').val().length > 10) {
//        alert('Invalid Item Type!!!');
//        return;
//    }
	 var SERVICE_ID = $("#SERVICE_ID").val(); 
    var DESCRIPTION = $("#DESCRIPTION").val();
    var SHORT_CODE = $("#SHORT_CODE").val();
    
    var ACTIVE_DT = $("#ACTIVE_DT").val();
    var DEACTIVE_DT = $("#DEACTIVE_DT").val();
    
    var ACTIVE_STATUS = $("#ACTIVE_STATUS").val();
 
   
    $.post('../serviceType/saveJS', {
    	SERVICE_ID:SERVICE_ID,
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
function editService(code,SERVICE_ID) {
    CODE = code;
     
    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == code) {
        	
        	$("#EDITSERVICE_ID").val(SERVICE_ID);
        	
            $("#EDITDESCRIPTION").val(row[i][2]);
            $("#EDITSHORT_CODE").val(row[i][3]);
            $("#EDITACTIVE_DT").val(row[i][4]);
            $("#EDITDEACTIVE_DT").val(row[i][5]);            
            $("#EDITACTIVE_STATUS").val(row[i][6]);
            
        }
    }
}

function updateServiceType() {
	
	var SERVICE_ID = $("#EDITSERVICE_ID").val();
    var DESCRIPTION = $("#EDITDESCRIPTION").val();
    var SHORT_CODE = $("#EDITSHORT_CODE").val();
    
    var ACTIVE_DT = $("#EDITACTIVE_DT").val();
    
    var DEACTIVE_DT = $("#EDITDEACTIVE_DT").val();
    var ACTIVE_STATUS = $("#EDITACTIVE_STATUS").val();
    
    $.post('../servicetype/update', {
    	SERVICE_TYPE_ID: CODE,
    	SERVICE_ID: SERVICE_ID,
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

    $.post('../serviceType/delete', {
    	SERVICE_TYPE_ID: CODE
    }, function (data) {
        location.reload();
        alert(data);
    });
}

