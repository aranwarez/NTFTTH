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
					<h4 class="modal-title" id="myModalLabel">Role define</h4>
				</div>
				


				<form action="<c:url value="/role/save" />" method="post"
					acceptCharset="UTF-8">
					
					<div class="modal-body">
						<div class="form-group">
							<label for="name">ROLE_CODE</label> <input type="text"
								class="form-control" name="ROLE_CODE" placeholder="Enter Role">

						</div>

						<div class="form-group">
							<label for="name">DESCRIPTION</label> <input type="text"
								class="form-control" name="DESCRIPTION"
								placeholder="Enter description">
						</div>

					</div>


					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="submit" class="btn btn-primary">Save
							changes</button>
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
					<h4 class="modal-title" id="myModalLabel">Role delete</h4>
				</div>

				<div class="modal-body">
					<p>Are you sure you want to delete this data. This cannot be
						undone</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
					<button type="button" class="btn btn-primary"
						onclick="return del()">Yes</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- edit modal -->
        <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">Edit Role </h4>
                    </div>
                    
                    <form action="<c:url value="/role/update" />" method="post"
					acceptCharset="UTF-8">
					
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="name">ROLE_CODE</label>
                            <input type="text" class="form-control" name="ROLE_CODE" id="EDITROLE_CODE" placeholder="Enter Role" readonly="readonly">
                        </div>

                        <div class="form-group">
                            <label for="name">DESCRIPTION</label>
                            <input type="text" class="form-control"  name="DESCRIPTION" id="EDITDESCRIPTION" placeholder="Enter description">
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" onclick="return updateRole();" class="btn btn-primary">Update</button>
                    </div>
                    
                    </form>
                    
                </div>
            </div>
        </div>
        