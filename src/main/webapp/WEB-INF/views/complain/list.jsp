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
				<h1>Complain Management</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li><a href="#">Complain</a></li>
					<li class="active">New Complain</li>

				</ol>
			</section>

			<!-- Main content -->
			<section class="content">
				<div class="row">
					<div class="col-xs-12">

						<!-- /.box -->

						<div class="box">
							<div class="overlay">
								<i class="fa fa-refresh fa-spin"></i>
							</div>
							<div class="box-header with-border">
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



								<div class="row with-border">
									<div class="col-md-2">
										<div class="form-group">
											<label>Select Your Information</label> <select id="infotype"
												class="form-control " style="width: 100%;">
												<option value="contactNum">Contact No.</option>
												<option value="cpeSN">CPE Serial No.</option>
												<option value="custId">Customer ID</option>
												<option value="ftthDataNum">FTTH Data No.</option>
												<option value="ftthVoiceNum">FTTH Voice No.</option>
											</select>
										</div>

									</div>
									<!-- /.col -->
									<div class="col-md-3">
										<label>Enter Valid Information</label>
										<div class="input-group">
											<input id="info" type="text" class="form-control"> <span
												class="input-group-btn">
												<button onclick="getCustomerInfo()" type="button"
													class="btn btn-info btn-flat">Go!</button>
											</span>
										</div>

									</div>

									<div class="col-md-5">
										<label id="customerlabel">Customer Name : </label> <span
											id="customerName"></span> <BR> <label id="customerlabel">Contact
											No :</label> <span id="ContantNum"></span><BR> <label
											id="customerlabel">Address :</label> <span id="Address"></span>
									</div>
									<div class="col-md-2">
									
										<button type="button" class="btn btn-block btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
											<i class="fa fa-edit"></i>Register Complain
										</button>

									</div>

									<!-- 									<div class="col-md-4"> -->
									<!-- 										<label id="customerlabel">Address</label> <span -->
									<!-- 											class="form-control" id="Address"></span> -->
									<!-- 									</div> -->




									<!-- /.col -->
								</div>

								<!--           second row -->
								<div id="divcustomerinfo" hidden="true">
								
								
								
								
									<!-- opening of new row -->
									<div class="col-md-12">
						<!-- AREA CHART -->
						<div class="box box-primary">
							<div class="box-header with-border">
								<h3 class="box-title"><i
												class="fa fa-list-alt"></i>Status Information</h3>

								<div class="box-tools pull-right">
									<button type="button" class="btn btn-box-tool" data-widget="collapse">
										<i class="fa fa-minus"></i>
									</button>
									<button type="button" class="btn btn-box-tool" data-widget="remove">
										<i class="fa fa-times"></i>
									</button>
								</div>
							</div>
							<div   class="box-body">
							<label>onuStatus:</label>  <span id="onuStatus"></span>
							<label>onuRxPower:</label>  <span id="onuRxPower"></span>
							<label>onuOltRxPower:</label><span id="onuOltRxPower"></span>
							<label>onuDistance:</label><span id="onuDistance"></span>
							<label>onuTemprature:</label><span id="onuTemprature"></span>
							
							
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->

						
					</div>								



								</div>
								<!-- 							div	customer info -->

							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->
					</div>
					<!-- /.col -->
			<div class="col-md-12">
						<!-- AREA CHART -->
						<div class="box box-primary">
							<div class="box-header with-border">
								<h3 class="box-title"><i
												class="fa fa-calendar"></i>Subscription Information</h3>

								<div class="box-tools pull-right">
									<button type="button" class="btn btn-box-tool" data-widget="collapse">
										<i class="fa fa-minus"></i>
									</button>
									<button type="button" class="btn btn-box-tool" data-widget="remove">
										<i class="fa fa-times"></i>
									</button>
								</div>
							</div>
							<div id="divforsubsinfo"  class="box-body">
							
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->

						
					</div>
			
					
					
					
			
			<div class="col-md-12">
						<!-- AREA CHART -->
						<div class="box box-primary collapsed-box">
							<div class="box-header with-border">
								<h3 class="box-title"><i
												class="fa fa-info-circle"></i>CPE / FAP Information</h3>

								<div class="box-tools pull-right">
									<button type="button" class="btn btn-box-tool" data-widget="collapse">
										<i class="fa fa-plus"></i>
									</button>
									<button type="button" class="btn btn-box-tool" data-widget="remove">
										<i class="fa fa-times"></i>
									</button>
								</div>
							</div>
							<div class="box-body">
							    <div class="col-md-4">
     		
						
						
						 <table class="table table-condensed">
												<tbody>
													<tr>
														<td><label>cpeMac </label></td>
														<td><span id="cpeMac"></span></td>
													</tr>
													<tr>
														<td><label>cpeSN </label></td>
														<td><span id="cpeSN"></span></td>
													</tr>
													<tr>
														<td><label>fapName </label></td>
														<td><span id="fapName"></span></td>
													</tr>
													<tr>
														<td><label>fapPortName </label></td>
														<td><span id="fapPortName"></span></td>
													</tr>
													<tr>
														<td><label>fapPortSpec </label></td>
														<td><span id="fapPortSpec"></span></td>
													</tr>
													<tr>
														<td><label>fapSerialNumber </label></td>
														<td><span id="fapSerialNumber"></span></td>
													</tr>

												</tbody>
											</table>
						
												
							</div>
							<div class="col-md-4">
													
						 <table class="table table-condensed">
												<tbody>
													<tr>
														<td><label>distribCblName </label></td>
														<td><span id="distribCblName"></span></td>
													</tr>
													<tr>
														<td><label>distribCoreNo </label></td>
														<td><span id="distribCoreNo"></span></td>
													</tr>
													<tr>
														<td><label>distribPortNo </label></td>
														<td><span id="distribPortNo"></span></td>
													</tr>
													<tr>
														<td><label>fdcName</label></td>
														<td><span id="fdcName"></span></td>
													</tr>
													<tr>
														<td><label>feederCblName </label></td>
														<td><span id="feederCblName"></span></td>
													</tr>
													<tr>
														<td><label>feederCoreNo </label></td>
														<td><span id="feederCoreNo"></span></td>
													</tr>

												</tbody>
											</table>
							
							</div>
							
							<div class="col-md-4">
													
						 <table class="table table-condensed">
												<tbody>
													<tr>
														<td><label>Fap Location </label></td>
														<td><span id="faplocation"></span></td>
													</tr>
													<tr>
														<td><label>Longitude </label></td>
														<td><span id="Longitude"></span></td>
													</tr>
														<tr>
														<td><label>Latitude </label></td>
														<td><span id="Latitude"></span></td>
													</tr>	

												</tbody>
											</table>
							
							</div>
							
							
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->

						
					</div>
					
					
					
<!-- 					cols -->

	<div class="col-md-12">
						<!-- AREA CHART -->
						<div class="box box-primary collapsed-box">
							<div class="box-header with-border">
								<h3 class="box-title"><i
												class="fa fa-exchange"></i>OLT/OLE Information</h3>

								<div class="box-tools pull-right">
									<button type="button" class="btn btn-box-tool" data-widget="collapse">
										<i class="fa fa-plus"></i>
									</button>
									<button type="button" class="btn btn-box-tool" data-widget="remove">
										<i class="fa fa-times"></i>
									</button>
								</div>
							</div>
							<div class="box-body">
							    <div class="col-md-6">
							     <table class="table table-condensed">
												<tbody>
													<tr>
														<td><label>feederPortNo</label></td>
														<td><span id="feederPortNo"></span></td>
													</tr>
													<tr>
														<td><label>l1SplitterNo </label></td>
														<td><span id="l1SplitterNo"></span></td>
													</tr>
													<tr>
														<td><label>odfInPortOdfOutPort </label></td>
														<td><span id="odfInPortOdfOutPort"></span></td>
													</tr>
													<tr>
														<td><label>odfInfo </label></td>
														<td><span id="odfInfo"></span></td>
													</tr>
													<tr>
														<td><label>odfName </label></td>
														<td><span id="odfName"></span></td>
													</tr>
													<tr>
														<td><label>odfNo </label></td>
														<td><span id="odfNo"></span></td>
													</tr>

												</tbody>
											</table>
       					
							</div>
							<div class="col-md-6">
							<table class="table table-condensed">
												<tbody>
													<tr>
														<td><label>oltId</label></td>
														<td><span id="oltId"></span></td>
													</tr>
													<tr>
														<td><label>oltInfo </label></td>
														<td><span id="oltInfo"></span></td>
													</tr>
													<tr>
														<td><label>oltName </label></td>
														<td><span id="oltName"></span></td>
													</tr>
													<tr>
														<td><label>oltType </label></td>
														<td><span id="oltType"></span></td>
													</tr>

												</tbody>
											</table>
							
							</div>
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->

						
					</div>
					
					
<!-- 					team row -->


	<div class="col-md-12">
						<!-- AREA CHART -->
						<div class="box box-primary collapsed-box">
							<div class="box-header with-border">
								<h3 class="box-title"><i
												class="fa fa-users"></i>Team Information</h3>

								<div class="box-tools pull-right">
									<button type="button" class="btn btn-box-tool" data-widget="collapse">
										<i class="fa fa-plus"></i>
									</button>
									<button type="button" class="btn btn-box-tool" data-widget="remove">
										<i class="fa fa-times"></i>
									</button>
								</div>
							</div>
							<div class="box-body">
							    <div class="col-md-6">
							     <table class="table table-condensed">
												<tbody>
													<tr>
														<td><label>Team Name</label></td>
														<td><span id="teamName"></span></td>
													</tr>
													<tr>
														<td><label>Team SuperVisor Name </label></td>
														<td><span id="teamSupervisorName"></span></td>
													</tr>
													<tr>
														<td><label>Team SuperVisor Contact Number </label></td>
														<td><span id="teamSupervisorContactNumber"></span></td>
													</tr>
													
													

												</tbody>
											</table>
       					
							</div>
							<div class="col-md-6">
							<table class="table table-condensed">
												<tbody>
													<tr>
														<td><label>Team Leader </label></td>
														<td><span id="teamleaderName"></span></td>
													</tr>
													<tr>
														<td><label>Team Leader Contact Number </label></td>
														<td><span id="teamleaderContactNumber"></span></td>
													</tr>

												</tbody>
											</table>
							
							</div>
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->

						
					</div>

			
<!-- 	  end				team row -->
			
			
			
			
			
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

	<jsp:include page="${request.contextPath}/complain/dialog"></jsp:include>
	<jsp:include page="${request.contextPath}/footJS"></jsp:include>
	<script
		src="<c:url value="/resources/function/Complain/NewComplain.js" />"></script>
	<script src="<c:url value="/resources/adminltd/js/commonajax.js" />"></script>



</body>
</html>
