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
				<h4 class="modal-title" id="myModalLabel">New Stream Type
					Define</h4>
			</div>

			<div class="modal-body">
				<div class="form-group">
					<label for="name">STREAM_TYPE</label> <input type="text"
						class="form-control" name="STREAM_TYPE" id="STREAM_TYPE"
						placeholder="Enter Stream Type">
				</div>
				<div class="form-group">
					<label for="name">DESCRIPTION</label> <input type="text"
						class="form-control" name="DESCRIPTION" id="DESCRIPTION"
						placeholder="Enter description">
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="submit" class="btn btn-primary" onclick="saveStream()">Save
					changes</button>
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
				<h4 class="modal-title" id="myModalLabel">Edit Stream</h4>
			</div>

			<form action="<c:url value="/stream/update" />" method="post"
				acceptCharset="UTF-8">

				<div class="modal-body">
					<div class="form-group">
						<label for="name">STREAM_TYPE</label> <input type="text"
							class="form-control" name="STREAM_TYPE" id="EDITSTREAM_TYPE"
							placeholder="Enter Stream" readonly="readonly">
					</div>

					<div class="form-group">
						<label for="name">DESCRIPTION</label> <input type="text"
							class="form-control" name="DESCRIPTION" id="EDITDESCRIPTION"
							placeholder="Enter description">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" onclick="return updateStream();"
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
				<h4 class="modal-title" id="myModalLabel">Stream delete</h4>
			</div>

			<div class="modal-body">
				<p>Are you sure you want to delete this data. This cannot be
					undone</p>
			</div>

			<div class="modal-footer">
				<form action="<c:url value="/stream/delete" />" method="post"
					acceptCharset="UTF-8">
					<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
					<button type="button" class="btn btn-primary"
						onclick="return del()">Yes</button>
				</form>
			</div>
		</div>
	</div>
</div>
