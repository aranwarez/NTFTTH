var problemlist;
$(document).ready(function() {
	
	 $.get('../problem/JSlist', {
		
 }, function(response) {
 problemlist=response;
 // getProblemlist($('#serviceid').val());
 });

	 getservicetypes();
		
	
	 // if url parameter present
	 if(getURLParameter('CPE').length>0){
	$('#infotype').val('cpeSN');
	$('#info').val(getURLParameter('CPE'));
	
	getCustomerInfo();
	 }

});

function getURLParameter(name) {
	  return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
	}

function getCustomerInfo() {

	$.get('../complain/getCustomerInfo', {
		getaccCenList : "getlist",
		infotype : $('#infotype').val(),
		info : $('#info').val()
	}, function(response) {
		try{
			TEST = JSON.parse(response);
			$('#divcustomerinfo').fadeIn();
			
			$('#customerName').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.customerName);
			$('#Address').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.installAddress);
			$('#ContantNum').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.contactNum);
			$('#cpeMac').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.cpeInfo.cpeMac);
			$('#cpeSN').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.cpeInfo.cpeSN);
			$('#fapName').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.fapInfo.fapName);
			$('#fapPortName').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.fapPortInfo.fapPortName);
			$('#fapPortSpec').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.fapPortInfo.portSpec);
			$('#fapSerialNumber').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.fapPortInfo.serialNumber);
			// OLT
			$('#distribCblName').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.distribCblName);
			$('#distribCoreNo').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.distribCoreNo);
			$('#distribPortNo').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.distribPortNo);
			$('#fdcName').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.fdcName);
			$('#feederCblName').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.feederCblName);
			$('#feederCoreNo').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.feederCoreNo);
			$('#feederPortNo').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.feederPortNo);
			$('#l1SplitterNo').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.l1SplitterNo);
			$('#odfInPortOdfOutPort').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.odfInPortOdfOutPort);
			$('#odfInfo').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.odfInfo);
			$('#odfName').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.odfName);
			$('#odfNo').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.odfNo);
			$('#oltId').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.oltId);
			$('#oltInfo').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.oltInfo);
			$('#oltName').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.oltName);
			$('#oltType').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.oltType);
			// subscriber info
			
			$('#balanceOfCreditLimit').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.subsInfo.balanceOfCreditLimit);
			$('#offerName').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.subsInfo.offerName);
			$('#serviceNumber').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.subsInfo.serviceNumber);
			$('#status').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.subsInfo.status);
			$('#vasName').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.subsInfo.vasName);
			// $('#').html(TEST.);
			
		// getCustomerInfo(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.cpeInfo.cpeSN);
		}catch (e) {
			alert(response);
			}
		
		

	}); // closing function(responseJson)

}

function getProblemlist(serviceid){
// alert(serviceid);
	var select = $('#servicetypeid'+serviceid);
 
	select.find('option').remove();
  $('<option>').val("").text("Select").appendTo(select);
    $.each(problemlist, function (index, value) {
    	// alert(value.SERVICE_TYPE_ID);
    	if(value.SERVICE_TYPE_ID==serviceid){
        $('<option>').val(value.PROBLEM_ID).text(value.DESCRIPTION).appendTo(select);
    	}
    });
	
}

function getservicetypes(){
	$.get('../serviceType/jsonlist', {
		
	}, function(response) {
	// debugger;
		 $.each(response, function (index, value) {
			 
			 var appenddiv="<input class='cproblemid' id='cproblemid"+value.SERVICE_TYPE_ID+"' type='checkbox'>"+":"+value.DESCRIPTION+"-"+"<select id='servicetypeid"+value.SERVICE_TYPE_ID+"'></select><input type='text' id='remarksid"+value.SERVICE_TYPE_ID+"' class='form-control' placeholder='Remarks for "+value.DESCRIPTION+"'></BR>";
			 $('#complainservcies').append(appenddiv);	 
				
			 getProblemlist(value.SERVICE_TYPE_ID);
	
				
				
			
		 
		    });
		 
		
	});

	
}

function PostRegister(){
	if($('#cpeSN').html()==""){
		alert("Please enter customer infomation first!!!");
				return false;
		
	}
	
	var contactname=$('#customerName').html();
	var contactperno=$('#ContantNum').html();
	if($('#contactperno').val()!=""){
		contactperno=$('#contactperno').val();
	}
	
	if($('#contatper').val()!=""){
		contactname=$('#contatper').val();
	}
	
	if ($('.cproblemid:checked').length == 0) {
		if (confirm("No service has been checked!!! This will halted for FLMT!")) {
		} else {
			  return false;
			}
	}
	
			// generate arraylist for multiple services type
		
			var transnop = [];
			$(".cproblemid:checked").each(function() {
				var myobject = new Object();
				myobject.SERVICE_ID=$(this).attr('id').substring(10);
				myobject.PROBLEM_ID=$('#servicetypeid'+myobject.SERVICE_ID).val();
				myobject.REMARKS=$('#remarksid'+myobject.SERVICE_ID).val();
				
				transnop.push(myobject);
			});
			
			
			
			
			
			// arraylist generation till here-----------------------
			$.post('../complain/Register', {
				 Complain_no:contactperno,
				 SRV_NO : $('#cpeSN').html(),
				 Remarks: $('#userremark').val(),
				 contactName : contactname,
				 JSON:JSON.stringify(transnop),
				 fdcname:$('#fdcName').html()
				 	 }, function(response) {
			alert(response);
			 });
		
	// alert("No service has been checked!!! This will be");
		
	

// console.log(trasnop);
}



