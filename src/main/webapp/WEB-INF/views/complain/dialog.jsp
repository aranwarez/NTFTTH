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
			<div class="modal-body">
				<div class="form-group">
					<label for="name">Service</label> <select id="serviceid" onchange="getProblemlist(this.value)">
					<c:forEach var="list" items="${Services}">
					<option value="${list.SERVICE_ID}">${list.DESCRIPTION}</option>
					
					</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label for="name">Problem</label> <select id="problemid"></select>
				</div>
				
				<div class="form-group">
					<label for="name">Remark</label> <input type="text"
						class="form-control" id="DESCRIPTION">
				</div>
				<div class="form-group">
					<label for="name">DESCRIPTION</label> <input type="text"
						class="form-control" id="DESCRIPTION">
				</div>
				
				

			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="submit" class="btn btn-primary"
					onclick="return saveRegion()">Save changes</button>
			</div>
		</div>
	</div>
</div>