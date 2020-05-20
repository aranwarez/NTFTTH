var UPLOAD_ID;

function validateForm() {
  var x = document.forms["myForm"]["fname"].value;
  if (x == "") {
    alert("Name must be filled out");
    return false;
  }
}

function editRegion(code,DISPLAY_FLAG) {
	UPLOAD_ID = code;
			$("#EDITDISPLAY_FLAG").val(DISPLAY_FLAG);
			
		}


function updateButton() {
	$.post('../update/uploadpath', {UPLOAD_ID:UPLOAD_ID,
		 DISPLAY_FLAG : $("#EDITDISPLAY_FLAG").val()
	}, function(data) {
		alert(data);
		location.reload();

	});
}
function delFILE(code) {
	UPLOAD_ID = code;

}

function del() {
	$.post('../upload/delete', {
		UPLOAD_ID : UPLOAD_ID
	}, function(data) {
		alert(data);
		location.reload();

	});
}