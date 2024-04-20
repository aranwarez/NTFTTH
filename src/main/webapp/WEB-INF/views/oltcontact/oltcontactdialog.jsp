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
				<h4 class="modal-title" id="olttitlelable"></h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label for="name">Contact No</label> <input type="text"
						class="form-control" id="CONTACT_NO"
						placeholder="Enter Contact Number">
				</div>

				<div class="form-group">
					<label for="name">Contact Name</label> <input type="text"
						class="form-control" id="CONTACT_NAME"
						placeholder="Enter Contact Person Name">
				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary"
					onclick="return saveOLTCONTACT();">Save changes</button>
			</div>
		</div>
	</div>
</div>



<!-- delete modal -->

<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">

	<div class="modal-dialog" role="document">

		<div class="modal-content">

			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="deletecontactLabel">Delete Contact</h4>
			</div>

			<div class="modal-body">
				<p>Are you sure you want to delete this data. This cannot be
					undone</p>
			</div>

			<div class="modal-footer">

				<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
				<button type="button" class="btn btn-primary" onclick="return del()">Yes</button>

			</div>
		</div>
	</div>
</div>


<!-- edit modal -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="eolttitlelable"></h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label for="name">Contact No</label> <input type="text"
						class="form-control" id="eCONTACT_NO"
						placeholder="Enter Contact Number">
				</div>

				<div class="form-group">
					<label for="name">Contact Name</label> <input type="text"
						class="form-control" id="eCONTACT_NAME"
						placeholder="Enter Contact Person Name">
				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary"
					onclick="return updateOLTCONTACT();">Save changes</button>
			</div>
		</div>
	</div>
</div>


