function getImpNtspFilterList() {
	var year = $("#QIMP_YEAR").val();
	var period = $("#QIMP_PERIOD").val();
	var month = $("#QIMP_MONTH").val();
	var service = $("#QSERVICE_CODE").val();
	var ntsp = $("#QNT_SP").val();
	var postflag = $("#QPOST_FLAG").val();
	if (service == 'SMS') {
		$("#thmo").html("MO");
	} else {

		$("#thmo").html("MIN");
	}
	reponse = null;
	$
			.get(
					'netting/getImpNtSplist',
					{
						IMP_YEAR : year,
						IMP_PERIOD : period,
						IMP_MONTH : month,
						SERVICE_CODE : service,
						NT_SP : ntsp,
						POST_FLAG : postflag
					},
					function(response) {

						if (response !== null) {
							temp = $('#example1').DataTable();
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
																		value.SEQ_NO,
																		value.IMP_YEAR,
																		value.IMP_PERIOD,
																		value.IMP_MONTH,
																		value.SERVICE_CODE,
																		value.NT_SP,
																		value.CATEGORY,
																		value.CP_DESC,
																		value.S_NO,
																		value.ESME_CODE,
																		value.RATE,
																		value.START_DT,
																		value.MO_1ST,
																		value.MT_1ST,
																		'<a href="#" class="btn btn-info" data-toggle="modal" data-target="#editModal" onclick="return editImpntsp(\''
																				+ value.TRANS_NO
																				+ "','"
																				+ value.SEQ_NO
																				+ '\')"> <i class="fa fa-edit"></i> Edit </a>',
																		'<a href="#" class="btn btn-info" data-toggle="modal" data-target="#deleteModal" onclick="return deleteImpntsp(\''
																				+ value.TRANS_NO
																				+ "','"
																				+ value.SEQ_NO
																				+ '\')"> <i class="fa fa-trash"></i> Delete </a>' ]);
											});
						}
					});

}

function editImpntsp(code, seqno) {
	var row = $("#example1").dataTable().fnGetData();
	var l = row.length;
	for (var i = 0; i < l; i++) {
		if (row[i][0] == code && row[i][1] == seqno) {
			// jQuery.ajaxSetup({async: false});

			// alert(row[i][9]);

			// getSPServiceList(row[i][1]);

			$("#EDITTRANS_NO").val(row[i][0]);
			$("#EDITSEQ_NO").val(row[i][1]);
			$("#EDITIMP_YEAR").val(row[i][2]);
			$("#EDITIMP_PERIOD").val(row[i][3]);
			$("#EDITIMP_MONTH").val(row[i][4]);
			$("#EDITSERVICE_CODE").val(row[i][5]);
			$("#EDITNT_SP").val(row[i][6]);
			$("#EDITCATEGORY").val(row[i][7]);
			$("#EDITCP_DESC").val(row[i][8]);
			$("#EDITS_NO").val(row[i][9]);
			$("#EDITESME_CODE").val(row[i][10]);
			$("#EDITMO_1ST").val(row[i][13]);
			$("#EDITMT_1ST").val(row[i][14]);

		}
	}
}
function updateImpntsp() {

	var CODE = $("#EDITTRANS_NO").val();
	var SEQNO = $("#EDITSEQ_NO").val();
	var IMP_YEAR = $("#EDITIMP_YEAR").val();
	var IMP_PERIOD = $("#EDITIMP_PERIOD").val();
	var IMP_MONTH = $("#EDITIMP_MONTH").val();
	var SERVICE_CODE = $("#EDITSERVICE_CODE").val();
	var NT_SP = $("#EDITNT_SP").val();
	var CATEGORY = $("#EDITCATEGORY").val();
	var CP_DESC = $("#EDITCP_DESC").val();
	var S_NO = $("#EDITS_NO").val();
	var ESME_CODE = $("#EDITESME_CODE").val();
	var MO_1ST = $("#EDITMO_1ST").val();
	var MT_1ST = $("#EDITMT_1ST").val();
	// alert ("nab "+S_NO);
	$.post('Netting/update', {
		TRANS_NO : CODE,
		SEQ_NO : SEQNO,
		IMP_YEAR : IMP_YEAR,
		IMP_PERIOD : IMP_PERIOD,
		IMP_MONTH : IMP_MONTH,
		SERVICE_CODE : SERVICE_CODE,
		NT_SP : NT_SP,
		CATEGORY : CATEGORY,
		CP_DESC : CP_DESC,
		S_NO : S_NO,
		ESME_CODE : ESME_CODE,
		MO_1ST : MO_1ST,
		MT_1ST : MT_1ST
	}, function(data) {
		// location.reload();
		alert(data);
		// temp = $('#example1').DataTable();
		// temp.clear().draw();
		if (data.substring(0, 6) === "Succes") {
			getImpNtspFilterList();
			$('.modal').modal('hide');
		}

	});
}

function deleteImpntsp(code, seqno) {
	CODE = code;
	SEQNO = seqno;
}

function del() {

	$.post('../impntsp/delete', {
		TRANS_NO : CODE,
		SEQ_NO : SEQNO
	}, function(data) {
		location.reload();
		alert(data);
	});
}

function saveImpntsp() {
	if ($('#S_NO') == null || $('#S_NO') == "" || $('#S_NO').val().length > 20) {
		alert('Invalid S_NO code!!!');
		return;
	}
	// alert('Invalid sp code!!!');
	var CODE = $("#TRANS_NO").val();
	var IMP_YEAR = $("#IMP_YEAR").val();
	var IMP_PERIOD = $("#IMP_PERIOD").val();
	var IMP_MONTH = $("#IMP_MONTH").val();
	var SERVICE_CODE = $("#SERVICE_CODE").val();
	var NT_SP = $("#NT_SP").val();
	var CATEGORY = $("#CATEGORY").val();
	var CP_DESC = $("#CP_DESC").val();
	var S_NO = $("#S_NO").val();
	var ESME_CODE = $("#ESME_CODE").val();
	var MO_1ST = $("#MO_1ST").val();
	var MT_1ST = $("#MT_1ST").val();
	// alert("nabin"+SHORT_CODE);
	$.post('Netttingimpntsp/saveJS', {
		TRANS_NO : CODE,
		IMP_YEAR : IMP_YEAR,
		IMP_PERIOD : IMP_PERIOD,
		IMP_MONTH : IMP_MONTH,
		SERVICE_CODE : SERVICE_CODE,
		NT_SP : NT_SP,
		CATEGORY : CATEGORY,
		CP_DESC : CP_DESC,
		S_NO : S_NO,
		ESME_CODE : ESME_CODE,
		MO_1ST : MO_1ST,
		MT_1ST : MT_1ST
	}, function(data) {
		alert(data);
		if (data.substring(0, 6) === "Succes") {
			location.reload();
		}
	});
}
