<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<!-- new modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Register New Complain</h4>
			</div>
			<div id="complainservcies" class="modal-body">
				

			</div>
			<div  class="modal-body">
				<input class="form-control" id="contatper" placeholder="Enter Contact Person Name">
				<input class="form-control" id="contactperno" placeholder="Enter Contact Number">
						<input class="form-control" id="userremark" placeholder="Enter User Remark">

			</div>
						

			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="submit" class="btn btn-primary"
					onclick="PostRegister()">Register</button>
			</div>
		</div>
	</div>
</div>