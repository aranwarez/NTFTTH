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
					<span aria-hidden="true">&times; </span>
					<div class="overlay">
						<i class="fa fa-refresh fa-spin"></i>
					</div>
				</button>


				<h4 class="modal-title" id="myModalLabel">New Work Order Entry

				</h4>

			</div>

			<div class="modal-body">


				<div class="form-group">
					<label for="TEAM_CODE">Work Order Element Type</label> <select
						id="type" name="role_code" class="form-control"
						onchange="elementtype()">
						<c:forEach var="role" items="${workorder_element}">

							<option value="${role.ID}">${role.DESCRIPTION}</option>

						</c:forEach>

					</select>

				</div>

				<div class="form-group divhidden" id="DIVOLT">
					<label for="TEAM_CODE">OLT</label> <select id="OLT"
						class="form-control"
						onchange="getCardfromOLT(this.value);getODFFromOLT(this.value)">
						<c:forEach var="role" items="${oltlist}">

							<option value="${role.DESCRIPTION}">${role.DESCRIPTION}</option>

						</c:forEach>

					</select>

				</div>
				<div class="form-group divhidden" id="DIVOLTCARD">
					<label for="DESCRIPTION">OLTCARD</label> <select id="OLTCARD"
						onchange="getPortFromCard(this.value);"></select>

				</div>

				<div class="form-group divhidden" id="DIVOLTPORT">
					<label for="DESCRIPTION">OLTPORT</label> <select id="OLTPORT"></select>

				</div>
				<div class="form-group divhidden" id="DIVODF">
					<label for="DESCRIPTION">ODF</label> <select id="ODF"></select>

				</div>



				<div class="form-group divhidden" id="DIVFDC">
					<label for="TEAM_CODE">FDC CODE</label> <select id="fdc"
						name="role_code" class="form-control"
						onchange="getFAPfromFDC(this.value)">
						<c:forEach var="role" items="${location}">

							<option value="${role.FDC}">${role.FDC}</option>

						</c:forEach>

					</select>

				</div>


				<div class="form-group divhidden" id="DIVFAP">
					<label for="DESCRIPTION">FAP</label> <select id="FAP"></select>

				</div>
				<div class="form-group">
					<label for="DESCRIPTION">Remarks</label>
					<textarea id="remarks" class="form-control" rows="3"
						placeholder="Enter Remarks"></textarea>
				</div>
				<div class="form-group">
					<label for="DESCRIPTION">Start Time</label> <input
						type="datetime-local" id="starttime">
				</div>
				<div class="form-group">
					<label for="DESCRIPTION">Enable Flag</label> <input type="checkbox"
						id="active_flag">
				</div>




			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="submit" class="btn btn-primary"
					onclick="return saveWorkOrder()">
					Save changes
					<div class="overlay">
						<i class="fa fa-refresh fa-spin"></i>
					</div>

				</button>
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
				<h4 class="modal-title" id="myModalLabel">Edit Team</h4>
			</div>

			<form action="<c:url value="/service/update" />" method="post"
				acceptCharset="UTF-8">

				<div class="modal-body">
					<div class="form-group">
						<label for="TEAM_CODE">TEAM_CODE</label> <input type="text"
							class="form-control" name="EDITTEAM_CODE" id="EDITTEAM_CODE"
							placeholder="Enter TEAM_CODE" readonly="readonly">
					</div>

					<div class="form-group">
						<label for="DESCRIPTION">DESCRIPTION</label> <input type="text"
							class="form-control" name="EDITDESCRIPTION" id="EDITDESCRIPTION"
							placeholder="Enter DESCRIPTION">

					</div>



				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" onclick="return updateTeam();"
						class="btn btn-primary">Update</button>
				</div>

			</form>

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
				<h4 class="modal-title" id="myModalLabel">Complete a Work Order</h4>
			</div>

			<div class="modal-body">
				<p>Are you sure you want to complete this work order. Please make sure date and time is correct. This will close all the ticket related to work-order from start time to completion time</p>
			</div>

			<div class="modal-footer">
				<form action="<c:url value="/workorder/delete" />" method="post"
					acceptCharset="UTF-8">
					<div class="form-group">
						<textarea id="endremarks" class="form-control" rows="3"
							placeholder="Enter Remarks"></textarea>
					</div>
					<div class="form-group">
						<label for="DESCRIPTION">Completion Date & Time</label> <input
							type="datetime-local" id="endtime">
					</div>

					<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
					<button type="button" class="btn btn-primary"
						onclick="return del()">Yes</button>
				</form>
			</div>
		</div>
	</div>
</div>
