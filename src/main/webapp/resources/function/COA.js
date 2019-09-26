/**
 * 
 */
$(document).ready(function() {
	getFolder();
	treeconfig();

});

function getFolder() {
	var table = $('#example1').DataTable();
	table.clear().draw();

	$
			.get(
					'../coa/getCOAList',
					{},
					function(responseJson) {
						if (responseJson != null) {
							// alert(JSON.stringify(responseJson));
							var modrow = [];
							$
									.each(
											responseJson,
											function(key, value) {
												var myobj = new Object();
												myobj.id = value.SLDG_CODE;
												if (value.PARENT_SLDG_CODE == null) {
													myobj.parent = '#';

													// code to draw the table
													// for parent code null item
													{
														$("#example1")
																.dataTable()
																.fnAddData(
																		[
																				value.SLDG_CODE,
																				value.ENG_DESC,
																				value.NEP_DESC,
																				value.PARENT_SLDG_CODE,
																				value.STATEMENT_TYPE,
																				value.ACTIVE_FLAG,
																				value.DR_CR_FLAG,
																				value.AC_TYPE,
																				value.REMARKS,
																				"<a href=\"#\" data-toggle=\"modal\" data-target=\"#editcoa\"  title=\"Delete\" onclick=\"return edititem('"
																						+ value.SLDG_CODE
																						+ "')\"<span class=\"fa fa-pencil-square-o\"></span></a>",
																				"<a href=\"#\" data-toggle=\"modal\" data-target=\"#deleteModal\"  title=\"Delete\" onclick=\"return delAccCenter('"
																						+ value.SLDG_CODE
																						+ "')\"<span class=\"fa fa-trash\"></span></a>" ]); // closing
																																			// fnAddData()
													}

												} else
													myobj.parent = value.PARENT_SLDG_CODE;
												if (typeof value.NEP_DESC === 'undefined'
														|| (value.NEP_DESC).length > 0) {
													myobj.text = value.ENG_DESC
															+ '('
															+ value.NEP_DESC
															+ ')';
												} else {
													myobj.text = value.ENG_DESC;

												}
												modrow.push(myobj);

											});
							// alert(JSON.stringify(modrow));
							$('#html1').jstree({
								'core' : {
									"themes" : {
										"icons" : false
									},
									"dblclick_toggle" : false,
									'check_callback' : true,
									'data' : modrow
								}
							});
							// $('#html1').jstree(true).refresh();
							$("#html1").jstree(true).deselect_all(true);

						}
					});
}

function treeconfig() {

	$('#html1')
			.on(
					"select_node.jstree",
					function(e, data) {
						// alert("node_id: " + data.node.id);
						// alert("node_id: " + data.node.parent);
						// $('#fileuptopic').val(data.node.id);
						parentnode = data.node.parent;
						var table = $('#example1').DataTable();
						table.clear().draw();

						$
								.get(
										'../coa/getCOAList',
										{},
										function(responseJson) {
											if (responseJson != null) {
												// alert(JSON.stringify(responseJson));
												$
														.each(
																responseJson,
																function(key,
																		value) {
																	if (value.PARENT_SLDG_CODE == data.node.id) {

																		$(
																				"#example1")
																				.dataTable()
																				.fnAddData(
																						[
																								value.SLDG_CODE,
																								value.ENG_DESC,
																								value.NEP_DESC,
																								value.PARENT_SLDG_CODE,
																								value.STATEMENT_TYPE,
																								value.ACTIVE_FLAG,
																								value.DR_CR_FLAG,
																								value.AC_TYPE,
																								value.REMARKS,
																								"<a href=\"#\" data-toggle=\"modal\" data-target=\"#myModaleditFolder\"  title=\"Delete\" onclick=\"return edititem('"
																										+ value.SLDG_CODE
																										+ "')\"<span class=\"fa fa-pencil-square-o\"></span></a>",
																								"<a href=\"#\" data-toggle=\"modal\" data-target=\"#deleteModal\"  title=\"Delete\" onclick=\"return delAccCenter('"
																										+ value.SLDG_CODE
																										+ "')\"<span class=\"fa fa-trash\"></span></a>" ]); // closing
																																							// fnAddData()
																	}
																});
											}
										});

					});
}

function edititem(code) {
	CODE = code;

	var row = $("#example1").dataTable().fnGetData();
	var l = row.length;
	for (var i = 0; i < l; i++) {
		if (row[i][0] == code) {
			getSPServiceList(row[i][1]);
			$("#EDITCP_CODE").val(row[i][0]);
			$("#EDITSP_CODE").val(row[i][1]);
			$("#EDITSP_CODE").trigger('change');
			getSPServiceList(row[i][1]);
			$("#EDITSERVICE_CODE").val(row[i][3]);
			$("#EDITCP_NAME").val(row[i][4]);
			$("#EDITESME_CODE").val(row[i][5]);
			$("#EDITCATEGORY_MAP").val(row[i][6]);
			$("#EDITESME_CODE_MAP").val(row[i][7]);
			$("#EDITSRV_FOR").val(row[i][8]);
			$("#EDITPACKAGE_TYPE").val(row[i][9]);
			$("#EDITSTREAM_TYPE").val(row[i][10]);
			$("#EDITSTART_DT").val(row[i][11]);
			$("#EDITEND_DT").val(row[i][12]);
			$("#EDITSHARING_TYPE").val(row[i][13]);
			$("#EDITSHARE_NT_PER").val(row[i][14]);
			$("#EDITAFS_FLAG").val(row[i][15]);
			$("#EDITMIN_QTY").val(row[i][16]);
			getItemTariffListRental(row[i][3], "RENTAL")
			$("#EDITRENTAL_ITEM_CODE").val(row[i][17]);
			getItemTariffListVpn(row[i][3], "VPN")
			$("#EDITVPN_ITEM_CODE").val(row[i][18]);
			getItemTariffListSpace(row[i][3], "SPACE")
			$("#EDITSPACE_ITEM_CODE").val(row[i][19]);

			jQuery.ajaxSetup({
				async : true
			});

		}
	}
}


