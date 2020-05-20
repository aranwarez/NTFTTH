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
				<h4 class="modal-title" id="myModalLabel">Upload File Request </h4>
			</div>
			<form method="POST" action="../uploadFile" enctype="multipart/form-data">
			<div class="modal-body">
						
				<div class="form-group">
					<label for="name">File to upload:</label> 
					
					<input type="file" id='inputfile' class="form-control"  name="file" onChange='getoutput()' >
						
 		

				<div class="form-group">
					<label for="name">Name</label> <input type="text"
						class="form-control" id="name"  name="name">
				</div>
				<div class="form-group">
				<label for="name">ROLE</label>
				<select id="role_code" name="role_code" class="form-control">
							 	<c:forEach var="role" items="${rolelist}">

								<option value="${role.getROLE_CODE()}">${role.getDESCRIPTION()}
									(${role.getROLE_CODE()})</option>

							</c:forEach>

						</select> 
				</div>
				
				
				<div class="form-group">
					<label for="name">Display</label> 
					<select class="form-control" id="DISPLAY_FLAG"  name="display_flag">
					<option value="Y">Yes</option>
					<option value="N">No</option>
					<option></option>
						</select>
						
				
				</div>
				
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<input type="submit" class="btn btn-primary" value="Upload">
			</div>
			</div>
			</form>	
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
				<h4 class="modal-title" id="myModalLabel">Update Flag</h4>
			</div>
			<div class="modal-body">
				
				<div class="form-group">
					<label for="name">Display</label> 
					<select class="form-control" id="EDITDISPLAY_FLAG"  name="display_flag">
					<option value="Y">Yes</option>
					<option value="N">No</option>
					
						</select>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary"
					onclick="return updateButton()">Update</button>
			</div>
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
				<h4 class="modal-title" id="myModalLabel">Notification delete</h4>
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