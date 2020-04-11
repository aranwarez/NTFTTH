<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<c:set var="count" value="0" scope="page" />

<div class="col-xs-12 table-responsive">
 
                
                  <input type="radio" name="IS_ACCESS_ALL" class="flat-red" onclick="checkFlag('Y');"> Access All               
                  <input type="radio" name="IS_ACCESS_ALL" class="flat-red" onclick="checkFlag('N');" > Disable ALL
                
               
                 
             
	<table id="checkDatatable" class="table table-striped example1">
		<thead>
			<tr>
				<th></th>
				<th>Sub Team</th>
				<th>Active Date</th>
				<th>DeActive Date</th>
				<td>Access</td>
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
					<input type="text" class="nepali-calender from-control" id="ACTIVE_DT${count}">
					</td>
						
					<td>
					<input type="text" class="nepali-calender from-control" id="DEACTIVE_DT${count}">
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
	onclick="return saveUserTeam()" class="btn btn-success">


