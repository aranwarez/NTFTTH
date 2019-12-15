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
				<h4 class="modal-title" id="myModalLabel">New Bank</h4>
			</div>

			<div class="modal-body">
				<div class="form-group">
					<label for="name">BANK CODE</label> <input type="text"
						class="form-control" name="STREAM_TYPE" id="BANK_CD"
						placeholder="Enter BANK Code"> <label for="name">DESCRIPTION</label>
					<input type="text" class="form-control" name="DESCRIPTION"
						id="DESCRIPTION" placeholder="Enter description"> <label
						for="name">Address</label> <input type="text" class="form-control"
						name="DESCRIPTION" id="ADDRESS" placeholder="Enter Address">
					<label for="name">Account No</label> <input type="text"
						class="form-control" name="DESCRIPTION" id="ACCTNO"
						placeholder="Enter Account No."> <label for="name">Account
						Type</label> <input type="text" class="form-control" name="DESCRIPTION"
						id="ACCTYPE" placeholder="Enter Type"> <label for="name">Active
						FLAG</label> <select class="form-control" id="ACCTFLAG"><option
							value="T">Y</option>
						<option value="N">N</option></select> <label for="name">Deactive
						Date</label> <input type="text" class="nepali-calendar form-control"
						name="DESCRIPTION" id="DEACTIVE_DT">
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="submit" class="btn btn-primary" onclick="addbank()">Add
					Bank</button>
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
				<h4 class="modal-title" id="myModalLabel">Edit Bank</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label for="name">BANK CODE</label> <input type="text"
						class="form-control" readonly="readonly" id="EDITBANK_CD"
						placeholder="Enter BANK Code"> <label for="name">DESCRIPTION</label>
					<input type="text" class="form-control" name="DESCRIPTION"
						id="EDITDESCRIPTION" placeholder="Enter description"> <label
						for="name">Address</label> <input type="text" class="form-control"
						name="DESCRIPTION" id="EDITADDRESS" placeholder="Enter Address">
					<label for="name">Account No</label> <input type="text"
						class="form-control" name="DESCRIPTION" id="EDITACCTNO"
						placeholder="Enter Account No."> <label for="name">Account
						Type</label> <input type="text" class="form-control" name="DESCRIPTION"
						id="EDITACCTYPE" placeholder="Enter Type"> <label
						for="name">Active FLAG</label> <select class="form-control"
						id="EDITACCTFLAG"><option value="T">Y</option>
						<option value="N">N</option></select> <label for="name">Deactive
						Date</label> <input type="text" class="nepali-calendar form-control"
						name="DESCRIPTION" id="EDITDEACTIVE_DT">
				</div>


			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" onclick="editbank()"
					class="btn btn-primary">Update</button>
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
				<h4 class="modal-title" id="myModalLabel">Stream delete</h4>
			</div>

			<div class="modal-body">
				<p>Are you sure you want to delete this data. This cannot be
					undone</p>
			</div>

			<div class="modal-footer">
				<form action="<c:url value="/stream/delete" />" method="post"
					acceptCharset="UTF-8">
					<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
					<button type="button" class="btn btn-primary"
						onclick="return del()">Yes</button>
				</form>
			</div>
		</div>
	</div>
</div>
