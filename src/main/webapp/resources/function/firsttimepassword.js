
		         
	$(document).ready(function() { 
	    	   setTimeout(function() {
    		   
		   //if(!localStorage.getItem('popupShown')) {	    			   
	    			   notification();
	    			   localStorage.setItem('popupShown', 'false');	    			   
  		//	 }
	    		   
	    		   
	           	check();
	    	   }, 1000);
	    	   
	    	   
	    	   
	    	   $(document).on("keyup", function(e){
	    		   if(e.which == 27){
	    			   setTimeout(function() {
	    			   check();
	    			   }, 1000);
	    		  }
	    		});
	           	
	           });
			function check(){
				var output;
				
					 var USER_ID = $("#USER_ID").val();

						$.get("../firsttime/check", {
						}, function(sss) {
													
							 if(sss==='N'){
								 $("#firsttimepasswordchange").modal();	 
							 }
						});
						   
					}
			function notification(){
				
				$.get("../max-OneActiveFile/check", {
				}, function(sss) {
											
//					 if(sss==='N'){
					$("#image_notication").modal();	 
					
					
					// }
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

			    return false;

			}
			
			

			
			
			
			
		        
			