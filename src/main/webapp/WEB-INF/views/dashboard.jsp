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

<link href="<c:url value="/resources/adminltd/css/Chart.css" />"
	rel="stylesheet">
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
					<li><a href="#">Report</a></li>
					<li class="active">Ledger</li>
				</ol>




			</section>

			<!-- Main content -->
			<section class="content">
				<div class="row">
					<div class="col-md-6">
						<!-- AREA CHART -->
						<div class="box box-primary">
							<div class="box-header with-border">
								<h3 class="box-title">Team Trouble Tickets</h3>

								<div class="box-tools pull-right">
									<button type="button" class="btn btn-box-tool"
										data-widget="collapse">
										<i class="fa fa-minus"></i>
									</button>
									<button type="button" class="btn btn-box-tool"
										data-widget="remove">
										<i class="fa fa-times"></i>
									</button>
								</div>
							</div>
							<div class="box-body">
							    
        <!--//Div that will hold the pie chart-->
							
								<div class="chart">
									<canvas id="areaChart" style="height: 174px; width: 348px;" height="217" width="435"></canvas>
								</div>
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->

						
					</div>
					
						<div class="col-md-6">
						
						<div class="box box-primary">
							<div class="box-header with-border">
								<h3 class="box-title">Service Wise Trouble Ticket</h3>

								<div class="box-tools pull-right">
									<button type="button" class="btn btn-box-tool"
										data-widget="collapse">
										<i class="fa fa-minus"></i>
									</button>
									<button type="button" class="btn btn-box-tool"
										data-widget="remove">
										<i class="fa fa-times"></i>
									</button>
								</div>
							</div>
							<div class="box-body">
							    
        <!--//Div that will hold the pie chart-->
							
								<div class="chart">
									<canvas id="pieChart" style="height: 174px; width: 348px;" height="217" width="435"></canvas>
								</div>
							</div>
							<!-- /.box-body -->
						</div>
			
				</div>
				
				
<!-- 				2nd row  -->
<div class="col-md-9">
<div class="box">
					
<!-- 					<div id="barprtoverlay" class="overlay"> -->
<!-- 								<i class="fa fa-refresh fa-spin"></i> -->
<!-- 							</div>	 -->
						<div class="box box-danger">
							<div class="box-header with-border">
								<h3 class="box-title">Solved VS Unsolved</h3>

								<div class="box-tools pull-right">
									<button type="button" class="btn btn-box-tool"
										data-widget="collapse">
										<i class="fa fa-minus"></i>
									</button>
									<button type="button" class="btn btn-box-tool"
										data-widget="remove">
										<i class="fa fa-times"></i>
									</button>
								</div>
							</div>
							<div class="box-body">
							    
        <!--//Div that will hold the pie chart-->
							
								<div class="chart">
									<canvas id="barmonthly" style="height: 174px; width: 348px;" height="217" width="435"></canvas>
									<progress id="animationProgress" max="10" value="0" style="width: 100%"></progress>
								</div>
							</div>
							<!-- /.box-body -->
						</div>
			
				</div>
				</div>
<!-- close 2nd row -->
					
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

	<%--         <jsp:include page="${request.contextPath}/cashsale/dialog"></jsp:include> --%>

	<jsp:include page="${request.contextPath}/footJS"></jsp:include>

	 
	 	<script src="<c:url value="/resources/adminltd/js/Chart.bundle.js" />"></script>
	 
	
	 
	
   	<script src="<c:url value="/resources/function/Dashboard/srvwiserevenue.js" />"></script>
<%-- 	<script src="<c:url value="/resources/adminltd/js/commonajax.js" />"></script> --%>
</body>
</html>
