var problemlist;
$(document).ready(function() {

	$.get('../problem/JSlist', {
		
	}, function(response) {
		problemlist=response;
			getProblemlist($('#serviceid').val());
	});

	

});



function getCustomerInfo() {

	$.get('../complain/getCustomerInfo', {
		getaccCenList : "getlist",
		infotype : $('#infotype').val(),
		info : $('#info').val()
	}, function(response) {
		console.log(response);
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
	var select = $('#problemid');
    select.find('option').remove();
 // $('<option>').val("").text("Select").appendTo(select);
    $.each(problemlist, function (index, value) {
    	// alert(value.SERVICE_TYPE_ID);
    	if(value.SERVICE_TYPE_ID==serviceid){
        $('<option>').val(value.PROBLEM_ID).text(value.DESCRIPTION).appendTo(select);
    	}
    });
	
}



