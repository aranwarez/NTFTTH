/**
 * 
 */
var itemlist;
var rowcount = 0;
var globaltsc;
var globalvat;

$(document).ready(function() {

	// prevent multiple click
	jQuery('.myClickDisabledElm').bind('click', function(e) {
		e.preventDefault();

	});

	$("#SP_CODE").select2({
		placeholder : "Select a Customer",
		allowClear : true
	});

	$("#BANK_CODE").select2({
		placeholder : "Select Bank",
		allowClear : true
	});

	$('.nepali-calendar').nepaliDatePicker();

	getTaxableRate();
	getItem();

	$('.overlay').fadeOut();
});

function focusFunction(row) {
	if ($('#rowcnt' + row).css("background-color") != 'rgb(240, 240, 255)') {
		$('#rowcnt' + row).css({
			"background-color" : "#ffff99"
		});
		$('#rate' + row).css({
			'background-color' : '#ffff99'
		});
		$('#quan' + row).css({
			'background-color' : '#ffff99'
		});

	}

}
function blurFunction(row) {
	if ($('#rowcnt' + row).css("background-color") != 'rgb(240, 240, 255)') {
		$('#rowcnt' + row).css({
			"background-color" : "transparent"
		});
		$('#rate' + row).css({
			'background-color' : 'transparent'
		});
		$('#quan' + row).css({
			'background-color' : 'transparent'
		});

	}
}

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

function getcustomerinfo(SPCODE) {
	$.get('../cashsale/getCustomerInfo', {
		SPCODE : SPCODE
	}, function(response) {
		$.each(response, function(key, value) {
			$('#address').html(value.ADDRESS);
			$('#panno').html(value.PAN_NO);
		});
		// alert(JSON.stringify(response));
	});

}

function getItemTariff() {
	$('#itemcode' + itemid).html(a.value);

}

function getItem() {
	$.get('../cashsale/getitemlist', {}, function(response) {
		itemlist = response;
	//	alert(JSON.stringify(response));
	});

}

function getTaxableRate() {
	$.get('../cashsale/getitemtariff', {
		Itemcode : 'TSC'
	}, function(response) {

		globaltsc = Number(response);
	});

	$.get('../cashsale/getitemtariff', {
		Itemcode : 'VAT'
	}, function(response) {

		globalvat = Number(response);
	});

}

function getbank() {
	$.get('../cashsale/getbankList', {}, function(response) {

	});

}

function additem() {
	rowcount = rowcount + 1;
	var button = '<a href="#" id="remo'
			+ rowcount
			+ '" class="btn btn-default bg-red" onclick="removeitem(this)"><i class="fa fa-trash"></i></a>';
	var appendrow = '<tr id="rowcnt' + rowcount
			+ '"><td style="text-align:center">' + button
			+ '</td><td id="itemcode' + rowcount + '"></td>';
	appendrow = appendrow + '<td><select style="width: 100%;" id="item'
			+ rowcount
			+ '" onchange="itemchange(this)" onfocus="focusFunction(' + '\''
			+ rowcount + '\'' + ')"  onblur="blurFunction(' + '\'' + rowcount
			+ '\'' + ')"></select></td>';
	appendrow = appendrow
			+ '<td><input id="quan'
			+ rowcount
			+ '" style="text-align:right;"type="number" min="0" onfocus="focusFunction('
			+ '\'' + rowcount + '\'' + ')"  onblur="blurFunction(' + '\''
			+ rowcount + '\''
			+ ')" placeholder="Quantity" onchange="calc(this)"></td>';
	appendrow = appendrow
			+ '<td><input id="rate'
			+ rowcount
			+ '" style="text-align:right;"type="number" min="0" placeholder="Rate" onchange="calc(this)" onfocus="focusFunction('
			+ '\'' + rowcount + '\'' + ')"  onblur="blurFunction(' + '\''
			+ rowcount + '\'' + ')"><input id="tscflag' + rowcount
			+ '" type="hidden"><input id="vatflag' + rowcount
			+ '" type="hidden"></td>';
	appendrow = appendrow
			+ '<td><span id="rev'
			+ rowcount
			+ '" style="text-align:right;"type="number" min="0" class="revclass" onfocus="focusFunction('
			+ '\'' + rowcount + '\'' + ')"  onblur="blurFunction(' + '\''
			+ rowcount + '\'' + ')"></span></td>';
	appendrow = appendrow
			+ '<td><span id="tsc'
			+ rowcount
			+ '" style="text-align:right;"type="number" min="0" class="tscclass"></span></td>';
	appendrow = appendrow
			+ '<td><span id="vat'
			+ rowcount
			+ '" style="text-align:right;"type="number" min="0" class="vatclass"></span></td>';
	appendrow = appendrow
			+ '<td><span id="total'
			+ rowcount
			+ '" style="text-align:right;"type="number" min="0" class="totclass"></span></td></tr>';

	$('#example1').append(appendrow);

	$.each(itemlist, function(key, value) {
		if (value.IS_RECURRING === 'N') {
			$('#item' + rowcount)
					.append(
							new Option(value.DESCRIPTION, value.ITEM_CODE,
									false, false));
		}
	});
	$('#item' + rowcount).val('');
	$('#item' + rowcount).select2({
		placeholder : "Select a item",
		allowClear : true
	});
}

function itemchange(a) {
	var itemid = a.id.substring(4);
	$('#itemcode' + itemid).html(a.value);

	// filling rate and

	$.each(itemlist, function(key, value) {
		if (value.ITEM_CODE === a.value) {
			$.get('../cashsale/getitemtariff', {
				Itemcode : a.value
			}, function(response) {
				$('#tscflag' + itemid).val(value.TAXABLE_AMT);
				$('#vatflag' + itemid).val(value.VATABLE_AMT);
				$('#rate' + itemid).val(Number(response));

			});

		}

	});
}

function calc(a) {

	var itemid = a.id.substring(4);
	var rate = $('#rate' + itemid).val();
	var quantity = $('#quan' + itemid).val();
	var tsc = 0;
	if ($('#tscflag' + itemid).val() != 0) {
		tsc = globaltsc * 0.01 * Number(rate) * Number(quantity);
		tsc = Number(tsc.toFixed(2));
	}

	$('#tsc' + itemid).html(tsc);
	var vat = 0;
	if ($('#tscflag' + itemid).val() != 0) {
		vat = globalvat * 0.01 * Number(tsc + (rate * quantity));
		vat = Number(vat.toFixed(2));
	}
	var rev = Number(rate * quantity) + Number(tsc) + Number(vat);
	rev = Number(rev.toFixed(2));
	$('#vat' + itemid).html(vat);
	$('#rev' + itemid).html(rate * quantity);

	$('#total' + itemid).html(rev);

	// getting sum of footer
	getsumoffooter();
}

function getsumoffooter() {
	tfooter('revclass', 'sumrev', null);
	tfooter('tscclass', 'sumtsc', null);
	tfooter('vatclass', 'sumvat', null);
	tfooter('totclass', 'sumtot', null);

}
function removeitem(a) {
	var itemid = a.id.substring(4);
	$('#rowcnt' + itemid).remove();
	getsumoffooter();
}

function tfooter(clas, fid, msid) {
	var sum = 0;

	// iterate through each textboxes and add the values
	$("." + clas).each(function() {

		// add only if the value is number
		if (!isNaN($(this).text()) && $(this).text().length != 0) {
			sum += parseFloat($(this).text());
		} else if (!isNaN($(this).val()) && $(this).val().length != 0) {
			sum += parseFloat($(this).val());
		}
	});

	// .to111Fixed() method will roundoff the final sum to 2 decimal places
	$('#' + fid).text(sum.toFixed(2));
	if (msid != null) {
		$('#' + msid).text(sum.toFixed(2));
	}
}

function post() {
	var temparray = [];
	$(".revclass").each(function() {
		var rowid = (this.id.substring(3));

		var arraydata = new Object();
		arraydata.ITEM_CODE = $('#item' + rowid).val();
		arraydata.RATE = $('#rate' + rowid).val();
		arraydata.QUANTITY = $('#quan' + rowid).val();
		arraydata.REV = $('#rev' + rowid).html();
		arraydata.TSC = $('#tsc' + rowid).html();
		arraydata.VAT = $('#vat' + rowid).html();
		temparray.push(arraydata);
	});
	// alert(JSON.stringify(temparray));
	var param = {
		SP_CODE : $('#SP_CODE').val(),
		nepdate : $('#nepdate').val(),
		BANK_CODE : $('#BANK_CODE').val(),
		// REMARKS:$('#remarks').val(),
		AMT : $('#AMT').val()

	};
	var valid = validateform(param);
	if (valid !== 0) {
		alert("Invalid !!!!! " + valid)
		return false;
	}
	if ($('#AMT').val() != $('#sumtot').html()) {
		alert("Invalid Bank Amount");
		return false;
	}
	$('.overlay').fadeIn();
	$('#savebtn').prop('disabled', true);

	$("#savebtn").html('Saving...');

	// posting data
	$.post('../cashsale/savebill', {
		SP_CODE : $('#SP_CODE').val(),
		nepdate : $('#nepdate').val(),
		BANK_CODE : $('#BANK_CODE').val(),
		REMARKS : $('#remarks').val(),
		AMT : $('#AMT').val(),
		DATA : JSON.stringify(temparray)

	}, function(response) {
		debugger;
		alert((response));
		$('#savebtn').prop('disabled', false);
		$("#savebtn").html('response.substring(0, 6)');
		
		if (response.substring(0, 6) === "Sucess") {
			$("#transno").val(response.substring(40));
			$("#savebtn").html('Saved');
			$('#savebtn').prop('disabled', true);
			$("#hiddentransno").val(response.substring(40));
			$('#printbtn').prop('disabled', false);
			
		}

		// this.disabled=false;
	});
	$('.overlay').fadeOut();

	// alert(JSON.stringify(temparray));
}
