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
				<h4 class="modal-title" id="myModalLabel">Account Center define</h4>
			</div>
			<div class="modal-body">

				<div class="form-group">
					<label for="region">REGION</label> <select id="DOREGION_CODE"
						class="form-control" onchange="return getAccountCenter()">
						<option></option>
					</select>
				</div>

				<div class="form-group">
					<label for="name">ACC_CEN_CODE</label> <input type="text"
						class="form-control" id="ACC_CEN_CODE">
				</div>

				<div class="form-group">
					<label for="name">DESCRIPTION</label> <input type="text"
						class="form-control" id="DESCRIPTION">
				</div>

				<div class="form-group">
					<label for="ERP_ACC_CEN_CD">ERP_ACC_CEN_CD</label> <input
						type="text" class="form-control" id="ERP_ACC_CEN_CD">
				</div>

			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="submit" class="btn btn-primary"
					onclick="return saveAccCenter()">Save changes</button>
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
				<h4 class="modal-title" id="myModalLabel">Edit Account Center</h4>
			</div>
			<div class="modal-body">

				<div class="form-group">
					<label for="region">REGION</label> <select id="EDITREGION_CODE"
						class="form-control" onchange="return getAccountCenter()">
						<option></option>
					</select>
				</div>

				<div class="form-group">
					<label for="ACC_CEN_CODE">ACC_CEN_CODE</label> <input type="text"
						class="form-control" id="EDITRACC_CEN_CODE" readonly="readonly">
				</div>

				<div class="form-group">
					<label for="name">DESCRIPTION</label> <input type="text"
						class="form-control" id="EDITDESCRIPTION"
						placeholder="Enter description">
				</div>


				<div class="form-group">
					<label for="ERP_ACC_CEN_CD">ERP_ACC_CEN_CD</label> <input
						type="text" class="form-control" id="EDITERP_ACC_CEN_CD">
				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary"
					onclick="return updateAccCenter()">Update</button>
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
				<h4 class="modal-title" id="myModalLabel">AccCenter delete</h4>
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