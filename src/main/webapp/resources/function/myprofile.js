function validateForm() {
	
  var fullname=$("#UFULL_NAME").val();
  var mobile=$("#UMOBILE_NO").val();

  
  
  if (fullname == "") {
    alert("Full Name must be filled out");
    return false;
  }
  
  if(isNaN(mobile))
  {
      alert("please enter digits only");
      return false;
  }

  else if(mobile.length!=10)
  {
      alert("invalid mobile number");
      return false;
  }
  
  if (mobile == "") {
	    alert("mobile number must be filled out");
	    return false;
	 }
  updateProfile()
  return false;
}

function updateProfile(){
	var USER_ID = $("#UUSER_ID").val();
	
	var FULL_NAME = $("#UFULL_NAME").val();	
	var MOBILE_NO = $("#UMOBILE_NO").val();
	
	$.post('../profile-update/saveJS', {
		USER_ID: USER_ID,
		FULL_NAME: FULL_NAME, MOBILE_NO: MOBILE_NO
	}, function (data) {
	    alert(data);
	    if (data.substring(0, 6) === "Succes") {
	        location.reload();
	    }


	});
}

function OldPassword() {
    var myOldPassword = $("#myOldPassword").val();
   
    
    $.post('../checkOldPASS', { PASSWORD: myOldPassword}, function (response) {
        //     alert('!'+response+'!');
        $("#oldpass").html(response);

    });
}

function changePassword() {
    //   alert($("#oldpass").html());
    // checkPass();

    if ($("#oldpass").html().substring(0, 9) == 'Confirmed' && $('#pass').val() == $('#cpassmatch').val()) {


        $.post('../update-user/password', { PASSWORD: $("#pass").val()}, function (response) {
            $("#updatepass").html(response);
            alert(response);
            location.reload();
        });

    }
    else alert('Confirmation password didnt match!!')



}
