var currentdate;
var glbUser;
$(document).ready(function() {

	$('#checkDatatable').dataTable({
		"paging" : false,
		"searching" : false,
		"info" : false,
		"ordering" : false,
		"fixedHeader": true,
	});
	
	
	$.fn.dataTable.ext.errMode = 'none';
	viewUser(null,null,null,null);
	
	
});


function getEditMode() {

	var USER_ID = $("#USER_ID").val();

	$.get("../getuserfdc", {
		USER_ID : USER_ID
	}, function(data) {
		
		if (data.length == 0 ||data.length == undefined) {
			
			clearDataTable();
			return;
		}	
		
			$.each(data, function(index, value) {					
				checkDropDown(value.fdc_CODE,value.active_DT,value.deactive_DT);
				
		});
					
	
		// console.log(JSON.stringify(data));

	});
}
function getZone(){
	
	var REGION_CODE=$("#REGION_CODE").val();
	
	$.get("../getZoneByRegion", {
		REGION_CODE : REGION_CODE
	}, function(data) {
		
		 var select = $('#ZONE_CODE');
	        select.find('option').remove();
	        $('<option>').val("").text("SELECT Zone").appendTo(select);	        
	        $('#DISTRICT_CODE').find('option:not(:first)').remove();	        
	        $('#OFFICE_CODE').find('option:not(:first)').remove();	        
	        $('#OLT_CODE').find('option:not(:first)').remove();
	        
		if (data.length == 0 ||data.length == undefined) {
			
			clearDataTable();
			return;
		}	
       
		        $.each(data, function (index, value) {
		            $('<option>').val(value.ZONE_CODE).text(value.DESCRIPTION).appendTo(
		                    select);

		        });
					
		        
	});
	
}

function getDistrict(){
	
	var ZONE_CODE=$("#ZONE_CODE").val();
	
	$.get("../getDistrictByZone", {
		ZONE_CODE : ZONE_CODE
	}, function(data) {
		
		 var select = $('#DISTRICT_CODE');
	        select.find('option').remove();
	        $('<option>').val("").text("SELECT District").appendTo(select);
	        
	         
	        $('#OFFICE_CODE').find('option:not(:first)').remove();	        
	        $('#OLT_CODE').find('option:not(:first)').remove();
	        
	        
		if (data.length == 0 ||data.length == undefined) {
			
			clearDataTable();
			return;
		}	
       
		        $.each(data, function (index, value) {
		            $('<option>').val(value.DISTRICT_CODE).text(value.DESCRIPTION).appendTo(
		                    select);

		        });
					
		        
	});


}


function getOffice(){
	
	var DISTRICT_CODE=$("#DISTRICT_CODE").val();
	
	$.get("../getOfficeByDistrict", {
		DISTRICT_CODE : DISTRICT_CODE
	}, function(data) {
		
		var select = $('#OFFICE_CODE');
        select.find('option').remove();
        $('<option>').val("").text("SELECT Office").appendTo(select);
     	        
        $('#OLT_CODE').find('option:not(:first)').remove();
		if (data.length == 0 ||data.length == undefined) {
			
			clearDataTable();
			return;
		}	
        
		        $.each(data, function (index, value) {
		            $('<option>').val(value.OFFICE_CODE).text(value.DESCRIPTION).appendTo(
		                    select);

		        });
					
		        
	});
	
	
}


function getOLT(){
	
	var OFFICE_CODE=$("#OFFICE_CODE").val();
	
	$.get("../getOLTByOffice", {
		OFFICE_CODE : OFFICE_CODE
	}, function(data) {
		
		
		  var select = $('#OLT_CODE');
	        select.find('option').remove();
	        $('<option>').val("").text("SELECT OLT").appendTo(select);
	              
	        $('#OLT_CODE').find('option:not(:first)').remove();
		if (data.length == 0 ||data.length == undefined) {
			
			clearDataTable();
			return;
		}	
      
		        $.each(data, function (index, value) {
		            $('<option>').val(value.OLT_CODE).text(value.DESCRIPTION+"-"+value.OLT_CODE).appendTo(
		                    select);

		        });
					
		        
	});
	
}
function fetchView(){
	
	
//	var FDC_CODE=$("#FDC_CODE").val();	
	var REGION_CODE=$("#REGION_CODE").val();	
	var ZONE_CODE=$("#ZONE_CODE").val();	
	var DISTRICT_CODE=$("#DISTRICT_CODE").val();	
	var OFFICE_CODE=$("#OFFICE_CODE").val();	
	var OLT_CODE=$("#OLT_CODE").val();	
	var USER_ID=$("#USER_ID").val();
	
	var currentdate='';
	$.get('../currentNepaliDate', {getRegionList: "getlist"}, function (response) {
	    
		currentdate= response;
		
	});
	
	setTimeout(function () {
		viewFDC(currentdate,REGION_CODE,ZONE_CODE,DISTRICT_CODE,OFFICE_CODE,OLT_CODE);
		}, 1000);
	

	
	getEditMode();
	
}



function viewFDC(currentdate1,REGION_CODE,ZONE_CODE,DISTRICT_CODE,OFFICE_CODE,OLT_CODE){
	

	$.get("../getFDCByALL", {
		REGION_CODE : REGION_CODE,
		ZONE_CODE:ZONE_CODE,
		DISTRICT_CODE:DISTRICT_CODE,
		OFFICE_CODE:OFFICE_CODE,
		OLT_CODE:OLT_CODE
	}, function(data) {
		
//		console.log(data.length);
		 var t = $('#checkDatatable').DataTable();
         t.clear().draw();
         
		if (data.length == 0 ||data.length == undefined) {
			
			clearDataTable();
			return;
		}	
	     var counter = 1;
	    
		        $.each(data, function (index, value) {
		
			
		        var active='<input type="text" class="nepali-calendar form-control" id="ACTIVE_DT'+counter+'" value="'+''+'">';
		        var deactive='<input type="text" class="nepali-calendar form-control" id="DEACTIVE_DT'+counter+'">';
		            
		        var yesno='<select class="list'+counter+'"><option value="Y">Y</option><option value="N" selected>N</option></select>'
				var fdc = '<div class="checkbox"><label style="width: 360px"><input type="checkbox" class="mastersetup';				
				fdc +=counter+'" value="'+value.FDC_CODE+'" style="display:none">'+value.FDC_LOCATION+'('+value.FDC_CODE+')('+value.FDC+')</label></div>';

				
				
		                t.row.add( [
		                    counter,
		                    fdc,
		                    active,
		                    deactive,
		                    yesno
		                ] ).draw( false );
		                
		              
		                
		                $('#ACTIVE_DT'+counter).nepaliDatePicker();
		                $('#DEACTIVE_DT'+counter).nepaliDatePicker();
		           
		                
		                $('#ACTIVE_DT'+counter).val(currentdate1);
		                $('#DEACTIVE_DT'+counter).val(currentdate1);
		                
		                counter++;

		                
		        	
		        });
					
		        
	});
}

function viewUser(WORKING_REGION_CODE,WORKING_ZONE_CODE,WORKING_DIS_CODE,WORKING_OFFICE_CODE){
	
	$.get("../getUserByALL", {
		WORKING_REGION_CODE : WORKING_REGION_CODE,
		WORKING_ZONE_CODE:WORKING_ZONE_CODE,
		WORKING_DIS_CODE:WORKING_DIS_CODE,
		WORKING_OFFICE_CODE:WORKING_OFFICE_CODE
	}, function(data) {
		
//		console.log(data.length);
		 var select = $('#USER_ID');
	        select.find('option').remove();
	        $('<option>').val("").text("SELECT USER").appendTo(select);
		if (data.length == 0 ||data.length == undefined) {
			
			clearDataTable();
			return;
		}	
       
		       
		        $.each(data, function (index, value) {
//		        	if(value.LOCK_FLAG=='N'){	        		
		        	
		            $('<option>').val(value.USER_ID).text(value.FULL_NAME+"-"+value.USER_ID+"-"+value.WORKING_DISTRICT+"-"+value.MOBILE_NO).appendTo(
		                    select);

//		        	}
		        	
		        	
		        });
					
		        
	});
}
function saveUserFDC() {

	var table = $('#checkDatatable').dataTable();
	
//	alert(table.fnGetData().length);
	
	if($('#USER_ID').val()==''){
		alert('Please select user ');
		return;
	}
	
	// Get the total rows
	var inserteditdeletepost = [];
	for (i = 1; i <= table.fnGetData().length; i++) {
		var myobj = new Object();		
		if ($('#USER_ID').val() != null ) {	
			
//			console.log('user cha '+$('#USER_ID').val());
			
			if($('.list' + i).val()=='Y'){

			myobj.USER_ID = $("#USER_ID").val();
			myobj.FDC_CODE = $('.mastersetup' + i).val();
			myobj.ACTIVE_DT = $('#ACTIVE_DT' + i).val();
			myobj.DEACTIVE_DT = $('#DEACTIVE_DT' + i).val();
		
			myobj.LIST_FLAG = $('.list' + i).val();
			
			inserteditdeletepost.push(myobj);
		
			}
		}
	}

	// console.log(JSON.stringify(inserteditdeletepost));

	
	$.post("../saveuserfdc", {
		USER_ID : $("#USER_ID").val(),
		menu_mode : JSON.stringify(inserteditdeletepost)
	}, function(data) {
		alert(data);
		// getAllMenu();

	});

}
function clearDataTable() {
	var table = $('#checkDatatable').dataTable();
	var currentdate='';
	$.get('../currentNepaliDate', {getRegionList: "getlist"}, function (response) {
	    
		currentdate= response;
		
	});
	for (i = 1; i <= table.fnGetData().length; i++) {

		if ($('#USER_ID').val() != null) {

			$('#ACTIVE_DT' + i).val(currentdate);
			$('#DEACTIVE_DT' + i).val(currentdate);
			$('.list' + i).val('N');


		}
	}
}
function checkDropDown(fdcCode,activeDt,deactiveDt){
	
//	mastersetup1
	
var table = $('#checkDatatable').dataTable();
	
	for (i = 1; i <= table.fnGetData().length; i++) {
				
		if($('.mastersetup' + i).val()===fdcCode){
			
			$('.list' + i).val('Y');
			$('#ACTIVE_DT' + i).val(activeDt);
			$('#DEACTIVE_DT' + i).val(deactiveDt);
			
		}
			
		
	}
	
	
}
function checkFlag(flag){
	
var table = $('#checkDatatable').dataTable();
	
	for (i = 1; i <= table.fnGetData().length; i++) {
		$('.list' + i).val(flag);
			
	}
}