<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<c:set var="count" value="0" scope="page" />

<div class="col-xs-12 table-responsive">

	<table id="checkDatatable" class="table table-striped example1">
		<thead>
			<tr>
				<th></th>
				<th></th>
				<th>Access</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach var="pmanu" items="${subteamlist}">
			
			<c:set var="count" value="${count + 1}" scope="page"/>
			
				<tr>
					<td>${count}</td>
					<td>
						<div class="checkbox">
							<label> <input type="checkbox"
								class="mastersetup${count}"
								value="${pmanu.SUB_TEAM_CODE}"  style="display:none">${pmanu.DESCRIPTION} (${pmanu.SUB_TEAM_CODE})(${pmanu.TEAM_CODE})
							</label>
						</div>
					</td>
					<td>
					<select class="list${count}">
							<option value="Y">Y</option>
							<option value="N" selected>N</option>
					</select></td>
					
				</tr>
				

			</c:forEach>

		</tbody>
	</table>
</div>


<input type="submit" name="save" value="save"
	onclick="return saveServiceTeam()" class="btn btn-success">


