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
			<div id="complainservcies" class="modal-body"></div>
			<div class="modal-body">
				<input class="form-control" id="contatper"
					placeholder="Enter Contact Person Name"> <input
					class="form-control" id="contactperno" maxlength="10"
					placeholder="Enter Contact Number"> <input
					class="form-control" id="userremark"
					placeholder="Enter User Remark">

			</div>


			<div class="modal-footer">
			<button type="submit" class="btn btn-success pull-left"
					onclick="PostRegister(true)">Solved</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="submit" class="btn btn-primary"
					onclick="PostRegister(false)">Register</button>
					
			</div>
		</div>
	</div>
</div>



<!-- List of all service modal -->
<div class="modal fade" id="serviceModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Service List</h4>
			</div>
			<div id="complainservcies" class="modal-body"></div>
			<div class="modal-body">
			<table style="width:100%"class="table table-condensed" id='listservice'>
			<thead>
			<tr><td>
			CPE Service</td><td>Customer name
			</td>
			<td>Service Number</td>
			</tr>
			</thead>
			</table>
			</div>


			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				
			</div>
		</div>
	</div>
</div>