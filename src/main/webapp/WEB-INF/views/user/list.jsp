<%@page session="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="home" value="/" scope="request" />



<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Nepal Telecom | ${fx}</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<jsp:include page="${request.contextPath}/headCss"></jsp:include>
<style>
.example-modal .modal {
	position: relative;
	top: auto;
	bottom: auto;
	right: auto;
	left: auto;
	display: block;
	z-index: 1;
}

.example-modal .modal {
	background: transparent !important;
}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<header class="main-header">
			<jsp:include page="${request.contextPath}/topmenu"></jsp:include>
		</header>
		<!-- Left side column. contains the logo and sidebar -->
		<aside class="main-sidebar">
			
					<jsp:include page="${request.contextPath}/leftmenu"></jsp:include>
				
				
		</aside>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>User Management</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li><a href="#">Tables</a></li>
					<li class="active">Data tables</li>

				</ol>
				<a href="#" class="btn btn-primary pull-right" data-toggle="modal"
					data-target="#myModal"> <i class="fa fa-plus"></i> Add
				</a>
			</section>

			<!-- Main content -->
			<section class="content">
				<div class="row">
					<div class="col-xs-12">

						<!-- /.box -->

						<div class="box">
							<div class="box-header">
								<h3 class="box-title">${fx}</h3>
							</div>

							<%
								if (request.getParameter("sucess") != null) {
							%>>
							<div class="alert alert-success">
								<strong> <%=request.getParameter("sucess")%>
								</strong>
							</div>
							<%
								}
							%>
							<%
								if (request.getParameter("error") != null) {
							%>>
							<div class="alert alert-danger">
								<strong> <%=request.getParameter("error")%>
								</strong>
							</div>
							<%
								}
							%>

							<!-- /.box-header -->
							<div class="box-body">
								<table id="userlist" class="table table-bordered table-striped">
									<thead>
										<tr>
											<th>USER_ID</th>
											<th>FULL_NAME</th>
											<th>EMP_CODE</th>										
											<th>Delete</th>
											<th>SUPER</th>
											<th>DISABLE</th>
											
											<th>LOCATION</th>
											
											<th>USER</th>
											
											<th>ROLE</th>
											<th>OFFICE</th>
											
											<th>MOBILE</th>
											<th>Edit</th>
											<th>Delete</th>

										</tr>
									</thead>
									<tbody>
										<c:forEach var="user" items="${data_list}">

											<tr>
												
												<td>${user.getUSER_ID()}</td>

												<td>${user.getFULL_NAME()}</td>
												<td>${user.getEMPLOYEE_CODE()}</td>

												<td>${user.getLOCK_FLAG()}</td>
												<td>${user.getSUPER_FLAG()}</td>

												<td>${user.getDISABLE_FLAG()}</td>
												
												<td>${user.getLOCATION_CODE()}</td>
												
												<td>${user.getUSER_LEVEL()}</td>
												
												<td>${user.getROLE_CODE()}</td>
												
												
												<td>${user.getOFFICE()}</td>

												<td>${user.getMOBILE_NO()}</td>
												
											
												<td>

													<div class="btn-group">

														<a href="#" class="btn btn-info" data-toggle="modal"
															data-target="#changePassModal"
															onclick="return getHold('${user.getUSER_ID()}')">
															Pass Change </a> <a href="#" class="btn btn-info"
															data-toggle="modal" data-target="#editModal"
															onclick="return editUser('${user.getUSER_ID()}','${user.getOFFICE_CODE()}')"> <i
															class="fa fa-edit"></i> Edit
														</a>
													</div>
												</td>


												<td>

													<div>
														<a href="#" class="btn btn-default" data-toggle="modal"
															data-target="#deleteModal"
															onclick="return deleteUser('${user.getUSER_ID()}')">
															<i class="fa fa-trash"></i> Delete
														</a>
													</div>


												</td>

											</tr>
										</c:forEach>

									</tbody>

								</table>
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<footer class="main-footer">
			<jsp:include page="${request.contextPath}/footer"></jsp:include>
		</footer>

		<!-- Control Sidebar -->
		<aside class="control-sidebar control-sidebar-dark">
			<!-- Create the tabs -->
			<ul class="nav nav-tabs nav-justified control-sidebar-tabs">
				<li><a href="#control-sidebar-home-tab" data-toggle="tab"><i
						class="fa fa-home"></i></a></li>
				<li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i
						class="fa fa-gears"></i></a></li>
			</ul>

		</aside>
		<!-- /.control-sidebar -->
		<!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
		<div class="control-sidebar-bg"></div>
	</div>
	<!-- ./wrapper -->

	<jsp:include page="${request.contextPath}/dialoguser?a=1"></jsp:include>
	<jsp:include page="${request.contextPath}/footJS"></jsp:include>

	<script>
		$(function() {
		
			 $('#userlist').DataTable( {
			        "scrollX": true
			    } );
			 $("#EMPLOYEE_CODE,#EDITEMPLOYEE_CODE,#EDITOFFICE_CODE,#OFFICE_CODE").select2();
			 
			 //$('select:not(.normal)').each(function () {
	            //    $(this).select2({
	         //           dropdownParent: $(this).parent()
	            //    });
	           // });
			
		})
	</script>
	<script src="<c:url value="/resources/function/user.js?a=156" />"></script>

</body>
</html>
