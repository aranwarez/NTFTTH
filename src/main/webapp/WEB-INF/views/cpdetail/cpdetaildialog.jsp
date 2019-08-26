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
				<h4 class="modal-title" id="myModalLabel">New Content Provider
					Rate Define</h4>
			</div>

			<div class="modal-body">
				<div class="form-group">
					<label for="transid">ID</label> <input type="text"
						class="form-control" name="TRANS_ID" id="TRANS_ID"
						placeholder="Auto Generate ID" readonly="true"> <label
						for="cpcode">Cp Code</label> <input type="text"
						class="form-control" name="CP_CODE" id="CP_CODE"
						placeholder="Enter Content Provider Name" readonly="true">
					<label for="spcode">Vas Provider Code</label> <input type="text"
						class="form-control" name="SP_CODE" id="SP_CODE"
						placeholder="Enter VAS Provider Name" readonly="true"> <label
						for="spcode">Vas Provider</label> <input type="text"
						class="form-control" name="SP_NAME" id="SP_NAME" readonly="true"
						placeholder="Enter VAS Provider Name"> <label
						for="servicecode">Service</label> <input type="text"
						class="form-control" name="SERVICE_CODE" id="SERVICE_CODE"
						readonly="true" placeholder="Enter VAS Service"> <label
						for="cpname">Cp Name</label> <input type="text"
						class="form-control" name="CP_NAME" id="CP_NAME" readonly="true"
						placeholder="Enter Content Provider"> <label
						for="esmecode">ESME Code</label> <input type="text"
						class="form-control" name="ESME_CODE" id="ESME_CODE"
						readonly="true" placeholder="Enter Esme Code"> <label
						for="package">Package</label> <input type="text"
						class="form-control" name="PACKAGE_TYPE" id="PACKAGE_TYPE"
						readonly="true" placeholder="Enter Esme Code">
					<table>
						<tr>
							<td><label for="Rate">Rate</label> <input
								style="width: 280px;" class="form-control" type="number"
								name="RATE" id="RATE" placeholder="Enter Rate"></td>
							<td><label for="effective">Effective Dt.</label> <input
								type="text" style="width: 280px;"
								class="nepali-calendar form-control" name="EFFECTIVE_DT"
								id="EFFECTIVE_DT" placeholder="Enter effective dt."></td>

						</tr>

					</table>
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
<div class="modal fade" id="editRate" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="editRatetitle">Update Rate</h4>
			</div>

			<div class="modal-body">
				<table>
					<tr>
						<td><label for="Rate">Rate</label> <input
							style="width: 280px;" class="form-control" type="number"
							name="RATE" id="EDITRATE" placeholder="Enter Rate"></td>
						<td><label for="effective">Effective Dt.</label> <input
							type="text" style="width: 280px;"
							class="nepali-calendar form-control" name="EFFECTIVE_DT"
							id="EDITEFFECTIVE_DT" placeholder="Enter effective dt."></td>

					</tr>

				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="submit" class="btn btn-primary"
					onclick="updaterate()">Update Rate</button>
			</div>
		</div>
	</div>
</div>

<!-- End of Target Modal -->
