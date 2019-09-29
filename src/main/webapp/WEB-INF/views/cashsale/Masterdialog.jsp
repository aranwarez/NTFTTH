<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<!-- edit modal -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">

	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Bill View</h4>
			</div>


			<div class="modal-body">
				<div class="form-group no-padding">
					<table class="table table-condensed">
						<tr>
							<td><label for="Receipt No ">Trans No.</label></td>
							<td><label id="transno"></label></td>
							<td><label for="salesdt">Sales Dt.</label></td>
							<td><label id="salesdt">Sales Dt.</label></td>
						</tr>
						<tr>
							<td><label for="spcode">Customer</label></td>
							<td><label id="spcode">Customer</label></td>
							<td><label for="Bankcd">Bank Name</label></td>
							<td><label class="form" id="bankcd">Bank Name</label></td>
						</tr>

						<tr>
							<td><label for="amt">Amount.</label></td>
							<td><label id="amt"></label></td>
							<td><label for="Remarks">Remarks</label></td>
							<td><label id="remarks"></label></td>
						</tr>

					</table>

					<table id="detailtab" class="table table-condensed">
						<thead>

							<tr>

								<th>Code</th>
								<th>Item Name</th>
								<th>Quantity</th>
								<th>Rate</th>
								<th>Revenue</th>
								<th>TSC</th>
								<th>VAT</th>
								<th>Total</th>
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
				<h4 class="modal-title" id="myModalLabel">Cancel Bill for <span id="cancellabel"></span></h4>
			</div>

			<div class="modal-body">
				<p>Are you sure you want to cancel this receipt. This cannot be
					undone</p>
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
				<button type="button" class="btn btn-primary" onclick="return del()">Yes</button>
			</div>
		</div>
	</div>
</div>
