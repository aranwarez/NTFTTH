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

<jsp:include page="${request.contextPath}/footJS"></jsp:include>
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
				<h1>${fx}</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li><a href="#">Tables</a></li>
					<li class="active">Data tables</li>

				</ol>

			</section>
			
			<!-- Main content -->
			<section class="content">



				<div class="row">


					<div class="col-xs-2">
						<select id="REGION_CODE" onchange="return getZone()"
							class="form-control">
							<option value="">Select Region</option>
							<c:forEach var="user3" items="${regionlist}">
								<c:if test="${user3.ACTIVE_STATUS == 'Y'}">
									<option value="${user3.REGION_CODE}">${user3.DESCRIPTION}</option>
								</c:if>
							</c:forEach>

						</select>

					</div>


					<div class="col-xs-2">
						<select id="ZONE_CODE" onchange="return getDistrict()"
							class="form-control">
							<option value="">Select Zone</option>


						</select>

					</div>

					<div class="col-xs-2">
						<select id="DISTRICT_CODE" onchange="return getOffice()"
							class="form-control">
							<option value="">Select District</option>



						</select>

					</div>


					<div class="col-xs-2">
						<select id="OFFICE_CODE" onchange="return getOLT()"
							class="form-control">
							<option value="">Select Office</option>


						</select>

					</div>



					<div class="col-xs-2">
						<select id="OLT_CODE" onchange="return getEditMode()"
							class="form-control">
							<option value="">Select Olt</option>
						</select>

					</div>
					<div class="col-xs-2">
						<select id="SERVICE_TYPE"
							class="form-control">
							<option value="">Select Service Type</option>
							<c:forEach var="user3" items="${servicetypelist}">
								<c:if test="${user3.ACTIVE_STATUS == 'Y'}">
									<option value="${user3.SERVICE_TYPE_ID}">${user3.DESCRIPTION}</option>
								</c:if>
							</c:forEach>

						</select>

					</div>

					<c:forEach var="DAT" items="${Date_list}">

						<div class="col-xs-2">

							<input type="text" value="${DAT.NEP_FROM_DATE}"
								class="nepali-calendar form-control" name="QFROM_DT"
								id="QFROM_DT" placeholder="From Date">
						</div>
						<div class="col-xs-2">

							<input type="text" value="${DAT.NEP_TODAY_DATE}"
								class="nepali-calendar form-control" name="QTO_DT" id="QTO_DT"
								placeholder="To Date">

						</div>


					</c:forEach>
					
					
						<div class="col-xs-2">
						<select id="SUBTEAMCODE"
							class="form-control">
							<option value="">Select Team</option>
							<c:forEach var="user3" items="${userteamlist}">
									<option value="${user3.SUB_TEAM_CODE}">${user3.SUB_TEAM_CODE}</option>
							</c:forEach>

						</select>

					</div>
		<div class="col-xs-2">
						<select id="Statusflag"
							class="form-control">
									<option value="N">Unsolved</option>
									<option value="Y">Solved</option>
									<option value="">ALL</option>
							
						</select>

					</div>
						



					<div class="col-xs-3">
						<div class="align-middle">
							<button type="submit" class="btn btn-primary"
								onclick="return fetchView()">View</button>
						</div>
					</div>

					<div class="col-xs-3"></div>



					<div class="col-xs-12">
						<!-- /.box -->

						<div class="box">
							<div class="box-header">
								<h3 class="box-title">List of All Tickets</h3>
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

							<div class="overlay">
								<i class="fa fa-refresh fa-spin"></i>
							</div>


							<!-- /.box-header -->
							<div class="box-body ">
								
								
								<table id="example1" class="table table-bordered table-striped">
									<thead>
										<tr>
											<th>TOKEN</th>
											<th>Type</th>
											<th>SubTeam</th>											
											<th>Problem</th>
											<th>FDC</th>
											<th>Complain No</th>
											<th>Customer</th>
											<th>Forward</th>
											<th>Resolved</th>
											<th>Info</th>
											
	
										</tr>
									</thead>
									<tbody>
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

	<jsp:include page="${request.contextPath}/troubleticket/dialog"></jsp:include>


	<script
		src="<c:url value="/resources/function/Complain/TroubleTickets.js" />"></script>
	<script src="<c:url value="/resources/adminltd/js/commonajax.js" />"></script>


</body>
</html>
