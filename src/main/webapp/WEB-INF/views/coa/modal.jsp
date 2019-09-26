<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- new modal -->
<div class="modal fade" id="myModal" tabindex=-1 role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">New Account Setup</h4>
			</div>

			<div class="modal-body">
				<div class="form-group">
					<label for="SLDG_CODE">SLDG_CODE</label> <input type="text"
						class="form-control" name="SLDG_CODE" id="SLDG_CODE"
						placeholder="Account Code"> <label for="Description">Description
						Code</label> <input type="text" class="form-control" name="Description"
						id="Description" placeholder="Enter Description Name"> <label
						for="PARENT_SLDG_CODE">PARENT_SLDG_CODE</label> <input type="text"
						class="form-control" name="PARENT_SLDG_CODE" id="PARENT_SLDG_CODE"
						placeholder="Enter Parent Account Code"> <label
						for="STATEMENT_TYPE">STATEMENT TYPE</label> <select
						class="form-control" name="STATEMENT_TYPE" id="STATEMENT_TYPE">
						<option value="1">Liabilities</option>
						<option value="2">Asset</option>
						<option value="3">Income</option>
						<option value="4">Expenses</option>

					</select> <label for="ACTIVE_FLAG">ACTIVE_FLAG</label> <select
						class="form-control" name="ACTIVE_FLAG" id="ACTIVE_FLAG">
						<option value="Y">Y</option>
						<option value="N">N</option>
					</select> <label for="DR_CR_FLAG">DR_CR_FLAG</label> <select
						class="form-control" name="DR_CR_FLAG" id="DR_CR_FLAG">
						<option value="1">DR</option>
						<option value="-1">CR</option>
					</select> <label for="REMARKS">REMARKS</label> 
						<textarea class="form-control" rows="3" name="REMARKS" id="REMARKS"
						placeholder="Enter Remarks"></textarea>
					
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="submit" class="btn btn-primary"
					onclick="saveCpdetail()">Save changes</button>
			</div>

		</div>
	</div>
</div>



<!-- detail modal -->
<div class="modal fade" id="myDetailModal" tabindex=-1 role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="RatesLabel">Rates</h4>
			</div>

			<div class="modal-body">
				<div class="table-responsive">
					<table id="detailtable" class="table table-bordered table-striped">
						<thead>
							<tr>
								<th>Rate</th>
								<th>Effective Dt</th>
								<th>Create_by</th>
								<th>Edit</th>
								<th>Delete</th>
							</tr>
						</thead>

					</table>
				</div>



			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>

		</div>
	</div>
</div>


<!-- Edit modal -->
<div class="modal fade" id="editcoa" tabindex=-1 role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">New Account Setup</h4>
			</div>

			<div class="modal-body">
				<div class="form-group">
					<label for="SLDG_CODE">SLDG_CODE</label> <input type="text"
						class="form-control" name="SLDG_CODE" id="SLDG_CODE"
						placeholder="Account Code"> <label for="Description">Description
						Code</label> <input type="text" class="form-control" name="Description"
						id="Description" placeholder="Enter Description Name"> <label
						for="PARENT_SLDG_CODE">PARENT_SLDG_CODE</label> <input type="text"
						class="form-control" name="PARENT_SLDG_CODE" id="PARENT_SLDG_CODE"
						placeholder="Enter Parent Account Code"> <label
						for="STATEMENT_TYPE">STATEMENT TYPE</label> <select
						class="form-control" name="STATEMENT_TYPE" id="STATEMENT_TYPE">
						<option value="1">Liabilities</option>
						<option value="2">Asset</option>
						<option value="3">Income</option>
						<option value="4">Expenses</option>

					</select> <label for="ACTIVE_FLAG">ACTIVE_FLAG</label> <select
						class="form-control" name="ACTIVE_FLAG" id="ACTIVE_FLAG">
						<option value="Y">Y</option>
						<option value="N">N</option>
					</select> <label for="DR_CR_FLAG">DR_CR_FLAG</label> <select
						class="form-control" name="DR_CR_FLAG" id="DR_CR_FLAG">
						<option value="1">DR</option>
						<option value="-1">CR</option>
					</select> <label for="REMARKS">REMARKS</label> 
						<textarea class="form-control" rows="3" name="REMARKS" id="REMARKS"
						placeholder="Enter Remarks"></textarea>
					
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="submit" class="btn btn-primary"
					onclick="saveCpdetail()">Save changes</button>
			</div>

		</div>
	</div>
</div>

<!-- End of COA Modal -->
