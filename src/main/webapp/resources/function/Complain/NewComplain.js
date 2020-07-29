var datanum;
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
	 
	 // set max date for AAA as today
	 var today = new Date();
	 var dd = today.getDate();
	 var mm = today.getMonth()+1; // January is 0!
	 var yyyy = today.getFullYear();
	  if(dd<10){
	         dd='0'+dd
	     } 
	     if(mm<10){
	         mm='0'+mm
	     } 

	 today = yyyy+'-'+mm+'-'+dd;
	 document.getElementById("AAAdatepicker").setAttribute("max", today);

	 
	 // -- till here
	 
	 

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
			
			
			else if($('#infotype').val()=="ftthVoiceNum" && TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.nil){
				alert("No result found..");
				return;
			}
			else if ($('#infotype').val()=="cpeSN" && TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.customerName.nil){
				alert("No result found..")
				return;
			}
			
			
			
			$('#divcustomerinfo').fadeIn();
			$('#customerName').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.customerName);
			$('#Address').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.installAddress);
			$('#ContantNum').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.contactNum);
			$('#cpeMac').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.cpeInfo.cpeMac);
			$('#cpeSN').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.cpeInfo.cpeSN);
			$('#CPEserial').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.cpeInfo.cpeSN);
			
			
			$('#fapName').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.fapInfo.fapName);
			$('#fapPortName').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.fapPortInfo.fapPortName);
			$('#fapPortSpec').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.fapPortInfo.portSpec);
			$('#fapSerialNumber').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.fapPortInfo.serialNumber);
			// OLT
			$('#distribCblName').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.distribCblName);
			$('#distribCoreNo').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.distribCoreNo);
			$('#distribPortNo').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.distribPortNo);
			$('#fdcName').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.fdcName);
			$('#feederCblName').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.feederCblName);
			$('#feederCoreNo').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.feederCoreNo);
			$('#feederPortNo').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.feederPortNo);
			$('#l1SplitterNo').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.l1SplitterNo);
			$('#odfInPortOdfOutPort').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.odfInPortOdfOutPort);
			$('#odfInfo').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.odfInfo);
			$('#odfName').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.odfName);
			$('#odfNo').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.odfNo);
			$('#oltId').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.oltId);
			$('#oltInfo').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.oltInfo);
			$('#oltName').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.oltName);
			$('#oltType').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.oltOdfFdcInfo.oltType);
	
			// fap info
			$('#faplocation').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.fapInfo.location);
			$('#Longitude').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.fapInfo.longitude);
			$('#Latitude').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.fapInfo.latitude);
			
			
			
			// team info
			
			$('#teamName').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.fdcTeamDetail.teamName);
			$('#teamSupervisorContactNumber').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.fdcTeamDetail.teamSupervisorContactNumber);
			$('#teamSupervisorName').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.fdcTeamDetail.teamSupervisorName);
			$('#teamleaderContactNumber').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.fdcTeamDetail.teamleaderContactNumber);
			$('#teamleaderName').html(TEST.Body.queryServiceNumberCPEInfosResponse.queryServiceNumberCPEInfosResult.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.fdcTeamDetail.teamleaderName);
			
			// subscriber info
			getSubsInfo(TEST.subsinfo);
	
			
			// $('#balanceOfCreditLimit').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.subsInfo.balanceOfCreditLimit);
	// $('#offerName').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.subsInfo.offerName);
	// $('#serviceNumber').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.subsInfo.serviceNumber);
	// $('#status').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.subsInfo.status);
	// $('#vasName').html(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.subsInfo.vasName);
			// $('#').html(TEST.);
			
		// getCustomerInfo(TEST.Body.queryServiceNumberCPEInfosResponse.return.serviceNumberCPEInfosRsp.serviceNumberCPEEptInfo.resourceView.cpeInfo.cpeSN);
			
			// clearing all contains from forms
			
			clearformcontains();
			
			// get AAA data
			getstatusInfo(); 
			
			
		
		}catch (e) {
			console.log(e);
			alert("Error:"+response);
			}
		
		

	}); // closing function(responseJson)

}

function clearformcontains(){
	// clearing CPE cpeoltstatusfield
	$('.cpeoltstatusfield').html("");
	
	// clearing complain cart
	$('#complainservcies').empty();
	// clearing AAA values
	
	$('.AAAinfield').html("");
	Date.prototype.toDateInputValue = (function() {
	    var local = new Date(this);
	    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
	    return local.toJSON().slice(0,10);
	});
	  $('#AAAdatepicker').val(new Date().toDateInputValue());
	// clearing TMS status
	$('#divforCPEinfo').empty();
	
	
	
}


function getAAAstatus(){
		$.get('../complain/getAAAstatus1', {
		FTTHDatanum : datanum
			}, function(response) {
				console.log(response);
				$('#planAAA').html(response.plan);
				$('#calleridAAA').html(response.fcallerid);
				$('#loginIPAAA').html(response.loginip);
				$('#startimeidAAA').html(response.starttime);
				$('#calleridactAAA').html(response.callerid);
				$('#useridAAA').html(response.userid);
				$('#AAAuserid').html(response.oldUSERID);
			});
}


function getAAAAuthenticationlog(){
	if($('#AAAuserid').html()==null || $('#AAAuserid').html()==""){
		alert('UserID not found for NTDATA');
		return;
	}
	var asd=$('#AAAdatepicker').val();
	var mmdd=asd.substr(5,2)+asd.substr(8,2);
	
	$.get('../complain/getAAAstatusviewAuthenticationlog', {
		FTTHDatanum : $('#AAAuserid').html(),
		mmdd:mmdd
			}, function(response) {
				$('#AAAviewlogdiv').empty();
				var tableappend="<table id='AAAviewlogtable' style='width:100%' class='table table-condensed'><thead><tr><td>Access Time</td><td>CallerID</td><td>Result</td></tr></thread></table>"
					$('#AAAviewlogdiv').append(tableappend);
					var table=$("#AAAviewlogtable").DataTable();
				table
			    .clear()
			    .draw();
				$.each(response, function (index, value) {
					var result;
					if(value.result=="0"){
						result='Authentication Successful';
					}
					else if(value.result=="4001"){
						result='Wrong Password';
					}
					else if(value.result=="4002"){
						result='Account Inactive';
						
					}else if(value.result=="4003"){
						result='Account Expired';
						
					}else if(value.result=="4004"){
						result='Account Locked';
					}else if(value.result=="4005"){
						result='Account Transfer';
					}else if(value.result=="4006"){
						result='Unauthorized Caller ID';
					}else if(value.result=="4008"){
						result='Subscriber Not Exist';
					}else if(value.result=="4010"){
						result='No Free Resource';
					}
					else result=value.result;
				      
				      
					$("#AAAviewlogtable")
					.dataTable()
					.fnAddData(
							[
								value.accesstime,
								value.calling_station_id,
								result
								 ]);
					
					
				});
				
			});
	
}

function getAAAAccountinglog(){
	if($('#AAAuserid').html()==null || $('#AAAuserid').html()==""){
		alert('UserID not found for NTDATA');
		return;
	}
	var asd=$('#AAAdatepicker').val();
	var mmdd=asd.substr(5,2)+asd.substr(8,2);
	
	$.get('../complain/getAAAstatusviewAccountinglog', {
		FTTHDatanum : $('#AAAuserid').html(),
		mmdd:mmdd
			}, function(response) {
				$('#AAAviewlogdiv').empty();
				var tableappend="<table id='AAAviewlogtable' style='width:100%' class='table table-condensed'><thead><tr><td>starttime</td><td>stoptime</td><td>Time Diff</td><td>Total Volume(MB)</td><td>Termination Cause</td></tr></thread></table>"
					$('#AAAviewlogdiv').append(tableappend);
					var table=$("#AAAviewlogtable").DataTable();
				table
			    .clear()
			    .draw();
				$.each(response, function (index, value) {
					var result;
					 debugger;
					if(value.terminate_cause=="1" || value.terminate_cause=="2" || value.terminate_cause=="3"){
						result='User Side: Line SNR or Router Power Problem';
					}
					else if(value.terminate_cause=="4"){
						result='Idle TimeOut';
					}
					else if(value.terminate_cause=="5"){
						result='Normal Session Out';
						
					}
					else result="Other";
				      
				     
					$("#AAAviewlogtable")
					.dataTable()
					.fnAddData(
							[
								jsondatetonormale(value.starttime)
								,
								jsondatetonormale(value.stoptime),
								gettimediff(value.starttime,value.stoptime),
								
								(Number(value.total_volume)/(1024*1024)).toFixed(2),
								result
								 ]);
					
					
				});
				
			});
	
}



function getSubsInfo(subsinfo) {
		try{
			
			$('#divforsubsinfo').empty();
			
			$.each(subsinfo, function (index, value) {
				// checking if DATA service available in subsinfo and calling
				// AAA status
				if(value.serviceNumber.substr(0,6)==='NTFTTH'){
				datanum=value.serviceNumber;
					getAAAstatus();
						}
				
				
				// till herechecking if DATA service available in subsinfo and
				// calling AAA status
				
				
				
				var div='<div class="col-md-6"><table id="subsinfotable'+index+'" class="table table-condensed"><tbody><tr><td><label>Service No. </label></td><td><span id="serviceNumber'+index+'"></span></td></tr><tr><td><label>offerName </label></td><td><span id="offerName'+index+'"></span></td>		</tr>  <tr><td><label>Service Type</label></td><td><span id="servicetype'+index+'"></span></td></tr> <tr><td><label>Status</label></td>						<td><span id="status'+index+'"></span></td>					</tr>	<tr><td><label id="labelbalanceid'+index+'">balanceOfCreditLimit</label></td>		<td><span id="balanceOfCreditLimit'+index+'"></span></td>			</tr></tbody>			</table>		</div>';						
			if((index+1)%2==0){
				 div='<div class="row"><div class="col-md-6"><table id="subsinfotable'+index+'" class="table table-condensed"><tbody><tr><td><label>Service No.  </label></td><td><span id="serviceNumber'+index+'"></span></td></tr><tr><td><label>offerName </label></td><td><span id="offerName'+index+'"></span></td>		</tr>  <tr><td><label>Service Type</label></td><td><span id="servicetype'+index+'"></span></td></tr> <tr><td><label>Status</label></td>						<td><span id="status'+index+'"></span></td>					</tr>	<tr><td><label id="labelbalanceid'+index+'">balanceOfCreditLimit</label></td>		<td><span id="balanceOfCreditLimit'+index+'"></span></td>			</tr></tbody>			</table>		</div>';				
				
				div=div+'</div>';
			}
				
				$('#divforsubsinfo').append(div);	
			// var button=': <div class="btn-group
			// complain'+value.serviceNumber+'"><input type="checkbox"
			// onchange="chkaddremoveservicecomplain(this)"
			// value="'+value.serviceNumber+'" /><button
			// onclick="addservicecomplain(\''+value.serviceNumber+'\')"
			// type="button" class="btn btn-box-tool"><i class="fa
			// fa-plus"></i></button><button
			// onclick="removeservicecomplain(\''+value.serviceNumber+'\')"
			// type="button" class="btn btn-box-tool"><i class="fa
			// fa-minus"></i></button></div>';
			// replaced with checkbox for + button
				var button=': <div class="btn-group complain'+value.serviceNumber+'"><label><input type="checkbox" class="big-checkbox" onchange="chkaddremoveservicecomplain(this)" value="'+value.serviceNumber+'" />Click Here</label></div>';
				
				$('#balanceOfCreditLimit'+index).html(value.balanceOfCreditLimit);
			$('#offerName'+index).html(value.offerName);
			$('#serviceNumber'+index).html(value.serviceNumber+button);
			$('#status'+index).html(value.status);

			$.each(value.vasName, function (indexvas, valuevas) {
				if(valuevas.substring(0,7)=='Install'){
					$('#subsinfotable'+index+' tr:last').after('<tr><td><label>CPE Install VAS</label></td><td>'+valuevas+'</td></tr>');
				}else
				$('#subsinfotable'+index+' tr:last').after('<tr><td><label>FTTH Package</label></td><td>'+valuevas+'</td></tr>');
				
			});	 
			
			// getting CRMS balance resource
getservicestatus(value.serviceNumber,index);
// getting balance;
getCRMSServiceBalance(value.serviceNumber,index);
// getting resource FRi date
getQueryFreeResource(value.serviceNumber,index);

			 });	
			
			
			// check if complain already exist
			
			$.get('../complain/getComplainServiceInfo', {
				cpeSn:$('#cpeSN').html()
			}, function(response) {
			console.log(response);
			if(response.length>0){
				if(getURLParameter('CPE')==null){
				alert('Complain Already exist for this CPE Serial');
				}
				$.each(response, function (index, value) {
				// only if problem exist
				if(value.MASTER_SOLVE_FLAG!="C"){
					var remark='<span data-toggle="tooltip" title="'+value.MASTER_REMARKS+'">'+value.PROBLEM_DESC+'</span>';
				$('.complain'+value.MASTER_SERVICE_NO).empty();
				$('.complain'+value.MASTER_SERVICE_NO).append(remark);
				$('.complain'+value.MASTER_SERVICE_NO).css('background-color', '#FACC2E');
				}
				
				});
				
				var detaillink='<a target="_blank" href="../troubleticket/list?token_id='+response[0].TOKEN_ID+'" class="btn bg-green"> <i class="fa fa-info-circle"></i> View All In Detail </a>';
				detaillink=detaillink+'<br><a target="_blank" href="../ticket-history/fetch?MAIN_SRV_NO='+response[0].MAIN_SRV_NO+'" class="btn bg-blue"> <i class="fa fa-history"></i> View CPE History Detail</a>';
				
				$("#divforsubsinfo").append(detaillink);
			
			
			}
				
			});
			
			
			
					}catch (e) {
			alert(response);
			}
		
		

	
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
	
	if(TEST3.onuStatus=="On"){
		$('#onuStatus').css('background-color', '#9FF781');
	}
	else $('#onuStatus').css('background-color', '#FE2E2E');
	
	if(TEST3.onuRxPower==0){
		 $('#onuRxPower').css('background-color', '#FE2E2E');
		}
	else if(TEST3.onuRxPower>=-25){
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
  // $('<option>').val("").text("Select").appendTo(select);
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

// checkbox for add or remove service
function chkaddremoveservicecomplain(a){
	if(a.checked){
		addservicecomplain(a.value);
	}
	else{
		removeservicecomplain(a.value);
	}
	
}

function addservicecomplain(serno){
	if (($('.'+serno)[0])){
	alert('Complain for this service has been added for registration. Please click on Register Complain to continue ');
	}else{
	
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
			return false;
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
			
			// return false;
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
				 solved:solved,
				 // info customer for report
				 CUSTOMER_NAME : $('#customerName').html(),
				 CONTACT_NO:$('#ContantNum').html(),
				 OLT_PORT:$('#oltInfo').html(),
				 FAP_LOCATION:$('#faplocation').html(),
				 FAP_PORT:$('#fapPortName').html(),
				 CPE_RX_LVL:$('#onuRxPower').html()
					 	 }, function(response) {
					 		if(response.substr(0,55)=="Resolving Service Complain while Complain Already Exist"){
					 			if (confirm(response+'. Do you want to Resolve service to provided token?')) {
					 				  // Save it!
					 				$.post('../complain/addthencloservexistingtoken', {
					 					JSON:JSON.stringify(transnop),
					 					tokenid:response.substr(65)
					 				 }, function(valres) {
					 					 alert(valres);
					 					location.reload();
					 					// return;
					 				 });
					 				  console.log('Thing was saved to the database.');
					 				} else {
					 				  // Do nothing!
					 				  return;
					 				}
					 			// location.reload();
					 		 return;
					 		 }
					 		 
					 		 
					 		else if(response.substr(0,22)=="Complain Already Exist"){
					 			if (confirm(response+'. Do you want to add another service to provided token?')) {
					 				  // Save it!
					 				$.post('../complain/addsrvexistingtoken', {
					 					JSON:JSON.stringify(transnop),
					 					tokenid:response.substr(32)
					 				 }, function(valres) {
					 					 alert(valres);
					 				// return;
					 				 });
					 				  console.log('Thing was saved to the database.');
					 				} else {
					 				  // Do nothing!
					 				  return;
					 				}
					 			// location.reload();
					 		 return;
					 		 }
					 		 
			alert(response);
			location.reload();
			 });
		
	// alert("No service has been checked!!! This will be");
		
	

// console.log(trasnop);
}
function getCRMSServiceBalance(serviceNo,index){
	// alert(serviceNo);
		 $.ajax({
	         url: '../complain/getCRMSServiceBalance',
	        // global: false,
	         type: 'get',
	         data: {MDN:serviceNo},
	       // async: false, //blocks window close
	         success: function(response) {
	        		var state=response.split('!');
	        		var statestatus;			
	        		$('#balanceOfCreditLimit'+index).css('background-color','#FE2E2E');
	        		if($('#servicetype'+index).html().substring(0,3)=='Pos'){
	        			// postpaid
	        			$('#labelbalanceid'+index).html("Credit Limit");
	        						statestatus='Due Amt:'+(state[1]/100)+' &nbsp  &nbsp  &nbsp  &nbsp'+'Rem Credit:'+(state[0]/100);
	        						if(state[0]>0){
	        		        			$('#balanceOfCreditLimit'+index).css('background-color','#9FF781');
	        		        		}			
	        		
	        		}
	        		else if($('#servicetype'+index).html().substring(0,3)=='Pre'){
			 // prepaid
	        			$('#labelbalanceid'+index).html("Balance");
	        			statestatus='Balance:'+(state[0]/100)+' &nbsp  &nbsp  &nbsp'+'Expiry Dt of Subs:'+state[1];
	        			if(state[0]>0){
		        			$('#balanceOfCreditLimit'+index).css('background-color','#9FF781');
		        		}
	        		} 
	        		
	        // if data type no need to check balance
	        		if(serviceNo.substring(0,6)=='NTFTTH'){
//	        		 javexpdt=stringToDate(state[1].substring(0,10),"yyyy-mm-dd","-");
//	        	     			var today = new Date();
//	        		// incase of postpiad there is no date instead remaning
			// credit in state[1]
//	        			if(today<=javexpdt){
//	        			$('#balanceOfCreditLimit'+index).css('background-color','#9FF781');
//	        		}
//	        		 
	        		// marking balance as green as it is based on package
					// validity
	        		$('#balanceOfCreditLimit'+index).css('background-color','#9FF781');
	        		
	        		}
	        		
	        			        		$('#balanceOfCreditLimit'+index).html(statestatus);

	        			
	        			
	         }
	     });
		
	}

//we will call this function if validity is expired calling it again since its not async
function getCRMSServiceBalanceifvalidityexp(serviceNo,index){
		 $.ajax({
	         url: '../complain/getCRMSServiceBalance',
	        // global: false,
	         type: 'get',
	         data: {MDN:serviceNo},
	       // async: false, //blocks window close
	         success: function(response) {
	        		var state=response.split('!');
	        		var statestatus;			
	        		$('#balanceOfCreditLimit'+index).css('background-color','#FE2E2E');
	        		if($('#servicetype'+index).html().substring(0,3)=='Pos'){
	        			// postpaid
	        			$('#labelbalanceid'+index).html("Credit Limit");
	        						statestatus='Due Amt:'+(state[1]/100)+' &nbsp  &nbsp  &nbsp  &nbsp'+'Rem Credit:'+(state[0]/100);
	        						if(state[0]>0){
	        		        			$('#balanceOfCreditLimit'+index).css('background-color','#9FF781');
	        		        		}			
	        		
	        		}
	        		else if($('#servicetype'+index).html().substring(0,3)=='Pre'){
			 // prepaid
	        			$('#labelbalanceid'+index).html("Balance");
	        			statestatus='Balance:'+(state[0]/100)+' &nbsp  &nbsp  &nbsp'+'Expiry Dt of Subs:'+state[1];
	        			if(state[0]>0){
		        			$('#balanceOfCreditLimit'+index).css('background-color','#9FF781');
		        		}
	        		} 
	        			        		$('#balanceOfCreditLimit'+index).html(statestatus);

	         }
	     });
		
	}



function getQueryFreeResource(serviceNo,index){
	// alert(serviceNo);
		 $.ajax({
	         url: '../complain/getCRMSQueryFreeResource',
	        // global: false,
	         type: 'get',
	         data: {MDN:serviceNo},
	       // async: false, //blocks window close
	         success: function(response) {
	        	 console.log(response);
	        	 
	        	 	        		if(response!='-1'){
	        			var state=response.split('!');
		        		var statestatus;			
		        		var FRIExp;
	        			// postpaid
	        						statestatus='Total Vol:'+state[1]+'/Remaming Vol:'+state[2];
	        		
	        	 
	        		$('#subsinfotable'+index+' tr:last').after('<tr><td><label>Package Validity</label></td><td><span id="FRIExpdt'+index+'"></span></td></tr>');
	        		
	        		$('#subsinfotable'+index+' tr:last').after('<tr><td><label>Package Vol. Usage</label></td><td><span id="Freeresourceqry'+index+'"></span></td></tr>');
	        		$('#FRIExpdt'+index).css('background-color','#9FF781');
	        		if(state[0]=='102'){
	        			FRIExp=state[3];			
		        		
	        			javexpdt=	stringToDate(state[3].substring(0,10),"MM/dd/yyyy","/");
	        			var today = new Date();
	        		if(today>javexpdt){
	        			$('#FRIExpdt'+index).css('background-color','#FE2E2E');
	        			//if FRI exipired check balance then mark it red if balance less than 0
	        			getCRMSServiceBalanceifvalidityexp(serviceNo,index);
	        		}
		 
	        		}
	        		else if(state[0]=='104'){
	        			FRIExp=state[3]+'&nbsp; &nbsp; &nbsp; &nbsp;'+state[4];			
		        		javexpdt=	stringToDate(state[4].substring(0,10),"MM/dd/yyyy","/");
	        			var today = new Date();
	        			if(today>javexpdt){
		        			$('#FRIExpdt'+index).css('background-color','#FE2E2E');
		        			//if FRI exipired check balance then mark it red if balance less than 0
		        			getCRMSServiceBalanceifvalidityexp(serviceNo,index);
		        			
		        		}			
	        			
	        		}
	        		$('#Freeresourceqry'+index).html(statestatus);
	        	    $('#FRIExpdt'+index).html(FRIExp);
	        			
	        			        		
	        			        		
	        		}	
	         }
	     });
		
	}




function getservicestatus(serviceNo,index){
// alert(serviceNo);
	 $.ajax({
         url: '../complain/getCRMSServiceStatus',
        // global: false,
         type: 'get',
         data: {MDN:serviceNo},
        async: false, // blocks window close
         success: function(response) {
        		var state=response.split('!');
        		var statestatus;
        		$('#status'+index).css('background-color', '#FE2E2E');
        		if(state[0]==0){
        			$('#servicetype'+index).html('Prepaid');
        					}
        		else if(state[0]==1){
        			$('#servicetype'+index).html('Postpaid');
        			        		} 
        		if(state[1]==1001){
        			statestatus='Active';
        			 $('#status'+index).css('background-color', '#9FF781');
        		}
        		else if(state[1]==1011){
        			statestatus='One way Bar';
        		}
        		else if(state[1]==1010){
        			statestatus='Pre-Terminated';
        		}
        		else if(state[1]==1006){
        			statestatus='Two way Bar';
        		}
        		else if(state[1]==1007){
        			statestatus='Frozen';
        		}
        		$('#status'+index).html(statestatus);
         }
     });
	
}


function refreshtmsstatus(){
	if($('#cpeSN').html()==null || $('#cpeSN').html().length<1){
		alert('CPE Serial Not Selected!!!! Please refresh this page');
		return false;
	}
	$.get('../complain/getCPETMSStatus', {
		cpeSn:$('#cpeSN').html()
		}, function(response) {
		 console.log(response);
		try{
			// TEST3 = JSON.parse(response);
		
		
		 var appendtxt;
		 // tmsstatusoverlay
		 $('#divforCPEinfo').empty();
			
				
		 var div='<div class="col-md-6"><table class="table table-condensed"><tbody>';						
			var count= 1;
		 $.each(response, function (index, value) {
			    
			 div=div+'<tr><td>'+index+'</td><td>'+value+'</td>  </tr>';
			if (count%14==0){
				div=div+'</tbody></table></div><div class="col-md-6"><table class="table table-condensed"><tbody>';
			} 
			count=count+1;
		 
		 });
		 div=div+'</tbody></table></div>';
		 $('#divforCPEinfo').append(div);	 
	
			
					}catch (e) {
			alert(e+"response"+response);
			}

	}); // closing function(responseJson)
}

function stringToDate(_date,_format,_delimiter)
{
            var formatLowerCase=_format.toLowerCase();
            var formatItems=formatLowerCase.split(_delimiter);
            var dateItems=_date.split(_delimiter);
            var monthIndex=formatItems.indexOf("mm");
            var dayIndex=formatItems.indexOf("dd");
            var yearIndex=formatItems.indexOf("yyyy");
            var month=parseInt(dateItems[monthIndex]);
            month-=1;
            var formatedDate = new Date(dateItems[yearIndex],month,dateItems[dayIndex]);
            return formatedDate;
}

function jsondatetonormale(indate){
	var currentTime = new Date(indate);
	var month = currentTime.getMonth() + 1;
	var day = currentTime.getDate();
	var year = currentTime.getFullYear();
	var date = day + "/" + month + "/" + year+" "+currentTime.getHours()+":"+currentTime.getMinutes();
	return date;
}

function gettimediff(adate, bdate){
	var date1 = new Date(adate);
	var date2 = new Date(bdate);
	var diff = date2.getTime() - date1.getTime();
	var msec = diff;
	var hh = Math.floor(msec / 1000 / 60 / 60);
	msec -= hh * 1000 * 60 * 60;
	var mm = Math.floor(msec / 1000 / 60);
	msec -= mm * 1000 * 60;
	var ss = Math.floor(msec / 1000);
	msec -= ss * 1000;
	return (hh + ":" + mm + ":" + ss);
}





