<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
   <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <c:set var="today" value="<%=new java.util.Date()%>" />
    
    	<div class="pull-right hidden-xs">
				<b>Version</b> ${version}
			</div>
			<strong>Copyright &copy; <fmt:formatDate pattern="yyyy-MM-dd" value="${today}" /> <a
				href="${website}" target="_BLANK">Nepal Telecom</a>.
			</strong> All rights reserved.
<!-- first-time-password-change -->
  
  <div class="modal fade" id="firsttimepasswordchange" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Update your password</h4>
				
				</div>
				
				<form action="<c:url value="/role/save" />" method="post"
					acceptCharset="UTF-8" >
					
					<div class="modal-body">
					<b><p>You need to update your password because this is the first time you are signing in,</br>
					 Thank you !</p></b>
					<div class="form-group">
							<label for="password">Old Password</label> <input type="password"
								class="form-control" id="myOldPassword" onchange="return OldPassword()">
				<span id="oldpass"></span>
						</div>
						
						<div class="form-group">
							<label for="name">New Password</label> <input type="password"
								class="form-control" id="pass">

						</div>

						<div class="form-group">
							<label for="name">Re- Password</label> <input type="password"
								class="form-control" id="cpassmatch"
							>
						</div>

					</div>


					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal" onclick="return check()" >Close</button>
						<button type="submit" class="btn btn-primary" onclick="return changePassword();">Save changes</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	
	
	<!-- image_notication_center -->
    <div class="modal fade" id="image_notication" role="dialog">
      <div class="modal-dialog">
      
        <!-- Modal content-->
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h4 class="modal-title">Notification</h4>
          </div>
          <div class="modal-body">
          
         <div id="container">
		</div>
            
       
            </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
          </div>
        </div>
      </div>