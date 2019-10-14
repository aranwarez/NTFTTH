

$(document).ready(function() {
	// $(".js-example-basic-single").select2();
	$('.nepali-calendar').nepaliDatePicker();

	$("#SP_CODE").select2({
		placeholder : "Select a Service Provider Code"
	// allowClear: false
	});
});

