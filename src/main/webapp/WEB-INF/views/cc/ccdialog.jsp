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
				<h4 class="modal-title" id="myModalLabel">Collection Center
					define</h4>
			</div>
			<div class="modal-body">


				<div class="form-group">
					<label for="REGION_CODE">REGION</label> <select id="DOREGION_CODE"
						onchange="return getDOAccountCenter()">
						<option></option>
					</select>

				</div>

				<div class="form-group">
					<label for="DOACCOUNT_CENTER">ACCOUNT CENTER</label> <select
						id="DOACC_CEN_CODE" onchange="return getDOCC()">
						<option></option>
					</select>
				</div>

				<div class="form-group">
					<label for="name">CC_CODE</label> <input type="text"
						class="form-control" id="CC_CODE"
						title="Max Character length is 8">
				</div>

				<div class="form-group">
					<label for="name">DESCRIPTION</label> <input type="text"
						class="form-control" id="DESCRIPTION">
				</div>

				<div class="form-group">
					<label for="ERP_CC_CD">ERP_CC_CD</label> <input type="text"
						class="form-control" id="ERP_CC_CD">
				</div>

				<div class="form-group">
					<label for="CC_TYPE">CC_TYPE</label> <select type="text"
						class="form-control" id="CC_TYPE">
						<option value="C">Collection</option>
						<option value="N">Normal</option>
						<option value="H">Head</option>
					</select>
				</div>
				<div class="form-group">
					<label for="OFFICE_CODE">Office</label> <select id="OFFICE_CODE" class="form-control">
						<option></option></select>
				</div>

			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="submit" class="btn btn-primary"
					onclick="return saveCC()">Save changes</button>
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
				<h4 class="modal-title" id="myModalLabel">Edit Collection
					Center</h4>
			</div>
			<div class="modal-body">

				<div class="form-group">
					<label for="REGION_CODE">REGION</label> <select
						id="EDITREGION_CODE" onchange="return getEDITAccountCenter()">
						<option></option>
					</select>

				</div>

				<div class="form-group">
					<label for="DOACCOUNT_CENTER">ACCOUNT CENTER</label> <select
						id="EDITACC_CEN_CODE">
						<option></option>
					</select>
				</div>

				<div class="form-group">
					<label for="name">CC_CODE</label> <input type="text"
						class="form-control" id="EDITCC_CODE" readonly="readonly"
						title="Max Character length is 5">
				</div>

				<div class="form-group">
					<label for="name">DESCRIPTION</label> <input type="text"
						class="form-control" id="EDITDESCRIPTION">
				</div>

				<div class="form-group">
					<label for="ERP_CC_CD">ERP_CC_CD</label> <input type="text"
						class="form-control" id="EDITERP_CC_CD">
				</div>

				<div class="form-group">
					<label for="CC_TYPE">CC_TYPE</label> <select class="form-control"
						id="EDITCC_TYPE">
						<option value="C">Collection</option>
						<option value="N">Normal</option>
						<option value="H">Head</option>
					</select>
				</div>

				<div class="form-group">
					<label for="OFFICE_CODE">Office</label> <select
						id="EDITOFFICE_CODE" class="form-control">
						<option></option>
						</select>
				</div>

			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="submit" class="btn btn-primary"
					onclick="return updateCollectioncenter()">Update</button>
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
				<h4 class="modal-title" id="myModalLabel">Collection Center
					delete</h4>
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