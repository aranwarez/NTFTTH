var problemlist;
var servicetypelist;
$(document).ready(function() {

	 getservicetypes();
	 // if url parameter present
	 
	 if(getURLParameter('CPE')!=null && getURLParameter('CPE').length>0){
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
			if($('#infotype').val()=="custId"){
				
			 	$('#serviceModal').modal('show');
				
				var table = $('#listservice').DataTable();
				 
				table
				    .clear()
				    .draw();
				

				$
						.each(
								TEST,
								function(key, value) {
									var anchor='<a target="_blank" href="../complain/list?CPE='+value.cpeSn+'" class="btn bg-green"> <i class="fa fa-edit"></i> '+value.cpeSn+' </a>'
									$("#listservice")
											.dataTable()
											.fnAddData(
													[anchor,
														value.customerName,
														value.serviceNumber
														 ]);
								});
			 	
			return;
			}
			
			
			
			
			
			
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
	
			// team info
			
			$('#teamName').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.fdcTeamDetail.teamName);
			$('#teamSupervisorContactNumber').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.fdcTeamDetail.teamSupervisorContactNumber);
			$('#teamSupervisorName').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.fdcTeamDetail.teamSupervisorName);
			$('#teamleaderContactNumber').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.fdcTeamDetail.teamleaderContactNumber);
			$('#teamleaderName').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.fdcTeamDetail.teamleaderName);
			
			// subscriber info
			
	// $('#balanceOfCreditLimit').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.subsInfo.balanceOfCreditLimit);
	// $('#offerName').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.subsInfo.offerName);
	// $('#serviceNumber').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.subsInfo.serviceNumber);
	// $('#status').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.subsInfo.status);
	// $('#vasName').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.subsInfo.vasName);
			// $('#').html(TEST.);
			
		// getCustomerInfo(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.cpeInfo.cpeSN);
			// get AAA data
			getstatusInfo(); 
			
			getSubsInfo();
		
		}catch (e) {
			console.log(e);
			alert(response);
			}
		
		

	}); // closing function(responseJson)

}

function getSubsInfo() {

	$.get('../complain/getSubsInfo', {
		cpeSn:$('#cpeSN').html()
	}, function(response) {
		try{
			TEST2 = JSON.parse(response);
			$('#divforsubsinfo').empty();
			
			$.each(TEST2, function (index, value) {
				
				var div='<div class="col-md-6"><table id="subsinfotable'+index+'" class="table table-condensed"><tbody><tr><td><label>balanceOfCreditLimit</label></td>		<td><span id="balanceOfCreditLimit'+index+'"></span></td>			</tr>				<tr>				<td><label>offerName </label></td><td><span id="offerName'+index+'"></span></td>		</tr>					<tr>						<td><label>serviceNumber </label></td>						<td><span id="serviceNumber'+index+'"></span></td>					</tr>					<tr>						<td><label>status</label></td>						<td><span id="status'+index+'"></span></td>					</tr>													</tbody>			</table>		</div>';				
			if((index+1)%2==0){
				 div='<div class="row"><div class="col-md-6"><table id="subsinfotable'+index+'" class="table table-condensed"><tbody><tr><td><label>balanceOfCreditLimit</label></td>		<td><span id="balanceOfCreditLimit'+index+'"></span></td>			</tr>				<tr>				<td><label>offerName </label></td><td><span id="offerName'+index+'"></span></td>		</tr>					<tr>						<td><label>serviceNumber </label></td>						<td><span id="serviceNumber'+index+'"></span></td>					</tr>					<tr>						<td><label>status</label></td>						<td><span id="status'+index+'"></span></td>					</tr>													</tbody>			</table>		</div>';				
				
				div=div+'</div>';
			}
				
				$('#divforsubsinfo').append(div);	
				var button=': <div class="btn-group"><button onclick="addservicecomplain(\''+value.serviceNumber+'\')" type="button" class="btn btn-box-tool"><i class="fa fa-plus"></i></button><button onclick="removeservicecomplain(\''+value.serviceNumber+'\')" type="button" class="btn btn-box-tool"><i class="fa fa-minus"></i></button></div>';
			$('#balanceOfCreditLimit'+index).html(value.balanceOfCreditLimit);
			$('#offerName'+index).html(value.offerName);
			$('#serviceNumber'+index).html(value.serviceNumber+button);
			$('#status'+index).html(value.status);

			$.each(value.vasName, function (indexvas, valuevas) {
				$('#subsinfotable'+index+' tr:last').after('<tr><td><label>VasName</label></td><td>'+valuevas+'</td></tr>');
			});	 
			
			
			 });	
			
			
					}catch (e) {
			alert(response);
			}
		
		

	}); // closing function(responseJson)

}

function getstatusInfo() {

	$.get('../complain/getStatusInfo', {
		cpeSn:$('#cpeSN').html()
	}, function(response) {
		try{
			TEST3 = JSON.parse(response);
			
			 
				
	$('#onuStatus').html(TEST3.onuStatus);
	$('#onuRxPower').html(TEST3.onuRxPower);
	$('#onuOltRxPower').html(TEST3.onuOltRxPower);
	$('#onuDistance').html(TEST3.onuDistance);
	$('#onuTemprature').html(TEST3.onuTemprature);
	// $('#').val(value.);
	
	if(TEST3.onuRxPower>=-25){
	 $('#onuRxPower').css('background-color', '#9FF781');
	}
	else if(TEST3.onuRxPower<-25 && TEST3.onuRxPower>=-28 ){
		$('#onuRxPower').css('background-color', '#FACC2E');		
		
	}
	else 	$('#onuRxPower').css('background-color', '#FE2E2E');
		
					}catch (e) {
			alert(response);
			}
		
		

	}); // closing function(responseJson)

}



function getProblemlist(serviceid,serno){
 // alert(serviceid);
	var select = $('#servicetypeid'+serno);
 
	select.find('option').remove();
  //$('<option>').val("").text("Select").appendTo(select);
    $.each(problemlist, function (index, value) {
    	// alert(value.SERVICE_TYPE_ID);
    	if(value.SERVICE_TYPE_ID==serviceid){
        $('<option>').val(value.PROBLEM_ID).text(value.DESCRIPTION).appendTo(select);
    	}
    });
	
}

function getservicetypes(){
	
	 $.get('../problem/JSlist', {
		
}, function(response) {
problemlist=response;
// getProblemlist($('#serviceid').val());
});

	
	$.get('../serviceType/jsonlist', {
		
	}, function(response) {
	// debugger;
		servicetypelist=response;
	
		
	});

	
}

function addservicecomplain(serno){
	if (($('.'+serno)[0])){
	alert('Complain for this service already added');
	}else{
		debugger;
	var servicetype;
	if(serno.length==9){servicetype=2;}
	else if(serno.substr(0,6)==='NTFTTH'){
		servicetype=1;
	}
	else if(serno.substr(0,6)==='NTTV'){
		servicetype=3;
	}

	
	 $.each(servicetypelist, function (index, value) {
	if(servicetype==value.SERVICE_TYPE_ID)	 
		 var appenddiv="<DIV class='input-group "+serno+"'><span class='input-group-addon'><input value="+value.SERVICE_TYPE_ID+" class='cproblemid' id='cproblemid"+serno+"' type='checkbox' checked=checked></span><label>"+value.DESCRIPTION+"("+serno+")</label><select class='form-control' id='servicetypeid"+serno+"'></select><input type='text' id='remarksid"+serno+"' class='form-control' placeholder='Remarks for "+value.DESCRIPTION+"'></DIV>";
		 $('#complainservcies').append(appenddiv);	 
			
		 getProblemlist(servicetype,serno);

			
			
		
	 
	    });
		

	}
}

function removeservicecomplain(serno){
	if ($('.'+serno)[0]){
		$('.'+serno).remove();
		// Do something if class exists
	}

}

function PostRegister(solved){
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
				myobject.SERVICE_NO=$(this).attr('id').substring(10);
				myobject.SERVICE_ID=$('#cproblemid'+myobject.SERVICE_NO).val();
				myobject.PROBLEM_ID=$('#servicetypeid'+myobject.SERVICE_NO).val();
				myobject.REMARKS=$('#remarksid'+myobject.SERVICE_NO).val();
				
				transnop.push(myobject);
			});
			
			console.log(transnop)
			
			//return false;
			var	urlpath='../complain/Register';
			// arraylist generation till here-----------------------
			$.post('../complain/Register', {
				 Complain_no:contactperno,
				 SRV_NO : $('#cpeSN').html(),
				 Remarks: $('#userremark').val(),
				 contactName : contactname,
				 JSON:JSON.stringify(transnop),
				 fdcname:$('#fdcName').html(),
				 teamname:$('#teamName').html(),
				 Supervisorname:$('#teamSupervisorName').html(),
				 SupervisorContno:$('#teamSupervisorContactNumber').html(),
				 Teamleader:$('#teamleaderName').html(),
				 TeamleaderNo:$('#teamleaderContactNumber').html(),
				 solved:solved
					 	 }, function(response) {
			alert(response);
			 });
		
	// alert("No service has been checked!!! This will be");
		
	

// console.log(trasnop);
}



