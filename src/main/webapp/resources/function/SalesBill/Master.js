var CODE;

$(document).ready(function() {
	// $(".js-example-basic-single").select2();
	$('.nepali-calendar').nepaliDatePicker();
	$.fn.dataTable.ext.errMode = 'none';
	$("#QSP_CODE").select2({
		placeholder : "Select a Service Provider Code",
		allowClear : true
	});
	$("#SP_CODE").select2({
		dropdownParent : $("#myModal"),
		placeholder : "Select a Service Provider Code"
	// allowClear: false
	});

	$("#BANK_CD").select2({
		dropdownParent : $("#myModal"),
		placeholder : "Select a Bank Code"
	// allowClear: false
	});
	$("#OBANK_CD").select2({
		dropdownParent : $("#myModal"),
		placeholder : "Select a Bank Code"
	// allowClear: false
	});

});

function getReceiptFilterList() {
	var sp_code = $("#QSP_CODE").val();
	var from_dt = $("#QFROM_DT").val();
	var to_dt = $("#QTO_DT").val();
	var post_flag = $("#QPOST_FLAG").val();

	$
			.get(
					'../cashsale/getMasterlist',
					{
						S_NO : sp_code,
						FROM_DT : from_dt,
						TO_DT : to_dt,
						POST_FLAG : post_flag
					},
					function(response) {
						// alert(JSON.stringify(response));
						if (response !== null) {
							//temp = $('#example1').DataTable();
							temp = $('#example1')
							.DataTable(
									{
										"footerCallback" : function(
												row, data, start, end,
												display) {
											var api = this.api(), data;

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
											total = api
													.column(4)
													.data()
													.reduce(
															function(a,
																	b) {
																return intVal(a)
																		+ intVal(b);
															}, 0);

									

											// Total over this page
											pageTotal = api
													.column(
															4,
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
											$(api.column(4).footer())
													.html(
															+pageTotal.toFixed(2)
																	+ '<BR> '
																	+ total.toFixed(2));
										
										}
									});
							
							
							//
							temp.clear().draw();
							$
									.each(
											response,
											function(key, value) {
												$("#example1")
														.dataTable()
														.fnAddData(
																[
																		value.TRANS_NO,
																		value.NP_SALES_DATE,
																		value.CUSTOMER_CODE,
																		value.DBANK,
																		value.BANK_PAY_AMT,
																		value.REMARKS,
																		value.CREATE_BY,
																		value.NP_CREATE_DT,
																		value.CANCEL_BY,
																		value.CANCEL_DT,
																		'<a href="#" class="btn bg-purple" data-toggle="modal" data-target="#editModal" onclick="return editReceipt(\''
																				+ value.TRANS_NO
																				+ '\')"> <i class="fa fa-edit"></i> View </a>',
																		'<a href="#" class="btn bg-red" data-toggle="modal" data-target="#deleteModal" onclick="return deleteReceipt(\''
																				+ value.TRANS_NO
																				+ '\')"> <i class="fa fa-trash"></i> Cancel </a>' ]);
											});
						}
					});

}

function getSPServiceList(SP_CODE) {
	var sp_code
	// debugger;
	if (SP_CODE != null) {
		sp_code = SP_CODE;
	} else {
		sp_code = $('#SP_CODE').val();
	}
	$.get('../sp/getSPServiceList', {
		SP_CODE : sp_code
	}, function(response) {
		var select = $('#SERVICE_CODE,#EDITSERVICE_CODE');
		select.find('option').remove();
		$('<option>').val("").text("SELECT").appendTo(select);
		$.each(response, function(index, value) {
			$('<option>').val(value.SERVICE_CODE).text(value.SERVICE).appendTo(
					select);

		});

	});
}

function saveReceipt() {
	// debugger;
	if ($('#SP_CODE') == null || $('#SP_CODE') == ""
			|| $('#SP_CODE').val().length > 8) {
		alert('Invalid SP Code!!!');
		return;
	}
	var CC_CODE = "CC010201";
	var CODE = $("#RECEIPT_NO").val();
	var RECEIPT_DT = $("#RECEIPT_DT").val();
	var SP_CODE = $("#SP_CODE").val();
	var BANK_CD = $("#BANK_CD").val();
	var BANK_NAME = $("#BANK_NAME").val();
	var CHEQUE_NO = $("#CHEQUE_NO").val();
	var AMT = $("#AMT").val();
	var REMARKS = $("#REMARKS").val();
	// alert("nabin"+SHORT_CODE);
	$.post('../receipt/saveJS', {
		CC_CODE : CC_CODE,
		RECEIPT_NO : CODE,
		RECEIPT_DT : RECEIPT_DT,
		S_NO : SP_CODE,
		BANK_CD : BANK_CD,
		BANK_NAME : BANK_NAME,
		CHEQUE_NO : CHEQUE_NO,
		PAID_AMT : AMT,
		REMARKS : REMARKS
	}, function(data) {
		alert(data);
		if (data.substring(0, 6) === "Succes") {
			location.reload();
		}
	});
}

function editReceipt(code) {
	CODE = code;

	var row = $("#example1").dataTable().fnGetData();
	var l = row.length;
	for (var i = 0; i < l; i++) {
		if (row[i][0] == code) {
			// getSPServiceList(row[i][1]);
			$("#transno").html(row[i][0]);
			$("#hiddentransno").val(row[i][0]);
			
			$("#salesdt").html(row[i][1]);
			$("#spcode").html(row[i][2]);
			$("#bankcd").html(row[i][3]);
			$("#amt").html(row[i][4]);
			$("#remarks").html(row[i][5]);
		}
	}
	
	$
	.get(
			'../cashsale/getDetaillist',
			{
				transno : CODE
			},
			function(response) {
				// alert(JSON.stringify(response));
				if (response !== null) {
					temp = $('#detailtab').DataTable();
					temp.clear().draw();
					$
							.each(
									response,
									function(key, value) {
										var total=value.REVENUE_AMT+value.TSC_AMT+value.VAT_AMT;
										$("#detailtab")
												.dataTable()
												.fnAddData(
														[
																value.ITEM_CODE,
																value.DITEM,
																value.QTY,
																value.RATE,
																value.REVENUE_AMT,
																value.TSC_AMT,
																value.VAT_AMT,
																total
																 ]);
									});
				}
			});	
	
}

function deleteReceipt(code) {
	CODE = code;
	$('#cancellabel').html(' Trans No : '+ code);
}

function del() {

	$.post('../cashsale/cancelbill', {
		Transno : CODE
	}, function(data) {
		getReceiptFilterList();
		alert(data);
	});
}