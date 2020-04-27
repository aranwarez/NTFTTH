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
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<header class="main-header">
			<jsp:include page="${request.contextPath}/topmenu"></jsp:include>
		</header>
		<!-- Left side column. contains the logo and sidebar -->
		<aside class="main-sidebar">
			<!-- sidebar: style can be found in sidebar.less -->
			<section class="sidebar">
				<!-- Sidebar user panel -->
				<div class="user-panel">
					<div class="pull-left image">
						<img
							src="<c:url value="/resources/adminltd/dist/img/user2-160x160.jpg" />"
							class="img-circle" alt="User Image">
					</div>
					<div class="pull-left info">
						<p>NABIN</p>
						<a href="#"><i class="fa fa-circle text-success"></i> Online</a>
					</div>
				</div>
				<!-- search form -->
				<form action="#" method="get" class="sidebar-form">
					<div class="input-group">
						<input type="text" name="q" class="form-control"
							placeholder="Search..."> <span class="input-group-btn">
							<button type="submit" name="search" id="search-btn"
								class="btn btn-flat">
								<i class="fa fa-search"></i>
							</button>
						</span>
					</div>
				</form>
				<!-- /.search form -->
				<!-- sidebar menu: : style can be found in sidebar.less -->
				<ul class="sidebar-menu" data-widget="tree">
					<li class="header">MAIN NAVIGATION</li>
					<jsp:include page="${request.contextPath}/leftmenu"></jsp:include>
				</ul>
			</section>
			<!-- /.sidebar -->
		</aside>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>Employee Entry</h1>
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
							 <div class="col-xs-12 table-responsive">
								  <table class="table table-striped example1" id="example1">
									<thead>
										<tr>
										 <th>EMPLOYEE_CODE </th>
                                        <th>EMPLOYEE_NAME </th>
                                        <th>ADDRESS </th>                                    
                                        <th>SEX </th>
                                        <th>MARITAL_STATUS </th>
                                        <th>DOB </th>
                                        <th>DOJ </th>                                       
                                        <th>QUALIFICATION </th>
                                        <th>DISABLE_FLAG </th>
                                        <th>TEL_NO </th>
                                        <th>MOBILE_NO </th>
                                        <th>EMAIL </th>
                                        <th>EMP_NO </th>
                                        <th>TTC_NO </th>
                                        <th>POST_CD </th>
                                        <th>DEPT_CD </th>
                                        <th> LOCATION_CD </th>
                                        <th> EMP_LEVEL</th>
                                        <th> EMP_TYPE </th>
                                        <th> EMP_TITLE </th>
                                        <th>Edit</th>
                                        <th>Delete</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="user" items="${employeelist}">

											<tr>
											
												<td>${user.EMPLOYEE_CODE}</td>												
												<td>${user.EMPLOYEE_NAME}</td>
												<td>${user.ADDRESS}</td>
												<td>${user.SEX}</td>
												<td>${user.MARITAL_STATUS}</td>
												<td>${user.DOB}</td>
												<td>${user.DOJ}</td>
												<td>${user.QUALIFICATION}</td>
												<td>${user.DISABLE_FLAG}</td>
												
												
												<td>${user.TEL_NO}</td>
												
												
												<td>${user.MOBILE_NO}</td>
												
												
												
												<td>${user.EMAIL}</td>
												
												<td>${user.EMP_NO}</td>
												<td>${user.TTC_NO}</td>
												
												<td>${user.POST_CD}</td>
												<td>${user.DEPT_CD}</td>
												<td>${user.LOCATION_CD}</td>
												<td>${user.EMP_LEVEL}</td>
												<td>${user.EMP_TYPE}</td>
												<td>${user.EMP_TITLE}</td>
												<td>${user.DOB}</td>
												<td>${user.DOB}</td>
												
												<td>
													<div class="btn-group">
														<a href="#" class="btn btn-info" data-toggle="modal"
															data-target="#editModal"
															onclick="return editEmployee'${user.EMPLOYEE_CODE}')">
															<i class="fa fa-edit"></i> Edit
														</a>
													</div>
												</td>
												<td>
													<div>
														<a href="" class="btn btn-default" data-toggle="modal"
															data-target="#deleteModal"
															onclick="return delAccCenter'${user.EMPLOYEE_CODE}')">
															<i class="fa fa-trash"></i> Delete
														</a>
													</div>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								</div>
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

	<jsp:include page="${request.contextPath}/dialogemployee"></jsp:include>
	<jsp:include page="${request.contextPath}/footJS"></jsp:include>

	<script>
		$(function() {

			   $.fn.dataTable.ext.errMode = 'none';
		        jQuery.ajaxSetup({async: false});
		        officelist();
		        getlist();
		  
		        var table = $('#example1').DataTable();
		        table.clear().draw();

		     

		})
	</script>
	<script src="<c:url value="/resources/function/employee.js" />"></script>


</body>
</html>
