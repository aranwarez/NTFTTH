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
				<button type="submit" class="btn btn-success pull-left submitbutton"
					onclick="PostRegister(true)">Solved</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="submit" class="btn btn-primary submitbutton"
					onclick="PostRegister(false)">Register</button>

			</div>
		</div>
	</div>
</div>


<!-- Wifi modal -->
<div class="modal fade" id="WifiModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel"><i class="fa fa-wifi"></i>Change Wifi Setting</h4>
			</div>
			<div id="complainservcies" class="modal-body"></div>
			<div class="modal-body">
				<input type="text" class="form-control" id="wifiname"
					placeholder="Wifi Name / SSID">
					 <input type="password"
					class="form-control" id="wifipassword" 
					placeholder="Enter Wifi Password">

			</div>


			<div class="modal-footer">
				<button type="submit" class="btn btn-success pull-left submitbutton"
					onclick="changewifiname()">Change Wifi Name</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="submit" class="btn btn-primary submitbutton"
					onclick="changewifipassword()">Change Wifi Password</button>

			</div>
		</div>
	</div>
</div>
<!-- End of wifi modal -->


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
				<table style="width: 100%" class="table table-condensed"
					id='listservice'>
					<thead>
						<tr>
							<td>CPE Service</td>
							<td>Customer name</td>
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



<!-- View Log AAA status  modal -->
<div class="modal fade" id="AAAstatusModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">

	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="overlay">
				<i class="fa fa-refresh fa-spin"></i>
			</div>

			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">AAA Status Log Detail</h4>
			</div>
			<div id="complainservcies" class="modal-body"></div>
			<div class="modal-body">

				<div class="input-group">
					<div class="input-group-addon">
						<i class="fa fa-calendar"></i>
					</div>
					<input type="date" class="form-control" id="AAAdatepicker"
						min="2020-01-01">
				</div>

				<div id="AAAviewlogdiv"></div>

			</div>


			<div class="modal-footer">
				<button type="button" class="btn bg-purple pull-left"
					onclick="getAAAAuthenticationlog()">Authentication Log</button>
				<button type="button" class="btn bg-blue"
					onclick="getAAAAccountinglog()">Accounting Log</button>
				<button type="button" class="btn bg-red" data-dismiss="modal">Close</button>

			</div>
		</div>
	</div>
</div>

<!-- List all app detail -->

<div class="modal fade" id="nettvappmodal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">

	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="overlay">
				<i class="fa fa-refresh fa-spin"></i>
			</div>

			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">NET TV APP Detail</h4>
			</div>
			
			<div id="nettvappdetail" class="modal-body">

				

				

			</div>


			<div class="modal-footer">
				
				<button type="button" class="btn bg-red" data-dismiss="modal">Close</button>

			</div>
		</div>
	</div>
</div>
