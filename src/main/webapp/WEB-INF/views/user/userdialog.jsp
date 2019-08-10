<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- new modal -->
<div class="modal fade modal-combobox" id="myModal" tabindex="-3"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">User define</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label for="name">USER_ID</label> <input type="text"
						class="form-control" id="USER_ID" placeholder="Enter user id">
				</div>

				<div class="form-group">
					<label for="name">FULL_NAME</label> <input type="text"
						class="form-control" id="FULL_NAME" placeholder="Enter full name">
				</div>
				<div class="form-group">
					<label for="name">PASSWORD</label> <input type="password"
						class="form-control" id="PASSWORD" placeholder="Enter password">
					<span class="matchpass"></span>
				</div>

				<div class="form-group">
					<label>Re- Password </label> <input type="password"
						class="form-control" id="passmatch" onchange="checkPass()">
					<span class="matchpass"></span>

				</div>

				<div class="form-group">
					<label>EMPLOYEE_CODE</label> <select
						class="form-control modal-combobox" name="EMPLOYEE_CODE"
						id="EMPLOYEE_CODE">
						<c:forEach var="COA" items="${empList}">
							<option value="${COA.EMPLOYEE_CODE}">${COA.EMPLOYEE_NAME}(${COA.EMPLOYEE_CODE})</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label>REGION_CODE</label> <select class="form-control"
						name="REGION_CODE" id="REGION_CODE"
						onchange="return getAccountCenter()">
						<option value="">Select Region</option>

						<c:forEach var="region" items="${regionlist}">
							<option value="${region.REGION_CODE}">${region.DESCRIPTION}(${region.REGION_CODE})</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label>ACC_CEN_CODE</label> <select class="form-control"
						name="ACC_CEN_CODE" id="ACC_CEN_CODE" onchange="return getCC()">

					</select>
				</div>
				<div class="form-group">
					<label>CC_CODE</label> <select class="form-control" name="CC_CODE"
						id="CC_CODE">

					</select>
				</div>
				<div class="form-group">
					<label>LOCK_FLAG</label> <input type="radio" name="LOCK_FLAG"
						value="Y" class="LOCK_FLAG"> Yes <input type="radio"
						name="LOCK_FLAG" value="N" class="LOCK_FLAG" checked="checked">
					No

				</div>

				<div class="form-group">
					<label>SUPER_FLAG</label> <input type="radio" name="SUPER_FLAG"
						value="Y" class="SUPER_FLAG"> Yes <input type="radio"
						name="SUPER_FLAG" value="N" class="SUPER_FLAG" checked> No

				</div>
				<div class="form-group">
					<label>DISABLE_FLAG</label> <input type="radio" name="DISABLE_FLAG"
						value="Y" class="DISABLE_FLAG"> Yes <input type="radio"
						name="DISABLE_FLAG" value="N" class="DISABLE_FLAG" checked>
					No

				</div>


				<div class="form-group">
					<label>ROLE_CODE</label> <select class="form-control"
						name="ROLE_CODE" id="ROLE_CODE">

						<c:forEach var="role" items="${rolelist}">
							<option value="${role.ROLE_CODE}">${role.DESCRIPTION}
								(${role.ROLE_CODE})</option>
						</c:forEach>
					</select>
				</div>

				<div class="form-group">
					<label>USER_LEVEL</label> <select class="form-control"
						name="USER_LEVEL" id="USER_LEVEL">

						<option value="1">SUPER</option>
						<option value="2">Region</option>
						<option value="3">ACCOUNT</option>
						<option value="4">CC</option>

					</select>
				</div>
				<div class="form-group">
					<label>Module</label> <select class="form-control" name="MODULE"
						id="MODULE">
						<option value="B">Both</option>
					</select>
				</div>




			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<a class="btn btn-primary" onclick="return saveUser()">Save
					changes</a>
			</div>
		</div>
	</div>
</div>
<!-- edit modal -->
<div class="modal fade modal-combobox" id="editModal" tabindex="-3"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Edit User</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label for="EDITUSER_ID">USER_ID</label> <input type="text"
						class="form-control" id="EDITUSER_ID" disabled="disabled">
				</div>

				<div class="form-group">
					<label for="name">FULL_NAME</label> <input type="text"
						class="form-control" id="EDITFULL_NAME">
				</div>



				<div class="form-group">
					<label>EMPLOYEE_CODE</label> <select
						class="form-control modal-combobox" name="EDITEMPLOYEE_CODE"
						id="EDITEMPLOYEE_CODE">
						<c:forEach var="COA" items="${empList}">
							<option value="${COA.EMPLOYEE_CODE}">${COA.EMPLOYEE_NAME}
								(${COA.EMPLOYEE_CODE})</option>
						</c:forEach>

					</select>
				</div>
				<div class="form-group">
					<label>REGION_CODE</label> <select class="form-control"
						name="EDITREGION_CODE" id="EDITREGION_CODE"
						onchange="return getEditAccountCenter()">

						<c:forEach var="region" items="${regionlist}">
							<option value="${region.REGION_CODE}">${region.DESCRIPTION}(${region.REGION_CODE})</option>
						</c:forEach>

					</select>
				</div>
				<div class="form-group">
					<label>ACC_CEN_CODE</label> <select class="form-control"
						name="EDITACC_CEN_CODE" id="EDITACC_CEN_CODE"
						onchange="return getEditCC()">

					</select>
				</div>
				<div class="form-group">
					<label>CC_CODE</label> <select class="form-control"
						name="EDITCC_CODE" id="EDITCC_CODE">

					</select>
				</div>
				<div class="form-group">
					<label>LOCK_FLAG</label> <input type="radio" name="EDITLOCK_FLAG"
						value="Y" class="EDITLOCK_FLAG"> Yes <input type="radio"
						name="EDITLOCK_FLAG" value="N" class="EDITLOCK_FLAG"
						checked="checked"> No

				</div>

				<div class="form-group">
					<label>SUPER_FLAG</label> <input type="radio" name="EDITSUPER_FLAG"
						value="Y" class="EDITSUPER_FLAG"> Yes <input type="radio"
						name="EDITSUPER_FLAG" value="N" class="EDITSUPER_FLAG" checked>
					No

				</div>
				<div class="form-group">
					<label>DISABLE_FLAG</label> <input type="radio"
						name="EDITDISABLE_FLAG" value="Y" class="EDITDISABLE_FLAG">
					Yes <input type="radio" name="EDITDISABLE_FLAG" value="N"
						class="EDITDISABLE_FLAG" checked> No

				</div>


				<div class="form-group">
					<label>ROLE_CODE</label> <select class="form-control"
						name="EDITROLE_CODE" id="EDITROLE_CODE">
						<c:forEach var="role" items="${rolelist}">
							<option value="${role.ROLE_CODE}">${role.DESCRIPTION}
								(${role.ROLE_CODE})</option>
						</c:forEach>

					</select>
				</div>


				<div class="form-group">
					<label>USER_LEVEL</label> <select class="form-control"
						name="EDITUSER_LEVEL" id="EDITUSER_LEVEL">

						<option value="1">SUPER</option>
						<option value="2">Region</option>
						<option value="3">ACCOUNT</option>
						<option value="4">CC</option>

					</select>
				</div>

				<div class="form-group">
					<label>Module</label> <select class="form-control"
						name="EDITMODULE" id="EDITMODULE">
						<option value="B">Both</option>
					</select>
				</div>

			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<a class="btn btn-primary" onclick="return updateUser()">Save
					changes</a>
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
				<h4 class="modal-title" id="myModalLabel">User delete</h4>
			</div>
			<div class="modal-body">
				<p>Are you sure you want to delete this data?</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
				<button type="button" class="btn btn-primary" onclick="return del()">Yes</button>
			</div>
		</div>
	</div>
</div>
<!-- /.content-wrapper -->



<!--start dialog of change password of user-->

<!-- edit modal -->
<div class="modal fade" id="changePassModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Change Password-</h4>
				<h4 id="pcuser"></h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label for="name">New Password</label> <input type="password"
						class="form-control" id="newpass"> <span
						class="matchpass1"></span>
				</div>
				<div class="form-group">
					<label>Re- Password </label> <input type="password"
						class="form-control" id="newpassmatch"> <span
						class="matchpass1"></span>
				</div>
			</div>
			<span id="updatepass"></span>

			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<a class="btn btn-primary" onclick="return changePassword()">Change</a>
			</div>
		</div>
	</div>
</div>
