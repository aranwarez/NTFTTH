var CODE;
var SEQNO;
var oFileIn;
var JSONimport;
var JSONarrayList;
var srcFilename;
var SNO;
$(document).ready(function() {
	// $(".js-example-basic-single").select2();
	$('.nepali-calendar').nepaliDatePicker();
	$.fn.dataTable.ext.errMode = 'none';

});

function validateform(Parameters) {
	var item = 0;
	$.each(Parameters, function(key, value) {
		if (isempty(value)) {
			item = key;
		}
		return (!isempty(value));
	});
	return item;
};

function isempty(object) {
	if (object == "" || object === "" || typeof (object) === "undefined") {
		return true;
	} else
		return false;
}

function getInvoiceLockList() {
	// jQuery.ajaxSetup({async: false});
	var year = $("#QIMP_YEAR").val();
	var period = $("#QIMP_PERIOD").val();
	var month = $("#QIMP_MONTH").val();
	var postflag = $("#QPOST_FLAG").val();

	reponse = null;
	$
			.get(
					'../invoicelock/getInvoiceLockList',
					{
						IMP_YEAR : year,
						IMP_PERIOD : period,
						IMP_MONTH : month,
						POST_FLAG : postflag
					},
					function(response) {
						// alert(JSON.stringify(response));

						if (response !== null) {
							// footer callback
							temp = $('#example1')
									.DataTable(
											{"aoColumnDefs": [
											    { className: "money text-right", "targets": [ 7,8 ] }
											    ]
												,
												"footerCallback" : function(
														row, data, start, end,
														display) {
													var api = this.api(), data;

													
													//currency formatter
													 var formater = OSREC.CurrencyFormatter.getFormatter
										                ({
										                    selector: '.money',
										                    currency: 'NPR',
										                    pattern: '##,##,##,##,##,##,##,##0.00'
										                });
													
													
													// Remove the formatting to
													// get integer data for
													// summation
													var intVal = function(i) {
														return typeof i === 'string' ? i
																.replace(
																		/[\$,]/g,
																		'') * 1
																: typeof i === 'number' ? i
																		: 0;
													};

													// Total over all pages
													total7 = api
															.column(7)
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);

													total8 = api
															.column(8)
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);

													// Total over this page
													pageTotal7 = api
															.column(
																	7,
																	{
																		page : 'current'
																	})
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);

													pageTotal8 = api
															.column(
																	8,
																	{
																		page : 'current'
																	})
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);

													// Update footer
													pageTotal7=formater(+pageTotal7);
													pageTotal8=formater(+pageTotal8);
													total7=formater(+total7);
													total8=formater(+total8);
													
													$(api.column(7).footer())
															.html(
																	pageTotal7
																			+ '<BR> '
																			+ total7);
													$(api.column(8).footer())
															.html(
																	pageTotal8
																			+ '<BR> '
																			+ total8);
												}
											});

							// end of footer callback
							temp.columns([7, 8]).header().to$().removeClass("money").removeClass("text-right").addClass("heeeee");
							temp.columns([7, 8]).footer().to$().removeClass("money");
							
							temp.clear().draw();
							$
									.each(
											response,
											function(key, value) {
												var postflag = '';
												var postflagbox = value.POST_FLAG;
												var qdelete = '';
												var unpost = '';
												if (postflagbox == 'Y') {
													postflag = '<input type="checkbox" checked onclick="return false" disabled />';
													// bd = "<a href = \"#\"
													// data-toggle=\"modal\"
													// data-target=\"#modalBankDeposit\"
													// title=\"Bank Deposit\"
													// onclick=\"return
													// editBankDeposit('" +
													// value.TRANS_NO +
													// "')\"<span class=\"fa
													// fa-pencil-square-o\"></span></a>";
													unpost = "<a href = \"#\" data-toggle=\"modal\" class=\"btn btn-info\" data-target=\"#UnpostRevenue\" onclick=\"return unpostInvoicelock('"
															+ value.TRANS_NO
															+ "')\">UNPOST</a>";
												} else if (postflagbox == 'N') {
													postflag = '<input type=\"checkbox\"  id=\"'
															+ value.TRANS_NO
															+ '\" class=\"postCancel\" value="'
															+ value.TRANS_NO
															+ '">';
													// edit = "<a href=\"#\"
													// data-toggle=\"modal\"
													// class=\"btn btn-info\"
													// onclick=\"return
													// editDetails('" +
													// value.OFFICE_CODE +
													// "',\'" + value.TRANS_NO +
													// "',\'" + value.NEP_DT +
													// "')\">EDIT</a> ";
													qdelete = "<a href = \"#\" data-toggle=\"modal\" class=\"btn btn-info\" data-target=\"#deleteRevenue\" onclick=\"return deleteInvoicelock('"
															+ value.TRANS_NO
															+ "')\">Delete</a>";
												}
												$("#example1")
														.dataTable()
														.fnAddData(
																[
																		postflag,
																		value.TRANS_NO,
																		value.NEP_DT,
																		value.IMP_YEAR,
																		value.IMP_PERIOD,
																		value.IMP_MONTH,
																		value.S_NO,
																		value.SHARING,
																		value.NON_SHARING,
																		value.POST_FLAG,
																		value.POST_BY,
																		'<a href="#" class="btn btn-info" data-toggle="modal" data-target="#myDetailModal" onclick="return detailBill(\''
																				+ value.TRANS_NO
																				+ '\')"> <i class="fa fa-edit"></i> View </a>',
																		'<a href="#" class="btn bg-purple" data-toggle="modal" data-target="#unpostModal" onclick="return unpostInvoicelock(\''
																				+ value.TRANS_NO
																				+ '\')"> <i class="fa fa-undo"></i> Unpost </a>',
																		'<a href="#" class="btn bg-red" data-toggle="modal" data-target="#deleteModal" onclick="return deleteInvoicelock(\''
																				+ value.TRANS_NO
																				+ '\')"> <i class="fa fa-trash"></i> Delete </a>' ]);
											});
							   OSREC.CurrencyFormatter.formatAll(
									   {
										   selector: '.money',
								           currency: 'NPR',
								           pattern: '##,##,##,##,##,##,##,##0.00'
									   });
						
						}
					});
}

function detailBill(trans_no) {
	CODE = trans_no;
	var row = $("#example1").dataTable().fnGetData();
	var l = row.length;
	// debugger;
	for (var i = 0; i < l; i++) {
		if (row[i][1] == trans_no) {
			// $("#CP_CODE").val(row[i][0]);
			// $("#SP_CODE").val(row[i][1]);
			// $("#SP_NAME").val(row[i][2]);
			// $("#SERVICE_CODE").val(row[i][3]);
			$("#RatesLabel").html(
					row[i][3] + " " + row[i][5] + "(" + row[i][6] + ")");
			// $("#ESME_CODE").val(row[i][5]);
			// $("#PACKAGE_TYPE").val(row[i][6]);
			// $("#RATE").val(row[i][7]);
			// $("#EFFECTIVE_DT").val(row[i][8]);
		}
	}

	$.get('../invoicelock/getInvoiceDetailList', {
		TRANS_NO : trans_no
	}, function(response) {
		// alert(JSON.stringify(response));
		if (response !== null) {

			tempdetaillist = response;

			detailtemp = $('#detailtable').DataTable(
					{"aoColumnDefs": [
					    { className: "moneydetail text-right", "targets": [5,6, 7,8,9 ] }
					    ]
						,
						"footerCallback" : function(row, data, start, end,
								display) {
							
							var api = this.api(), data;

							// Remove the formatting to get integer data for
							// summation
							var intVal = function(i) {
								return typeof i === 'string' ? i.replace(
										/[\$,]/g, '') * 1
										: typeof i === 'number' ? i : 0;
							};

							// total current page
							debugger;
							// Total over all pages
							var row = api
				                .rows( { page: 'current'} )
				                .data();
							
							var l = row.length;
						    var sumamt=0;
						    var sumtroyal=0;
						    var sumtsc=0;
						    var sumvat=0;
						    var sumtot=0;
						    for (var i = 0; i < l; i++) {
						    	var mod=0;
						    	drcrflag=row[i][4];
						    	if(drcrflag=='DR'){
						    		mod=1;
						    	}else if(drcrflag=='CR'){
						    		mod=-1;
						    	}
						    amt=mod*Number(row[i][5]);
						    royal=mod*Number(row[i][6]);
						    tsc=mod*Number(row[i][7]);
						    vat=mod*Number(row[i][8]);
						    total=mod*Number(row[i][9]);
						    sumamt=intVal(sumamt)+intVal(amt);
						    sumtroyal=intVal(sumtroyal)+intVal(royal);
						    sumtsc=intVal(sumtsc)+intVal(tsc);
						    sumvat=intVal(sumvat)+intVal(vat);
						    sumtot=intVal(sumtot)+intVal(total);
						    
						    
						    }

						
						
						// Update footer
						$(api.column(5).footer()).html(sumamt);
						$(api.column(6).footer()).html(sumtroyal);
						$(api.column(7).footer()).html(sumtsc);
						$(api.column(8).footer()).html(sumvat);
						$(api.column(9).footer()).html(sumtot);
						}
					});
			
			detailtemp.columns([5,6,7, 8,9]).header().to$().removeClass("moneydetail").removeClass("text-right");
		//	detailtemp.columns([5,6,7, 8,9]).footer().to$().removeClass("money");
			

			detailtemp.clear().draw();
			$.each(response, function(key, value) {
				var postflag = '';
				var postflagbox = value.POST_FLAG;
				$("#detailtable").dataTable().fnAddData(
						[ value.TRANS_ID, value.SERVICE_CODE, value.ITEM_CODE,
								value.SHARING_TYPE, value.DR_CR_FLAG,
								value.AMT, value.ROYALTY_AMT, value.TSC_AMT,
								value.VAT_AMT, value.TOTAL ]);
			});
//			Convert pattern
			   OSREC.CurrencyFormatter.formatAll(
					   {
						   selector: '.moneydetail',
				           currency: 'NPR',
				           pattern: '##,##,##,##,##,##,##,##0.00'
					   });
			
		}
	});

}

function select_check(no) {
	if (no == true) {
		$('.postCancel').prop('checked', true);
		$("#selectcheck").hide();
		$("#selectcheckf").show();
	} else {
		$('.postCancel').prop('checked', false);
		$("#selectcheck").show();
		$("#selectcheckf").hide();
	}
}

function post() {
	if ($('.postCancel:checked').length == 0) {
		alert("checkbox empty");
		return false;
	}
	var trasnop = [];
	$(".postCancel:checked").each(function() {

		trasnop.push($(this).attr('id'));
	});

	// jQuery.ajaxSetup({async: false});
	$.post("../invoicelock/post", {
		Transno_List : JSON.stringify(trasnop)
	}, function(data) {
		getInvoiceLockList();
	});
}

function deleteInvoicelock(code) {
	CODE = code;
}

function del() {

	$.post('../invoicelock/delete', {
		TRANS_NO : CODE
	}, function(data) {
		// location.reload();
		alert(data);
		getInvoiceLockList();
		$('.modal').modal('hide');
	});
}

function unpostInvoicelock(code) {
	CODE = code;
}

function unpost() {

	$.post('../invoicelock/unpost', {
		TRANS_NO : CODE
	}, function(data) {
		// location.reload();
		alert(data);
		getInvoiceLockList();
		$('.modal').modal('hide');
	});
}